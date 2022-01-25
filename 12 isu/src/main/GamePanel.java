package main;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.io.*;
import javax.swing.*;

import entity.Moving;
import entity.NPC;
import entity.Nurse;
import entity.Person;
import entity.Player;
import getimages.LoadImage;
import getimages.SpriteSheet;
import map.Building;
import map.Camera;
import map.Gate;
import map.Location;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable
{
	private Main main;
	private Thread gameThread;
	private static KeyHandler keyHandler;
	
	private Dialogue dialogue;
	private Camera camera;
	private Moving moving;
	private final int FPS = 60;
	public final static int screenWidth = 1080;
	public final static int screenHeight = 720;
	private Font font = new Font("Pokemon GB", Font.PLAIN, 22);
	private boolean test = true;
	private boolean interact;
	
	private boolean battling;
	// Player Variables
	
	private Player player; 
	
	public GamePanel(Main main, Player player)
	{	
		this.main = main;
		this.player = player;
		
		// Setting up the panel
		setPreferredSize(new Dimension(screenWidth, screenHeight));
	    setBackground(Color.BLACK);
	    BufferedImage pokecentre1 = null, house1 = null;
	    
	    // Loading the images
	    LoadImage loader = new LoadImage();
		
	    // Common Buildings
	    try
		{
			pokecentre1 = loader.loadImage("res/pokecentre.png");
			pokecentre1 = loader.resize(pokecentre1, 739, 550);
			house1 = loader.loadImage("res/house1.jpeg");
			house1 = loader.resize(house1, 582, 436);
		}
		catch(IOException e) {}
		
	    // MAPS
	 // 10. Hearthome City:
		// Collisions
		// Use canva, multiply by 3, subtract the height by 30 and width by 10 or so
		Rectangle[] collisions10 = new Rectangle[8];
		collisions10[0] = new Rectangle(0, 0, 366, 1911); // trees
		collisions10[1] = new Rectangle(366, 0, 288, 396); // top left building
		collisions10[2] = new Rectangle(656, 0, 480, 648); // trees next to ^
		collisions10[3] = new Rectangle(1153, 0, 1297, 396); // trees next to ^
		collisions10[4] = new Rectangle(2433, 0, 260, 415); // right most building
		collisions10[5] = new Rectangle(2727, 0, 377, 1877); // trees below right ^
		collisions10[6] = new Rectangle(724, 732, 228, 181); // pokecentre
		collisions10[7] = new Rectangle(0, 651, 284, 1438); // leftmost building
		// Buildings -----
		Building[] buildings10 = new Building[2];
		Person[] peoplePC = new Person[1];
		peoplePC[0] = new Nurse(new Rectangle(519, 225, 46, 55), "nurse.png", 17, 24, "nurse.txt");
		Rectangle[] collisionsPC = new Rectangle[0];
		buildings10[0] = new Building(new Rectangle(1052, 882, 62, 56), new Rectangle(540, 543, 5, 1), new Person[0], new Rectangle[0], house1);
		buildings10[1] = new Building(new Rectangle(818, 894, 62, 56), new Rectangle(535, 567, 81, 32), peoplePC, collisionsPC, pokecentre1);
		//-----
		//People
		Person[] people10 = new Person[1];
		people10[0] = new Person(new Rectangle(665, 922, 51, 72), "down", "nurse.png", 17, 24, "d1.txt");
		//Grass: None
		Location heartHome = new Location("hearthome.png", collisions10, buildings10, people10, null);
		
	// Route 208
		// Collisions
		Rectangle[] collisions208 = new Rectangle[1];
		collisions208[0] = new Rectangle(2286, 0, 787, 541); // leftmost up trees
		// Buildings ----
		Building[] buildings208 = new Building[1];
		buildings208[0] = new Building(new Rectangle(2726, 574, 62, 56), new Rectangle(540, 543, 81, 32), new Person[0], new Rectangle[0], house1);
		//-----
		// People
		Person[] people208 = new Person[0];
		// Grass
		Rectangle[] grass208 = new Rectangle[1];
		grass208[0] = new Rectangle(2007, 738, 480, 471);
		Location T208 = new Location("208.png", collisions208, buildings208, people208, grass208);
	
		// {0, hearthome.getHeight()-screenHeight, }

	// Gate: 11-HeartHome and 208
		Gate gate11 = new Gate(0, T208.getBG().getWidth()-screenWidth, T208.getBG().getHeight()-screenHeight, heartHome, new Rectangle(283, 1973, 54, 109), T208, new Rectangle(3021, 975, 52, 93));
		heartHome.addGate(gate11);
		T208.addGate(reverseGate(gate11, 0, heartHome.getBG().getHeight()-screenHeight));		
		
		// Functionalities
		dialogue = new Dialogue(this, player);
		dialogue.setBounds(8, 8, 1064, 172);
		camera = new Camera(heartHome, 300, 1200);
	    moving = new Moving(this, player, camera);
	    keyHandler = new KeyHandler(this, player, moving);
	    interact = false;
	    gameThread = new Thread(this);
		gameThread.start();
	}

	// this is da threading?
	public void run() 
	{
		double paintInterval = 1000000000/FPS;
		double nextPaint = System.nanoTime() + paintInterval;
		
		while(true) 
		{
			update();
			repaint();
			
			//add start battle if bool == true here
			
			try
			{
				double remaining = nextPaint - System.nanoTime();
				remaining /= 1000000;
				
				if(remaining < 0)
					remaining = 0;
					
				Thread.sleep((long) remaining);
				
				nextPaint += paintInterval;
			}
			catch(InterruptedException e) {}
		}
	}
	
	public void update() 
	{
		moving.changeSprite();
		if(player.direction.equals("left") || player.direction.equals("right"))
		{
			if(camera.getBuilding() == null && !camera.getLocation().getEdgeReachedX())
				moving.moveCameraX();
			else
				moving.movePlayerX();
		}
		else if(player.direction.equals("up") || player.direction.equals("down"))
		{
			if(camera.getBuilding() == null && !camera.getLocation().getEdgeReachedY())
				moving.moveCameraY();
			else
				moving.movePlayerY();
		}
	}
	
	public Gate reverseGate(Gate g, int camX, int camY)
	{
		int edge = 0;
		if(g.getAxis() == 0)
			edge = 1;
		else if(g.getAxis() == 1)
			edge = 0;
		else if(g.getAxis() == 2)
			edge = 3;
		else 
			edge = 2;

		return new Gate(edge, camX, camY, g.getL2(), g.getR2(), g.getL1(), g.getR1());
	}
	public void interactMessage(Graphics g)
	{
		g.setFont(font);
		g.setColor(Color.WHITE);
		g.drawString("Press X to interact!", 375, 623);
	}
	
	// Battle
	public void startBattle()
	{
//		main.battle(new NPC);
		main.openBattlePanel(null, true);
		//basically this will make an npc w just one mon and then we can makethe constructor have the boolean
		//isWIld to true (so pokeballs can be used)

	}
	public void startNPCBattle(NPC npc)
	{
		System.out.println("hello");
		main.openBattlePanel(npc, false);
	}
	
	public void showDialogue()
	{
		this.add(dialogue);
		dialogue.startDialogue(player.getTalkingTo());
	}
	public void hideDialogue()
	{
		Container parent = dialogue.getParent();
		parent.remove(dialogue);
		parent.revalidate();
		parent.repaint();
	}
	
	public void openBox()
	{
		main.openBox();
	}
	public void openMart()
	{
		main.openMart();
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		if(test)
		{
			g2.drawString("test", 0, 0);
			test = false;
		}
		
		camera.draw(g2);
		moving.draw(g2);
		
		if(interact)
		{
			interactMessage(g);
		}
	}
	
	// Getters and Setters
	public KeyHandler getKeyHandler()
	{
		return keyHandler;
	}
	public Moving getMoving()
	{
		return moving;
	}
	public Dialogue getDialogue()
	{
		return dialogue;
	}
	public boolean getInteract()
	{
		return interact;
	}
	public Player getPlayer()
	{
		return player;
	}
	public void setInteract(boolean set)
	{
		interact = set;
	}
	
//	public static void main(String[] args)
//	{
//		JFrame frame = new JFrame ("Pokemon");
//		GamePanel panel = new GamePanel();
//		
//		frame.add(panel);
//		frame.addKeyListener(keyHandler);
//		frame.setVisible(true);
//		frame.setResizable(false);
//		frame.pack();
//		frame.setLocationRelativeTo(null);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	}

}
