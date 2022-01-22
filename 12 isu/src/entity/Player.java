package entity;

import pokesetup.Pokemon; 

import java.io.IOException;

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
	
	private Pokemon[] party;

	public final static int width = 50;
	public final static int height = 62;
	public final int speed = 5;

	public Player(int screenX, int screenY)
	{
		this.screenX = screenX;
		this.screenY = screenY;

		direction = "down";
		
		this.party = new Pokemon[6];

		SpriteSheet player = new SpriteSheet("char1.png");
		sprites = player.getSprites();
		
		interacting = false;
		talkingTo = null;
	}
	
	public void healParty() {
		for (Pokemon p1: party)
			p1.heal();
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
	
	public void setScreenX(int set)
	{
		screenX = set;
	}
	public void setScreenY(int set)
	{
		screenY = set;
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
	public void setInteracting(boolean set)
	{
		interacting = set;
	}
	public void setTalkingTo(Person p)
	{
		talkingTo = p;
	}


}
