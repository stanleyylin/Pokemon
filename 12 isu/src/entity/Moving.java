// Moving animates the player and makes it move.

package entity;

import map.Building;
import map.Camera;
import map.Gate;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;

import main.GamePanel;
import main.Main;

public class Moving {
	
	private GamePanel game;
	private Player main; // the player
	private Camera camera; // the camera following the player
	private BufferedImage[] playerSprites; // the player sprites
	private int spriteCounter; // used to switch sprites
	private int moving; // whether or not the player is moving, 0 - not moving, 1 - first moving sprite
	// 2 - second moving sprite

	// Constructor
	public Moving(GamePanel game, Player player, Camera camera)
	{
		this.game = game;
		main = player;
		this.camera = camera;
		playerSprites = main.getSprites();
		moving = 0;
		spriteCounter = 0;
	}
	
	void checkCollisions(Rectangle[] collisions, boolean cameraXOn, boolean cameraYOn)
	{
		int camX = 0;
		int camY = 0;
		if(camera.getBuilding() == null)
		{
			camX = camera.getX();
			camY = camera.getY();
		}
		Rectangle player = new Rectangle(camX + main.getScreenX(), camY + main.getScreenY(), Player.width, Player.height);
		for(int i = 0; i < collisions.length; i++)
		{
			Rectangle collision = collisions[i];
			if(player.intersects(collision))
			{
				double left1 = player.getX(); // player
				double right1 = player.getX() + player.getWidth();
				double top1 = player.getY();
				double bottom1 = player.getY() + player.getHeight();
				double left2 = collision.getX(); // collision
				double right2 = collision.getX() + collision.getWidth();
				double top2 = collision.getY();
				double bottom2 = collision.getY() + collision.getHeight();

				if(right1 > left2 && left1 < left2 && right1 - left2 < bottom1 - top2 && right1 - left2 < bottom2 - top1)
				{
					//rect collides from left side of the wall
					if(cameraXOn)
						camera.setX((int) (camX - (right1-left2)));
					else
						main.setScreenX(collision.x-camX - Player.width);
				}
				else if(left1 < right2 && right1 > right2 && right2 - left1 < bottom1 - top2 && right2 - left1 < bottom2 - top1)
				{
					//rect collides from right side of the wall
					if(cameraXOn)
						camera.setX((int) (camX + right2-left1));
					else
						main.setScreenX(collision.x-camX + collision.width);
				}
				else if(bottom1 > top2 && top1 < top2)
				{
					if(cameraYOn)
						camera.setY((int) (camY - (bottom1-top2)));
					else
						main.setScreenY(collision.y-camY - Player.height);
					//rect collides from top side of the wall
					// rect.y = wall.y - rect.height;
				}
				else if(top1 < bottom2 && bottom1 > bottom2)
				{
					if(cameraYOn)
						camera.setY((int) (camY + (bottom2-top1)));
					else
						main.setScreenY(collision.y-camY + collision.height);
					//rect collides from bottom side of the wall
					// rect.y = wall.y + wall.height;
				}
				break;
			}
		}
	}
	
	void checkGate(ArrayList<Gate> gates)
	{
		Rectangle player = new Rectangle(camera.getX() + main.getScreenX(), camera.getY() + main.getScreenY(), Player.width, Player.height);
		for(Gate g : gates)
		{
			if(g.getR1().intersects(player))
			{
				camera.setLocation(g.getL2());
				camera.setX(g.getCamX());
				camera.setY(g.getCamY());
				camera.getLocation().setEdgeReachedX(g.getXEdge());
				camera.getLocation().setEdgeReachedY(g.getYEdge());
				main.setScreenX(g.getScreenX());
				main.setScreenY(g.getScreenY());
			}
		}
	}
	
	void interact()
	{
		int camX = 0;
		int camY = 0;
		if(camera.getBuilding() == null)
		{
			camX = camera.getX();
			camY = camera.getY();
		}
		Rectangle player = new Rectangle(camX + main.getScreenX(), camY + main.getScreenY(), Player.width, Player.height);
		
		if(camera.getBuilding() == null)
		{
			for(int i = 0; i < camera.getLocation().getPeople().length; i++)
			{
				if(camera.getLocation().getPeople()[i].getI().intersects(player))
				{
					game.setInteract(true);
					main.setTalkingTo(camera.getLocation().getPeople()[i]);
					break;
				}
				else
				{
					game.setInteract(false);
				}
			}
		}
		else if(camera.getBuilding() != null)
		{
			for(int i = 0; i < camera.getBuilding().getPeople().length; i++)
			{
				if(camera.getBuilding().getPeople()[i].getI().intersects(player))
				{
					game.setInteract(true);
					main.setTalkingTo(camera.getBuilding().getPeople()[i]);
					break;
				}
				else if(i == camera.getBuilding().getPeople().length-1)
					game.setInteract(false);
			}
		}
	}
	
	void checkCollisions(Person[] people, boolean cameraXOn, boolean cameraYOn)
	{
		int camX = 0;
		int camY = 0;
		if(camera.getBuilding() == null)
		{
			camX = camera.getX();
			camY = camera.getY();
		}
		Rectangle player = new Rectangle(camX + main.getScreenX()+10, camY + main.getScreenY() + 15, Player.width-10, Player.height-15);
		
		for(int i = 0; i < people.length; i++)
		{
			Rectangle collision = people[i].getC();
			if(player.intersects(collision))
			{
				double left1 = player.getX(); // player
				double right1 = player.getX() + player.getWidth();
				double top1 = player.getY();
				double bottom1 = player.getY() + player.getHeight();
				double left2 = collision.getX(); // collision
				double right2 = collision.getX() + collision.getWidth();
				double top2 = collision.getY();
				double bottom2 = collision.getY() + collision.getHeight();

				if(right1 > left2 && left1 < left2 && right1 - left2 < bottom1 - top2 && right1 - left2 < bottom2 - top1)
				{
					//rect collides from left side of the wall
					if(cameraXOn)
						camera.setX((int) (camX - (right1-left2)));
					else
						main.setScreenX(collision.x-camX - Player.width);
				}
				else if(left1 < right2 && right1 > right2 && right2 - left1 < bottom1 - top2 && right2 - left1 < bottom2 - top1)
				{
					//rect collides from right side of the wall
					if(cameraXOn)
						camera.setX((int) (camX + right2-left1));
					else
						main.setScreenX(collision.x-camX + collision.width);
				}
				else if(bottom1 > top2 && top1 < top2)
				{

					if(cameraYOn)
						camera.setY((int) (camY - (bottom1-top2)));
					else
						main.setScreenY(collision.y-camY - Player.height);
					//rect collides from top side of the wall
					// rect.y = wall.y - rect.height;
				}
				else if(top1 < bottom2 && bottom1 > bottom2)
				{
					if(cameraYOn)
						camera.setY((int) (camY + (bottom2-top1)));
					else
						main.setScreenY(collision.y-camY + collision.height);
					//rect collides from bottom side of the wall
					// rect.y = wall.y + wall.height;
				}
				break;
			}
		}
	}
	
	void doorEntered()
	{
		if(camera.getBuilding() == null && main.direction == "up")
		{
			int bounds = 10;
			Rectangle player = new Rectangle(camera.getX() + main.getScreenX()+bounds, camera.getY() + main.getScreenY()+bounds, Player.width-bounds, Player.height-bounds);
			for(Building b : camera.getLocation().getBuildings())
			{
				if(player.intersects(b.getEntrance()))
				{
					camera.getLocation().setLastPosition(main.getScreenX(), main.getScreenY(), camera.getX(), camera.getY()+main.speed);
					camera.setBuilding(b);
					main.setScreenX((int) b.getExit().getX()-Player.width/2);
					main.setScreenY((int) b.getExit().getY() - Player.height - 5);
				}
			}
		}
		else if (camera.getBuilding() != null && main.direction.equals("down"))
		{
			int bounds = 10;
			Rectangle player = new Rectangle(main.getScreenX()+bounds, main.getScreenY()+bounds, Player.width-bounds, Player.height-bounds);
			if(player.intersects(camera.getBuilding().getExit()))
			{
				int[] savePos = camera.getLocation().getLastPosition();
				camera.setBuilding(null);
				main.setScreenX(savePos[0]);
				main.setScreenY(savePos[1]);
				camera.setX(savePos[2]);
				camera.setY(savePos[3]);
			}
		}
	}
	
	void inGrass()
	{
		if(camera.getLocation().getGrass() == null)
			return;
		
		Rectangle player = new Rectangle(camera.getX() + main.getScreenX(), camera.getY() + main.getScreenY(), Player.width, Player.height);
		
		for(int i = 0; i < camera.getLocation().getGrass().length; i++)
		{
			if(camera.getLocation().getGrass()[i].intersects(player))
			{
				int temp = (int)(Math.random() * ((140) + 1));
				//1% chance
				if (temp == 40) {
					System.out.println("cauight");
//					main.startBattle(new NPC);
					//basically this will make an npc w just one mon and then we can makethe constructor have the boolean
					//isWIld to true (so pokeballs can be used)
				}
			}
		}
		
	}
	
	public void changeSprite()
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
	}
	
	public void movePlayerX()
	{
		if(moving > 0)
		{
			if(main.direction.equals("left"))
			{
				main.setScreenX(main.getScreenX() - main.speed);
			}
			else if(main.direction.equals("right"))
			{
				main.setScreenX(main.getScreenX() + main.speed);
			}
			else
				return;
			
		}
		else
		{
			spriteCounter = 0;
		}
		
		// Check bounds
		if(camera.getBuilding() != null)
		{
			if(main.getScreenX() < camera.getBuilding().screenX)
				main.setScreenX(camera.getBuilding().screenX);
			else if(main.getScreenX() > camera.getBuilding().maxX - Player.width)
				main.setScreenX(camera.getBuilding().maxX - Player.width);
		}
		else
		{
			if(main.getScreenX() < 0)
				main.setScreenX(0);
			else if(main.getScreenX() > GamePanel.screenWidth - Player.width)
				main.setScreenX(GamePanel.screenWidth - Player.width);
		}
		
		doorEntered();
		
		// Check collisions
		if(camera.getBuilding() == null)
		{
			if(moving != 0)
				inGrass();
			checkCollisions(camera.getLocation().getCollisions(), !camera.getLocation().getEdgeReachedX(), !camera.getLocation().getEdgeReachedY());
			checkCollisions(camera.getLocation().getPeople(), !camera.getLocation().getEdgeReachedX(), !camera.getLocation().getEdgeReachedY());
		}
		else
		{
			checkCollisions(camera.getBuilding().getCollisions(), !camera.getBuilding().getEdgeReachedX(), !camera.getBuilding().getEdgeReachedY());
			checkCollisions(camera.getBuilding().getPeople(), !camera.getBuilding().getEdgeReachedX(), !camera.getBuilding().getEdgeReachedY());
		}
		
		interact();
		
		// Check if player is in center
		if(camera.getBuilding() == null && camera.getLocation().getEdgeReachedX())
		{
			if(camera.getX() == 0)
			{
				if(main.getScreenX() > GamePanel.screenWidth/2-Player.width/2)
				{
					camera.getLocation().setEdgeReachedX(false);
				}
			}
			else if(camera.getX() == camera.getLocation().maxX-GamePanel.screenWidth)
			{
				if(main.getScreenX() < GamePanel.screenWidth/2-Player.width/2)
				{
					camera.getLocation().setEdgeReachedX(false);
				}
			}
		}
		checkGate(camera.getLocation().getGates());
	}
	
	public void movePlayerY()
	{
		if(moving > 0)
		{
			if(main.direction.equals("up"))
			{
				main.setScreenY(main.getScreenY() - main.speed);
			}
			else if(main.direction.equals("down"))
			{
				main.setScreenY(main.getScreenY() + main.speed);
				
			}
			else
				return;
		}
		else
		{
			spriteCounter = 0;
			return;
		}
		// Check bounds
		if(camera.getBuilding() != null)
		{
			if(main.getScreenY() < camera.getBuilding().screenY)
				main.setScreenY(camera.getBuilding().screenY);
			else if(main.getScreenY() > camera.getBuilding().maxY - Player.height)
				main.setScreenY(camera.getBuilding().maxY - Player.height);
		}
		else
		{
			if(main.getScreenY() < 0)
				main.setScreenY(0);
			else if(main.getScreenY() > GamePanel.screenHeight - Player.height)
				main.setScreenY(GamePanel.screenHeight - Player.height);
		}
		
		doorEntered();
		
		// Check collisions
		if(camera.getBuilding() == null)
		{
			if(moving != 0)
				inGrass();
			checkCollisions(camera.getLocation().getCollisions(), !camera.getLocation().getEdgeReachedX(), !camera.getLocation().getEdgeReachedY());
			checkCollisions(camera.getLocation().getPeople(), !camera.getLocation().getEdgeReachedX(), !camera.getLocation().getEdgeReachedY());
		}
		else
		{
			checkCollisions(camera.getBuilding().getCollisions(), !camera.getBuilding().getEdgeReachedX(), !camera.getBuilding().getEdgeReachedY());
			checkCollisions(camera.getBuilding().getPeople(), !camera.getBuilding().getEdgeReachedX(), !camera.getBuilding().getEdgeReachedY());
		}
	
		interact();
		
		// Check if player is in center
		if(camera.getBuilding() == null && camera.getLocation().getEdgeReachedY())
		{
			if(camera.getY() == 0)
			{
				if(main.getScreenY() > GamePanel.screenHeight/2-Player.height/2)
				{
					camera.getLocation().setEdgeReachedY(false);
				}
			}
			else if(camera.getY() == camera.getLocation().maxY-GamePanel.screenHeight)
			{
				if(main.getScreenY() < GamePanel.screenHeight/2-Player.height/2)
				{
					camera.getLocation().setEdgeReachedY(false);
				}
			}
		}
		checkGate(camera.getLocation().getGates());
	
	}
	
	public void moveCameraX() 
	{
		if(moving > 0)
		{
			if(main.direction.equals("left"))
			{
				camera.setX(camera.getX() - main.speed);
			}
			else if(main.direction.equals("right"))
			{
				camera.setX(camera.getX() + main.speed);
			}	
		}
		else
		{
			spriteCounter = 0;
		}	
		
		if(camera.getBuilding() == null)
		{
			if(moving != 0)
				inGrass();
			checkCollisions(camera.getLocation().getCollisions(), !camera.getLocation().getEdgeReachedX(), !camera.getLocation().getEdgeReachedY());
			checkCollisions(camera.getLocation().getPeople(), !camera.getLocation().getEdgeReachedX(), !camera.getLocation().getEdgeReachedY());

		}
		else
		{
			checkCollisions(camera.getBuilding().getCollisions(), !camera.getBuilding().getEdgeReachedX(), !camera.getBuilding().getEdgeReachedY());
			checkCollisions(camera.getBuilding().getPeople(), !camera.getBuilding().getEdgeReachedX(), !camera.getBuilding().getEdgeReachedY());
		}
		doorEntered();
		interact();
		checkGate(camera.getLocation().getGates());
	}
	
	public void moveCameraY()
	{
		if(moving > 0)
		{
			if(main.direction.equals("up"))
			{
				camera.setY(camera.getY() - main.speed);
			}
			else if(main.direction.equals("down"))
			{
				camera.setY(camera.getY() + main.speed);
			}
		}
		else
		{
			spriteCounter = 0;
		}
		
		if(camera.getBuilding() == null)
		{
			if(moving != 0)
				inGrass();
			checkCollisions(camera.getLocation().getCollisions(), !camera.getLocation().getEdgeReachedX(), !camera.getLocation().getEdgeReachedY());
			checkCollisions(camera.getLocation().getPeople(), !camera.getLocation().getEdgeReachedX(), !camera.getLocation().getEdgeReachedY());
		}
		else
		{
			checkCollisions(camera.getBuilding().getCollisions(), !camera.getBuilding().getEdgeReachedX(), !camera.getBuilding().getEdgeReachedY());
			checkCollisions(camera.getBuilding().getPeople(), !camera.getBuilding().getEdgeReachedX(), !camera.getBuilding().getEdgeReachedY());
		}
		doorEntered();
		interact();
		checkGate(camera.getLocation().getGates());
	}
	
	public void draw(Graphics2D g2)
	{
		if(moving == 1)
		{
			if(main.direction.equals("down"))
				g2.drawImage(playerSprites[1], main.screenX, main.screenY, null);
			else if(main.direction.equals("up"))
				g2.drawImage(playerSprites[4], main.screenX, main.screenY, null);
			else if(main.direction.equals("left"))
				g2.drawImage(playerSprites[10], main.screenX, main.screenY, null);
			else if(main.direction.equals("right"))
				g2.drawImage(playerSprites[7], main.screenX, main.screenY, null);
		}
		else if(moving == 2)
		{
			if(main.direction.equals("down"))
				g2.drawImage(playerSprites[2], main.screenX, main.screenY, null);
			else if(main.direction.equals("up"))
				g2.drawImage(playerSprites[5], main.screenX, main.screenY, null);
			else if(main.direction.equals("left"))
				g2.drawImage(playerSprites[11], main.screenX, main.screenY, null);
			else if(main.direction.equals("right"))
				g2.drawImage(playerSprites[8], main.screenX, main.screenY, null);
		}
		else if(moving == 0)
		{
			if(main.direction.equals("down"))
				g2.drawImage(playerSprites[0], main.screenX, main.screenY, null);
			else if(main.direction.equals("up"))
				g2.drawImage(playerSprites[3], main.screenX, main.screenY, null);
			else if(main.direction.equals("left"))
				g2.drawImage(playerSprites[9], main.screenX, main.screenY, null);
			else if(main.direction.equals("right"))
				g2.drawImage(playerSprites[6], main.screenX, main.screenY, null);
		}
		
	}
	
	public void setMoving(int set)
	{
		moving = set;
	}
}