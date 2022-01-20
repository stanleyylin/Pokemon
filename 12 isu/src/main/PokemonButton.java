package main;

import javax.swing.JPanel;
import java.awt.*;

import getimages.LoadImage;
import pokesetup.BlankMon;
import pokesetup.Move;
import pokesetup.Pokemon;

import java.awt.event.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.awt.font.TextAttribute;

@SuppressWarnings("serial")
public class PokemonButton extends JPanel implements MouseListener
{
	BufferedImage healthBar;
	BufferedImage selected;
	BufferedImage deselected;
	BufferedImage pokeball;
	BufferedImage openedBall;
	BufferedImage bg;
	// 515, 198
	Pokemon poke;
	
	private final int width = 515;
	private final int height = 198;
	
	public PokemonButton(Pokemon poke, BufferedImage selected, BufferedImage deselected, BufferedImage pokeball, BufferedImage openedBall)
	{
		setPreferredSize(new Dimension(width, height));
		setLayout(null);
		setOpaque(true);
		
		this.poke = poke;
		this.selected = selected;
		this.deselected = deselected;
		bg = deselected;
		
		LoadImage loader = new LoadImage();
		try
		{
			healthBar = loader.loadImage("res/battle/health bar.png");
			healthBar = healthBar.getSubimage(86, 74, 163, 17);	
		}
		catch(IOException e) {}
		
		selected = loader.resize(selected, width, height);
		deselected = loader.resize(deselected, width, height);
	}

	public void mouseClicked(MouseEvent e) 
	{
		
	}

	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}

	public void mouseEntered(MouseEvent e) 
	{
		
	}

	public void mouseExited(MouseEvent e) 
	{
		
		
	}
	
	public void paintComponent(Graphics g) 
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(bg, 0, 0, this);
		
		
	}

}
