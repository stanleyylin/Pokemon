// NPC extends Person and is battleable.
package entity;

import java.awt.*;
import java.awt.image.BufferedImage;

import pokesetup.BlankMon;
import pokesetup.Pokemon;

public class NPC extends Person 
{
	private String name;
	private Pokemon[] party; // Party of 6 Pokemon
	private boolean battleable; // Whether or not player has battled
	private String loseFile; // txt file for when NPC has been beaten
	// In Person Class: Rectangle collision, Rectangle interaction, String imageFile;
	// BufferedImage sprite, String direction, String textFile, boolean shown;

	// Constructor
	public NPC(String name, Rectangle collision, String direction, String imageFile, int w, int h, String firstFile, String loseFile, Pokemon[] party)
	{
		super(collision, direction, imageFile, w, h, firstFile);

		this.name = name;
		this.party = party;
		battleable = false;
		this.loseFile = loseFile;
	}

	//constructor if they are not on screen (or a sprite already existed on the map)
	public NPC(String name, Rectangle collision, String direction, String firstFile, String loseFile, Pokemon[] party)
	{
		super(collision, direction, firstFile);
		this.name = name;
		if (party != null)
		this.party = party;
		else
			this.party = new Pokemon[6];
		battleable = false;
		this.loseFile = loseFile;
	}

	//heals NPC party
	public void healParty() {
		for (Pokemon p1: party)
		{
			if(p1 != null)
				p1.heal();
		}
	}

	//generates party of random pokemons
	public void generateParty(int maxMons, int minLvl, int maxLvl) {

		int pSize = 1+ (int) (Math.random() * maxMons);

		for (int i = 0; i< pSize; i++) {
			int newLvl= minLvl+ (int) (Math.random() * (maxLvl-minLvl+1));
			
			
			
			int newID = 1 + (int) (Math.random() * ((135-1)+1));
			party[i] = new Pokemon(Pokemon.getStatsForEvolve().get(newID).getName(),Pokemon.getStatsForEvolve().get(newID).getName(),newLvl);
		}

	}

	//adds given pokemon to party
	public void addPokemonToParty(Pokemon pokemon) {
		int temp = this.findNextPartySlot();
		if (temp >= 0)
			party[temp] = pokemon;
	}

	//finds next available party slot
	public int findNextPartySlot() {
		for (int i = 0; i < 6; i++) 
			if (party[i] == null )
				return i;
		return -1;
	}

	//prints pokemon names
	public void printPokemon() {
		for (Pokemon p1 : party) {
			if (p1 == null)
				System.out.println("empty slot");
			else
				System.out.println(p1 + "\n");
		}
	}

	//finds next alive mon
	public int findNextAvailableMon() {
		for (int i = 0; i < 6; i++) {
			if (this.party[i] == null)
				break;
			if (this.party[i].getIsFainted())
				continue;
			else
				return i;	
		}

		return -1;
	}

	// Getters and Setters
	public void changeFile()
	{
		textFile = loseFile;
	}
	public String getName()
	{
		return name;
	}
	public Pokemon[] getParty() {
		return this.party;
	}
	public boolean getBattleable()
	{
		return battleable;
	}
	public String getLines()
	{
		return textFile;
	}	
	public void setBattleable(boolean set)
	{
		battleable = set;
	}

}
