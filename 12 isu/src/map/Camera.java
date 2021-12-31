package map;
import java.awt.*;
import java.awt.image.BufferedImage;

import main.Driver2;

public class Camera {
	
	private int worldX;
	private int worldY;
	private BufferedImage location;
	
	private final int screenWidth = Driver2.screenWidth;
	private final int screenHeight = Driver2.screenHeight;
	
	public Camera(int worldX, int worldY, BufferedImage starting)
	{
		this.worldX = worldX;
		this.worldY = worldY;
		location = starting;
	}
	
	// Drawing the terrain
	public void draw(Graphics2D g2)
	{
		g2.drawImage(location, 0, 0, screenWidth, screenHeight, worldX, worldY, worldX+screenWidth, worldY+screenHeight, null);
	}
	
	// Getters and Setters
	public int getX()
	{
		return worldX;
	}
	public int getY()
	{
		return worldY;
	}	
	public void setX(int X)
	{
		worldX = X;
	}
	public void setY(int Y)
	{
		worldY = Y;
	}

}
