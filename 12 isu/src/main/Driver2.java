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
import map.Camera;

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
	
	private BufferedImage background; // background
	private Player main; // player class ? rn its just keeping track of coordinates
	
	public Driver2()
	{	
		// Setting up the panel
		setPreferredSize(new Dimension(screenWidth, screenHeight));
	    setBackground(Color.BLACK);
		
	    // Loading the images (for now, just the bg and the player sprites)
	    LoadImage loader = new LoadImage();
		try
		{
			background = loader.loadImage("res/map.png");
		}
		catch(IOException e) {}
		
		
		// Player, temp
		main = new Player(screenWidth/2, screenHeight/2);
		keyHandler = new KeyHandler(main);
		
		// Setting up the camera
	    camera = new Camera(340, 380, background, false, false);
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
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public void update() 
	{
		moving.changeSprite();
		if(main.direction.equals("left") || main.direction.equals("right"))
		{
			if(camera.getEdgeReachedX())
				moving.movePlayerX();
			else
				moving.moveCameraX();
		}
		else if(main.direction.equals("up") || main.direction.equals("down"))
		{
			if(camera.getEdgeReachedY())
				moving.movePlayerY();
			else
				moving.moveCameraY();
		}
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		camera.draw(g2);
		moving.draw(g2);
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
