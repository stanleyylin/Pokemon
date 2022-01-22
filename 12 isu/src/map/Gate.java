package map;

import java.util.*;

import entity.Player;

import java.awt.*;

public class Gate {
	private Location l1;
	private Rectangle r1;
	
	private Location l2;
	private Rectangle r2;
	private int camX2;
	private int camY2;
	
	private int axis; // 0 - left, 1 - right, 2 - up, 3 - down
	
	public Gate(int axis, int[] coordinates, Location l1, Rectangle r1, Location l2, Rectangle r2)
	{
		camX2 = coordinates[0];
		camY2 = coordinates[1];
		
		this.axis = axis;
		this.l1 = l1;
		this.r1 = r1;
		this.l2 = l2;
		this.r2 = r2;
	}
	
	public void changeLocation(Camera c, Player p)
	{
		c.setLocation(l2);
		c.setX(camX2);
		c.setY(camY2);
		c.setBuilding(null);
		l2.setEdgeReachedX(true);
		l2.setEdgeReachedY(true);
		if(axis == 0) // gate on left --> gate on right
		{
			p.setScreenX((int) r2.getX()-c.getX()-Player.width);
			p.setScreenY((int) r2.getY()-c.getY()+(int)(r2.getHeight()/2));
		}
		else if(axis == 1) // gate on right --> gate on left
		{
			p.setScreenX((int) r2.getX()-c.getX()+(int)r2.getWidth()+Player.width);
			p.setScreenY((int) r2.getY()-c.getY()+(int)(r2.getHeight()/2));
		}
		else if(axis == 2) // gate up --> gate up
		{
			p.setScreenX((int) r2.getX()-c.getX()+(int)(r2.getWidth()/2));
			p.setScreenY((int) r2.getY()-c.getY()-Player.height);
		}
		else if(axis == 3) // gate down --> gate down
		{
			p.setScreenX((int) r2.getX()-c.getX()+(int)(r2.getWidth()/2));
			p.setScreenY((int) r2.getY()-c.getY()+(int)r2.getWidth()+Player.height);
		}
	}
	
	public Location getL1()
	{
		return l1;
	}
	public Location getL2()
	{
		return l2;
	}
	public Rectangle getR1()
	{
		return r1;
	}
	public Rectangle getR2()
	{
		return r2;
	}
}
