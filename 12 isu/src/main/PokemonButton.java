// PokemonButton is for PokeSelect: it displays the info for a Pokemon

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
	private PokeSelect pokeSelect; // The Pokemon selection screen

	// BG
	private static BufferedImage selected; // Green box
	private static BufferedImage deselected; // Blue box
	private static BufferedImage fainted; // Black box
	private BufferedImage bg; // Actual bg to be displayed

	private static BufferedImage healthBar; // The HP status bar
	private static BufferedImage[] health = new BufferedImage[3]; // Health bar that 
	// goes into healthBar: 0 - green, 1 - yellow, 2 - red
	private BufferedImage actualHealth; // The actual health bar

	private static BufferedImage[] pokeBallSprites = new BufferedImage[2]; // 0 - closed, 1 - open
	private BufferedImage pokeStatus; // Actual pokeball to indicate status of pokemon 
	// closed means the pokemon is in the ball, open means it is outside the pokeball

	private Pokemon poke; // The pokemon
	private BufferedImage pokemon; // The image of the pokemon
	private boolean visible; // Whether or not this is visible

	private Font font = new Font("Pokemon GB", Font.PLAIN, 22); // front

	public static final int width = 509;
	public static final int height = 178;

	// Constructor
	public PokemonButton(PokeSelect pokeSelect)
	{
		this.pokeSelect = pokeSelect;

		setPreferredSize(new Dimension(width, height));
		setLayout(null);
		setOpaque(true);

		if(pokeSelect.getInBattle())
		{
			addMouseListener(this);
		}
		visible = false;
	}

	// Called to intialize images for all pokemon buttons
	public static void setImages()
	{
		LoadImage loader = new LoadImage();
		try
		{
			fainted = loader.loadImage("res/battle/deselectedmove.png");
			fainted = fainted.getSubimage(2, 4, 252, 88);
			fainted = loader.resize(fainted, width, height);
			selected = loader.loadImage("res/battle/greenmove.png");
			selected = selected.getSubimage(2, 4, 252, 88);
			selected = loader.resize(selected, width, height);
			deselected = loader.loadImage("res/battle/selectedmove.png");
			deselected = deselected.getSubimage(2, 4, 252, 88);
			deselected = loader.resize(deselected, width, height);

			BufferedImage healthSprites = loader.loadImage("res/battle/battle stats.png");
			// green
			health[0] = healthSprites.getSubimage(161, 18, 119, 5);
			health[0] = loader.resize(health[0], 175, 8);
			// yellow
			health[1] = healthSprites.getSubimage(161, 25, 119, 5);
			health[1] = loader.resize(health[1], 175, 8);
			// red
			health[2] = healthSprites.getSubimage(161, 32, 119, 5);
			health[2] = loader.resize(health[2], 175, 8);

			healthBar = loader.loadImage("res/battle/health bar.png");
			healthBar = healthBar.getSubimage(86, 74, 163, 17);	
			healthBar = loader.resize(healthBar, 235, 24);

			BufferedImage ballSprites = null;
			ballSprites = loader.loadImage("res/battle/pokeballs.png");

			pokeBallSprites[0] = ballSprites.getSubimage(132, 9 + 24 + 15, 24, 24);
			pokeBallSprites[0] = loader.resize(pokeBallSprites[0], 48, 48);
			pokeBallSprites[1] = ballSprites.getSubimage(132, 24*10 + 16*10, 24, 35);
			pokeBallSprites[1] = loader.resize(pokeBallSprites[1], 48, 70);
		}
		catch(IOException e) {}
	}
	// Pranav: write code here, call pokeselect's pokemonSelected method
	// to select a pokemon
	// NOTE IN THE FUTURE: need to have a boolean for inbattle functionalities vs outta battle
	public void mouseClicked(MouseEvent e) 
	{

		System.out.println(poke);
		
		pokeSelect.pokemonSelected(e);
	}

	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}

	// Hovering
	public void mouseEntered(MouseEvent e) 
	{
		if(poke != null)
		{
			bg = selected;
			repaint();
			pokeSelect.refresh();
		}
	}
	public void mouseExited(MouseEvent e) 
	{

		if (poke != null && poke.getIsFainted()) {
			bg = fainted;
			repaint();
			pokeSelect.refresh();

		}
		else if(poke != null)
		{
			bg = deselected;
			repaint();
			pokeSelect.refresh();
		}
	}

	// Updates the pokemon info AND redraws
	public void update(Pokemon p, boolean current)
	{
		poke = p;

		if(poke == null)
			return;

		visible = true;

		LoadImage loader = new LoadImage();
		pokemon = poke.getFront();
		pokemon = loader.resize(pokemon, 120, 118);

		if(poke.getIsFainted())
			bg = fainted;
		else
			bg = deselected;

		if(current)
			pokeStatus = pokeBallSprites[1];
		else
			pokeStatus = pokeBallSprites[0];

		if((double)poke.getCurHP() / poke.getHPStat() <= 0.2)
			actualHealth = health[2];
		else if((double)poke.getCurHP() / poke.getHPStat() <= 0.5)
			actualHealth = health[1];
		else if((double)poke.getCurHP() / poke.getHPStat() <= 1)
			actualHealth = health[0];

		repaint();
	}

	public void paintComponent(Graphics g) 
	{
		if(visible)
		{
			Graphics2D g2 = (Graphics2D) g;

			g2.drawImage(bg, 0, 0, this); // bg
			if(pokeStatus == pokeBallSprites[1])
				g2.drawImage(pokeStatus, 15, 15, this); // pokeball
			else
				g2.drawImage(pokeStatus, 15, 25, this);

			g2.drawImage(pokemon, 68, 2, this); // pokemon

			g2.setFont(font);
			g2.setColor(Color.white);
			g2.drawString(poke.getNickName(), 210, 50); // name

			g2.drawImage(healthBar, 212, 94, this); // health info
			if(!poke.getIsFainted())
				g2.drawImage(actualHealth, 265, 99, this); // health bar

			g2.drawString("Lv. " + poke.getLevel(), 30, 152); // level
			g2.drawString(poke.getCurHP() + "/" + poke.getHPStat(), 302, 142); // health - text
		}
	}


	public Pokemon getPoke() {
		return this.poke;
	}
}
