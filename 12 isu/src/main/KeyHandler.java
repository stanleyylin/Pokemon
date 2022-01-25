package main;
// KeyHandler takes in user keyboard input.

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import entity.Moving;
import entity.Player;

public class KeyHandler implements KeyListener {

	private GamePanel game;
	private Player main;
	private Moving move;
	private boolean pressed;
	private boolean xPressed; 
	
	public KeyHandler(GamePanel game, Player main, Moving move)
	{
		this.game = game;
		this.main = main;
		this.move = move;
		pressed = false;
		xPressed = false;
	}
	
	// keyTyped is unused.
	public void keyTyped(KeyEvent e) {}
	
	// keyPressed checks if the user presses a key.
	// Parameters: keyEvent (when a key is pressed)
	// Returns: void
	public void keyPressed(KeyEvent e) 
	{
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_B)
		{
			game.openBox();
		}
		if(key == KeyEvent.VK_I)
		{
			game.openBag();
		}
		
		if (key == KeyEvent.VK_P)
		{
			game.startBattle();
		}
		// main.moving - if its 0, its resting, if its above 0 its moving
		if(game.getInteract() && key == KeyEvent.VK_X)
		{
			if(!xPressed)
			{
				xPressed = true;
				if(main.isInteracting())
				{
					game.getDialogue().nextLine();
				}
				else
				{
					main.setInteracting(true);
					game.showDialogue();
				}
			}
		}
		
		else if(!main.isInteracting())
		{
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
	
	public void setXPressed(boolean set)
	{
		xPressed = set;
	}

}
