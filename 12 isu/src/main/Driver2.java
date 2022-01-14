package main;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.io.*;
import javax.swing.*;

import entity.Moving;
import entity.Player;
import getimages.LoadImage;
import getimages.SpriteSheet;
import map.Building;
import map.Camera;
import map.Location;

@SuppressWarnings("serial")
public class Driver2 extends JPanel implements Runnable
{
	private Thread gameThread;
	private static KeyHandler keyHandler;
	
	private Camera camera;
	private Moving moving;
	private final int FPS = 60;
	public final static int screenWidth = 1080;
	public final static int screenHeight = 720;
	// Player Variables
	
	private Location[][] worldMap;
	private Player main; 
	
	public Driver2()
	{	
		// Setting up the panel
		setPreferredSize(new Dimension(screenWidth, screenHeight));
	    setBackground(Color.BLACK);
	    BufferedImage map1 = null, pokecentre1 = null, house1 = null;
	    
	    // Loading the images
	    // HEARTHOME
	    LoadImage loader = new LoadImage();
		try
		{
			map1 = loader.loadImage("res/hearthome.jpeg");
			map1 = loader.resize(map1, 3000, 2292);
		}
		catch(IOException e) {}
			// Pokecentre
		try
		{
			pokecentre1 = loader.loadImage("res/pokecentre.png");
			pokecentre1 = loader.resize(pokecentre1, 739, 550);
		}
		catch(IOException e) {}
			// House First Floor
		try
		{
			house1 = loader.loadImage("res/house1.jpeg");
			house1 = loader.resize(house1, 582, 436);
		}
		catch(IOException e) {}
		
		// Player, temp
		main = new Player(screenWidth/2-Player.size/2, screenHeight/2-Player.size/2);
		keyHandler = new KeyHandler(main);
		
		// Setting up the MAPS
		worldMap = new Location[1][1];
		
			// Map 1:
		Rectangle[] collisions1 = new Rectangle[3];
		collisions1[0] = new Rectangle(700, 690, 430, 160);
		collisions1[1] = new Rectangle(0, 0, 353, 630);
		collisions1[2] = new Rectangle(0, 630, 275, 1390);
		Building[] buildings1 = new Building[2];
		buildings1[0] = new Building(new Rectangle(815, 904, 5, 1), new Rectangle(565, 592, 3, 1), null, null, pokecentre1);
		buildings1[1] = new Building(new Rectangle(1050, 904, 5, 1), new Rectangle(540, 543, 5, 1), null, null, house1);
		worldMap[0][0] = new Location(map1, collisions1, buildings1, null, null);
		// maps[0] = new Map();
	   
		// Functionalities
		camera = new Camera(worldMap[0][0], 700, 700);
	    moving = new Moving(main, camera);
	    
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
	
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		camera.draw(g2);
		moving.draw(g2);

	}
	
	public Player getPlayer()
	{
		return main;
	}
	
	public static void main(String[] args)
	{
		JFrame frame = new JFrame ("Pokemon");
		Driver2 panel = new Driver2();
		frame.add(panel);
		frame.addKeyListener(keyHandler);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
