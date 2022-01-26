package map;

import java.util.*;

import entity.Player;

import java.awt.*;

public class Gate {
	private Rectangle r1;
	
	private Location l2;
	private int camX2;
	private int camY2;
	private boolean camXReached;
	private boolean camYReached;
	private int screenX;
	private int screenY;
	
	public Gate(int camX2, boolean camXReached, int camY2, boolean camYReached, int screenX, int screenY, Rectangle r1, Location l2)
	{
		this.camX2 = camX2;
		this.camXReached = false;
		this.camY2 = camY2;
		this.camYReached = false;
		this.screenX = screenX;
		this.screenY = screenY;
		this.r1 = r1;
		this.l2 = l2;
	}

	
	public Location getL2()
	{
		return l2;
	}
	public Rectangle getR1()
	{
		return r1;
	}
	public boolean getXEdge()
	{
		return camXReached;
	}
	public boolean getYEdge()
	{
		return camYReached;
	}
	public int getScreenX()
	{
		return screenX;
	}
	public int getScreenY()
	{
		return screenY;
	}
	public int getCamX()
	{
		return camX2;
	}
	public int getCamY()
	{
		return camY2;
	}
	
}
