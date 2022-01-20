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
public class PokeSelect extends JPanel {
	
	private Pokemon[] player;
	private int curr;
	private BufferedImage healthBar;
	
	private BufferedImage selected; // green
	private BufferedImage unselected; // blue
	private BufferedImage fainted; // grey
	private Button pokemonBack;

	private Font font = new Font("Pokemon GB", Font.PLAIN, 22);
	
	public PokeSelect(Button pokemonBack, Pokemon[] player, int curr)
	{
		
		LoadImage loader = new LoadImage();
		try {
			selected = loader.loadImage("res/battle/greenmove.png");
		} catch (IOException e) {}
		try {
			unselected = loader.loadImage("res/battle/deselectedmove.png");
		} catch (IOException e) {}
		unselected = loader.resize(unselected, 268, 102);
		try {
			unselected = loader.loadImage("res/battle/deselectedmove.png");
		} catch (IOException e) {}
	}
	
	public void pokemonSelected(MouseEvent e)
	{
		
	}
	
	

}
