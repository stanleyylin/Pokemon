package entity;

public abstract class Entity {
	
	protected int x;
	protected int y;
	protected int screenX;
	protected int screenY;
	public String direction;
	
	public int getX() 
	{ 
		return x; 
	}
	public int getY() 
	{ 
		return y; 
	}
	
	public void deltaX(int amount)
	{
		x += amount;
	}
	public void deltaY(int amount)
	{
		y += amount;
	}
}
