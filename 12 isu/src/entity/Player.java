package entity;

import pokesetup.Pokemon; 

import java.io.IOException;

import getimages.LoadImage;
import getimages.SpriteSheet;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class Player extends Entity {

	private BufferedImage spriteSheet;
	private BufferedImage[] sprites;

	private Pokemon[] party;

	public final static int size = 80;
	public final int speed = 3;

	public Player(int screenX, int screenY)
	{
		this.screenX = screenX;
		this.screenY = screenY;

		direction = "down";

		LoadImage loader = new LoadImage();
		
		this.party = new Pokemon[6];

		try
		{
			spriteSheet = loader.loadImage("res/char1.png");
			Image tmp = spriteSheet.getScaledInstance(240, 320, Image.SCALE_SMOOTH);
			spriteSheet = new BufferedImage(240, 320, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2d = spriteSheet.createGraphics();
			g2d.drawImage(tmp, 0, 0, null);
			g2d.dispose();
		}
		catch(IOException e) {}
		SpriteSheet player = new SpriteSheet(spriteSheet, 3, 4);
		sprites = player.getSprites(size, size);
	}
	
	public void healParty() {
		for (Pokemon p1: party)
			p1.heal();
	}

	public void addPokemonToParty(Pokemon pokemon) {
		int temp = this.findNextPartySlot();
		System.out.println(temp);
		if (temp >= 0)
			party[temp] = pokemon;
		else
			System.out.println("your party is full");
	}
	
	public int findNextPartySlot() {
		for (int i = 0; i < 6; i++) 
			if (party[i] == null )
				return i;
			return -1;
	}

	public void printPokemon() {
		for (Pokemon p1 : party) {
			if (p1 == null)
				System.out.println("empty slot");
			else
			System.out.println(p1 + "\n");
		}
	}
	
	public BufferedImage[] getSprites()
	{
		return sprites;
	}

	public void setScreenX(int set)
	{
		screenX = set;
	}
	public void setScreenY(int set)
	{
		screenY = set;
	}


}
