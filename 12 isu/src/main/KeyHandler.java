package main;
// KeyHandler takes in user keyboard input.

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import entity.Moving;
import entity.Player;

public class KeyHandler implements KeyListener {
	
	private Player main;
	private Moving move;
	private boolean pressed;
	
	public KeyHandler(Player main, Moving move)
	{
		this.main = main;
		pressed = false;
	}
	
	// keyTyped is unused.
	public void keyTyped(KeyEvent e) {}
	
	// keyPressed checks if the user presses a key.
	// Parameters: keyEvent (when a key is pressed)
	// Returns: void
	public void keyPressed(KeyEvent e) 
	{
		int key = e.getKeyCode();
		
		// main.moving - if its 0, its resting, if its above 0 its moving
		if(key == KeyEvent.VK_A)
		{
			if(!pressed)
			{
				move.setMoving(1);
				pressed = true;
			}
			main.direction = "left";
		}
		else if(key == KeyEvent.VK_D) 
		{
			if(!pressed)
			{
				move.setMoving(1);
				pressed = true;
			}
			main.direction = "right";
		}
		else if(key == KeyEvent.VK_S) 
		{
			if(!pressed)
			{
				move.setMoving(1);
				pressed = true;
			}
			main.direction = "down";
		}
		else if(key == KeyEvent.VK_W) 
		{
			if(!pressed)
			{
				move.setMoving(1);
				pressed = true;
			}
			main.direction = "up";
		}
	}
	
	// resting
	public void keyReleased(KeyEvent e) 
	{
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_A || key == KeyEvent.VK_D || key == KeyEvent.VK_W || key == KeyEvent.VK_S) 
		{
			move.setMoving(0);
			pressed = false;
		}
	
	}

}
