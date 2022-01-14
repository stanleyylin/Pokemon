package map;
import java.awt.*;
import java.awt.image.BufferedImage;

import entity.Moving;
import entity.NPC;
import main.Driver2;

public class Camera {
	
	private int worldX;
	private int worldY;
	private Location location;
	private Building building;

	private final int screenWidth = Driver2.screenWidth;
	private final int screenHeight = Driver2.screenHeight;
	
	public Camera(Location location, int worldX, int worldY)
	{
		this.location = location;
		this.worldX = worldX;
		this.worldY = worldY;
	}

//	public void checkNPC()
//	{
//		for(NPC curr : currMap.getNPCs())
//		{
//			
//		}
//	}
	
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
		{
			g2.drawImage(building.getBG(), building.screenX, building.screenY, building.maxX, building.maxY, 0, 0, building.getBG().getWidth(), building.getBG().getHeight(), null);
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
