
public class Player {
	
	private int x;
	private int y;
	public int screenX;
	public int screenY;
	
	public String direction;
	public int moving; // 0 - rest, 1 - left foot, 2 - right foot
	
	public Player(int x, int y, int screenX, int screenY)
	{
		this.x = x;
		this.y = y;
		this.screenX = screenX;
		this.screenY = screenY;
		
		direction = "down";
		moving = 0;

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
