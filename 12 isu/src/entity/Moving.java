package entity;

import map.Camera;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Moving {
	
	private static Player main;
	private Camera camera;
	private BufferedImage[] playerSprites;
	private int spriteCounter;

	public static int moving;
	
	public Moving(Player player, Camera camera)
	{
		main = player;
		this.camera = camera;
		playerSprites = main.getSprites();
		moving = 0;
		spriteCounter = 0;
	}
	
	// moving da character
	public void move() 
	{
		if(moving > 0)
		{
			spriteCounter += 1;
			if(spriteCounter > 12)
			{
				if(moving == 1)
				{
					moving = 2;
					spriteCounter = 0;
				} 
				else if (moving == 2)
				{
					moving = 1;
					spriteCounter = 0;
				}
			}
			
			if(main.direction.equals("left"))
			{
				camera.setX(camera.getX() - main.speed);
			}
			else if(main.direction.equals("right"))
			{
				camera.setX(camera.getX() + main.speed);
			}
			else if(main.direction.equals("up"))
			{
				camera.setY(camera.getY() - main.speed);
			}
			else if(main.direction.equals("down"))
			{
				camera.setY(camera.getY() + main.speed);
			}
			
		}
		else
			spriteCounter = 0;
	}
	
	public void draw(Graphics2D g2)
	{
		if(moving == 1)
		{
			if(main.direction.equals("up"))
				g2.drawImage(playerSprites[4], main.screenX, main.screenY, 45, 45, null);
			else if(main.direction.equals("down"))
				g2.drawImage(playerSprites[1], main.screenX, main.screenY, 45, 45, null);
			else if(main.direction.equals("left"))
				g2.drawImage(playerSprites[10], main.screenX, main.screenY, 45, 45, null);
			else if(main.direction.equals("right"))
				g2.drawImage(playerSprites[7], main.screenX, main.screenY, 45, 45, null);
		}
		else if(moving == 2)
		{
			if(main.direction.equals("up"))
				g2.drawImage(playerSprites[5], main.screenX, main.screenY, 45, 45, null);
			else if(main.direction.equals("down"))
				g2.drawImage(playerSprites[2], main.screenX, main.screenY, 45, 45, null);
			else if(main.direction.equals("left"))
				g2.drawImage(playerSprites[11], main.screenX, main.screenY, 45, 45, null);
			else if(main.direction.equals("right"))
				g2.drawImage(playerSprites[8], main.screenX, main.screenY, 45, 45, null);
		}
		else if(moving == 0)
		{
			if(main.direction.equals("up"))
				g2.drawImage(playerSprites[3], main.screenX, main.screenY, 45, 45, null);
			else if(main.direction.equals("down"))
				g2.drawImage(playerSprites[0], main.screenX, main.screenY, 45, 45, null);
			else if(main.direction.equals("left"))
				g2.drawImage(playerSprites[9], main.screenX, main.screenY, 45, 45, null);
			else if(main.direction.equals("right"))
				g2.drawImage(playerSprites[6], main.screenX, main.screenY, 45, 45, null);
		}
	}
}