package main;

import java.awt.*;

import getimages.LoadImage;
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
	Font font;
	BufferedImage[] battleStats;
	
	boolean playerTurn;
	
	Pokemon[] player;
	Pokemon playerCurr;
	Pokemon[] opponent;
	Pokemon oppCurr;
	
	static Timer timer; // Timer for animation
	static boolean timerOn; // Is the timer on?
	static int counter;
	static float[] opacity;
	String message;
	
	static Button[] buttons1;
	
	public Battle(Pokemon[] player, Pokemon[] opponent)
	{
		this.player = player;
		this.opponent = opponent;
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
			font = new Font("Pokemon GB", Font.PLAIN, 22);
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
		playerTurn = false;
		timer = new Timer(35, new TimerEventHandler());
		timerOn = false;
	
//		bottomBar = new JPanel();
//		bottomBar.setPreferredSize(new Dimension(1080, 134));
//		bottomBar.setLayout(null);
//		bottomBar.setBackground(new Color(0f, 0f, 0f, 0f));
		buttons1 = new Button[4];
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
		buttons1[0] = new Button(temp1, temp2, 160, 100);
		buttons1[0].setBounds(59, 595, 160, 100);
		add(buttons1[0]);
		
		// Pokemon button
		temp1 = loader.resize(buttonSheet.getSubimage(14, 46, 102, 47), 220, 101);
		temp2 = loader.resize(buttonSheet.getSubimage(145, 46, 102, 47), 220, 101);
		buttons1[1] = new Button(temp1, temp2, 220, 101);
		buttons1[1].setBounds(320, 595, 220, 101);
		add(buttons1[1]);
		
		// Bag button
		temp1 = loader.resize(buttonSheet.getSubimage(32, 96, 88, 41), 190, 89);
		temp2 = loader.resize(buttonSheet.getSubimage(164, 96, 88, 41), 190, 89);
		buttons1[2] = new Button(temp1, temp2, 190, 89);
		buttons1[2].setBounds(650, 601, 190, 89);
		add(buttons1[2]);
		
		// Run button
		temp1 = loader.resize(buttonSheet.getSubimage(33, 143, 60, 44), 129, 95);
		temp2 = loader.resize(buttonSheet.getSubimage(162, 143, 60, 44), 129, 95);
		buttons1[3] = new Button(temp1, temp2, 129, 95);
		buttons1[3].setBounds(894, 606, 129, 95);
		add(buttons1[3]);
		
//		bottomBar.setBounds(0, 586, 1080, 134);
//		add(bottomBar);
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
	
	public static void hideButtons()
	{
		for(Button b : buttons1)
		{
			b.setIcon(null);
			b.displayed(false);
		}	
	}
	
	public static void showButtons()
	{
		
	}
	
	public static void showMoves()
	{
		
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
		int pY = 554-playerCurr.getBack().getHeight();
			
		g2.drawImage(playerCurr.getBack(), pX, pY, null);
		
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(background, 0, 0, null);
		g2.drawImage(battleStats[0], 0, 110, null);
		g2.drawImage(battleStats[1], Main.screenWidth-battleStats[1].getWidth(), 338, null);
		g2.setColor(Color.BLACK);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
		g2.fillRect(0, 586, 1080, 134);
		// updateText(g2);
	}
	
	public void newBattle(Pokemon[] playerParty, Pokemon[] oppParty)
	{
		player = playerParty;
		playerCurr = playerParty[0];
		opponent = oppParty;
		oppCurr = oppParty[0];
		
	}
	
	public static void main(String[] args)
	{
		JFrame frame = new JFrame ("Pokemon");
		
		Player pranav = new Player(0,0);
		pranav.addPokemonToParty(new Pokemon("Charizard", "fye", 48));
		pranav.addPokemonToParty(new Pokemon("Persian", "catty", 32));
		pranav.addPokemonToParty(new Pokemon("Machamp", "strong", 54));
		
		NPC gary = new NPC(0,0, null);
		gary.addPokemonToParty(new Pokemon ("Fearow", "birdy", 36));
		gary.addPokemonToParty(new Pokemon("Seel", "woop", 66));
		
		Battle panel = new Battle(pranav.getParty(), gary.getParty());
		frame.setContentPane(panel);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
	}

}
