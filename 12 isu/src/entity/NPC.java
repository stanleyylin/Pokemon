package entity;

import java.awt.*;
import java.awt.image.BufferedImage;

import pokesetup.Pokemon;

public class NPC extends Entity {
	private int worldX;
	private int worldY;
	private BufferedImage[] sprites;
	private Pokemon[] party;
	
	public NPC(int worldX, int worldY, BufferedImage[] sprites)
	{
		this.worldX = worldX;
		this.worldY = worldY;
		this.sprites = sprites;
		
		this.party = new Pokemon[6];
	}
	
	public int getWorldX()
	{
		return worldX;
	}
	public int getWorldY()
	{
		return worldY;
	}
}
