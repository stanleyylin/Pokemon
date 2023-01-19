// Nurse extends Person and can heal the player's pokemon.

package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.Timer;

import getimages.LoadImage;

public class Nurse extends Person 
{
	// Constructor
	public Nurse(Rectangle collision, String imageFile, int w, int h, String textFile)
	{
		super(collision, textFile);
		direction = "down";
		interaction = new Rectangle((int)collision.getX()-15, (int)collision.getY()+(int)collision.getHeight(), 90, 90);
		
		LoadImage loader = new LoadImage();
		try
		{
			sprite = loader.loadImage("res/sprites/" + imageFile);
			sprite = sprite.getSubimage(0, 0, w, h);
			sprite = loader.resize(sprite, 46, 63);
			sprite = sprite.getSubimage(0, 0, 46, 63-5);
		}
		catch(IOException e) {}
	}
	
	// heal heals the player's party of up to 6 pokemon.
	// returns void, parameters: Player p - the player
	public void heal(Player p)
	{
		p.healParty();
	}

}
