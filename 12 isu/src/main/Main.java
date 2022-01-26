package main;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;

import bag.Bag;
import bag.Item;
import box.Box;
import entity.NPC;
import entity.Player;
import pokemart.PokeMart;
import pokesetup.BlankMon;
import pokesetup.Pokemon;

public class Main extends JFrame {
	
	private MainMenu mainMenu;
	private GamePanel gamePanel;
	private Bag bag;
	private Box box;
	private PokeSelect pokeSelect;
	private PokeMart pokeMart;
	private Battle battle;
	
	private JPanel lastScreen;
	
	private Player player;
	NPC gary;
	
	public final static int screenWidth = 1080;
	public final static int screenHeight = 720;
	
	public Main()
	{
		player = new Player(screenWidth/2-Player.width/2, screenHeight/2-Player.height/2);
		
		try 
		{
			BlankMon.getAllMoves();
			BlankMon.getAllMoveLists();
			BlankMon.getAllAbilities();
			Pokemon.addAllPokemon();
		} 
		catch (IOException e) {}
		
		// Initialization
		mainMenu = new MainMenu(this);
		gamePanel = new GamePanel(this, player);
		box = new Box(this, player);
		pokeMart = new PokeMart(this, player);
		battle = new Battle(this,player);	
		bag = new Bag(this,player,battle);
		pokeSelect = new PokeSelect(battle, player,0);
		player.addPokemonToParty(new Pokemon ("Charmander", "Charmander",7));
	
		
		// TESTER-------
	
		openMainMenu();
	}
	
	public void openMainMenu()
	{
		setContentPane(mainMenu);
		setVisible(true);
		pack();
		mainMenu.setVisible();
	}
	public void openGamePanel()
	{
		setContentPane(gamePanel);
		setVisible(true);
		pack();
		addKeyListener(gamePanel.getKeyHandler());
	}
	public void finishBattle(boolean playerWon)
	{
		
		setContentPane(gamePanel);
		setVisible(true);
		pack();
		addKeyListener(gamePanel.getKeyHandler());
	}
	public void openBox() // pranav: press b when in gamepanel if u wanna test
	{
		removeKeyListener(gamePanel.getKeyHandler());
		box.startUp();
		setContentPane(box);
		setVisible(true);
		pack();
	}
	public void openMart()
	{
		removeKeyListener(gamePanel.getKeyHandler());
		setContentPane(pokeMart);
		setVisible(true);
		pack();
	}
	public void openBag(int state)
	{
		removeKeyListener(gamePanel.getKeyHandler());
		bag.loadScreen(state);
		setContentPane(bag);
		setVisible(true);
		pack();
	}
	public void startBattle(NPC npc, boolean isWild)
	{
		battle.newBattle(npc, isWild);
		setContentPane(battle);
		setVisible(true);
		pack();
	}
	public void openBattle()
	{
		setContentPane(battle);
		setVisible(true);
		pack();
	}
	public void openPokeSelect()
	{
		setContentPane(pokeSelect);
		setVisible(true);
		pack();
	}
	
	

	//
	//selectionMenu = new PokeSelect(this, player, 0, true);
	// bag = new Bag(player, this);
	
	public JPanel getLastScreen()
	{
		return lastScreen;
	}
	
	public static void main(String[] args) 
	{
		Main main = new Main();
		main.setResizable(false);
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.setVisible(true);
		main.pack();
		main.setLocationRelativeTo(null);
	}

}
