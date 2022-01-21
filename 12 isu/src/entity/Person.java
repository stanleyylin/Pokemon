package entity;

import java.awt.image.BufferedImage;
import java.io.IOException;

import getimages.LoadImage;

import java.awt.*;

public class Person {
	protected BufferedImage sprite;
	protected String direction;
	
	protected Rectangle collision;
	protected Rectangle interaction;
	
	protected boolean shown;
	// Dialogue
	protected String textFile;
	private static LoadImage loader = new LoadImage();
	
	public Person(int worldX, int worldY, int width, int height, String direction, String file, int w, int h)
	{
		this.direction = direction;
		
		if(direction.equals("left"))
			interaction = new Rectangle(worldX-42, worldY-15, 42, 62);
		else if(direction.equals("right"))
			interaction = new Rectangle(worldX+width, worldY-15, 42, 62);
		else if(direction.equals("up"))
			interaction = new Rectangle(worldX-15, worldY-42, 62, 42);
		else if(direction.equals("down"))
			interaction = new Rectangle(worldX-15, worldY+height, 62, 42);
		
		collision = new Rectangle(worldX, worldY, width, height);
		shown = false;
		
		try
		{
			sprite = loader.loadImage("res/sprites/" + file);
			sprite = sprite.getSubimage(0, 0, w, h);
		}
		catch(IOException e) {}
	}
	
	public void draw(Graphics2D g2, int cameraX, int cameraY)
	{
		g2.drawImage(sprite, (int) interaction.getX()-cameraX, (int) interaction.getY()-cameraY, null);
	}
	
	public boolean getShown()
	{
		return shown;
	}
	public void setShown(boolean set)
	{
		shown = set;
	}
	public Rectangle getC()
	{
		return collision;
	}
	public Rectangle getI()
	{
		return interaction;
	}
	public BufferedImage getSprite()
	{
		return sprite;
	}

}
