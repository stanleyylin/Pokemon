package entity;

import java.io.IOException;

import getimages.LoadImage;
import getimages.SpriteSheet;

import java.awt.image.BufferedImage;

public class Player extends Entity {
	
	private int moving;
	private BufferedImage spriteSheet;
	private BufferedImage[] sprites;
	public final int speed;
	
	public Player(int x, int y, int screenX, int screenY)
	{
		this.x = x;
		this.y = y;
		this.screenX = screenX;
		this.screenY = screenY;
		this.speed = 3;
		
		direction = "down";
		moving = 0;
		
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
	public int getMoving()
	{
		return moving;
	}
	public void setMoving(int moving)
	{
		this.moving = moving;
	}
	public void incrMoving()
	{
		this.moving++;
	}
		
}
