package entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class NPC extends Entity {
	private int worldX;
	private int worldY;
	private BufferedImage[] sprites;
	
	public NPC(int worldX, int worldY, BufferedImage[] sprites)
	{
		this.worldX = worldX;
		this.worldY = worldY;
		this.sprites = sprites;
	}
	
	public int getWorldX()
	{
		return worldX;
	}
	public int getWorldY()
	{
		return worldY;
	}
}
