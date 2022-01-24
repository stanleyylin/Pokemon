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
	private Random random = new Random();

	private Font font = new Font("Pokemon GB", Font.PLAIN, 22);
	BufferedImage background; // Background image
	BufferedImage[] battleStats;

	static JFrame frame;
	static Battle panel;

	boolean playerTurn;

	Player player;
	int playerCurr;
	NPC opponent;
	int oppCurr;

	boolean curLevelUp;
	boolean isConfuse;

	int currMoveNo;
	String currMove;
	//	int[] curPlayerStats= {0,0,0,0};
	//	int[] curEnemyStats= {0,0,0,0};
	//attack,defense,special attack,special defense


	// ANIMATION
	static Timer timer; // Timer for animation
	static boolean timerOn; // Is the timer on?
	// Game States
	static int gameState; 
	// 0 - player select (static), 1 - intro, 2 - calling enemy pokemon,
	// 3 - calling player pokemon, 4 - player move, 5 - opponent move, 6 - player faint, 
	// 7 - opponent faint, 8 - game end
	static int counter;
	static float[] opacity;
	String message;
	boolean playerIsFainted;
	boolean opponentIsFainted;
	boolean isWild;
	boolean justEvolved = false;

	int pokeX = 792;
	int pokeY = 100;
	BufferedImage[] pokeBallSprites = new BufferedImage[17];
	BufferedImage[] statuses = new BufferedImage[5];
	static Button back;
	static Button[] buttons;
	static MoveSelect[] moves;

	// **** To be migrated
	private PokeSelect selectionMenu;
	private Bag bag;

	public Battle(Player player, NPC opponent)
	{
		this.player = player;
		playerCurr = 0;
		this.opponent = opponent;
		this.isConfuse = false;
		oppCurr = 0;
		setPreferredSize(new Dimension(GamePanel.screenWidth, GamePanel.screenHeight));
		setLayout(null);
		setBackground(Color.BLACK);
		LoadImage loader = new LoadImage();
		battleStats = new BufferedImage[7];
		playerIsFainted = false;
		opponentIsFainted = false;
		isWild = false;




		// **** TO BE MIGRATED
		selectionMenu = new PokeSelect(this, player, 0, true);
		bag = new Bag(player, this);
		if (isWild)
			bag.loadScreen(2);
		else
			bag.loadScreen(1);


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

		try 
		{
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("res/PokemonGb-RAeo.ttf")));
			//			Map<TextAttribute, Object> attributes = new HashMap<TextAttribute, Object>();
			//			attributes.put(TextAttribute.TRACKING, -0.1);
			//			font = font.deriveFont(attributes);
		} 
		catch (FontFormatException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}

		// Back Button
		try
		{
			BufferedImage back1 = loader.loadImage("res/battle/back1.png");
			back1 = back1.getSubimage(0, 0, 1050, 219);
			back1 = loader.resize(back1, 194, 40);
			BufferedImage back2 = loader.loadImage("res/battle/back2.png");
			back2 = back2.getSubimage(0, 0, 1050, 219);
			back2 = loader.resize(back2, 194, 40);
			back = new Button(this, back1, back2, 194, 40);
		}
		catch(IOException e) {}
		back.setBounds(847, 526, 194, 40);

		BufferedImage ballSprites = null;
		try
		{
			ballSprites = loader.loadImage("res/battle/pokeballs.png");
		}
		catch(IOException e) {}
		for(int i = 0; i < 17; i++)
		{
			pokeBallSprites[i] = ballSprites.getSubimage(132, 9 + 24*i + 16*i, 24, 24);
		}

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
		buttons[0] = new Button(this, temp1, temp2, 160, 100);
		buttons[0].setBounds(59, 595, 160, 100);

		// Pokemon button
		temp1 = loader.resize(buttonSheet.getSubimage(14, 46, 102, 47), 220, 101);
		temp2 = loader.resize(buttonSheet.getSubimage(145, 46, 102, 47), 220, 101);
		buttons[1] = new Button(this, temp1, temp2, 220, 101);
		buttons[1].setBounds(320, 595, 220, 101);

		// Bag button
		temp1 = loader.resize(buttonSheet.getSubimage(32, 96, 88, 41), 190, 89);
		temp2 = loader.resize(buttonSheet.getSubimage(164, 96, 88, 41), 190, 89);
		buttons[2] = new Button(this, temp1, temp2, 190, 89);
		buttons[2].setBounds(650, 601, 190, 89);

		// Run button
		temp1 = loader.resize(buttonSheet.getSubimage(33, 143, 60, 44), 129, 95);
		temp2 = loader.resize(buttonSheet.getSubimage(162, 143, 60, 44), 129, 95);
		buttons[3] = new Button(this, temp1, temp2, 129, 95);
		buttons[3].setBounds(894, 606, 129, 95);


		MoveSelect.setImages();
		moves = new MoveSelect[4];
		for(int i = 0; i < 4; i++)
		{
			moves[i] = new MoveSelect(this);
			moves[i].setBounds(4+268*i, 598, 268, 102);
		}

		showButtons();
		gameState = 0;
		timer = new Timer(30, new TimerEventHandler ());
		timerOn = true;
		//		timer.start();
		counter = 0;

	}

	// TimeEventHandler class is for the timer.
	private class TimerEventHandler implements ActionListener
	{
		private String name = "Trainer Peppa";
		private int enemyAttack;
		// This method is called by the Timer, taking an ActionEvent as a parameter and returning void.
		public void actionPerformed (ActionEvent event)
		{
			//			if(gameState == 1)
			//			{
			//				if(counter >= 0 && counter <= 10)
			//				{
			//					message = "You are being challenged by " + name + "!";
			//				}
			//				if(counter == 10)
			//				{
			//					gameState = 2;
			//					counter = 0;
			//				}
			//			}
			//			else if (gameState == 2)
			//			{
			//				message = name + " sends out " + oppCurr.getName() + "!";
			//				
			//					
			//			}
			if (gameState == 2) {
				message = "opponent has sent out " + opponent.getParty()[oppCurr].getName();
				if (counter == 50) {
					counter = 0;
					message = "";
					repaint();
					gameState = 0;
					showButtons();
					timer.stop();
				}
			}
			if (gameState == 3) {
				//AT THE MOMENT, SWITCHING MONS WILL END THEIR TURN, SO IT SHOULD B CHANGED SUCH THAT IF YOU
				//WERE TO SWITHC MONS IN BATTLE(OUT OF CHOICE) it continues the mvoe (gamestate goes to 5 rather than 0)
				message  = "Go get them, " + player.getParty()[playerCurr].getNickName();
				if (counter == 50) {
					counter = 0;
					message = "";
					gameState = 0;
					showButtons();
					timer.stop();
				}
			}


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
					else
						message = player.getParty()[playerCurr].getNickName() + " has used " + currMove + "!";

					System.out.println("" + player.getParty()[playerCurr].getCurExp() + "/" + player.getParty()[playerCurr].getCurExpThreshold());
				}

				if (counter == 75 && findMoveCategory(currMove).equals("Status")) {

					Move curMove = BlankMon.getMoveList().get(currMove);
					if (curMove.getStatMod()[0] > 0)
						message = "enemy's attack has increased";
					else if (curMove.getStatMod()[0] < 0)
						message = "enemy's attack has fallen";

					if (curMove.getStatMod()[1] > 0)
						message = "enemy's defense has increased";
					else if (curMove.getStatMod()[1] < 0)
						message = "enemy's defense has fallen";

					if (curMove.getStatMod()[2] > 0)
						message = "enemy's sp. attack has increased";
					else if (curMove.getStatMod()[2] < 0)
						message = "enemy's sp. attack has fallen";

					if (curMove.getStatMod()[3] > 0)
						message = "enemy's sp. defense has increased";
					else if (curMove.getStatMod()[3] < 0)
						message = "enemy's sp. defense has fallen";


				}

				if(counter == 50)
				{
					if (player.getParty()[playerCurr].getStatus() != Pokemon.Status.FREEZE && player.getParty()[playerCurr].getStatus() != Pokemon.Status.SLEEP 
							&& player.getParty()[playerCurr].getStatus()!=Pokemon.Status.CONFUSED && player.getParty()[playerCurr].getStatus() != Pokemon.Status.PARALYSIS)
						pAttack(moves[currMoveNo].getName());

					int paraChance = 1 + (int)(Math.random() * (4));
					if (player.getParty()[playerCurr].getStatus() == Pokemon.Status.PARALYSIS && paraChance == 4) {
						message = "" + player.getParty() + " has gotten over its paralysis!";
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

				else if (counter == 125)
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
			else if (gameState == 5) {
				if(counter == 0)
				{
					enemyAttack = (int) (Math.random() * 4);

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
						message = "enemy's attack has increased";
					else if (curMove.getStatMod()[0] < 0)
						message = "enemy's attack has fallen";

					if (curMove.getStatMod()[1] > 0)
						message = "enemy's defense has increased";
					else if (curMove.getStatMod()[1] < 0)
						message = "enemy's defense has fallen";

					if (curMove.getStatMod()[2] > 0)
						message = "enemy's sp. attack has increased";
					else if (curMove.getStatMod()[2] < 0)
						message = "enemy's sp. attack has fallen";

					if (curMove.getStatMod()[3] > 0)
						message = "enemy's sp. defense has increased";
					else if (curMove.getStatMod()[3] < 0)
						message = "enemy's sp. defense has fallen";


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
				else if (counter >= 125)
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
			if (gameState == 6) {
				if (counter == 0) {
					System.out.println("teeest");
					message = "" + player.getParty()[playerCurr].getNickName() + " has fainted!";


				}
				else if (counter >= 50) {
					if (player.findNextAvailableMon()!=null) {
						showPokeMenu();
						counter = 0;
						gameState = 0;
						timer.stop();
					}
					else {
						counter = 0;
						message = "You have run out of usable pokemon!";
						playerIsFainted = true;
						repaint();
						gameState = 8;
						//						endBattle(true);
						//						timer.stop();
						//						return;
					}
				}

			}

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
						//						endBattle(false);
						//						timer.stop();
						//						return;
					}
				}
			}
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
						System.out.println("test");

						message = "Your " + player.getParty()[playerCurr].getNickName() + " has become a " + player.getParty()[playerCurr].getName()+"!";
					}
					else if (counter >= 150) {
						justEvolved = false;
						counter = 0;
						message = "";
						endBattle(true);
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
			showPokeMenu();
		}
		//Bag button (opens bag)
		else if(e.getSource().equals(buttons[2]))
		{
			hideButtons();
			showBag();


		}
		// Go back to the main buttons
		else if(e.getSource().equals(back))
		{
			hideBack();
			hideMoves();
			//			showBattleScreen();
			showButtons();
		}

		else if (e.getSource().equals(moves[0].getJLabel()))
		{
			hideBack();
			hideMoves();
			currMove = moves[0].getName();
			currMoveNo = 0;
			gameState = 4;
			timer.start();
		}
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




	public void endBattle(boolean isPlayer) {
		//it comes to this when u win/lose the battle

		//if(isPlayer)
		//trigger the blackkscreen & pokemon centre animation

		//else
		//switch the screen back to overworld and have them give you dialogue + money
	}

	public void pAttack(String attack) 
	{
		player.getParty()[playerCurr].attack(currMoveNo, opponent.getParty()[oppCurr]); //attacks the opponent
		player.getParty()[playerCurr].getCurMoves()[currMoveNo].useMove(); //subtracts 1 pp
	}
	public void oAttack(int enemyAttack) {
		opponent.getParty()[oppCurr].attack(enemyAttack, player.getParty()[playerCurr]); //attacks the player
	}
	public boolean checkFaint(Pokemon p1) {
		if (p1.getCurHP()<=0)
			return true;
		return false;
	}
	public void pokeFaint(Pokemon p1) {
		p1.setCurHP(0);
		p1.setIsFainted(true);
		System.out.println("" + p1.getName() +" has fainted");
	}
	public void addPoisonOrBurn (Pokemon p1) {
		int burnDmg = p1.getHPStat()/8;
		p1.setCurHP(p1.getCurHP() - burnDmg);
	}
	public int pokeCount(Pokemon[] party) {
		int count = 0;
		for (Pokemon p1 : party) {
			if (p1 != null)
				count++;
		}
		return count;
	}

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
		parent.remove(back);
		parent.revalidate();
		parent.repaint();
	}
	// Shows/hides the main buttons (fight/pokemon/run/bag)
	// void, no parameters
	public void hideButtons()
	{
		for(Button b : buttons)
		{
			Container parent = b.getParent();
			parent.remove(b);
			parent.revalidate();
			parent.repaint();
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
			if(m.getName() != null)
			{
				Container parent = m.getParent();
				parent.remove(m);
				parent.revalidate();
				parent.repaint();
				m.setDisplayed(false);
				m.repaint();
			}
		}
	}

	public void pokeClicked(MouseEvent e) {
		System.out.println();
	}

	public void useItem(String s) {

		showBattleScreen();
		if (s.equals("Potion"))
			player.getParty()[playerCurr].heal(20);
		if (s.equals("Super Potion"))
			player.getParty()[playerCurr].heal(50);
		if (s.equals("Hyper Potion"))
			player.getParty()[playerCurr].heal(200);
		if (s.equals("Full Heal"))
			player.getParty()[playerCurr].setStatus(null);
		if (s.equals("Full Restore"))
			player.getParty()[playerCurr].heal();
		if (s.equals("Ether")) {
			int n = getLeastPPMove();
			int cur = player.getParty()[playerCurr].getCurMoves()[n].getCurPP();
			player.getParty()[playerCurr].getCurMoves()[n].setCurPP(cur + 10);
		}
		if (s.equals("Elixir")) {
			for (Move m : player.getParty()[playerCurr].getCurMoves())
				m.setCurPP(m.getCurPP() + 10);
		}


		gameState = 5;
		timer.start();
	}

	public boolean checkForEvolve() {
		if (player.getParty()[playerCurr].getIfEvolve() && player.getParty()[playerCurr].getLevel() >= player.getParty()[playerCurr].getEvolveLvl()) 
			return true;

		else return false;
	}



	public void backToMain() {
		gameState = 0;
		showButtons();
		hideMoves();
		showBattleScreen();
	}

	public Integer getLeastPPMove() {
		TreeMap<Integer, Move> tm = new TreeMap<Integer,Move>();
		tm.put(player.getParty()[playerCurr].getCurMoves()[0].getCurPP(), player.getParty()[playerCurr].getCurMoves()[0]);
		tm.put(player.getParty()[playerCurr].getCurMoves()[1].getCurPP(), player.getParty()[playerCurr].getCurMoves()[1]);
		tm.put(player.getParty()[playerCurr].getCurMoves()[2].getCurPP(), player.getParty()[playerCurr].getCurMoves()[2]);
		tm.put(player.getParty()[playerCurr].getCurMoves()[3].getCurPP(), player.getParty()[playerCurr].getCurMoves()[3]);

		return tm.firstKey();
	}

	public void setNextMon(int i) 
	{
		playerCurr = i;
		showBattleScreen();
		gameState = 3;
		timer.start();
	}

	// Changing Screens
	public void showPokeMenu() {
		panel.setVisible(false);
		frame.setContentPane(selectionMenu);
		selectionMenu.setVisible(true);
		selectionMenu.updatePokemon();
		frame.pack();
	}
	public void showBattleScreen() {
		frame.setContentPane(this);
		this.setVisible(true);
		frame.pack();
	}
	public void showBag() {
		frame.setContentPane(bag);
		bag.setVisible(true);
		frame.pack();
	}

	// Draw methods: Displays background, player/opponent pokemons and their stats.
	public void paintComponent(Graphics g) {
		//static
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(background, 0, 0, null);
		g2.drawImage(battleStats[0], 0, 110, null);
		g2.drawImage(battleStats[1], Main.screenWidth-battleStats[1].getWidth(), 338, null);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
		g2.setColor(Color.BLACK);
		g2.fillRect(0, 586, 1080, 134);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

		if(gameState != 1)
		{
			if (!playerIsFainted) {
				drawPStats(g2);
				loadPlayerMon(g2);
			}
			if (!opponentIsFainted) {
				drawOStats(g2);
				loadOpponentMon(g2);
			}
		}
		if (gameState == 0 && message != null)
			updateText(g2);

		if(gameState == 1 && message != null)
		{
			updateText(g2);
		}

		if (gameState == 2 || gameState == 3 && message != null) {
			updateText(g2);
		}
		if ((gameState == 4 || gameState == 5) && message != null) {
			updateText(g2);
		}
		if (gameState == 6  || gameState == 7 && message != null)
			updateText(g2);
		if (gameState == 0 && message != null)
			updateText(g2);
		if (gameState == 8 && message != null)
			updateText(g2);
		if (frame.getContentPane().equals(selectionMenu))
			selectionMenu.update(g2);
	}
	public void loadPlayerMon(Graphics2D g2)
	{
		int pX = 400 - (player.getParty()[playerCurr].getBack().getWidth()/2);
		int pY = 590-player.getParty()[playerCurr].getBack().getHeight();

		g2.drawImage(player.getParty()[playerCurr].getBack(), pX, pY, null);
	}
	public void updateText(Graphics2D g)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.setFont(font);
		g2.setColor(Color.WHITE);
		g2.drawString(message, 69, 648);
	}
	public void loadOpponentMon(Graphics2D g2)
	{
		int oX = 793 - (opponent.getParty()[oppCurr].getFront().getWidth()/2);
		int oY = 300 - opponent.getParty()[oppCurr].getFront().getHeight();

		g2.drawImage(opponent.getParty()[oppCurr].getFront(), oX, oY, null);
	}
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

			int width = battleStats[3].getWidth() * (opponent.getParty()[oppCurr].getCurHP() / opponent.getParty()[oppCurr].getHPStat();
			if(width > 0)
			{
				BufferedImage drawBar = barColor.getSubimage(0, 0, width, barColor.getHeight());
				g2.drawImage(drawBar, 152, 144, this);
			}

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
			if(!wild)
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
			if(width > 0)
			{
				BufferedImage drawBar = barColor.getSubimage(0, 0, width, barColor.getHeight());
				g2.drawImage(drawBar, 859, 368, null);
			}

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
			if(level > 0)
			{
				BufferedImage drawBar = battleStats[2].getSubimage(0, 0, level, battleStats[2].getHeight());
				g2.drawImage(drawBar, 775, 417, null);
			}

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

	public void newBattle(Player newPlayer, NPC newOpponent, boolean isW)
	{
		player = newPlayer;
		playerCurr = 0;
		opponent = newOpponent;
		oppCurr = 0;
		isWild = isW;
	}

	public void setPlayerCur(int i) {
		this.playerCurr = i;
	}

	public void refresh()
	{
		repaint();
	}

	public Pokemon getCurPlayerMon() {
		return player.getParty()[playerCurr];
	}

	public static void main(String[] args)
	{
		frame = new JFrame ("Pokemon");
		try {
			BlankMon.getAllMoves();
			BlankMon.getAllMoveLists();
			BlankMon.getAllAbilities();
			Pokemon.addAllPokemon();
		} 
		catch (IOException e) {}

		Player pranav = new Player(0,0);
		pranav.addPokemonToParty(new Pokemon("Charmander", "BBQ Dragon", 7));
//		pranav.getParty()[0].setCurExp(2500);
		pranav.addPokemonToParty(new Pokemon("Persian", "catty", 32));
		pranav.addOnItem("Potion", 1, 5);
		pranav.addOnItem("Master Ball", 0, 2);
		pranav.addKeyItem("Badge Case");
		pranav.addKeyItem("Town Map");


		//		pranav.addPokemonToParty(new Pokemon("Machamp", "strong", 22));
		Pokemon[] temp = new Pokemon[6];
		temp[0] = new Pokemon("Bulbasaur", "Bulby", 12);
		NPC gary = new NPC(new Rectangle(12, 12, 12, 12), "up", "Up", 0,0, "hi", "hi", temp);
		gary.getParty()[0].setStatus(Pokemon.Status.FREEZE);
		gary.getParty()[0].setCurHP(2);;

		gary.addPokemonToParty(new Pokemon ("Fearow", "birdy", 25));
		gary.getParty()[1].setStatus(Pokemon.Status.FREEZE);
		gary.getParty()[1].setCurHP(2);;

		panel = new Battle(pranav, gary);

		frame.setContentPane(panel);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		frame.setLocationRelativeTo(null);


	}


}
