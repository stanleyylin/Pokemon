package entity;

import java.awt.image.BufferedImage;
import java.awt.*;

public class Character {
	protected int worldX;
	protected int worldY;
	protected BufferedImage sprite;
	protected String direction;
	
	protected Rectangle collision;
	protected Rectangle interaction;
	
	// Dialogue
	protected String textFile;
	
	public Character(int worldX, int worldY, int width, int height, String direction, BufferedImage sprite)
	{
		this.worldX = worldX;
		this.worldY = worldY;
		this.sprite = sprite;
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
	}
	
	public void draw(Graphics2D g2, int cameraX, int cameraY)
	{
		g2.drawImage(sprite, worldX-cameraX, worldY-cameraY, null);
	}
	
	public Rectangle getCollision()
	{
		return collision;
	}
	public Rectangle getInteraction()
	{
		return interaction;
	}

}
