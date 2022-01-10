package map;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.*;

import entity.NPC;

import java.awt.*;

public class Map {
	
	private BufferedImage background;
	private Rectangle[] collisions;
	private Building[] locations;
	private NPC[] npcs;
	
	private int[][] startingPoints;

	public Map(BufferedImage bg, Rectangle[] collisions, Building[] locations, NPC[] npcs, int[][] startingPoints)
	{
		background = bg;
		this.collisions = collisions;
		this.locations = locations;
		this.npcs = npcs;
		this.startingPoints = startingPoints;
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
	public int[][] startingPoints()
	{
		return startingPoints;
	}
}
