package entity;

import java.io.IOException;

import getimages.LoadImage;
import getimages.SpriteSheet;

import java.awt.image.BufferedImage;

public class Player extends Entity {
	
	private BufferedImage spriteSheet;
	private BufferedImage[] sprites;
	public final static int size = 50;
	public final int speed;
	
	public Player(int screenX, int screenY)
	{
		this.screenX = screenX;
		this.screenY = screenY;
		this.speed = 3;
		
		direction = "down";
		
		LoadImage loader = new LoadImage();
		
		try
		{
			spriteSheet = loader.loadImage("res/char1.png");
		}
		catch(IOException e) {}
		
		SpriteSheet player = new SpriteSheet(spriteSheet, 3, 4);
		sprites = player.getSprites(32, 32);
	}
	
	public BufferedImage[] getSprites()
	{
		return sprites;
	}
	
	public void setScreenX(int set)
	{
		screenX = set;
	}
	public void setScreenY(int set)
	{
		screenY = set;
	}

		
}
