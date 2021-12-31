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
	public final static int screenWidth = 600;
	public final static int screenHeight = 454;
	
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
		main = new Player(100, 200, 300, 200);
		keyHandler = new KeyHandler(main);
		
		// Setting up the camera
	    camera = new Camera(340, 380, background);
	    moving = new Moving(main, camera);
		
	    gameThread = new Thread(this);
		gameThread.start();
	}

	// this is da threading?
	public void run() 
	{
		long curTime;
		double paintInterval = 1000000000/FPS;
		double delta = 0;
		long prevTime = System.nanoTime();
		long timer = 0; // FPS
		int drawCount = 0; // FPS
		
		while(gameThread != null) 
		{
			curTime = System.nanoTime();
			delta += (curTime - prevTime) / paintInterval;
			timer += (curTime - prevTime); // FPS
			prevTime = curTime;
			
			if(delta >= 1)
			{
				update();
				repaint();
				delta -= 1;
				drawCount++; // FPS
			}
			
			// tracking FPS
			if(timer >= 1000000000)
			{
				System.out.println("FPS: " + drawCount);
				drawCount = 0;
				timer = 0;
			}
		}
	}
	
	public void update() 
	{
		moving.move();
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
