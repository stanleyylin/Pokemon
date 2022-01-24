package box;

import javax.swing.*;

import entity.Player;
import getimages.LoadImage;

import java.awt.image.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import main.Battle;
import main.GamePanel;
import pokesetup.Pokemon;

public class BoxPokemon extends JPanel implements MouseListener {
	
	private Pokemon pokemon;
	private boolean inParty;
	private BufferedImage pokeSprite;
	
	private boolean isSelected;
	private boolean isVisible;
	
	private static BufferedImage hex;
	
	private BufferedImage hexagon;
	
	public BoxPokemon(int x, int y)
	{
		setPreferredSize(new Dimension(115, 115));
		setBackground(new Color(0f, 0f, 0f, 0f));
		addMouseListener(this);
		hexagon = hex;
		inParty = false;
		isSelected = false;
		isVisible = false;
	}
	
	public static void setHex()
	{
		LoadImage loader = new LoadImage();
		try
		{
			hex = loader.loadImage("res/box/selected.png");
			hex = loader.resize(hex, 96, 86);
		}
		catch(IOException e) {}
	}
	
	public void updatePokemon(Pokemon p, boolean inParty)
	{
		LoadImage loader = new LoadImage();
		this.inParty = inParty;
		
		isVisible = true;
		
		pokemon = p;
		pokeSprite = pokemon.getFront();
		pokeSprite = loader.resize(pokeSprite, 69, 69);
		revalidate();
		repaint();
	}
	
	public void hidePokemon()
	{
		isVisible = false;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(isVisible)
		{
			Graphics2D g2 = (Graphics2D) g;
			if(hexagon != null || isSelected)
				g2.drawImage(hexagon, 9, 14, null);
			g2.drawImage(pokeSprite, 22, 22, null);
		}
	}
	
	public void setSelected(boolean set)
	{
		this.isSelected = set;
	}
	
	public void mouseClicked(MouseEvent e) 
	{
		
	}

	public void mousePressed(MouseEvent e) {}

	public void mouseReleased(MouseEvent e) {}

	public void mouseEntered(MouseEvent e) 
	{
		hexagon = hex;
	}

	public void mouseExited(MouseEvent e) 
	{
		hexagon = null;
	}

}
