package map;

import entity.NPC;
import entity.Person;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Building {
	private Person[] people; //all people in a building
	private Rectangle[] collisions; //all collisions in a building
	
	private Rectangle entrance; //entrance collision
	private Rectangle exit; //exit collision
	
	private BufferedImage bg; //background image
	public final int maxX; 
	public final int maxY;
	public final int screenX;
	public final int screenY;
	
	private boolean xEdgeReached;
	private boolean yEdgeReached;
	
	//constructor
	public Building(Rectangle entrance, Rectangle exit, Person[] people, Rectangle[] collisions,  BufferedImage bg)
	{
		this.entrance = entrance;
		this.exit = exit;
		this.people = people;
		this.collisions = collisions;
		this.bg = bg;
		
		if(bg.getWidth() < GamePanel.screenWidth)
		{
			screenX = GamePanel.screenWidth/2 - bg.getWidth()/2;
			maxX = screenX + bg.getWidth();
			xEdgeReached = true;
		}
		else
		{
			screenX = 0;
			maxX = bg.getWidth();
			xEdgeReached = false;
		}
		
		if(bg.getHeight() < GamePanel.screenHeight)
		{
			screenY = GamePanel.screenHeight/2 - bg.getHeight()/2;
			maxY = screenY + bg.getHeight();
			yEdgeReached = true;
		}
		else
		{
			screenY = 0;
			maxY = bg.getHeight();
			yEdgeReached = false;
		}
	}
	
	public Rectangle getEntrance()
	{
		return entrance;
	}
	public Rectangle getExit()
	{
		return exit;
	}
	public BufferedImage getBG()
	{
		return bg;
	}
	public Rectangle[] getCollisions()
	{
		return collisions;
	}
	public boolean getEdgeReachedX()
	{
		return xEdgeReached;
	}
	public Person[] getPeople()
	{
		return people;
	}
	public boolean getEdgeReachedY()
	{
		return yEdgeReached;
	}
	public void setEdgeReachedX(boolean edge)
	{
		xEdgeReached = edge;
	}
	public void setEdgeReachedY(boolean edge)
	{
		yEdgeReached = edge;
	}
}
