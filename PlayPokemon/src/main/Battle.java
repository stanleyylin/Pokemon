package main;

import java.awt.*;

import getimages.LoadImage;
import pokesetup.BlankMon;
import pokesetup.Move;
import pokesetup.Pokemon;

import java.awt.event.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.text.Style;
import javax.swing.text.StyleContext;

import bag.Bag;
import entity.NPC;
import entity.Player;

import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.awt.font.TextAttribute;

@SuppressWarnings("serial")
public class Battle extends JPanel {

	private boolean wild;
	private Main main;
	private Random random = new Random();

	private Font font = new Font("Pokemon GB", Font.PLAIN, 22);
	private BufferedImage background; // Background image
	private BufferedImage[] battleStats;

	private boolean intro; //if the intro is playing
	private boolean isTrainer; //if it is a trainer or not
	
	private Player player; //player obj
	private int playerCurr; //current players pokemon
	private NPC opponent; //opponent as an NPC obj
	private int oppCurr;//opponents current pokemon

	private boolean curLevelUp; //if you leveled up
	private boolean isConfuse;//if you are confused

	private int currMoveNo; //the selected move number
	private String currMove; //the selected move
	
	
	//	int[] curPlayerStats= {0,0,0,0};
	//	int[] curEnemyStats= {0,0,0,0};
	//attack,defense,special attack,special defense


	// ANIMATION
	private static Timer timer; // Timer for animation
	private static boolean timerOn; // Is the timer on?
	// Game States
	private static int gameState; 
	// 0 - player select (static), 1 - intro, 2 - calling enemy pokemon,
	// 3 - calling player pokemon, 4 - player move, 5 - opponent move, 6 - player faint, 
	// 7 - opponent faint, 8 - game end
	private static int counter;
	private static float[] opacity;
	private String message; //current message to be displayed
	private boolean playerIsFainted; //if the player's mon is fainted
	private boolean opponentIsFainted; //if the opponent's mon is fainted
	private boolean isWild; //if the pokemon is wild or not
	private boolean justEvolved = false; //if the pokemon has just evolved
	private double curCatchChance = 0;//chance of catching current pokemon

	private ImageHolder playerBall; //image of players pokeball
	private ImageHolder opponentBall;//image of opponents pokeball
	
	BufferedImage[] statuses = new BufferedImage[5]; //image array of all the statuses
	static Button back; //back button
	static Button[] buttons; //main buttons (fight,pokemon,item,run)
	static MoveSelect[] moves;//move buttons

	//constructor
	public Battle(Main main, Player player)
	{
		this.main = main;
		this.player = player;
		playerCurr = 0;
//		this.opponent = opponent;
		this.isConfuse = false;
		oppCurr = 0;
		setPreferredSize(new Dimension(GamePanel.screenWidth, GamePanel.screenHeight));
		setLayout(null);
		setBackground(Color.BLACK);
		battleStats = new BufferedImage[7];
		playerIsFainted = false;
		opponentIsFainted = false;
		isWild = false;
		isTrainer = false;
		playerBall = new ImageHolder(0);
		opponentBall = new ImageHolder(1);

		loadImages();

		gameState = 1;
		timer = new Timer(30, new TimerEventHandler ());
		timerOn = true;
		//		timer.start();
		counter = 0;

	}
	
	//method to load images on screen
	public void loadImages()
	{
		LoadImage loader = new LoadImage();
		// Background
		try
		{
			background = loader.loadImage("res/battle/grass.png");
		}
		catch(IOException e) {}
		// Enemy Stats
		try
		{
			battleStats[0] = loader.loadImage("res/battle/battle stats.png").getSubimage(15, 56, 316, 44);
			battleStats[0] = loader.resize(battleStats[0], 442, 62);
		}
		catch(IOException e) {}
		// Player Stats
		try
		{
			battleStats[1] = loader.loadImage("res/battle/battle stats.png").getSubimage(15, 110, 296, 63);
			battleStats[1] = loader.resize(battleStats[1], 415, 88);
		}
		catch(IOException e) {}
		// Blue level bar
		try
		{
			battleStats[2] = loader.loadImage("res/battle/battle stats.png").getSubimage(161, 10, 94, 4);
			battleStats[2] = loader.resize(battleStats[2], 278, 4);
		}
		catch(IOException e) {}
		// Green health bar
		try
		{
			battleStats[3] = loader.loadImage("res/battle/battle stats.png").getSubimage(161, 18, 119, 5);
			battleStats[3] = loader.resize(battleStats[3], 165, 6);
		}
		catch(IOException e) {}
		// Yellow health bar (Below 50% health)
		try
		{
			battleStats[4] = loader.loadImage("res/battle/battle stats.png").getSubimage(161, 25, 119, 5);
			battleStats[4] = loader.resize(battleStats[4], 165, 6);
		}
		catch(IOException e) {}
		// Red health bar (Below 20% health)
		try
		{
			battleStats[5] = loader.loadImage("res/battle/battle stats.png").getSubimage(161, 32, 119, 5);
			battleStats[5] = loader.resize(battleStats[5], 165, 6);
		}
		catch(IOException e) {}
		// Pokeballs
		try
		{
			battleStats[6] = loader.loadImage("res/battle/battle stats.png").getSubimage(106, 16, 19, 19);
			battleStats[6] = loader.resize(battleStats[6], 25, 25);
		}
		catch(IOException e) {}
		// Statuses
		try
		{
			statuses[0] = loader.loadImage("res/battle/statuses.png").getSubimage(0, 0, 117, 51);
			statuses[0] = loader.resize(statuses[0], 59, 26);
			statuses[1] = loader.loadImage("res/battle/statuses.png").getSubimage(133, 0, 117, 51);
			statuses[1] = loader.resize(statuses[1], 59, 26);
			statuses[2] = loader.loadImage("res/battle/statuses.png").getSubimage(0, 74, 117, 51);
			statuses[2] = loader.resize(statuses[2], 59, 26);
			statuses[3] = loader.loadImage("res/battle/statuses.png").getSubimage(133, 74, 117, 51);
			statuses[3] = loader.resize(statuses[3], 59, 26);
			statuses[4] = loader.loadImage("res/battle/statuses.png").getSubimage(0, 149, 117, 51);
			statuses[4] = loader.resize(statuses[4], 59, 26);
		}
		catch(IOException e) {}


		// Back Button
		try
		{
			BufferedImage back1 = loader.loadImage("res/battle/back1.png");
			back1 = back1.getSubimage(0, 0, 1050, 219);
			back1 = loader.resize(back1, 194, 40);
			BufferedImage back2 = loader.loadImage("res/battle/back2.png");
			back2 = back2.getSubimage(0, 0, 1050, 219);
			back2 = loader.resize(back2, 194, 40);
			back = new Button(this, back1, back2, 194, 40, true);
		}
		catch(IOException e) {}
		back.setBounds(847, 526, 194, 40);

		// Main Buttons
		buttons = new Button[4];
		BufferedImage buttonSheet = null;
		BufferedImage temp1;
		BufferedImage temp2;

		try
		{
			buttonSheet = loader.loadImage("res/battle/battlebuttons.png");
		}
		catch(IOException e) {}

		// Fight Button
		temp1 = loader.resize(buttonSheet.getSubimage(26, 0, 74, 46), 160, 100);
		temp2 = loader.resize(buttonSheet.getSubimage(158, 0, 74, 46), 160, 100);
		buttons[0] = new Button(this, temp1, temp2, 160, 100, true);
		buttons[0].setBounds(59, 595, 160, 100);

		// Pokemon button
		temp1 = loader.resize(buttonSheet.getSubimage(14, 46, 102, 47), 220, 101);
		temp2 = loader.resize(buttonSheet.getSubimage(145, 46, 102, 47), 220, 101);
		buttons[1] = new Button(this, temp1, temp2, 220, 101, true);
		buttons[1].setBounds(320, 595, 220, 101);

		// Bag button
		temp1 = loader.resize(buttonSheet.getSubimage(32, 96, 88, 41), 190, 89);
		temp2 = loader.resize(buttonSheet.getSubimage(164, 96, 88, 41), 190, 89);
		buttons[2] = new Button(this, temp1, temp2, 190, 89, true);
		buttons[2].setBounds(650, 601, 190, 89);

		// Run button
		temp1 = loader.resize(buttonSheet.getSubimage(33, 143, 60, 44), 129, 95);
		temp2 = loader.resize(buttonSheet.getSubimage(162, 143, 60, 44), 129, 95);
		buttons[3] = new Button(this, temp1, temp2, 129, 95, true);
		buttons[3].setBounds(894, 606, 129, 95);

		MoveSelect.setImages();
		moves = new MoveSelect[4];
		for(int i = 0; i < 4; i++)
		{
			moves[i] = new MoveSelect(this);
			moves[i].setBounds(4+268*i, 598, 268, 102);
		}
	}

	// TimeEventHandler class is for the timer.
	private class TimerEventHandler implements ActionListener
	{
		
//		private String name = opponent.getName();
		private int enemyAttack;
		// This method is called by the Timer, taking an ActionEvent as a parameter and returning void.
		public void actionPerformed (ActionEvent event)
		{
			
			//gamestate where the pokemon is being caught or running
			if (gameState == -1) {
				
				if (isWild) {
					if (counter == 0)
						message = "got away safely!";
					else if (counter >=50) {
						counter = 0;
						message = "";
						endBattle(false);
						timer.stop();
						return;
					}
				}
				else {
					if (counter == 0)
						message = "you can't run from a trainer!";
					else if (counter >=50) {
						counter = 0;
						message = "";
						gameState = 0;
						showButtons();
						timer.stop();
					}
				}
				
				
				
			}
			
			
			

			//gamestate where ytou are starting the battle
			if(gameState == 1)
			{
				message = "You are being challenged!";
				if(counter == 50)
				{
					gameState = 2;
					counter = 0;
				}
			}

			//gamestate where opp is throwing out their mon
			if (gameState == 2) {
				message = "Opponent has sent out " + opponent.getParty()[oppCurr].getName() + "!";
				if(counter == 0 || counter == 1)
				{
					opponentBall.setVisible(true, 793, 72);
				}
				if(counter > 0 && counter <= 30 && counter%3==0)
				{
					opponentBall.nextImage();
					opponentBall.setY(opponentBall.getY()+18);
				}
				if (counter == 50) 
				{
					opponentBall.reset();
					counter = 0;
					gameState = 0;
					message = "";
					if(intro)
						gameState = 3;
					else
					{
						showButtons();
						timer.stop();
					}
				}
			}
			
			//gaemstate where you are throwing our your mon
			if (gameState == 3) {
				message  = "Go get them, " + player.getParty()[playerCurr].getNickName();

				if(counter == 0 || counter == 1)
				{
					playerBall.setVisible(true, 408, 265);
				}
				if(counter > 0 && counter <= 30 && counter%3==0)
				{
					playerBall.nextImage();
					playerBall.setY(playerBall.getY()+21);
				}
				
				if (counter == 50) {
					counter = 0;
					playerBall.reset();
					message = "";
					gameState = 0;
					intro = false;
					showButtons();
					timer.stop();
				}
			}


			//gaemstate where you use move (also checks for statuses & stat modifiers)
			if (gameState == 4) 
			{
				if (counter == 0)
				{
		
					if (player.getParty()[playerCurr].getStatus() == Pokemon.Status.FREEZE) {
						message = "" + player.getParty()[playerCurr].getNickName() + " is frozen.";
					}
					else if (player.getParty()[playerCurr].getStatus() == Pokemon.Status.SLEEP)
						message = "" + player.getParty()[playerCurr].getNickName() + " is asleep.";
					else if (player.getParty()[playerCurr].getStatus() == Pokemon.Status.CONFUSED)
						message = "" + player.getParty()[playerCurr].getNickName() + " is confused.";
					else if (player.getParty()[playerCurr].getStatus() == Pokemon.Status.PARALYSIS)
						message = "" + player.getParty()[playerCurr].getNickName() + " is paralyzed";
					else if (player.getParty()[playerCurr].getCurMoves()[currMoveNo].getCurPP() == 0)
						message = "This move is out of pp!";
					else
						message = player.getParty()[playerCurr].getNickName() + " has used " + currMove + "!";

				}

				if (counter == 75 && findMoveCategory(currMove).equals("Status")) 
				{
					Move curMove = BlankMon.getMoveList().get(currMove);
					if (curMove.getStatMod()[0] > 0)
						message = "Enemy's attack has increased!";
					else if (curMove.getStatMod()[0] < 0)
						message = "Enemy's attack has fallen!";

					if (curMove.getStatMod()[1] > 0)
						message = "Enemy's defense has increased!";
					else if (curMove.getStatMod()[1] < 0)
						message = "Enemy's defense has fallen!";

					if (curMove.getStatMod()[2] > 0)
						message = "Enemy's sp. attack has increased!";
					else if (curMove.getStatMod()[2] < 0)
						message = "Enemy's sp. attack has fallen!";

					if (curMove.getStatMod()[3] > 0)
						message = "Enemy's sp. defense has increased!";
					else if (curMove.getStatMod()[3] < 0)
						message = "Enemy's sp. defense has fallen!";
				}
				if(counter == 50)
				{
					if (player.getParty()[playerCurr].getStatus() != Pokemon.Status.FREEZE && player.getParty()[playerCurr].getStatus() != Pokemon.Status.SLEEP 
							&& player.getParty()[playerCurr].getStatus()!=Pokemon.Status.CONFUSED && player.getParty()[playerCurr].getStatus() != Pokemon.Status.PARALYSIS)
						pAttack(moves[currMoveNo].getName());

					int paraChance = 1 + (int)(Math.random() * (4));
					if (player.getParty()[playerCurr].getStatus() == Pokemon.Status.PARALYSIS && paraChance == 4) {
						message = "" + player.getParty()[playerCurr].getNickName() + " has gotten over its paralysis!";
						player.getParty()[playerCurr].setStatus(null);
					}
					if (player.getParty()[playerCurr].getStatus() == Pokemon.Status.PARALYSIS)
						message = "" + player.getParty()[playerCurr].getNickName() + " is paralyzed. It can't move!";

					isConfuse = random.nextBoolean();
					if (player.getParty()[playerCurr].getStatus() == Pokemon.Status.CONFUSED && isConfuse)
						message = "" + player.getParty()[playerCurr].getStatus() + " has hit themself in confusion!";
					else if (player.getParty()[playerCurr].getStatus() == Pokemon.Status.CONFUSED && !isConfuse)
						message = "" + player.getParty()[playerCurr].getNickName() + " has used " + currMove + "!";

				}
				else if (counter == 100 && player.getParty()[playerCurr].getStatus() == Pokemon.Status.CONFUSED) {
					if (isConfuse)
						player.getParty()[playerCurr].attack(currMoveNo, player.getParty()[playerCurr]);
					else if (player.getParty()[playerCurr].getCurMoves()[currMoveNo].getCurPP() == 0)
						System.out.println();
					else
						pAttack(moves[currMoveNo].getName());

				}
				else if (counter == 75 && player.getParty()[playerCurr].getStatus() == Pokemon.Status.FREEZE) {
					int chanceOfThaw = 1 + (int)(Math.random() * (5));
					if (chanceOfThaw == 4) {
						message = "" + player.getParty()[playerCurr].getNickName() + " has thawed out!"; 
						player.getParty()[playerCurr].setStatus(null);
					}

				}

				else if (counter == 75 && player.getParty()[playerCurr].getStatus() == Pokemon.Status.SLEEP) {
					int chanceOfWake = 1 + (int) (Math.random()*3);
					if (chanceOfWake == 2) {
						message = "" + player.getParty()[playerCurr].getNickName() + " has woken up!";
						player.getParty()[playerCurr].setStatus(null);
					}
				}

				else if (counter == 75 && (player.getParty()[playerCurr].getStatus() == Pokemon.Status.BURN  || player.getParty()[playerCurr].getStatus() == Pokemon.Status.POISON)) {
					if (player.getParty()[playerCurr].getStatus() == Pokemon.Status.POISON)
						message = "" + player.getParty()[playerCurr].getNickName() + " takes damage from poison.";
					if (player.getParty()[playerCurr].getStatus() == Pokemon.Status.BURN)
						message = "" + player.getParty()[playerCurr].getNickName() + " takes damage from its burn.";
					repaint();
				}
				else if (counter == 100 && (player.getParty()[playerCurr].getStatus() == Pokemon.Status.BURN  || player.getParty()[playerCurr].getStatus() == Pokemon.Status.POISON)) {

					addPoisonOrBurn(player.getParty()[playerCurr]);
					if (checkFaint(player.getParty()[playerCurr])) {
						pokeFaint(player.getParty()[playerCurr]);
						repaint();
						gameState = 6;
					}

				}
				else if (counter == 100 && player.getParty()[playerCurr].getStatus() == Pokemon.Status.CONFUSED) {
					if (isConfuse)
						player.getParty()[playerCurr].attack(currMoveNo, player.getParty()[playerCurr]);
					else
						pAttack(moves[currMoveNo].getName());

				}

				else if (counter == 150)
				{
					counter = 0;
					if (checkFaint(opponent.getParty()[oppCurr])) {
						pokeFaint(opponent.getParty()[oppCurr]);
						repaint();
						gameState = 7;
					}
					else

						gameState = 5;

					return;
				}
			}
			
			//gamestate where enym is using a move (checks for statuses & stat modifiers)
			else if (gameState == 5) {
				if(counter == 0)
				{
					enemyAttack = (int) (Math.random() * 4);
					while (opponent.getParty()[oppCurr].getCurMoves()[enemyAttack] == null)
						enemyAttack--;

					if (opponent.getParty()[oppCurr].getStatus() == Pokemon.Status.FREEZE) {
						message = "" + opponent.getParty()[oppCurr].getName() + " is frozen.";
					}
					else if (opponent.getParty()[oppCurr].getStatus() == Pokemon.Status.SLEEP)
						message = "" + opponent.getParty()[oppCurr].getName() + " is asleep.";
					else if (opponent.getParty()[oppCurr].getStatus() == Pokemon.Status.CONFUSED)
						message = "" + opponent.getParty()[oppCurr].getName() + " is confused.";
					else if (opponent.getParty()[oppCurr].getStatus() == Pokemon.Status.PARALYSIS)
						message = "" + player.getParty()[oppCurr].getName() + " is paralyzed";
					else
						message = "" + opponent.getParty()[oppCurr].getName() + " has used " + opponent.getParty()[oppCurr].getCurMoves()[enemyAttack].getName();
				}

				if (counter == 75 && opponent.getParty()[oppCurr].getCurMoves()[enemyAttack].getCategory().equals("Status")) {

					Move curMove = opponent.getParty()[oppCurr].getCurMoves()[enemyAttack];
					if (curMove.getStatMod()[0] > 0)
						message = "Your attack has increased!";
					else if (curMove.getStatMod()[0] < 0)
						message = "Your attack has fallen!";

					if (curMove.getStatMod()[1] > 0)
						message = "Your defense has increased!";
					else if (curMove.getStatMod()[1] < 0)
						message = "Your defense has fallen!";

					if (curMove.getStatMod()[2] > 0)
						message = "Your sp. attack has increased!";
					else if (curMove.getStatMod()[2] < 0)
						message = "Your sp. attack has fallen!";

					if (curMove.getStatMod()[3] > 0)
						message = "Your sp. defense has increased!";
					else if (curMove.getStatMod()[3] < 0)
						message = "Your sp. defense has fallen!";


				}

				else if(counter == 50)
				{
					if (opponent.getParty()[oppCurr].getStatus() != Pokemon.Status.FREEZE && opponent.getParty()[oppCurr].getStatus()!= Pokemon.Status.SLEEP
							&& opponent.getParty()[oppCurr].getStatus() != Pokemon.Status.CONFUSED && opponent.getParty()[oppCurr].getStatus() != Pokemon.Status.PARALYSIS)
						oAttack(enemyAttack);

					int paraChance = 1 + (int)(Math.random() * (4));
					if (opponent.getParty()[oppCurr].getStatus() == Pokemon.Status.PARALYSIS && paraChance == 4) {
						message = "" + opponent.getParty()[oppCurr].getName() + " has gotten over its paralysis!";
						opponent.getParty()[oppCurr].setStatus(null);
					}
					if (opponent.getParty()[oppCurr].getStatus() == Pokemon.Status.PARALYSIS)
						message = "" + opponent.getParty()[oppCurr].getName() + " is paralyzed. It can't move!";


					isConfuse = random.nextBoolean();
					if (opponent.getParty()[oppCurr].getStatus() == Pokemon.Status.CONFUSED && isConfuse)
						message = "" + opponent.getParty()[oppCurr].getStatus() + " has hit themself in confusion!";
					else if (opponent.getParty()[oppCurr].getStatus() == Pokemon.Status.CONFUSED && !isConfuse)
						message = "" + opponent.getParty()[oppCurr].getName() + " has used " + currMove + "!";
				}

				else if (counter == 75 && opponent.getParty()[oppCurr].getStatus() == Pokemon.Status.FREEZE) {
					int chanceOfThaw = 1 + (int)(Math.random() * (5));
					if (chanceOfThaw == 5) {
						message = "" + opponent.getParty()[oppCurr].getName() + " has thawed out!"; 
						opponent.getParty()[oppCurr].setStatus(null);
					}

				}

				else if (counter == 75 && opponent.getParty()[oppCurr].getStatus() == Pokemon.Status.SLEEP) {
					int chanceOfWake = 1 + (int) (Math.random()*3);
					if (chanceOfWake == 3) {
						message = "" + opponent.getParty()[oppCurr].getName() + " has woken up!";
						opponent.getParty()[oppCurr].setStatus(null);
					}
				}

				else if (counter == 75 && (opponent.getParty()[oppCurr].getStatus() == Pokemon.Status.BURN  || opponent.getParty()[oppCurr].getStatus() == Pokemon.Status.POISON)) {
					if (opponent.getParty()[oppCurr].getStatus() == Pokemon.Status.POISON)
						message = "" + opponent.getParty()[oppCurr].getNickName() + " takes damage from poison.";
					if (opponent.getParty()[oppCurr].getStatus() == Pokemon.Status.BURN)
						message = "" + opponent.getParty()[oppCurr].getNickName() + " takes damage from its burn.";
					repaint();
				}

				else if (counter == 100 && opponent.getParty()[oppCurr].getStatus() == Pokemon.Status.CONFUSED) {
					if (isConfuse)
						opponent.getParty()[oppCurr].attack(enemyAttack, opponent.getParty()[oppCurr]);
					else
						oAttack(enemyAttack);

				}

				else if (counter == 100 && (opponent.getParty()[oppCurr].getStatus() == Pokemon.Status.BURN  || opponent.getParty()[oppCurr].getStatus() == Pokemon.Status.POISON)) {

					addPoisonOrBurn(opponent.getParty()[oppCurr]);
					if (checkFaint(opponent.getParty()[oppCurr])) {
						pokeFaint(opponent.getParty()[oppCurr]);
						repaint();
						gameState = 7;
					}

				}
				else if (counter >= 150)
				{
					counter = 0;
					if (checkFaint(player.getParty()[playerCurr])) {
						pokeFaint(player.getParty()[playerCurr]);
						repaint();
						gameState = 6;
					}
					else {
						message = "";
						showButtons();
						gameState = 0;
						timer.stop();
						return;
					}
				}
			}
			
			//gamestat where friendly pokemon has fainted
			if (gameState == 6) {
				if (counter == 0) 
					message = "" + player.getParty()[playerCurr].getNickName() + " has fainted!";
				else if (counter >= 50) {
					if (player.findNextAvailableMon()!=null) 
					{
						main.openPokeSelect();
						counter = 0;
						gameState = 0;
						timer.stop();
					}
					else
					{
						counter = 0;
						message = "You have run out of usable pokemon!";
						playerIsFainted = true;
						repaint();
						gameState = 8;
					}
				}

			}

			//gamestate where enemy pokmemon has fainted
			if (gameState == 7) {
				if (counter == 0) {
					message = "" + opponent.getParty()[oppCurr].getName() + " has fainted!";
					player.getParty()[playerCurr].giveExp(opponent.getParty()[oppCurr]);
					if (player.getParty()[playerCurr].getCurExp() >= player.getParty()[playerCurr].getCurExpThreshold()) {
						player.getParty()[playerCurr].levelUp();
						curLevelUp = true;
					}

				}

				else if (counter == 40 & curLevelUp) {
					message = "" + player.getParty()[playerCurr].getNickName() + " has leveled up!";
					curLevelUp = false;
				}

				else if (counter >=75) {

					if (opponent.findNextAvailableMon() != -1) {
						oppCurr = opponent.findNextAvailableMon();
						message = "";
						repaint();
						gameState = 2;
						counter = 0;

					}
					else {
						message = "opponent has ran out of usable pokemon!";
						opponentIsFainted = true;
						repaint();
						counter = 0;
						gameState = 8;
					}
				}
			}
			//battle ending gamestate
			if (gameState == 8) {
				if (playerIsFainted) {
					if (counter >= 50) {
						counter = 0;
						message = "";
						endBattle(true);
						timer.stop();

						return;
					}
				}
				else if (opponentIsFainted && (checkForEvolve() || justEvolved)) {
					if (counter==50) {
						message = "Your " + player.getParty()[playerCurr].getNickName() + " is evolving!";
					}
					else if (counter == 100) {

						Pokemon newMon = player.getParty()[playerCurr].evolve();
						justEvolved = true;
						player.replace(playerCurr, newMon);
					}
					else if (counter == 115) {

						message = "Your " + player.getParty()[playerCurr].getNickName() + " has become a " + player.getParty()[playerCurr].getName()+"!";
					}
					else if (counter >= 150) {
						justEvolved = false;
						counter = 0;
						message = "";
						endBattle(false);
						timer.stop();
						return;
					}
				}
				else {
					if (counter >=50) {
						counter = 0;
						message = "";
						endBattle(false);
						timer.stop();
						return;
					}
				}
			}
			
			//pokeball gamestate
			if (gameState == 9) {
				if (counter == 0) {
					message = "you tried to catch " + opponent.getParty()[oppCurr].getName();
				}
				if (counter == 50) {
					if (catchPokemon()) {
						message = "you caught it!";
						if (player.findNextPartySlot() >= 0)
						player.addPokemonToParty(opponent.getParty()[oppCurr]);
						else 
							player.addToBox(opponent.getParty()[oppCurr]);
						

						
					}
					else 
						message = "it got away...";
						
				}
				
				else if (counter >= 100) {
	
					counter = 0;
					message = "";
					endBattle(false);
					timer.stop();
					return;
				}
			}



			counter++;
			repaint();
		}
	}

	// BUTTONS
	public void buttonClick(MouseEvent e)
	{
		// Main Button, Fight --> Show the moves
		if(e.getSource().equals(buttons[0]))
		{
			hideButtons();
			showBack();
			showMoves(player.getParty()[playerCurr].getAttacks());
		}
		//Pokemon button (opens pokemenu)
		else if(e.getSource().equals(buttons[1]))
		{
			hideButtons();
			main.openPokeSelect();
		}
		//Bag button (opens bag)
		else if(e.getSource().equals(buttons[2]))
		{
			hideButtons();
			if(isWild)
				main.openBag(2);
			else
				main.openBag(1);

		}
		//run button
		else if (e.getSource().equals(buttons[3])) {
			
			hideButtons();
			
			
				gameState = -1;
				counter = 0;
				timer.start();
				
			
		}
		// Go back to the main buttons
		else if(e.getSource().equals(back))
		{
			hideBack();
			hideMoves();
			//			showBattleScreen();
			showButtons();
		}

		//first move
		else if (e.getSource().equals(moves[0].getJLabel()))
		{
			hideBack();
			hideMoves();
			currMove = moves[0].getName();
			currMoveNo = 0;
			counter = 0;
			gameState = 4;
			timer.start();
		}
		//second move
		else if(e.getSource().equals(moves[1].getJLabel()))
		{
			hideBack();
			hideMoves();
			currMove = moves[1].getName();
			currMoveNo = 1;
			counter = 0;
			gameState = 4;
			timer.start();
		}
		//third move
		else if(e.getSource().equals(moves[2].getJLabel()))
		{
			hideBack();
			hideMoves();
			counter = 0;
			currMove = moves[2].getName();
			currMoveNo = 2;
			gameState = 4;
			timer.start();
		}
		//fourth move
		else if (e.getSource().equals(moves[3].getJLabel()))
		{
			hideBack();
			hideMoves();
			counter = 0;
			currMove = moves[3].getName();
			currMoveNo = 3;
			gameState = 4;
			timer.start();
		}
	}



	//it comes to this when u win/lose the battle
	public void endBattle(boolean isPlayer) {
		if (!isPlayer) {
			player.setPokeDollars(player.getPokeDollars() + 1000);
			if(isTrainer)
			{
				player.addBadges();
				isTrainer = false;
				if(player.getBadges() == 4)
				{
					main.openCongratulations();
					return;
				}
			}
		}
		
		main.setBattling(false);
		main.openGamePanel();

	}
	//attacks the enemy
	public void pAttack(String attack) 
	{
		player.getParty()[playerCurr].attack(currMoveNo, opponent.getParty()[oppCurr]); //attacks the opponent
		player.getParty()[playerCurr].getCurMoves()[currMoveNo].useMove(); //subtracts 1 pp
	}
	//enemy attacks you
	public void oAttack(int enemyAttack) {
		opponent.getParty()[oppCurr].attack(enemyAttack, player.getParty()[playerCurr]); //attacks the player
	}
	//checks if pokemon is fainted
	public boolean checkFaint(Pokemon p1) {
		if (p1.getCurHP()<=0)
			return true;
		return false;
	}
	//what it does if pokemon is fainted
	public void pokeFaint(Pokemon p1) {
		p1.setCurHP(0);
		p1.setIsFainted(true);
	}
	//adds poison or burn damage (1/8 total health)
	public void addPoisonOrBurn (Pokemon p1) {
		int burnDmg = p1.getHPStat()/8;
		p1.setCurHP(p1.getCurHP() - burnDmg);
	}
	//returns count of pokemon in party atm
	public int pokeCount(Pokemon[] party) {
		int count = 0;
		for (Pokemon p1 : party) {
			if (p1 != null)
				count++;
		}
		return count;
	}

	//finds category of move
	public String findMoveCategory(String s) {
		return BlankMon.getMoveList().get(s).getCategory();
	}

	// Shows/Hides back button
	// void, no parameters
	public void showBack()
	{
		this.add(back);
		this.revalidate();
		this.repaint();
	}
	public void hideBack()
	{
		Container parent = back.getParent();
		if (parent != null) {
			parent.remove(back);
			parent.revalidate();
			parent.repaint();
		}
	}
	// Shows/hides the main buttons (fight/pokemon/run/bag)
	// void, no parameters
	public void hideButtons()
	{
		for(Button b : buttons)
		{
			try
			{
				Container parent = b.getParent();
				parent.remove(b);
				parent.revalidate();
				parent.repaint();
			} catch(NullPointerException e) {}
		}
	}
	public void showButtons()
	{
		for(Button b : buttons)
		{

			this.add(b);
			this.revalidate();
			this.repaint();
		}
	}
	// Shows/hides moves that player can use
	// void, no parameters
	public void showMoves(Move[] m)
	{
		for(int i = 0; i < m.length; i++)
		{
			if(m[i] != null) {
				moves[i].updatePokemon(m[i].getName(), m[i].getType(), m[i].getCurPP(), m[i].getPP());
				this.add(moves[i]);
				this.revalidate();
				this.repaint();
				moves[i].setDisplayed(true);
				moves[i].repaint();
			}
		}
	}
	public void hideMoves()
	{
		for(MoveSelect m : moves)
		{
			if(m.getName()!= null)
			{
				Container parent = m.getParent();
				try
				{
					parent.remove(m);
					parent.revalidate();
					parent.repaint();
				} catch(NullPointerException e) {}
				m.setDisplayed(false);
				m.repaint();
			}
		}
	}

	public void pokeClicked(MouseEvent e) {
		System.out.println();
	}
	
	// When items are used these are their effects in battle
	// Takes in String which is the item, returns void
	public void useItem(String s) {

		main.openBattle();
		
		if (s.equals("Potion")) {
			player.getParty()[playerCurr].heal(20);
			gameState = 5;

		}
		if (s.equals("Super Potion")) {
			player.getParty()[playerCurr].heal(50);
			gameState = 5;

		}
		
		if (s.equals("Hyper Potion")) {
			player.getParty()[playerCurr].heal(200);
			gameState = 5;

		}
		
		if (s.equals("Full Heal")) {
			player.getParty()[playerCurr].setStatus(null);
			gameState = 5;

		}
		
		if (s.equals("Full Restore")) {
			player.getParty()[playerCurr].heal();
			gameState = 5;

		}
		
		if (s.equals("Ether")) {
			try
			{
				int n = getLeastPPMove();
				int cur = player.getParty()[playerCurr].getCurMoves()[n].getCurPP();
				player.getParty()[playerCurr].getCurMoves()[n].setCurPP(cur + 10);
			} catch(Exception e) {}
			gameState = 5;

		}
		
		if (s.equals("Elixir")) {
			try
			{
				for (Move m : player.getParty()[playerCurr].getCurMoves())
					m.setCurPP(m.getCurPP() + 10);
			} catch(Exception e) {}
			gameState = 5;

		}
		
		
		if(s.equals("Poke Ball")) {
			gameState = 9;
			curCatchChance = 1;
		}
		if(s.equals("Great Ball")) {
			main.openBattle();
			gameState = 9;
			curCatchChance = 1.5;
		}
		if(s.equals("Ultra Ball")) {
			gameState = 9;
			curCatchChance = 2;
		}
		if(s.equals("Master Ball")) {
			gameState = 9;
			curCatchChance = 255;
		}

		counter = 0;
		timer.start();
	}
	
	// Chance to catch pokemon
	public boolean catchPokemon() {
		return random.nextBoolean();
		
	}

	// Checks if pokemon can evolve
	public boolean checkForEvolve() {
		if (player.getParty()[playerCurr].getIfEvolve() && player.getParty()[playerCurr].getLevel() >= player.getParty()[playerCurr].getEvolveLvl()) 
			return true;

		else return false;
	}

	//go back to main
	public void backToMain() {
		gameState = 0;
		showButtons();
		main.openBattle();
	}
	
	//open battle
	public void openBattle()
	{
		gameState = 0;
		showButtons();
		main.openBattle();
	}

	//gets least pp move
	public Integer getLeastPPMove() {
		TreeMap<Integer, Move> tm = new TreeMap<Integer,Move>();
		tm.put(player.getParty()[playerCurr].getCurMoves()[0].getCurPP(), player.getParty()[playerCurr].getCurMoves()[0]);
		tm.put(player.getParty()[playerCurr].getCurMoves()[1].getCurPP(), player.getParty()[playerCurr].getCurMoves()[1]);
		tm.put(player.getParty()[playerCurr].getCurMoves()[2].getCurPP(), player.getParty()[playerCurr].getCurMoves()[2]);
		tm.put(player.getParty()[playerCurr].getCurMoves()[3].getCurPP(), player.getParty()[playerCurr].getCurMoves()[3]);

		return tm.firstKey();
	}

	//sets next mon as currmon once pokemon dies
	public void setNextMon(int i) 
	{
		playerCurr = i;
		main.openBattle();
		gameState = 3;
		timer.start();
	}

	// Draw methods: Displays background, player/opponent pokemons and their stats.
	public void paintComponent(Graphics g) {
		//static
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(background, 0, 0, null);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
		g2.setColor(Color.BLACK);
		g2.fillRect(0, 586, 1080, 134);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

		if(intro)
		{
			if(gameState == 2)
			{
				g2.drawImage(battleStats[0], 0, 110, null);
				drawOStats(g2);
				loadOpponentMon(g2);
			}
			else if(gameState == 3)
			{
				g2.drawImage(battleStats[1], Main.screenWidth-battleStats[1].getWidth(), 338, null);
				drawPStats(g2);
				loadPlayerMon(g2);
				g2.drawImage(battleStats[0], 0, 110, null);
				drawOStats(g2);
				loadOpponentMon(g2);
			}
		}

		if(gameState != 1 && !intro)
		{
			if (!playerIsFainted) {
				g2.drawImage(battleStats[1], Main.screenWidth-battleStats[1].getWidth(), 338, null);
				drawPStats(g2);
				loadPlayerMon(g2);
			}
			if (!opponentIsFainted) {
				g2.drawImage(battleStats[0], 0, 110, null);
				drawOStats(g2);
				loadOpponentMon(g2);
			}
		}
		
		if (message != null) 
		{
			updateText(g2);
		}
	}

	//loads players mon graphics
	public void loadPlayerMon(Graphics2D g2)
	{

		if(playerBall.getVisible())
		{
			g2.drawImage(playerBall.getImage(), playerBall.getX(), playerBall.getY(), null);
		}
		else if(!playerBall.getVisible() ||counter > 1)
		{
			int pX = 400 - (player.getParty()[playerCurr].getBack().getWidth()/2);
			int pY = 610-player.getParty()[playerCurr].getBack().getHeight();
			g2.drawImage(player.getParty()[playerCurr].getBack(), pX, pY, null);
	
		}
	}
	//updates text
	public void updateText(Graphics2D g)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.setFont(font);
		g2.setColor(Color.WHITE);
		g2.drawString(message, 69, 648);
	}
	//loads opp mon
	public void loadOpponentMon(Graphics2D g2)
	{
		if(opponentBall.getVisible())
		{
			g2.drawImage(opponentBall.getImage(), opponentBall.getX(), opponentBall.getY(), null);
		}
		else if(!opponentBall.getVisible() || counter > 1)
		{
			int oX = 793 - (opponent.getParty()[oppCurr].getFront().getWidth()/2);
			int oY = 320 - opponent.getParty()[oppCurr].getFront().getHeight();
			
			g2.drawImage(opponent.getParty()[oppCurr].getFront(), oX, oY, null);
		}
	}
	//draws opp pokemon stats
	public void drawOStats(Graphics2D g2)
	{
		if(opponent.getParty()[oppCurr] != null)
		{
			BufferedImage barColor = null;
			if(opponent.getParty()[oppCurr].getCurHP() / opponent.getParty()[oppCurr].getHPStat() <= 0.2)
				barColor = battleStats[4];
			else if(opponent.getParty()[oppCurr].getCurHP() / opponent.getParty()[oppCurr].getHPStat() <= 0.5)
				barColor = battleStats[5];
			else
				barColor = battleStats[3];

			int width = (int)((double)battleStats[3].getWidth() * ((double)opponent.getParty()[oppCurr].getCurHP() / (double)opponent.getParty()[oppCurr].getHPStat()));
				
			try
			{
				if(width > 0)
				{
					BufferedImage drawBar = barColor.getSubimage(0, 0, width, barColor.getHeight());
					g2.drawImage(drawBar, 152, 144, this);
				}
			}catch(RasterFormatException e) {}

			Map<TextAttribute, Object> attributes = new HashMap<TextAttribute, Object>();
			attributes.put(TextAttribute.TRACKING, -0.15);
			attributes.put(TextAttribute.SIZE, 22);
			g2.setFont(font.deriveFont(attributes));

			// Opponent Name
			g2.setColor(Color.BLACK);
			g2.drawString(opponent.getParty()[oppCurr].getName(), 32, 133);
			g2.setColor(Color.WHITE);
			g2.drawString(opponent.getParty()[oppCurr].getName(), 30, 131);

			// Opponent Level
			g2.setColor(Color.BLACK);
			g2.drawString(Integer.toString(opponent.getParty()[oppCurr].getLevel()), 330, 135);
			g2.setColor(Color.WHITE);
			g2.drawString(Integer.toString(opponent.getParty()[oppCurr].getLevel()), 328, 133);

			// Opponent Pokeballs
			if(!isWild)
			{
				int counter = 0;
				int pokeBallGap = 28;
				for(int i = 0; i < opponent.getParty().length; i++)
				{
					if(opponent.getParty()[i] != null && opponent.getParty()[i].getIsFainted() == false)
					{
						g2.drawImage(battleStats[6], pokeBallGap, 200, null);
						pokeBallGap += battleStats[6].getWidth()+38;
					}
					else
						counter++;
				}
				for(int i = 0; i < counter; i++)
				{
					g2.setColor(Color.BLACK);
					g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
					g2.fillOval(pokeBallGap, 200, 25, 25);
					g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
					pokeBallGap += battleStats[6].getWidth()+38;
				}
			}
			// States
			if(opponent.getParty()[oppCurr].getStatus() == Pokemon.Status.BURN)
				g2.drawImage(statuses[0], 5, 170, null);
			if(opponent.getParty()[oppCurr].getStatus() == Pokemon.Status.POISON)
				g2.drawImage(statuses[1], 86, 170, null);
			if(opponent.getParty()[oppCurr].getStatus() == Pokemon.Status.FREEZE)
				g2.drawImage(statuses[2], 169, 170, null);
			if(opponent.getParty()[oppCurr].getStatus() == Pokemon.Status.SLEEP)
				g2.drawImage(statuses[3], 256, 170, null);
			if(opponent.getParty()[oppCurr].getStatus() == Pokemon.Status.PARALYSIS)
				g2.drawImage(statuses[4], 343, 170, null);


		}
	}
	
	//draws your pokemon stats
	public void drawPStats(Graphics2D g2)
	{
		if(player.getParty()[playerCurr] != null)
		{
			// Player Bars
			BufferedImage barColor = null;
			if(player.getParty()[playerCurr].getCurHP() / player.getParty()[playerCurr].getHPStat() <= 0.2)
				barColor = battleStats[4];
			else if(player.getParty()[playerCurr].getCurHP() / player.getParty()[playerCurr].getHPStat() <= 0.5)
				barColor = battleStats[5];
			else
				barColor = battleStats[3];

			int width = battleStats[3].getWidth() * player.getParty()[playerCurr].getCurHP() / player.getParty()[playerCurr].getHPStat();
			
			try
			{
				if(width > 0)
				{
					BufferedImage drawBar = barColor.getSubimage(0, 0, width, barColor.getHeight());
					g2.drawImage(drawBar, 859, 368, null);
				}
			}catch(RasterFormatException e) {}

			Map<TextAttribute, Object> attributes = new HashMap<TextAttribute, Object>();
			attributes.put(TextAttribute.TRACKING, -0.15);
			attributes.put(TextAttribute.SIZE, 22);
			g2.setFont(font.deriveFont(attributes));

			// Level Bar
			int curLevel = player.getParty()[playerCurr].getCurExp();
			int maxLevel = player.getParty()[playerCurr].getCurExpThreshold();
			int level = (int) ((double)battleStats[2].getWidth() * ((double)curLevel/(double)maxLevel));
			// int level = battleStats[2].getWidth() * (player.getParty()[playerCurr].getCurExp() / player.getParty()[playerCurr].getCurExpThreshold());
			//			System.out.println(level);
			try {
				if(level > 0)
				{
					BufferedImage drawBar = battleStats[2].getSubimage(0, 0, level, battleStats[2].getHeight());
					g2.drawImage(drawBar, 775, 417, null);
				}
			}catch(RasterFormatException e) {}

			// Player Name
			g2.setColor(Color.BLACK);
			g2.drawString(player.getParty()[playerCurr].getNickName(), 718, 359);
			g2.setColor(Color.WHITE);
			g2.drawString(player.getParty()[playerCurr].getNickName(), 716, 357);

			// Player Level
			attributes.put(TextAttribute.TRACKING, -0.05);
			g2.setFont(font.deriveFont(attributes));
			g2.setColor(Color.BLACK);
			g2.drawString(Integer.toString(player.getParty()[playerCurr].getLevel()), 1020, 361);
			g2.setColor(Color.WHITE);
			g2.drawString(Integer.toString(player.getParty()[playerCurr].getLevel()), 1018, 359);

			// Player Health
			attributes.put(TextAttribute.SIZE, 18);
			g2.setFont(font.deriveFont(attributes));
			g2.setColor(Color.BLACK);
			int removeWidth1 = 0;
			int removeWidth2 = 0;
			if(player.getParty()[playerCurr].getCurHP() < 10)
				removeWidth1 = 50;
			else if(player.getParty()[playerCurr].getCurHP() < 100)
				removeWidth1 = 25;

			if(player.getParty()[playerCurr].getHPStat() < 10)
				removeWidth2 = 50;
			else if(player.getParty()[playerCurr].getHPStat() < 100)
				removeWidth2 = 25;
			else 
				removeWidth2 = 20;


			g2.drawString(Integer.toString(player.getParty()[playerCurr].getCurHP()), 880+removeWidth1, 404);
			g2.setColor(Color.WHITE);
			g2.drawString(Integer.toString(player.getParty()[playerCurr].getCurHP()), 878+removeWidth1, 402);
			g2.setColor(Color.BLACK);
			g2.drawString(Integer.toString(player.getParty()[playerCurr].getHPStat()), 994-removeWidth2, 404);
			g2.setColor(Color.WHITE);
			g2.drawString(Integer.toString(player.getParty()[playerCurr].getHPStat()), 992-removeWidth2, 402);

			// States
			if(player.getParty()[playerCurr].getStatus() == Pokemon.Status.BURN)
				g2.drawImage(statuses[0], 676, 434, null);
			if(player.getParty()[playerCurr].getStatus() == Pokemon.Status.POISON)
				g2.drawImage(statuses[1], 760, 434, null);
			if(player.getParty()[playerCurr].getStatus() == Pokemon.Status.FREEZE)
				g2.drawImage(statuses[2], 843, 434, null);
			if(player.getParty()[playerCurr].getStatus() == Pokemon.Status.SLEEP)
				g2.drawImage(statuses[3], 930, 434, null);
			if(player.getParty()[playerCurr].getStatus() == Pokemon.Status.PARALYSIS)
				g2.drawImage(statuses[4], 1015, 434, null);
		}
	}

	// Starts a new battle
	// Takes in the opponent (NPC) and isW (is the pokemon wild?)
	public void newBattle(NPC newOpponent, boolean isW)
	{
		playerCurr = 0;
		opponent = newOpponent;
		oppCurr = 0;
		isWild = isW;
		intro = true;
		playerIsFainted = false;
		opponentIsFainted = false;
		opponent.healParty();
		gameState = 1;
		counter = 0;
		timer.start();
	}

	public void setPlayerCur(int i) {
		this.playerCurr = i;
	}
	public void setTrainer(boolean set)
	{
		isTrainer = set;
	}
	public void refresh()
	{
		revalidate();
		repaint();
	}

	public Pokemon getCurPlayerMon() {
		return player.getParty()[playerCurr];
	}



}
