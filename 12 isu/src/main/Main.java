package main;

// Main controls the JFrame
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
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
	
	// All the different panels
	private MainMenu mainMenu;
	private GamePanel gamePanel;
	private Bag bag;
	private Box box;
	private PokeSelect pokeSelect;
	private PokeMart pokeMart;
	private Battle battle;
	private KeyItemsPanel keyItemsPanel;
	private Instructions instructions;
	private Congratulations congrats;
	
	// The player
	private Player player;
	
	// Music
	private Clip music, battleMusic;
	
	public final static int screenWidth = 1080;
	public final static int screenHeight = 720;
	
	// Constructor
	public Main()
	{
		player = new Player(screenWidth/2-Player.width/2, screenHeight/2-Player.height/2);
		// Getting the font
		try 
		{
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("res/PokemonGb-RAeo.ttf")));

		} 
		catch (FontFormatException e) {} 
		catch (IOException e) {}
		
		// Audio sound
		try
		{
			AudioInputStream sound = AudioSystem.getAudioInputStream(new File ("res/music.wav"));
			music = AudioSystem.getClip();

			music.open(sound);
			sound = AudioSystem.getAudioInputStream(new File ("res/battle.wav"));
			battleMusic = AudioSystem.getClip();
			battleMusic.open(sound);
			
		} catch(Exception e) {}
		
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
		Pokemon charm = new Pokemon ("Charmander", "Oak's Flame",12);
		charm.giveExp(charm);
		player.addPokemonToParty(charm);
		
		keyItemsPanel = new KeyItemsPanel(this,player);
		instructions = new Instructions(this);
		congrats = new Congratulations(this);
		
		player.addKeyItem("Town Map");
		player.addKeyItem("Badge Case");
		player.addOnItem("Poke Ball", 0, 5);

		openMainMenu();
	}
	
	// Opens the main menu
	public void openMainMenu()
	{
		if(battleMusic != null)
			battleMusic.stop();
		music.start();
		music.setFramePosition (0);
		music.loop(Clip.LOOP_CONTINUOUSLY);
		setContentPane(mainMenu);
		setVisible(true);
		pack();
		mainMenu.setVisible();
	}
	// Opens the game panel
	public void openGamePanel()
	{
		battleMusic.stop();
		music.start();
		music.setFramePosition (0);
		music.loop(Clip.LOOP_CONTINUOUSLY);
		setContentPane(gamePanel);
		setVisible(true);
		pack();
		addKeyListener(gamePanel.getKeyHandler());
	}
	// Ends the battle and returns to game panel
	public void finishBattle(boolean playerWon)
	{
		
		setContentPane(gamePanel);
		setVisible(true);
		pack();
		addKeyListener(gamePanel.getKeyHandler());
	}
	// Opens box
	public void openBox() 
	{
		removeKeyListener(gamePanel.getKeyHandler());
		box.startUp();
		setContentPane(box);
		setVisible(true);
		pack();
	}
	// Opens the store
	public void openMart()
	{
		removeKeyListener(gamePanel.getKeyHandler());
		setContentPane(pokeMart);
		setVisible(true);
		pack();
	}
	// Opens the bag
	public void openBag(int state)
	{
		removeKeyListener(gamePanel.getKeyHandler());
		bag.loadScreen(state);
		setContentPane(bag);
		setVisible(true);
		pack();
	}
	// Starts a new battle
	public void startBattle(NPC npc, boolean isWild)
	{
		music.stop();
		battleMusic.start();
		battleMusic.setFramePosition (0);
		battleMusic.loop(Clip.LOOP_CONTINUOUSLY);
		battle.newBattle(npc, isWild);
		removeKeyListener(gamePanel.getKeyHandler());
		setContentPane(battle);
		setVisible(true);
		pack();
	}
	// Returns to battle
	public void openBattle()
	{
		setContentPane(battle);
		setVisible(true);
		pack();
	}
	// Opens pokemon selection
	public void openPokeSelect()
	{
		pokeSelect.updatePokemon();
		setContentPane(pokeSelect);
		setVisible(true);
		pack();
	}
	// Opens the map
	public void openMap() {
		keyItemsPanel.setCurImg(true);
		setContentPane(keyItemsPanel);
		setVisible(true);
		pack();
		validate();

	}
	// Opens the case
	public void openCase() {
		keyItemsPanel.setCurImg(false);
		setContentPane(keyItemsPanel);
		setVisible(true);
		pack();
		keyItemsPanel.repaint();
		keyItemsPanel.revalidate();

	}
	// Opens the instructions
	public void openInstructions()
	{
		setContentPane(instructions);
		setVisible(true);
		pack();
		instructions.repaint();
		instructions.revalidate();
	}
	// Opens congrats
	public void openCongratulations()
	{
		setContentPane(congrats);
		setVisible(true);
		pack();
		congrats.repaint();
		congrats.revalidate();
	}

	
	public void setTrainer()
	{
		battle.setTrainer(true);
	}
	public void setBattling(boolean set)
	{
		gamePanel.setBattling(set);
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
