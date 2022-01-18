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
	
	private Font font;
	BufferedImage background; // Background image
	BufferedImage[] battleStats;
	
	boolean playerTurn;
	
	Pokemon[] player;
	Pokemon playerCurr;
	Pokemon[] opponent;
	Pokemon oppCurr;
	
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
	
	static Button back;
	static Button[] buttons;
	static MoveSelect[] moves;
	
	public Battle(Pokemon[] player, Pokemon[] opponent)
	{
		this.player = player;
		playerCurr = player[0];
		this.opponent = opponent;
		oppCurr = opponent[0];
		setPreferredSize(new Dimension(Driver2.screenWidth, Driver2.screenHeight));
		setLayout(null);
	    setBackground(Color.BLACK);
		LoadImage loader = new LoadImage();
		battleStats = new BufferedImage[7];
		
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
			battleStats[2] = loader.resize(battleStats[2], 165, 6);
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
		try
		{
			battleStats[6] = loader.loadImage("res/battle/battle stats.png").getSubimage(106, 16, 19, 19);
			battleStats[6] = loader.resize(battleStats[6], 165, 6);
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
		font = new Font("Pokemon GB", Font.PLAIN, 22);
		
		playerTurn = false;
		timer = new Timer(35, new TimerEventHandler());
		timerOn = false;

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
			moves[i] = new MoveSelect();
			moves[i].setBounds(4+268*i, 598, 268, 102);
		}
		
		gameState = 1;
		timer = new Timer(35, new TimerEventHandler ());
		timerOn = true;
		timer.start();
	}
	
	// TimeEventHandler class is for the timer.
	private class TimerEventHandler implements ActionListener
	{
		// This method is called by the Timer, taking an ActionEvent as a parameter and returning void.
		public void actionPerformed (ActionEvent event)
		{
			if(gameState == 1)
			{
				
			}
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
			showMoves(playerCurr.getAttacks());
		}
		// Go back to the main buttons
		else if(e.getSource().equals(back))
		{
			hideBack();
			hideMoves();
			showButtons();
		}
	}
	
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
	
	// Description: Hides the main buttons (fight/pokemon/run/bag)
	// Parameters: none
	// Returns: void
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
	
	// Description: Shows the main buttons (fight/pokemon/run/bag)
	// Parameters: none
	// Returns: void
	public void showButtons()
	{
		for(Button b : buttons)
		{
			this.add(b);
			this.revalidate();
			this.repaint();
		}
	}
	
	public void showMoves(Move[] m)
	{
		for(int i = 0; i < m.length; i++)
		{
			moves[i].updatePokemon(m[i].getName(), m[i].getType(), m[i].getCurPP(), m[i].getPP());
			this.add(moves[i]);
			this.revalidate();
			this.repaint();
			moves[i].setDisplayed(true);
			moves[i].repaint();
		}
	}
	
	public void hideMoves()
	{
		for(MoveSelect m : moves)
		{
			if(!m.equals(null))
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
	

	public void updateText(Graphics2D g)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.setFont(font);
		g2.setColor(Color.WHITE);
		g2.drawString("This is a message.", 69, 648);
	}

	
	public void loadPlayerMon(Graphics2D g2)
	{
		int pX = 400 - (playerCurr.getBack().getWidth()/2);
		int pY = 590-playerCurr.getBack().getHeight();
		
		g2.drawImage(playerCurr.getBack(), pX, pY, null);
	}
	
	public void loadOpponentMon(Graphics2D g2)
	{
		int oX = 793 - (oppCurr.getFront().getWidth()/2);
		int oY = 300 - oppCurr.getFront().getHeight();
		
		g2.drawImage(oppCurr.getFront(), oX, oY, null);
	}
	
	public void drawOStats(Graphics2D g2)
	{
		if(oppCurr != null)
		{
			BufferedImage barColor = null;
			if(oppCurr.getCurHP() / oppCurr.getHPStat() < 0.2)
				barColor = battleStats[4];
			else if(oppCurr.getCurHP() / oppCurr.getHPStat() < 0.7)
				barColor = battleStats[5];
			else
				barColor = battleStats[3];
			
			int width = battleStats[3].getWidth() * oppCurr.getCurHP() / oppCurr.getHPStat();
			BufferedImage drawBar = barColor.getSubimage(0, 0, width, barColor.getHeight());
			g2.drawImage(drawBar, 152, 144, this);
			
			Map<TextAttribute, Object> attributes = new HashMap<TextAttribute, Object>();
			attributes.put(TextAttribute.TRACKING, -0.15);
		    attributes.put(TextAttribute.SIZE, 22);
		    g2.setFont(font.deriveFont(attributes));
		    
		    // Opponent Name
		    g2.setColor(Color.BLACK);
			g2.drawString(oppCurr.getName(), 32, 133);
		    g2.setColor(Color.WHITE);
			g2.drawString(oppCurr.getName(), 30, 131);
			
			// Opponent Level
			g2.setColor(Color.BLACK);
			g2.drawString(Integer.toString(oppCurr.getLevel()), 330, 135);
		    g2.setColor(Color.WHITE);
			g2.drawString(Integer.toString(oppCurr.getLevel()), 328, 133);
			
			// Opponent Pokeballs
			
		}
	}
	
	public void drawPStats(Graphics2D g2)
	{
		if(playerCurr != null)
		{
			// Player Bars
			BufferedImage barColor = null;
			if(playerCurr.getCurHP() / playerCurr.getHPStat() < 0.2)
				barColor = battleStats[5];
			else if(playerCurr.getCurHP() / playerCurr.getHPStat() < 0.7)
				barColor = battleStats[4];
			else
				barColor = battleStats[3];
			
			int width = battleStats[3].getWidth() * playerCurr.getCurHP() / playerCurr.getHPStat();
			BufferedImage drawBar = barColor.getSubimage(0, 0, width, barColor.getHeight());
			g2.drawImage(drawBar, 859, 368, null);
			
			Map<TextAttribute, Object> attributes = new HashMap<TextAttribute, Object>();
			attributes.put(TextAttribute.TRACKING, -0.15);
		    attributes.put(TextAttribute.SIZE, 22);
		    g2.setFont(font.deriveFont(attributes));
		    
		    // Player Name
		    g2.setColor(Color.BLACK);
			g2.drawString(playerCurr.getNickName(), 718, 359);
		    g2.setColor(Color.WHITE);
			g2.drawString(playerCurr.getNickName(), 716, 357);
			
			// Player Level
			attributes.put(TextAttribute.TRACKING, -0.05);
		    g2.setFont(font.deriveFont(attributes));
			g2.setColor(Color.BLACK);
			g2.drawString(Integer.toString(playerCurr.getLevel()), 1020, 361);
		    g2.setColor(Color.WHITE);
			g2.drawString(Integer.toString(playerCurr.getLevel()), 1018, 359);
			
			// Player Health
			attributes.put(TextAttribute.SIZE, 18);
			g2.setFont(font.deriveFont(attributes));
			g2.setColor(Color.BLACK);
			g2.drawString(Integer.toString(playerCurr.getCurHP()), 880, 404);
		    g2.setColor(Color.WHITE);
			g2.drawString(Integer.toString(playerCurr.getCurHP()), 878, 402);
			g2.setColor(Color.BLACK);
			g2.drawString(Integer.toString(playerCurr.getHPStat()), 974, 404);
		    g2.setColor(Color.WHITE);
			g2.drawString(Integer.toString(playerCurr.getHPStat()), 972, 402);
		}
	}
	public void paintComponent(Graphics g) {
		//static
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(background, 0, 0, null);
		g2.drawImage(battleStats[0], 0, 110, null);
		g2.drawImage(battleStats[1], Main.screenWidth-battleStats[1].getWidth(), 338, null);
		g2.setColor(Color.BLACK);
		g2.fillRect(0, 586, 1080, 134);
		
		if(gameState != 1)
		{
			loadPlayermons(g2);
			drawPStats(g2);
			drawOStats(g2);
		}
		// updateText(g2);

	}
	
	public void newBattle(Pokemon[] playerParty, Pokemon[] oppParty)
	{
		player = playerParty;
		playerCurr = playerParty[0];
		opponent = oppParty;
		oppCurr = oppParty[0];
	}
	
	public Font getFont()
	{
		return font;
	}
	
	public static void main(String[] args)
	{
		JFrame frame = new JFrame ("Pokemon");
		try {
			BlankMon.getAllMoves();
			BlankMon.getAllMoveLists();
			BlankMon.getAllAbilities();
			Pokemon.addAllPokemon();
		} 
		catch (IOException e) {}

		Player pranav = new Player(0,0);
		pranav.addPokemonToParty(new Pokemon("Charizard", "BBQ Dragon", 48));
		pranav.addPokemonToParty(new Pokemon("Persian", "catty", 32));
		pranav.addPokemonToParty(new Pokemon("Machamp", "strong", 54));
		NPC gary = new NPC(0,0, null);
		gary.addPokemonToParty(new Pokemon("Machamp", "Machamp", 66));
		gary.addPokemonToParty(new Pokemon ("Fearow", "birdy", 36));
		Battle panel = new Battle(pranav.getParty(), gary.getParty());
		
		frame.setContentPane(panel);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		frame.setLocationRelativeTo(null);
		
	}
	

}
