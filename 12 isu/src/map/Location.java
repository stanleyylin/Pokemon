package map;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.*;

import entity.NPC;
import main.Driver2;

import java.awt.*;

public class Location {
	
	private BufferedImage background;
	private Rectangle[] collisions;
	private Building[] locations;
	private Character[] characters;
	private boolean xEdgeReached;
	private boolean yEdgeReached;
	
	public final int maxX;
	public final int maxY;
	
	private int lastX;
	private int lastY;
	private int cameraX;
	private int cameraY;
	
	public Location(BufferedImage bg, Rectangle[] collisions, Building[] locations, Character[] characters, int[][] startingPoints)
	{
		background = bg;
		this.collisions = collisions;
		this.locations = locations;
		this.characters = characters;
		maxX = background.getWidth();
		maxY = background.getHeight();
	}
	
	public void drawNPC(Graphics2D g2)
	{

	}
	
	public BufferedImage getBG()
	{
		return background;
	}
	public Rectangle[] getCollisions()
	{
		return collisions;
	}
	public Building[] getBuildings()
	{
		return locations;
	}
	public Character[] getChars()
	{
		return characters;
	}
	public boolean getEdgeReachedX()
	{
		return xEdgeReached;
	}
	public boolean getEdgeReachedY()
	{
		return yEdgeReached;
	}
	
	public int[] getLastPosition()
	{
		int[] coordinates = new int[4];
		coordinates[0] = lastX;
		coordinates[1] = lastY;
		coordinates[2] = cameraX;
		coordinates[3] = cameraY;
		return coordinates;
	}
	
	public void setLastPosition(int x, int y, int cameraX, int cameraY)
	{
		lastX = x;
		lastY = y;
		this.cameraX = cameraX;
		this.cameraY = cameraY;
	}
	public void setEdgeReachedX(boolean edge)
	{
		xEdgeReached = edge;
	}
	public void setEdgeReachedY(boolean edge)
	{
		yEdgeReached = edge;
	}
}
