import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.*;

import java.io.*;
import javax.swing.*;

public class Driver2 extends JPanel implements Runnable, KeyListener
{
	private Thread thread;
	
	private final int FPS = 50;
	private int screenWidth;
	private int screenHeight;
	
	// Player Variables
	private BufferedImage spriteSheet;
	private final int speed = 3;

	public int worldX;
	public int worldY;
	
	// temp
	private BufferedImage[] playerSprites;
	private BufferedImage background;
	private Player main;
	
	public Driver2()
	{
		// This is for full screen...
//		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//		screenWidth = screenSize.width;
//		screenHeight = screenSize.height;
		
		// Setting up the panel
		screenWidth = 600;
		screenHeight = 454;
		setPreferredSize(new Dimension(screenWidth, screenHeight));
	    setBackground(Color.BLACK);
		
	    // Loading the images (for now, just the bg and the player sprites
	    LoadImage loader = new LoadImage();
		try
		{
			background = loader.loadImage("res/map.png");
		}
		catch(IOException e) {}
		try
		{
			spriteSheet = loader.loadImage("res/char1.png");
		}
		catch(IOException e) {}
		
		// Player, temp
		main = new Player(100, 200, 300, 200);
		SpriteSheet player = new SpriteSheet(spriteSheet, 3, 4);
		playerSprites = player.getSprites(32, 32);
		worldX = 340;
		worldY = 380;

		thread = new Thread(this);
	    thread.start();
	}
	
	public void keyTyped(KeyEvent e) {}
	public void keyPressed(KeyEvent e) 
	{
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_A)
		{
			main.moving = 1;
			main.direction = "left";
		}
		else if(key == KeyEvent.VK_D) 
		{
			main.moving = 1;
			main.direction = "right";
		}
		else if(key == KeyEvent.VK_S) 
		{
			main.moving = 1;
			main.direction = "down";
		}
		else if(key == KeyEvent.VK_W) 
		{
			main.moving = 1;
			main.direction = "up";
		}
	}
	public void keyReleased(KeyEvent e) 
	{
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_A || key == KeyEvent.VK_D || key == KeyEvent.VK_W || key == KeyEvent.VK_S) 
			main.moving = 0;
	}

	public void run() 
	{
		while(true) {
			move();
			this.repaint();
			try {
				Thread.sleep(1000/FPS);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	void move() 
	{
		if(main.moving > 0)
		{
			if(main.direction.equals("left"))
			{
				worldX -= speed;
			}
			else if(main.direction.equals("right"))
			{
				worldX += speed;
			}
			else if(main.direction.equals("up"))
			{
				worldY -= speed;
			}
			else if(main.direction.equals("down"))
			{
				worldY += speed;
			}
		}
	}
	
	public void update() 
	{
		move();
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(background, 0, 0, screenWidth, screenHeight, worldX, worldY, worldX+screenWidth, worldY+screenHeight, this);
		
		if(main.moving >= 1 && main.moving <= 3)
		{
			if(main.direction.equals("up"))
				g2.drawImage(playerSprites[4], main.screenX, main.screenY, 45, 45, this);
			else if(main.direction.equals("down"))
				g2.drawImage(playerSprites[1], main.screenX, main.screenY, 45, 45, this);
			else if(main.direction.equals("left"))
				g2.drawImage(playerSprites[10], main.screenX, main.screenY, 45, 45, this);
			else if(main.direction.equals("right"))
				g2.drawImage(playerSprites[7], main.screenX, main.screenY, 45, 45, this);
			main.moving++;
		}
		else if(main.moving >= 4 && main.moving <= 6)
		{
			if(main.direction.equals("up"))
				g2.drawImage(playerSprites[5], main.screenX, main.screenY, 45, 45, this);
			else if(main.direction.equals("down"))
				g2.drawImage(playerSprites[2], main.screenX, main.screenY, 45, 45, this);
			else if(main.direction.equals("left"))
				g2.drawImage(playerSprites[11], main.screenX, main.screenY, 45, 45, this);
			else if(main.direction.equals("right"))
				g2.drawImage(playerSprites[8], main.screenX, main.screenY, 45, 45, this);
			main.moving++;
			if(main.moving == 6)
				main.moving = 1;
		}
		else if(main.moving == 0)
		{
			if(main.direction.equals("up"))
				g2.drawImage(playerSprites[3], main.screenX, main.screenY, 45, 45, this);
			else if(main.direction.equals("down"))
				g2.drawImage(playerSprites[0], main.screenX, main.screenY, 45, 45, this);
			else if(main.direction.equals("left"))
				g2.drawImage(playerSprites[9], main.screenX, main.screenY, 45, 45, this);
			else if(main.direction.equals("right"))
				g2.drawImage(playerSprites[6], main.screenX, main.screenY, 45, 45, this);
		}
	}
	
	public static void main(String[] args)
	{
		JFrame frame = new JFrame ("Pokemon");
		Driver2 panel = new Driver2();
		frame.add(panel);
		frame.addKeyListener(panel);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
