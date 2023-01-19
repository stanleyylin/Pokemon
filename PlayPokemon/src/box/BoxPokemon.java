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
	
	private Pokemon pokemon; // instance of a pokemon
	private boolean inParty; // if the pokemon is in a party
	private BufferedImage pokeSprite; //the pokemons sprite
	
	private Box box; // the box object
	private boolean isSelected; //stores whether a pokemon is selected
	private boolean isVisible; //stores whether a pokemon is visible
	
	private static BufferedImage hex1; // white
	private static BufferedImage hex2; // dark
	
	private int index;
	
	private BufferedImage hexagon;
	
	//constructor
	public BoxPokemon(Box box, int index)
	{
		this.box = box;
		
		setPreferredSize(new Dimension(114, 114));
		setBackground(new Color(0f, 0f, 0f, 0f));
		addMouseListener(this);
		hexagon = null;
		inParty = false;
		isSelected = false;
		isVisible = false;
		this.index = index;
	}
	
	//places the hexagon where the mouse is
	public static void setHex()
	{
		LoadImage loader = new LoadImage();
		try
		{
			hex1 = loader.loadImage("res/box/selected.png");
			hex1 = loader.resize(hex1, 96, 86);
		}
		catch(IOException e) {}
		try
		{
			hex2 = loader.loadImage("res/box/swap.png");
			hex2 = loader.resize(hex2, 96, 86);
		}
		catch(IOException e) {}
	}
	
	//updates the pokemon sprites based on its location
	public void updatePokemon(Pokemon p, boolean inParty)
	{
		LoadImage loader = new LoadImage();
		this.inParty = inParty;
		
		isVisible = true;
		pokemon = p;
		pokeSprite = pokemon.getFront();
		pokeSprite = loader.resize(pokeSprite, 115, 115);
		revalidate();
		repaint();
		box.refresh();
	}
	
	//hides pokemon if it is moved
	public void hidePokemon()
	{
		isVisible = false;
		repaint();
		revalidate();
		box.refresh();
	}
	
	//paint component
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(isVisible)
		{
			Graphics2D g2 = (Graphics2D) g;
			if(hexagon != null || isSelected)
				g2.drawImage(hexagon, 9, 14, null);
			g2.drawImage(pokeSprite, 0, 0, null);
		}
	}
	
	// Getters and Setters
	public Pokemon getPokemon()
	{
		return pokemon;
	}
	
	public void setSelected(boolean set)
	{
		this.isSelected = set;
		if(set && inParty)
			hexagon = hex2;
		else if(set)
			hexagon = hex1;
		else
			hexagon = null;
	}
	
	public void mouseClicked(MouseEvent e) 
	{
		if(!inParty && isVisible && !box.isSwapping())
		{
			box.selected(index);
			setSelected(true);
		}
		else if(inParty && box.isSwapping() && isVisible)
		{
			box.swap(index);
		}
	}

	public void mousePressed(MouseEvent e) {}

	public void mouseReleased(MouseEvent e) {}

	public void mouseEntered(MouseEvent e) 
	{
		if(inParty && box.isSwapping() && isVisible)
			hexagon = hex2;
		else if(!box.isSwapping() && isVisible)
			hexagon = hex1;
		repaint();
		box.refresh();
	}
	public void mouseExited(MouseEvent e) 
	{
		if(!isSelected && isVisible)
		{
			hexagon = null;
			repaint();
			box.refresh();
		}
	}

}
