package bag;

import java.awt.image.BufferedImage;
import java.io.IOException;

import getimages.LoadImage;

public class Item implements Comparable<Item> {

	private String name;
	private String effect;
	private String category; // pokeball, medicine, key item, other item
	private int cost;
	private BufferedImage sprite;

	private int quantity;
	
	private double catchChance;
	private int healing;
	private boolean removeStatus;
	private int ppIncrease;
	private boolean doesRevive;


	//poke ball
	public Item (String name, String effect, int cost, double catchChance, String file) 
	{
		this.name = name;
		this.effect = effect;
		this.category = "PokeBall";
		this.cost = cost;
		this.catchChance = catchChance;
		
		LoadImage loader = new LoadImage();
		try 
		{
			sprite = loader.loadImage("res/items/" + file);
			sprite = loader.resize(sprite, 45, 45);
		} 
		catch (IOException e) {
		}
		quantity = 0;
	}

	// Healing item
	public Item(String name, String effect, int cost, int healing, boolean removeStatus, int ppIncrease, boolean doesRevive, String file) 
	{
		this.name = name;
		this.effect = effect;
		this.category = "Medicine";
		this.cost = cost;
		this.healing = healing;
		this.removeStatus = removeStatus;
		this.ppIncrease = ppIncrease;
		this.doesRevive = doesRevive;
		
		LoadImage loader = new LoadImage();
		try 
		{
			sprite = loader.loadImage("res/items/" + file);
			sprite = loader.resize(sprite, 45, 45);
		} 
		catch (IOException e) {
		}
		quantity = 0;
	}

	//key items (cannot be sold btw)
	public Item(String name, String effect, String file)
	{
		this.name = name;
		this.effect = effect;
		this.category = "Key Items";
		
		LoadImage loader = new LoadImage();
		try 
		{
			this.sprite = loader.loadImage("res/items/" + file);
			sprite = loader.resize(sprite, 45, 45);
		} 
		catch (IOException e) {
		}
		quantity = 0;
	}
	
	public boolean equals(Object o)
	{
		Item i = (Item) o;
		if(this.getName().equals(i.getName()))
		{
			return true;
		}
		return false;
	}
	public int compareTo(Item o) {
		return this.getName().compareTo(o.getName());
	}
	
	public String toString() {
		if (this.category.equals("PokeBall"))
			return String.format("%s(%.1f) -- %d", this.name, this.catchChance, this.cost);
		if (this.category.equals("Medicine"))
			return String.format("%s-hp: %d, pp: %d, %b/%b ", this.name, this.healing, this.ppIncrease, this.removeStatus, this.doesRevive);
		else return this.name;
	}
	
	public String getName()
	{
		return name;
	}
	public String getEffect()
	{
		return effect;
	}
	public int getQuantity()
	{
		return quantity;
	}
	public int getCost()
	{
		return cost;
	}
	public BufferedImage getSprite()
	{
		return sprite;
	}
	
	public void addItems(int add)
	{
		quantity += add;
	}
	public void useOneItem() {
		this.quantity--;
	}

}

//items in game
/*
 * __PokeBalls__
 * Poke Ball
 * Great Ball
 * Ultra Ball
 * Master Ball
 * 
 * __Medicine__
 * Potion
 * Super Potion
 * Hyper Potion
 * Full Restore
 * Full Heal
 * Elixir
 * Revive
 * Max Revive
 * 
 * 
 * 
 * __Key Items__
 * Town Map
 * Badge Case
 * exp share
 * oak's letter
 * Fishing rod (not needed)
 * bike (not needed)
 * 
 * 
 */
