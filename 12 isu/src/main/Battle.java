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

public class Battle extends JPanel {
	
	BufferedImage background; // Background image
	private static Font font;
	BufferedImage[] battleStats;
	
	boolean playerTurn;
	
	Pokemon[] player;
	static Pokemon playerCurr;
	Pokemon[] opponent;
	Pokemon oppCurr;
	
	static Timer timer; // Timer for animation
	static boolean timerOn; // Is the timer on?
	static int counter;
	static float[] opacity;
	String message;
	
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
		battleStats = new BufferedImage[4];
		
		try
		{
			background = loader.loadImage("res/battle/grass.png");
		}
		catch(IOException e) {}
		try
		{
			battleStats[0] = loader.loadImage("res/battle/battle stats.png").getSubimage(15, 56, 316, 44);
			battleStats[0] = loader.resize(battleStats[0], 442, 62);
		}
		catch(IOException e) {}
		try
		{
			battleStats[1] = loader.loadImage("res/battle/battle stats.png").getSubimage(15, 110, 296, 63);
			battleStats[1] = loader.resize(battleStats[1], 415, 88);
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
		add(buttons[0]);
		
		// Pokemon button
		temp1 = loader.resize(buttonSheet.getSubimage(14, 46, 102, 47), 220, 101);
		temp2 = loader.resize(buttonSheet.getSubimage(145, 46, 102, 47), 220, 101);
		buttons[1] = new Button(this, temp1, temp2, 220, 101);
		buttons[1].setBounds(320, 595, 220, 101);
		add(buttons[1]);
		
		// Bag button
		temp1 = loader.resize(buttonSheet.getSubimage(32, 96, 88, 41), 190, 89);
		temp2 = loader.resize(buttonSheet.getSubimage(164, 96, 88, 41), 190, 89);
		buttons[2] = new Button(this, temp1, temp2, 190, 89);
		buttons[2].setBounds(650, 601, 190, 89);
		add(buttons[2]);
		
		// Run button
		temp1 = loader.resize(buttonSheet.getSubimage(33, 143, 60, 44), 129, 95);
		temp2 = loader.resize(buttonSheet.getSubimage(162, 143, 60, 44), 129, 95);
		buttons[3] = new Button(this, temp1, temp2, 129, 95);
		buttons[3].setBounds(894, 606, 129, 95);
		add(buttons[3]);
		
		MoveSelect.setImages();
		moves = new MoveSelect[4];
		for(int i = 0; i < 4; i++)
		{
			moves[i] = new MoveSelect();
			moves[i].setBounds(4+268*i, 598, 268, 102);
			add(moves[i]);
		}
	}
	
	// TimeEventHandler class is for the timer.
	private class TimerEventHandler implements ActionListener
	{
		// This method is called by the Timer, taking an ActionEvent as a parameter and returning void.
		public void actionPerformed (ActionEvent event)
		{
			counter++;
			repaint();
		}
	}
	
	public void buttonClick(MouseEvent e)
	{
		if(e.getSource().equals(buttons[0]))
		{
			hideButtons();
			showMoves(playerCurr.getAttacks());
		}
	}
	
	public void hideButtons()
	{
		for(Button b : buttons)
		{
			Container parent = b.getParent();
			parent.remove(b);
			parent.validate();
			parent.repaint();
		}
	}
	
	public void showButtons()
	{
		for(Button b : buttons)
		{
			add(b);
			validate();
			repaint();
		}
	}
	
	public void showMoves(Move[] m)
	{
		for(int i = 0; i < m.length; i++)
		{
			moves[i].updatePokemon(m[i].getName(), m[i].getType(), m[i].getCurPP(), m[i].getPP());
			moves[i].show();
		}
	}
	

	public void updateText(Graphics2D g)
	{
		
		Graphics2D g2 = (Graphics2D) g;
		g2.setFont(font);
		g2.setColor(Color.WHITE);
		g2.drawString("This is a message.", 69, 648);
	}
	
	public void updateStats(Graphics2D g)
	{
		
	}
	
	public void loadMons(Graphics2D g)
	{
		Graphics2D g2 = (Graphics2D) g;
		int pX = 400 - (playerCurr.getBack().getWidth()/2);
		int pY = 590-playerCurr.getBack().getHeight();
		int oX = 793 - (oppCurr.getFront().getWidth()/2);
		int oY = 300 - oppCurr.getFront().getHeight();
		g2.drawImage(playerCurr.getBack(), pX, pY, null);
		g2.drawImage(oppCurr.getFront(), oX, oY, null);
		
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(background, 0, 0, null);
		g2.drawImage(battleStats[0], 0, 110, null);
		g2.drawImage(battleStats[1], Main.screenWidth-battleStats[1].getWidth(), 338, null);
		g2.setColor(Color.BLACK);
		g2.fillRect(0, 586, 1080, 134);
		// updateText(g2);
		loadMons(g2);
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
		pranav.addPokemonToParty(new Pokemon("Charizard", "fye", 48));
		pranav.addPokemonToParty(new Pokemon("Persian", "catty", 32));
		pranav.addPokemonToParty(new Pokemon("Machamp", "strong", 54));
		NPC gary = new NPC(0,0, null);
		gary.addPokemonToParty(new Pokemon("Machamp", "woop", 66));
		gary.addPokemonToParty(new Pokemon ("Fearow", "birdy", 36));
		Battle panel = new Battle(pranav.getParty(), gary.getParty());
		frame.setContentPane(panel);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
	}
	

}
