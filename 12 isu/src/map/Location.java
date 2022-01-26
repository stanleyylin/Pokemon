package map;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

import entity.NPC;
import entity.Person;
import getimages.LoadImage;
import main.GamePanel;

import java.awt.*;

public class Location {
	//stores each town/city/route as  a location object
	
	private BufferedImage background; //background map
	private Rectangle[] collisions; //all collisions
	private Rectangle[] grass; //all grass  patches
	private Building[] buildings; //all buildings
	private Person[] people; //all people
	private boolean xEdgeReached; //if the horizontal edge is reached
	private boolean yEdgeReached; //if the vertical edge is reached
	
	public final int maxX; //max horizontal size
	public final int maxY; //max vertical size
	
	private int lastX; 
	private int lastY;
	private int cameraX; //camera x coord
	private int cameraY; //camera y coord
	
	private ArrayList<Gate> gates;
	
	//constructor
	public Location(String file, Rectangle[] collisions, Building[] buildings, Person[] people, Rectangle[] grass)
	{
		this.collisions = collisions;
		this.buildings = buildings;
		this.people = people;
		this.grass = grass;
		gates = new ArrayList<Gate>();
		
		LoadImage loader = new LoadImage();
		try
		{
			background = loader.loadImage("res/maps/" + file);
			background = loader.resize(background, background.getWidth()*3, background.getHeight()*3);
		}
		catch(IOException e) {}
		maxX = background.getWidth();
		maxY = background.getHeight();
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
	public Rectangle[] getGrass()
	{
		return grass;
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
