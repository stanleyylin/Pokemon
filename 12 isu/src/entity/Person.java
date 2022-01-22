package entity;

import java.awt.image.BufferedImage;
import java.io.IOException;

import getimages.LoadImage;

import java.awt.*;

public class Person {
	protected BufferedImage sprite;
	protected String direction;
	
	protected int worldX;
	protected int worldY;
	protected Rectangle collision;
	protected Rectangle interaction;
	
	protected boolean shown;
	// Dialogue
	protected String textFile;
	private static LoadImage loader = new LoadImage();
	
	public Person(Rectangle collision, String direction, String file, int w, int h)
	{
		this.collision = collision;
		this.direction = direction;
		
		if(direction.equals("left"))
			interaction = new Rectangle((int)collision.getX()-42, (int)collision.getY()-15, 42, 62);
		else if(direction.equals("right"))
			interaction = new Rectangle((int)collision.getX()+(int)collision.getWidth(), (int)collision.getY()-15, 42, 62);
		else if(direction.equals("up"))
			interaction = new Rectangle((int)collision.getX()-15, (int)collision.getY()-42, 62, 42);
		else if(direction.equals("down"))
			interaction = new Rectangle((int)collision.getX()-15, (int)collision.getY()+(int)collision.getHeight(), 62, 42);
		shown = false;
		
		try
		{
			sprite = loader.loadImage("res/sprites/" + file);
			sprite = sprite.getSubimage(0, 0, w, h);
			sprite = loader.resize(sprite, w*3, h*3);
		}
		catch(IOException e) {}
	}
	
	public Person(Rectangle collision, String direction)
	{
		this.collision = collision;
		this.direction = direction;
		
		if(direction.equals("left"))
			interaction = new Rectangle((int)collision.getX()-42, (int)collision.getY()-15, 42, 62);
		else if(direction.equals("right"))
			interaction = new Rectangle((int)collision.getX()+(int)collision.getWidth(), (int)collision.getY()-15, 42, 62);
		else if(direction.equals("up"))
			interaction = new Rectangle((int)collision.getX()-15, (int)collision.getY()-42, 62, 42);
		else if(direction.equals("down"))
			interaction = new Rectangle((int)collision.getX()-15, (int)collision.getY()+(int)collision.getHeight(), 62, 42);
		shown = false;
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
//	public Rectangle getI()
//	{
//		return interaction;
//	}
	public BufferedImage getSprite()
	{
		return sprite;
	}

}
