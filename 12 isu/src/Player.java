
public class Player {
	
	private int x;
	private int y;
	public int screenX;
	public int screenY;
	
	public String direction;
	public boolean moving;
	
	public Player(int x, int y)
	{
		this.x = x;
		this.y = y;
		
		direction = "down";
		moving = false;

	}
	
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
