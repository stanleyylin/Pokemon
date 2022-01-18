package entity;

import java.awt.image.BufferedImage;
import java.awt.*;

public class Character {
	private int worldX;
	private int worldY;
	private BufferedImage sprite;
	
	public Character(int worldX, int worldY, BufferedImage sprite)
	{
		this.worldX = worldX;
		this.worldY = worldY;
		this.sprite = sprite;
	}
	
	public void draw(Graphics2D g2, int cameraX, int cameraY)
	{
		g2.drawImage(sprite, worldX-cameraX, worldY-cameraY, null);
	}
	

}
