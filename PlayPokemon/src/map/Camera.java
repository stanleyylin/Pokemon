package map;
import java.awt.*;
import java.awt.image.BufferedImage;

import entity.Person;
import entity.Moving;
import entity.NPC;
import entity.Person;
import main.GamePanel;

public class Camera {
	
	private int worldX; //the x coord of the world the cam is on
	private int worldY; //the y coord of the world the cam is on
	private Location location; //the location obj that the cam is looking at 
	private Building building; // the building obj that the cam is looking at

	private final int screenWidth = GamePanel.screenWidth;
	private final int screenHeight = GamePanel.screenHeight;
	
	//constructor
	public Camera(Location location, int worldX, int worldY)
	{
		this.location = location;
		this.worldX = worldX;
		this.worldY = worldY;
	}

	//loads NPC into the map or building
	void loadNPC(Person[] people)
	{
		if(building == null)
		{
			for(Person c : people)
			{
				if(c.getSprite() == null)
				{
					continue;
				}
				if(c.getC().getX() > worldX-c.getC().getWidth() && c.getC().getX() < worldX+GamePanel.screenWidth+c.getC().getWidth())
				{
					if(c.getC().getY() > worldY-c.getC().getHeight() && c.getC().getY() < worldY+GamePanel.screenHeight+c.getC().getHeight())
					{
						c.setShown(true);
					}
					else
					{
						c.setShown(false);
					}
				}
			}
		}
		else
		{
			for(Person c : people)
			{
				c.setShown(true);
			}
		}
	}
	
	// Drawing the terrain
	public void draw(Graphics2D g2)
	{
		if(building == null)
		{
			if(worldX < 0)
			{
				location.setEdgeReachedX(true);
				worldX = 0;
			}
			else if (worldX > location.maxX-screenWidth)
			{
				location.setEdgeReachedX(true);
				worldX = location.maxX-screenWidth;
			}
			
			if(worldY < 0)
			{
				location.setEdgeReachedY(true);
				worldY = 0;
			}
			else if (worldY > location.maxY-screenHeight)
			{
				location.setEdgeReachedY(true);
				worldY = location.maxY-screenHeight;
			}
		}
		
		if(building == null)
			g2.drawImage(location.getBG(), 0, 0, screenWidth, screenHeight, worldX, worldY, worldX+screenWidth, worldY+screenHeight, null);
		else
			g2.drawImage(building.getBG(), building.screenX, building.screenY, building.maxX, building.maxY, 0, 0, building.getBG().getWidth(), building.getBG().getHeight(), null);

		if(building == null)
		{
			loadNPC(location.getPeople());
			for(Person p : location.getPeople())
			{
				if(p.getShown())
				{
					g2.drawImage(p.getSprite(), (int) p.getC().getX()-worldX, (int) p.getC().getY()-worldY, null);
				}
			}
		}
		else
		{
			loadNPC(building.getPeople());
			for(Person p : building.getPeople())
			{
				if(p.getShown() && p.getSprite() != null)
				{
					g2.drawImage(p.getSprite(), (int) p.getC().getX(), (int) p.getC().getY(), null);
				}
			}
		}
	
	}
	
	// Getters and Setters
	public int getX()
	{
		return worldX;
	}
	public int getY()
	{
		return worldY;
	}	
	public Location getLocation()
	{
		return location;
	}
	public Building getBuilding()
	{
		return building;
	}
	
	public void setLocation(Location l)
	{
		this.location = l;
	}
	public void setBuilding(Building building)
	{
		this.building = building;
	}
	public void setX(int X)
	{
		worldX = X;
	}
	public void setY(int Y)
	{
		worldY = Y;
	}

}
