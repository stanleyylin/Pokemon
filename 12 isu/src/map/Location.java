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
	private NPC[] npcs;
	private boolean xEdgeReached;
	private boolean yEdgeReached;
	
	public final int maxX;
	public final int maxY;
	
	private int screenX;
	private int screenY;
	private int cameraX;
	private int cameraY;
	
	public Location(BufferedImage bg, Rectangle[] collisions, Building[] locations, NPC[] npcs, int[][] startingPoints)
	{
		background = bg;
		this.collisions = collisions;
		this.locations = locations;
		this.npcs = npcs;
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
	public NPC[] getNPCs()
	{
		return npcs;
	}
	public boolean getEdgeReachedX()
	{
		return xEdgeReached;
	}
	public boolean getEdgeReachedY()
	{
		return yEdgeReached;
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
