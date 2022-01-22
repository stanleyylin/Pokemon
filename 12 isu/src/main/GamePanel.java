package main;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.io.*;
import javax.swing.*;

import entity.Moving;
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
	private Thread gameThread;
	private static KeyHandler keyHandler;
	
	private Camera camera;
	private Moving moving;
	private final int FPS = 60;
	public final static int screenWidth = 1080;
	public final static int screenHeight = 720;
	private Font font = new Font("Pokemon GB", Font.PLAIN, 22);
	private boolean interact;
	// Player Variables
	
	private Player main; 
	
	public GamePanel()
	{	
		// Setting up the panel
		setPreferredSize(new Dimension(screenWidth, screenHeight));
	    setBackground(Color.BLACK);
	    BufferedImage hearthome = null;
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
		try
		{
			hearthome = loader.loadImage("res/maps/hearthome.jpeg");
			hearthome = loader.resize(hearthome, hearthome.getWidth()*2.9, hearthome.getHeight()*2.9);
		}
		catch(IOException e) {}
		// Collisions
		// Use canva, subtract the height by 30 and width by 10 or so
		Rectangle[] collisions10 = new Rectangle[8];
		collisions10[0] = new Rectangle(0, 0, 354, 1847); // trees
		collisions10[1] = new Rectangle(354, 0, 279, 383); // top left building
		collisions10[2] = new Rectangle(634, 0, 464, 626); // trees next to ^
		collisions10[3] = new Rectangle(1115, 0, 1254, 383); // trees next to ^
		collisions10[4] = new Rectangle(2352, 0, 252, 401); // right most building
		collisions10[5] = new Rectangle(2636, 0, 364, 1814); // trees below right ^
		collisions10[6] = new Rectangle(700, 708, 220, 175); // pokecentre
		collisions10[7] = new Rectangle(0, 630, 275, 1390); // leftmost building
		
		// Buildings -----
		Building[] buildings10 = new Building[2];
		buildings10[0] = new Building(new Rectangle(788, 864, 54, 55), new Rectangle(535, 567, 81, 32), null, null, pokecentre1);
		buildings10[1] = new Building(new Rectangle(1017, 853, 60, 54), new Rectangle(540, 543, 5, 1), null, null, house1);
		
		// People
		Person[] people10 = new Person[1];
		people10[0] = new Person(new Rectangle(573, 951, 51, 72), "down", "nurse.png", 17, 24);
		Location heartHome = new Location(hearthome, collisions10, buildings10, people10);
		
	// 10: 208
		BufferedImage t208 = null;
		try
		{
			t208 = loader.loadImage("res/maps/208.png");
			t208 = loader.resize(t208, t208.getWidth()*2.9, t208.getHeight()*2.9);
		}
		catch(IOException e) {}
		// Collisions
		Rectangle[] collisions208 = new Rectangle[1];
		collisions208[0] = new Rectangle(2210, 0, 761, 523); // leftmost up trees
		
		// Buildings ----
		Building[] buildings208 = new Building[0];
		buildings10[0] = new Building(new Rectangle(2635, 555, 60, 54), new Rectangle(540, 543, 81, 32), null, null, house1);
		
		Person[] people208 = new Person[0];
		Location T208 = new Location(t208, collisions208, buildings208, people208);
	
		// {0, hearthome.getHeight()-screenHeight, }

	// Gate: 11-HeartHome and 208
		Gate gate11 = new Gate(0, t208.getWidth()-screenWidth, t208.getHeight()-screenHeight, heartHome, new Rectangle(274, 1908, 52, 105), T208, new Rectangle(2920, 943, 50, 90));
		heartHome.addGate(gate11);
		T208.addGate(reverseGate(gate11, 0, heartHome.getBG().getHeight()-screenHeight));		
		// Player, temp
		main = new Player(screenWidth/2-Player.width/2, screenHeight/2-Player.height/2);
		// Functionalities
		camera = new Camera(heartHome, 300, 1200);
	    moving = new Moving(main, camera);
	    keyHandler = new KeyHandler(main, moving);
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
		if(main.direction.equals("left") || main.direction.equals("right"))
		{
			if(camera.getBuilding() == null && !camera.getLocation().getEdgeReachedX())
				moving.moveCameraX();
			else
				moving.movePlayerX();
		}
		else if(main.direction.equals("up") || main.direction.equals("down"))
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
	public void interactMessage(Graphics2D g2)
	{
		g2.setFont(font);
		g2.setColor(Color.WHITE);
		g2.drawString("Press X to interact!", 375, 623);
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		camera.draw(g2);
		moving.draw(g2);
		
		if(interact)
			interactMessage(g2);
	}
	
	// Getters and Setters
	public Player getPlayer()
	{
		return main;
	}
	public KeyHandler getKeyHandler()
	{
		return keyHandler;
	}
	public Moving getMoving()
	{
		return moving;
	}
	public void setInteract(boolean set)
	{
		interact = true;
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
