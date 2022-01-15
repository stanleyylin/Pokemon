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
	
	public int getWorldX()
	{
		return worldX;
	}
	public int getWorldY()
	{
		return worldY;
	}
	
	public Pokemon[] getParty() {
		return this.party;
	}
	
}
