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
	
	private Player player = new Player(screenWidth/2-Player.width/2, screenHeight/2-Player.height/2);
; 
	
	public GamePanel()
	{	
//		this.main = main;
//		this.player = player;
		
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
	    
	//1. Twinleaf City
	    
	    Rectangle[] collisions1 = new Rectangle[12];
	    collisions1[0] = new Rectangle(0,0,520,120);
	    collisions1[1] = new Rectangle(0,0,255,1131);
	    collisions1[2] = new Rectangle(0,975,1470,180);
	    collisions1[3] = new Rectangle(960,0,540,150);
	    collisions1[4] = new Rectangle(1224,0,300,1116);
	    collisions1[5] = new Rectangle(360,210,255,195);
	    collisions1[6] = new Rectangle(900,270,185,165);
	    collisions1[7] = new Rectangle(390,630,185,165);
	    collisions1[8] = new Rectangle(885,570,255,195);
	    collisions1[9] = new Rectangle(600,340,45,60);
	    collisions1[10] = new Rectangle(840,720,45,60);
	    collisions1[11] = new Rectangle(720,480,75,50);

	    Building[] buildings1 = new Building[4];
		buildings1[0] = new Building(new Rectangle(390, 411, 45, 30), new Rectangle(540, 543, 5, 1), new Person[0], new Rectangle[0], house1);
		buildings1[1] = new Building(new Rectangle(930, 390, 50, 60), new Rectangle(540, 543, 5, 1), new Person[0], new Rectangle[0], house1);
		buildings1[2] = new Building(new Rectangle(450, 780, 45, 30), new Rectangle(540, 543, 5, 1), new Person[0], new Rectangle[0], house1);
		buildings1[3] = new Building(new Rectangle(905, 780, 45, 30), new Rectangle(540, 543, 5, 1), new Person[0], new Rectangle[0], house1);

	    Person[] people1 = new Person[1];
		people1[0] = new Person(new Rectangle(665, 922, 51, 72), "down", "nurse.png", 17, 24, "d1.txt");
		
	    Location twinLeaf = new Location("twinleaf2.png",collisions1,buildings1,people1,null);
	    
	//2.Sandgem City
	    
	    Rectangle[] collisions2 = new Rectangle[14];
	    collisions2[0] = new Rectangle(0,0,930,360);
	    collisions2[1] = new Rectangle(0,585,255,1025);
	    collisions2[2] = new Rectangle(0,0,510,390);
	    collisions2[3] = new Rectangle(945,0,189,105);
	    collisions2[4] = new Rectangle(1410,0,300,1200);
	    collisions2[5] = new Rectangle(1230,960,500,500);
	    collisions2[6] = new Rectangle(285,1011,669,500);
	    collisions2[7] = new Rectangle(690,375,300,60);
	    collisions2[8] = new Rectangle(1215,195,400,220);
	    collisions2[9] = new Rectangle(276,675,180,185);
	    collisions2[10] = new Rectangle(684,640,267,210);
	    collisions2[11] = new Rectangle(654,819,36,46);
	    collisions2[12] = new Rectangle(1032,816,36,49);
	    collisions2[13] = new Rectangle(1170,372,36,46);


	    Building[] buildings2 = new Building[5];
		buildings2[0] = new Building(new Rectangle(300, 890, 45, 28), new Rectangle(540, 543, 5, 1), new Person[0], new Rectangle[0], house1);
		buildings2[1] = new Building(new Rectangle(345, 399, 45, 28), new Rectangle(540, 543, 5, 1), new Person[0], new Rectangle[0], house1);//should b oaks lab
		buildings2[2] = new Building(new Rectangle(780, 408, 45, 34), new Rectangle(540, 543, 5, 1), new Person[0], new Rectangle[0], pokecentre1);
		buildings2[3] = new Building(new Rectangle(1260, 420, 45, 28), new Rectangle(540, 543, 5, 1), new Person[0], new Rectangle[0], house1);//should be pokemart
		buildings2[4] = new Building(new Rectangle(738, 870, 45, 28), new Rectangle(540, 543, 5, 1), new Person[0], new Rectangle[0], house1);

	    Person[] people2 = new Person[1];
		people2[0] = new Person(new Rectangle(665, 922, 51, 72), "down", "nurse.png", 17, 24, "d1.txt");

	    Location sandGem = new Location("sandgem.png",collisions2,buildings2,people2,null);

	    //3. Jubilife City
	    
	    //4. Oreburgh City
	    
	    Rectangle[] collisions4 = new Rectangle[12];
	    collisions4[0] = new Rectangle(350,0,1560,510);
	    collisions4[1] = new Rectangle(0,0,330,450);
	    collisions4[2] = new Rectangle(0,690,426,456);
	    collisions4[3] = new Rectangle(426,1065,2500,2500);
	    collisions4[4] = new Rectangle(2385,696,2500,2500);
	    collisions4[5] = new Rectangle(780,678,663,240);
	    collisions4[6] = new Rectangle(1740,489,180,240);
	    collisions4[7] = new Rectangle(2760,465,1000,1000);
	    collisions4[8] = new Rectangle(2130,222,1000,354);
	    collisions4[9] = new Rectangle(2265,0,1000,195);
	    collisions4[10] = new Rectangle(2604,600,1000,1000);
	    collisions4[11] = new Rectangle(2175,708,1000,190);


	    
	    Building[] buildings4 = new Building[3];
		buildings4[0] = new Building(new Rectangle(1224, 834, 45, 28), new Rectangle(540, 543, 5, 1), new Person[0], new Rectangle[0], house1);//should b gym
		buildings4[1] = new Building(new Rectangle(1380, 465, 45, 34), new Rectangle(540, 543, 5, 1), new Person[0], new Rectangle[0], pokecentre1);
		buildings4[2] = new Building(new Rectangle(2235, 855, 45, 28), new Rectangle(540, 543, 5, 1), new Person[0], new Rectangle[0], house1);//should be pokemart

	    
	    Person[] people4 = new Person[1];
		people4[0] = new Person(new Rectangle(665, 922, 51, 72), "down", "nurse.png", 17, 24, "d1.txt");
		
		Location oreburg = new Location("oreburgh.png",collisions4,buildings4,people4,null);

	    //Floarama City
		
		Rectangle[] collisions5 = new Rectangle[9];
		collisions5[0] = new Rectangle(0,900,1545,390);
		collisions5[1] = new Rectangle(1440,1140,400,1700);
		collisions5[2] = new Rectangle(620,2283,400,800);
		collisions5[3] = new Rectangle(0,2280,440,240);
		collisions5[4] = new Rectangle(678,2016,255,180);
		collisions5[5] = new Rectangle(1089,2004,219,185);
		collisions5[6] = new Rectangle(480,1590,219,185);
		collisions5[7] = new Rectangle(1110,1710,180,165);
		collisions5[8] = new Rectangle(810,1410,270,210);


	    Building[] buildings5 = new Building[3];
		buildings5[0] = new Building(new Rectangle(930, 1605, 45, 60), new Rectangle(540, 543, 5, 1), new Person[0], new Rectangle[0], house1);//should b champion battle
		buildings5[1] = new Building(new Rectangle(750, 2175, 45, 34), new Rectangle(540, 543, 5, 1), new Person[0], new Rectangle[0], pokecentre1);
		buildings5[2] = new Building(new Rectangle(1140, 1845, 45, 50), new Rectangle(540, 543, 5, 1), new Person[0], new Rectangle[0], house1);//should be pokemart

	    Person[] people5 = new Person[1];
		people5[0] = new Person(new Rectangle(665, 922, 51, 72), "down", "nurse.png", 17, 24, "d1.txt");


		Location floaroma = new Location("floaroma.png",collisions5,buildings5,people5,null);

		
		
	 // 10. Hearthome City:
		// Collisions
		// Use canva, multiply by 3, subtract the height by 30 and width by 10 or so
		Rectangle[] collisions10 = new Rectangle[18];
		collisions10[0] = new Rectangle(0, 0, 366, 1911); // trees
		collisions10[1] = new Rectangle(366, 0, 288, 396); // top left building
		collisions10[2] = new Rectangle(656, 0, 480, 648); // trees next to ^
		collisions10[3] = new Rectangle(1153, 0, 1297, 396); // trees next to ^
		collisions10[4] = new Rectangle(2433, 0, 260, 415); // right most building
		collisions10[5] = new Rectangle(2727, 0, 377, 1877); // trees below right ^
		collisions10[6] = new Rectangle(724, 732, 228, 181); // pokecentre
		collisions10[7] = new Rectangle(0, 651, 284, 1438); // leftmost building
		collisions10[8] = new Rectangle(0, 1635, 1460, 210); // trees on left above gate
		collisions10[9] = new Rectangle(0, 2097, 3100, 1000); // bnottom trees
		collisions10[10] = new Rectangle(2070, 1665, 1000, 180); // chairs onr ightv
		collisions10[11] = new Rectangle(1860,1170,540,245); // two right houses
		collisions10[12] = new Rectangle(1095,1215,450,195); // two middle houses
		collisions10[13] = new Rectangle(450,1110,330,300); // big left house 
		collisions10[14] = new Rectangle(720,750,495,180); // two left (pokecentre) houses
		collisions10[15] = new Rectangle(1740,720,225,165); // big middle house
		collisions10[16] = new Rectangle(1140,540,450,45); // coliseum
		collisions10[17] = new Rectangle(2280,720,360,210); // gym










		// Buildings -----
		Building[] buildings10 = new Building[4];
		Person[] peoplePC = new Person[1];
		peoplePC[0] = new Nurse(new Rectangle(519, 225, 46, 55), "nurse.png", 17, 24, "nurse.txt");
		Rectangle[] collisionsPC = new Rectangle[0];
		buildings10[0] = new Building(new Rectangle(1052, 882, 62, 56), new Rectangle(540, 543, 5, 1), new Person[0], new Rectangle[0], house1);
		buildings10[1] = new Building(new Rectangle(818, 894, 62, 56), new Rectangle(535, 567, 81, 32), peoplePC, collisionsPC, pokecentre1);
		buildings10[2] = new Building(new Rectangle(1380, 1395, 45, 50), new Rectangle(540, 543, 5, 1), new Person[0], new Rectangle[0], house1);//should be pokemart
		buildings10[3] = new Building(new Rectangle(2430, 915, 45, 60), new Rectangle(540, 543, 5, 1), new Person[0], new Rectangle[0], house1);//should b GYM

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
//		camera = new Camera(heartHome, 300, 1200);
		camera = new Camera(heartHome,300,1200);
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
		// main.openBattlePanel(null, true);
		//basically this will make an npc w just one mon and then we can makethe constructor have the boolean
		//isWIld to true (so pokeballs can be used)

	}
	public void startNPCBattle(NPC npc)
	{
		System.out.println("hello");
		// main.openBattlePanel(npc, false);
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
	public void openBag()
	{
		main.openBag(0);
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
	
	public static void main(String[] args)
	{
		JFrame frame = new JFrame ("Pokemon");
		GamePanel panel = new GamePanel();
		
		frame.add(panel);
		frame.addKeyListener(keyHandler);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
