package entity;

import pokesetup.Pokemon; 

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import bag.Item;
import bag.LoadItems;
import bag.SortItemByCost;
import getimages.LoadImage;
import getimages.SpriteSheet;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class Player {

	protected int screenX; //x position on screen
	protected int screenY; //y position on screen
	public String direction; //direction player is facing
	private BufferedImage[] sprites; //array of sprites
	private boolean interacting; // if the player is interacting w anyone
	private Person talkingTo; //the person they are talking to
	
	private boolean hasWon; //if the player has won a battle
	
	private ArrayList<Pokemon> box; //the players box
	private Pokemon[] party; //the players current party
	private int pokeDollars;//the players current currency
	private ArrayList<Item> pokeballs; //items in bag (pokeballs)
	private ArrayList<Item> medicine;//items in bag (medicine)
	private ArrayList<Item> keyItems;//items in bag (key items)
	private LoadItems loadItems;
	private int badges; //(num of badges player has)

	public final static int width = 50;
	public final static int height = 62;
	public final int speed = 5;

	//constructor
	public Player(int screenX, int screenY)
	{
		this.screenX = screenX;
		this.screenY = screenY;

		direction = "down";

		this.party = new Pokemon[6];
		this.box = new ArrayList<Pokemon>();
		SpriteSheet player = new SpriteSheet("char1.png");
		sprites = player.getSprites();

		interacting = false;
		talkingTo = null;
		badges = 0;
		hasWon = true;
		pokeballs = new ArrayList<Item>();
		medicine = new ArrayList<Item>();
		keyItems = new ArrayList<Item>();
		pokeDollars = 5000;
		loadItems();
	}

	//loads items from file
	public void loadItems()
	{
		loadItems = new LoadItems();
		// Poke balls
		pokeballs.add(loadItems.getItem("Poke Ball"));
		pokeballs.add(loadItems.getItem("Great Ball"));
		pokeballs.add(loadItems.getItem("Ultra Ball"));
		pokeballs.add(loadItems.getItem("Master Ball"));

		// Medicine
		medicine.add(loadItems.getItem("Potion"));
		medicine.add(loadItems.getItem("Super Potion"));
		medicine.add(loadItems.getItem("Hyper Potion"));
		medicine.add(loadItems.getItem("Full Restore"));
		medicine.add(loadItems.getItem("Full Heal"));
		medicine.add(loadItems.getItem("Ether"));
		medicine.add(loadItems.getItem("Elixir"));

	}

	//heals party to full
	public void healParty() {
		for (Pokemon p1: party)
		{
			if(p1 != null)
			{
				p1.heal();
				p1.setStatus(null);
			}
		}
	}


	//replaces a party mon w the given mon
	public void replace(int n, Pokemon p1) {
		this.party[n] = p1;
	}

	//finds next alive pokemon
	public Pokemon findNextAvailableMon() {
		for (Pokemon p1 : this.party) {
			if (p1 == null)
				break;
			if (p1.getIsFainted()) 
				continue;
			else
				return p1;
		}
		return null;
	}

	//adds pokemon to party innext given slot
	public void addPokemonToParty(Pokemon pokemon) {
		int temp = this.findNextPartySlot();
		if (temp >= 0)
			party[temp] = pokemon;
	}

	//finds next open party slot
	public int findNextPartySlot() {
		for (int i = 0; i < 6; i++) 
			if (party[i] == null )
				return i;
		return -1;
	}

	//prints all pokenames
	public void printPokemon() {
		for (Pokemon p1 : party) {
			if (p1 == null)
				System.out.println("empty slot");
			else
				System.out.println(p1 + "\n");
		}
	}

	//sorts each item category by name
	public void sortByName(int bagNum)
	{
		if(bagNum == 0)
			Collections.sort(pokeballs);
		else if(bagNum == 1)
			Collections.sort(medicine);
		else
			Collections.sort(keyItems);
	}

	//sorts each item category by cost
	public void sortByCost(int bagNum)
	{
		if(bagNum == 0)
			Collections.sort(pokeballs, new SortItemByCost());
		else if(bagNum == 1)
			Collections.sort(medicine, new SortItemByCost());
	}

	// Getters and Setters
	public Pokemon[] getParty()
	{
		return party;
	}
	public BufferedImage[] getSprites()
	{
		return sprites;
	}
	public boolean isInteracting()
	{
		return interacting;
	}
	public int getScreenX()
	{
		return screenX;
	}
	public int getScreenY()
	{
		return screenY;
	}
	public Person getTalkingTo()
	{
		return talkingTo;
	}
	public ArrayList<Item> getBag(int bagNum)
	{
		if(bagNum == 0)
			return pokeballs;
		else if(bagNum == 1)
			return medicine;
		else
			return keyItems;
	}
	public int getPokeDollars()
	{
		return pokeDollars;
	}

	public void setPokeDollars(int set)
	{
		pokeDollars = set;
	}
	public void setScreenX(int set)
	{
		screenX = set;
	}
	public void setScreenY(int set)
	{
		screenY = set;
	}
	public void setInteracting(boolean set)
	{
		interacting = set;
	}
	public void setTalkingTo(Person p)
	{
		talkingTo = p;
	}
	public void addKeyItem(String item)
	{
		Item temp = loadItems.getItem(item);
		temp.addItems(1);
		keyItems.add(temp);
	}
	public void swapPokemon(Pokemon p, int i)
	{
		box.remove(p);
		box.add(party[i]);
		party[i] = p;
	}
	public void addOnItem(String item, int bagNum, int quantity) // 0-pokeballs, 1-medicine, 3-keyItems
	{
		Item temp = loadItems.getItem(item);
		if(bagNum == 0)
		{
			pokeballs.get(pokeballs.indexOf(temp)).addItems(quantity);
		}
		else if(bagNum == 1)
		{
			medicine.get(medicine.indexOf(temp)).addItems(quantity);
		}
	}

	public ArrayList<Pokemon> getBox()
	{
		return box;
	}
	public void addToBox(Pokemon p)
	{
		box.add(p);
	}

	public int getBadges() {
		return this.badges;
	}
	public boolean getWon()
	{
		return hasWon;
	}
	public void addBadges() {
		if (this.badges<= 3)
			this.badges++;
	}

}
