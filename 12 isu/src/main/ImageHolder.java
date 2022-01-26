package main;

import java.awt.image.BufferedImage;
import java.io.IOException;

import getimages.LoadImage;

public class ImageHolder {
	
	private int x;
	private int y;
	private float transparency;
	private boolean visible;
	private BufferedImage[] sprites;
	private int index;
	private BufferedImage curr;
	int counter = 0;
	
	//constructor
	public ImageHolder(int type)
	{
		if(type == 0)
			loadBalls(true);
		else if(type == 1)
			loadBalls(false);
		else if(type == 2)
			loadStatus(true);
		else if(type == 3)
			loadStatus(false);
		
		visible = false;
		transparency = 0.1f;
		index = 0;
		curr = sprites[0];
	}
	
	//load pokeball sprites
	public void loadBalls(boolean big)
	{
		sprites = new BufferedImage[11];
		LoadImage loader = new LoadImage();
		BufferedImage ballSprites = null;
		try
		{
			ballSprites = loader.loadImage("res/battle/pokeballs.png");
		}
		catch(IOException e) {	}
		for(int i = 0; i < 10; i++)
		{
			sprites[i] = ballSprites.getSubimage(132, 9 + 24*i + 16*i, 24, 24);
			if(big)
				sprites[i] = loader.resize(sprites[i], 48, 48);
			else
				sprites[i] = loader.resize(sprites[i], 36, 36);
		}
		sprites[10] = ballSprites.getSubimage(131, 401, 27, 36);
		if(big)
			sprites[10] = loader.resize(sprites[10], 50, 70);
		else
			sprites[10] = loader.resize(sprites[10], 41, 54);
	}
	
	//load status sprites
	public void loadStatus(boolean increase)
	{
		sprites = new BufferedImage[3];
		LoadImage loader = new LoadImage();
		for(int i = 0; i < 3; i++)
		{
			try
			{
				if(increase)
					sprites[i] = loader.loadImage("res/battle/up" + (i+1) + ".png");
				else
					sprites[i] = loader.loadImage("res/battle/down" + (i+1) + ".png");

			} catch(IOException e) {}
		}
	}
	
	public boolean getVisible()
	{
		return visible;
	}
	public void setVisible(boolean set, int x, int y)
	{
		this.x = x;
		this.y = y;
		visible = set;
	}
	
	public void incrTransparency(float amount)
	{
		transparency += amount;
		if(transparency > 1f)
			transparency = 1f;
	}
	public float getTransparency()
	{
		return transparency;
	}
	public void nextImage()
	{
		index++;
		if(index < sprites.length)
			curr = sprites[index];
	}
	public void reset()
	{
		index = 0;
		curr = sprites[index];
		visible = false;
	}
	public BufferedImage getImage()
	{
		return curr;
	}
	
	public void setX(int set)
	{
		x = set;
	}
	public void setY(int set)
	{
		y = set;
	}
	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}
	
}
