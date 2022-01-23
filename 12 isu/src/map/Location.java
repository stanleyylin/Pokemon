package map;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.*;

import entity.NPC;
import entity.Person;
import main.GamePanel;

import java.awt.*;

public class Location {
	
	private BufferedImage background;
	private Rectangle[] collisions;
	private Building[] buildings;
	private Person[] people;
	private boolean xEdgeReached;
	private boolean yEdgeReached;
	
	public final int maxX;
	public final int maxY;
	
	private int lastX;
	private int lastY;
	private int cameraX;
	private int cameraY;
	
	private ArrayList<Gate> gates;
	
	public Location(BufferedImage bg, Rectangle[] collisions, Building[] buildings, Person[] people)
	{
		background = bg;
		this.collisions = collisions;
		this.buildings = buildings;
		this.people = people;
		maxX = background.getWidth();
		maxY = background.getHeight();
		gates = new ArrayList<Gate>();
	}
	
	public void addGate(Gate g)
	{
		gates.add(g);
	}
	
	public ArrayList<Gate> getGates()
	{
		return gates;
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
		return buildings;
	}
	public Person[] getPeople()
	{
		return people;
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
