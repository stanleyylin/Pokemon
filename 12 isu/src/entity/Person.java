package entity;

import java.awt.image.BufferedImage;
import java.io.IOException;

import getimages.LoadImage;

import java.awt.*;

public class Person {

	protected Rectangle collision;
	protected Rectangle interaction;
	protected String imageFile;
	protected BufferedImage sprite;
	protected String direction;

	protected String textFile;
	protected boolean shown;
	
	private static LoadImage loader = new LoadImage();
	
	public Person(Rectangle collision, String direction, String imageFile, int w, int h, String textFile)
	{
		this.collision = collision;
		this.direction = direction;
		this.imageFile = imageFile;
		this.textFile = textFile;
		
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
			sprite = loader.loadImage("res/sprites/" + imageFile);
			sprite = sprite.getSubimage(0, 0, w, h);
			sprite = loader.resize(sprite, w*3, h*3);
		}
		catch(IOException e) {}
	}
	
	// Invisible NPC (png is already on background)
	public Person(Rectangle collision, String direction, String textFile)
	{
		this.collision = collision;
		this.direction = direction;
		this.textFile = textFile;
		imageFile = null;
		
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

	// Super constructor for 
	public Person(Rectangle collision, String imageFile, int w, int h, String textFile)
	{
		this.collision = collision;
		this.imageFile = imageFile;
		this.textFile = textFile;
	}
	
	
	
	public boolean getShown()
	{
		return shown;
	}	
	public String getImageFile()
	{
		return imageFile;
	}	
	public String getLines()
	{
		return textFile;
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
	
	public void setShown(boolean set)
	{
		shown = set;
	}

}
