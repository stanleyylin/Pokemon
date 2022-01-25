package main;

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
	
	private static Player player;
	
	public final static int screenWidth = 1080;
	public final static int screenHeight = 720;
	
	public Main()
	{
		player = new Player(screenWidth/2-Player.width/2, screenHeight/2-Player.height/2);
		
		// Initialization
		mainMenu = new MainMenu(this);
		gamePanel = new GamePanel(this, player);
		box = new Box(this, player);
		pokeMart = new PokeMart(this, player);
		
		Pokemon[] temp = new Pokemon[6];
		temp[0] = new Pokemon("Bulbasaur", "Bulby", 12);
		temp[1] = new Pokemon ("Fearow", "birdy", 25);
		NPC gary = new NPC("Trainer Peppa", new Rectangle(12, 12, 12, 12), "up", "Up", 0,0, "hi", "hi", temp);
		player.addPokemonToParty(new Pokemon ("Fearow", "birdy", 25));
		battle = new Battle(this, player);
		
		bag = new Bag(this, player, battle);
		
		try 
		{
			BlankMon.getAllMoves();
			BlankMon.getAllMoveLists();
			BlankMon.getAllAbilities();
			Pokemon.addAllPokemon();
		} 
		catch (IOException e) {}
		
		setContentPane(mainMenu);
	}
	
	// To open Game Panel
	public void openGamePanel()
	{
		setContentPane(gamePanel);
		setVisible(true);
		pack();
		addKeyListener(gamePanel.getKeyHandler());
	}
	public void openBox() // pranav: press b when in gamepanel if u wanna test
	{
		removeKeyListener(gamePanel.getKeyHandler());
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
	public void openBag()
	{
		removeKeyListener(gamePanel.getKeyHandler());
		setContentPane(pokeMart);
		setVisible(true);
		pack();
	}
	public void openBattlePanel(NPC npc, boolean isWild)
	{
		setContentPane(battle);
		setVisible(true);
		pack();
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
