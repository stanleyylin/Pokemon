package main;

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

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
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
	private KeyItemsPanel keyItemsPanel;
	private Instructions instructions;
	private JPanel lastScreen;
	
	private Player player;
	NPC gary;
	
	private Clip music, battleMusic;
	
	public final static int screenWidth = 1080;
	public final static int screenHeight = 720;
	
	public Main()
	{
		player = new Player(screenWidth/2-Player.width/2, screenHeight/2-Player.height/2);
		try 
		{
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("res/PokemonGb-RAeo.ttf")));

		} 
		catch (FontFormatException e) {} 
		catch (IOException e) {}
		
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
		player.addPokemonToParty(new Pokemon ("Charmander", "Charmander",32));
		keyItemsPanel = new KeyItemsPanel(this,player);
		instructions = new Instructions(this);
		
		// TESTER-------
		player.addKeyItem("Town Map");
		player.addKeyItem("Badge Case");
		player.addOnItem("Poke Ball", 0, 5);

		openMainMenu();
	}
	
	public void openMainMenu()
	{
		battleMusic.stop();
		music.start();
		music.setFramePosition (0);
		music.loop(Clip.LOOP_CONTINUOUSLY);
		setContentPane(mainMenu);
		setVisible(true);
		pack();
		mainMenu.setVisible();
	}
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
		music.stop();
		battleMusic.start();
		battleMusic.setFramePosition (0);
		battleMusic.loop(Clip.LOOP_CONTINUOUSLY);
		battle.newBattle(npc, isWild);
		setContentPane(battle);
		setVisible(true);
		pack();
	}
	public void openBattle()
	{
		music.stop();
		battleMusic.start();
		battleMusic.setFramePosition (0);
		battleMusic.loop(Clip.LOOP_CONTINUOUSLY);
		setContentPane(battle);
		setVisible(true);
		pack();
	}
	public void openPokeSelect()
	{
		pokeSelect.updatePokemon();
		setContentPane(pokeSelect);
		setVisible(true);
		pack();
	}
	public void openMap() {
		keyItemsPanel.setCurImg(true);
		setContentPane(keyItemsPanel);
		setVisible(true);
		pack();
		validate();

	}
	public void openCase() {
		keyItemsPanel.setCurImg(false);
		setContentPane(keyItemsPanel);
		setVisible(true);
		pack();
		keyItemsPanel.repaint();
		keyItemsPanel.revalidate();

	}
	public void openInstructions()
	{
		setContentPane(instructions);
		setVisible(true);
		pack();
		instructions.repaint();
		instructions.revalidate();
	}
	
	

	//
	//selectionMenu = new PokeSelect(this, player, 0, true);
	// bag = new Bag(player, this);
	
	public JPanel getLastScreen()
	{
		return lastScreen;
	}
	public void setTrainer()
	{
		battle.setTrainer(true);
	}
	
	public static void main(String[] args) 
	{
		Main main = new Main();
//		main.setResizable(false);
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.setVisible(true);
		main.pack();
		main.setLocationRelativeTo(null);
	}

}
