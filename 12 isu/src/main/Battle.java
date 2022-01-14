package main;

import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;

import getimages.LoadImage;
import pokesetup.Pokemon;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.awt.font.TextAttribute;

public class Battle extends JPanel implements MouseListener {
	
	BufferedImage background; // Background image
	Font font;
	BufferedImage[] battleStats;
	
	boolean playerTurn;
	
	Pokemon[] player;
	Pokemon playerCurr;
	Pokemon[] opponent;
	Pokemon oppCurr;
	
	public Battle()
	{
		setPreferredSize(new Dimension(Driver2.screenWidth, Driver2.screenHeight));
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
			font = new Font("Pokemon GB", Font.PLAIN, 20);
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
		repaint();
	}
	
	public void mouseClicked(MouseEvent e) 
	{	
		
	}

	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(background, 0, 0, null);
		g2.drawImage(battleStats[0], 0, 110, null);
		g2.drawImage(battleStats[1], Main.screenWidth-battleStats[1].getWidth(), 338, null);
		g2.setColor(Color.BLACK);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
		g2.fillRect(0, 586, 1080, 134);
		
//		g2.setFont(font);
//		g2.setColor(Color.WHITE);
//		g2.drawString("Hello", 0, 20);
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
		Battle panel = new Battle();
		frame.setContentPane(panel);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
