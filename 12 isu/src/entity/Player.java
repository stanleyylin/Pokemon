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

	protected int screenX;
	protected int screenY;
	public String direction;
	private BufferedImage[] sprites;
	private boolean interacting;
	private Person talkingTo;
	
	private ArrayList<Pokemon> box;
	private Pokemon[] party;
	private ArrayList<Item> pokeballs;
	private ArrayList<Item> medicine;
	private ArrayList<Item> keyItems;
	private LoadItems loadItems;
	
	public final static int width = 50;
	public final static int height = 62;
	public final int speed = 5;

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
		
		pokeballs = new ArrayList<Item>();
		medicine = new ArrayList<Item>();
		keyItems = new ArrayList<Item>();
		
		loadItems();
	}
	
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
		medicine.add(loadItems.getItem("Revive"));
		medicine.add(loadItems.getItem("Max Revive"));
		
//		keyItems.add(loadItems.getItem("Town Map"));
//		keyItems.add(loadItems.getItem("Badge Case"));
//		keyItems.add(loadItems.getItem("Exp. Share"));
//		keyItems.add(loadItems.getItem("Oak's Letter"));
	}
	
	public void healParty() {
		for (Pokemon p1: party)
		{
			if(p1 != null)
				p1.heal();
		}
	}
	
	public void replace(int n, Pokemon p1) {
		this.party[n] = p1;
	}
	
	public void battle(NPC enemy) {
		
		Pokemon curFriendlyMon = this.party[0];
		Pokemon curEnemyMon = enemy.getParty()[0];
		
		//switch to battle screen
		
		
//		while ( findNextAvailableMon(this.party) != null || findNextAvailableMon(enemy.getParty()) != null) {
			//allow to pick attack/bag/pokemon/run
			
			//if attack
			
			//pick attack from buttons(0,1,2,3)
			int selectedAttack = 1;
			
			curFriendlyMon.attack(selectedAttack, curEnemyMon);
			//check if is dead & switch curEnemy mon/end battle if they run out
//			if (curEnemyMon.)
			
			//randomly generates move(eventually should generate optimal move)
			int enemyAttack = (int)Math.random() * (3 - 0 + 1) + 0;
			
			
			//if bag
			//implement items
		
		if (findNextAvailableMon() == null)
			System.out.println("you lost");
		else if (findNextAvailableMon() == null)
			System.out.println("you won!");
		
		//switch back to game screen

		
		
	}
	
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

	public void addPokemonToParty(Pokemon pokemon) {
		int temp = this.findNextPartySlot();
//		System.out.println(temp);
		if (temp >= 0)
			party[temp] = pokemon;
//		else
//			System.out.println("your party is full");
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
	
	public void sortByName(int bagNum)
	{
		if(bagNum == 0)
			Collections.sort(pokeballs);
		else if(bagNum == 1)
			Collections.sort(medicine);
		else
			Collections.sort(keyItems);
	}
	
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

}
