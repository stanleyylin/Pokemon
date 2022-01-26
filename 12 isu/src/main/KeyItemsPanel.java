package main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JPanel;

import entity.Player;
import getimages.LoadImage;

public class KeyItemsPanel extends JPanel{

	
	Main main;
	Player player;
	LoadImage loader = new LoadImage();
	
	BufferedImage RegionMap;
	BufferedImage BadgeCase;
	boolean curImg;
	
	public KeyItemsPanel(Main main, Player player) {
		
		this.main = main;
		this.player = player;
		
		try {
			RegionMap = loader.loadImage("regionMap.png");
			BadgeCase = loader.loadImage("badges0.png");
		} catch (IOException e) {
		}
		curImg = true;
		//true means map
		//false means badge case
	}
	
	
	
	public void updateBadgeCount() {
		try {
			BadgeCase = loader.loadImage("badges"  + player.getBadges()+ ".png" );
		} catch (IOException e) {
		}
	}
	
	public void setCurImg(boolean b) {
		curImg = b;
	}
	
	public void paintComponent (Graphics g) {
		if (curImg)
			g.drawImage(RegionMap, 0,0, null);
		else {
			updateBadgeCount();
			g.drawImage(BadgeCase, 0,0, null);
	}}
	
	
}

//add method in itemButton to do main.openMap and main.OpenBadgeCase
//method in main that does it and sets curImg to whatever and then sets content pane to this




//
//NPC 201trainer1 = new NPC ("gary", new Rectangle(710*3, 235*3,60,75),"left","So this is your first battle eh..?","Wow, you're a natural!",null);
//201trainer1.generateParty(1,5,8);
//NPC 201trainer2 = new NPC("reginald", new Rectangle(920*3,135*3,45,76),"left","Let's battle!","Darn it, I lost",null);
//201trainer2.generateParty(1,5,8);
//
//
//NPC 202trainer1 = new NPC("joe", new Rectangle(370*3,260*3,45,75), "down", "Hey hey hey!", "Hey hey hey!",null);
//202trainer1.generateParty(2,6,11);
//NPC 202trainer2= new NPC ("jon", new Rectangle(125*3,200*3,45,75), "down", "Look at my hat!", "My distraction didn't work ...",null);
//202trainer2.generateParty(2,6,11);
//NPC 202trainer3 = new NPC("mikey", new Rectangle(430*3,85*3,45,75), "down", "Wonderful weather we're having!", "The sun is warm",null);
//202trainer3.generateParty(2,7,13);
//
//
//NPC 203trainer1 = new NPC("joanna", new Rectangle(250*3,260*3,45,75),"down", "Good afternoon young fella", "Dagnabit I lost",null);
//203trainer1.generateParty(2,9,13);
//NPC 203trainer2 = new NPC("ken", new Rectangle(500*3,240*3,45,75),"down", "Konichiwa!", "Sayonara..",null);
//203trainer2.generateParty(2,10,14);
//NPC 203trainer3 = new NPC("quavo", new Rectangle(695*3,225*3,45,75),"left", "Welcome to the forest!", "Head into the cave to the next town",null);
//203trainer3.generateParty(2,11,15);
//
//
//NPC 207trainer1 = new NPC("hiker", new Rectangle(655*3,135*3,45,75),"up", "What a lovely day for a battle", "Guess I can go for a walk..",null);
//207trainer1.generateParty(2,13,16);
//NPC 207trainer2 = new NPC("scout", new Rectangle(655*3,80*3,45,75),"down", "See my twin over there", "We're indentical twins",null);
//207trainer2.generateParty(2,14,16);
//
//
//NPC 208trainer1 = new NPC("hiker", new Rectangle(570*3,237*3,45,75),"down", "Watch your step there, bud", "Stay safe!",null);
//208trainer1.generateParty(2,14,17);
//NPC 208trainer2 = new NPC("jett", new Rectangle(880*3,295*3,45,75),"left", "Woah what do you think you're doing!", "just kidding :p",null);
//208trainer2.generateParty(3,13,17);
//
//
//NPC 204trainer1 = new NPC("lass", new Rectangle(190*3,672*3,45,75),"right", "Floaroma town is north of here", "Safe journeys!",null);
//203trainer1.generateParty(2,9,13);
//NPC 204trainer2 = new NPC("sam", new Rectangle(205*3,440*3,45,75),"left", "I am the stairmaster!", "get it :p",null);
//203trainer1.generateParty(2,9,13);
//NPC 204trainer3 = new NPC("sam", new Rectangle(252*3,340*3,45,75),"right", "Wonder what's on the other side of the sea", "Could it be freedom..?",null);
//207trainer2.generateParty(3,14,16);
//NPC 204trainer4 = new NPC("michelle", new Rectangle(270*3,222*3,45,75),"down", "Keep heading north to find the champ", "Good luck young trainer!",null);
//207trainer2.generateParty(3,15,18);
//NPC 204trainer5 = new NPC("dababy", new Rectangle(245*3,67*3,45,75),"down", "waaahhhhhhhh!", "goo goo ga ga",null);
//208trainer2.generateParty(3,17,21);
//
