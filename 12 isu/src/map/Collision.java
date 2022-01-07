package map;

import entity.Player;

public class Collision {
	
	private int x;
	private int y;
	private int width;
	private int height;
	
	public Collision(int x, int y, int width, int height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public int collidesX(int playerX, int playerY, int playerWidth, int playerHeight)
	{
		int playerTop = playerY;
		int playerBottom = playerY + playerHeight;
		int playerLeft = playerX;
		int playerRight = playerX + playerWidth;
		int wallLeft = x;
		int wallRight = x+width;
		int wallTop = y;
		int wallBottom = y + height;
		
		if(playerRight > wallLeft && playerLeft < wallLeft && playerRight - wallLeft < playerBottom - playerTop && 
		playerRight - wallLeft < wallBottom - wallTop)
		{
		    return x - playerWidth;
		}
		else if(playerLeft < wallRight && playerRight > wallRight && wallRight - playerLeft < playerBottom - playerTop && wallRight - playerLeft < wallBottom - wallTop)
		{
			return x + width;
		}
		else
			return playerX;
		
	}
	public int collidesY(int playerX, int playerY, int playerWidth, int playerHeight)
	{
		int playerTop = playerY;
		int playerBottom = playerY + playerHeight;
		int wallTop = y;
		int wallBottom = y + height;
		
		 if(playerBottom > wallTop && playerTop < wallTop)
		 {
			 return y - playerHeight;
	     }
	     else if(playerTop < wallBottom && playerBottom > wallBottom)
	     {
	    	 return y + height;
	     }
	     else
	    	 return playerY;
	}
	public String collides(int playerX, int playerY, int playerWidth, int playerHeight)
	{
		int right = x + width;
		int bottom = y + height;
		int playerRight = playerX + playerWidth;
		int playerBottom = playerY + playerHeight;
		if(playerRight > x && playerX < x && playerRight - x < playerBottom - y && playerRight - x < bottom - playerY)
		{
			return "left";
		}
		else if(playerX < right && playerRight > right && right - playerX < playerBottom - y && right - playerX < bottom - playerY)
		{
			return "right";
		}
		else if(playerBottom > y && playerY < y)
		{
			return "top";
		}
		else if(playerY < bottom && playerBottom > bottom)
		{
			return "bottom";
		}
		
		return "no";
	}
	// Getters and Setters
	public int getWidth()
	{
		return width;
	}
	public int getHeight()
	{
		return height;
	}
	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}
	public void setWidth(int set)
	{
		width = set;
	}
	public void setHeight(int set)
	{
		height = set;
	}
	public void setX(int set)
	{
		x = set;
	}
	public void setY(int set)
	{
		y = set;
	}

}
