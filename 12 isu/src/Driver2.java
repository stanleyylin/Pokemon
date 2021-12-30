import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.*;

import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Driver2 extends JPanel implements Runnable, KeyListener
{
	
	int FPS = 60;
	Thread thread;
	int screenWidth = 600;
	int screenHeight = 600;
	BufferedImage bg;
	int speed = 1;
	int x = 0;  //reference point
	int width;	//background width
	boolean autoMove = true;
	
	public Driver2()
	{
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    setSize(screenSize.width, screenSize.height);
	    
	}
	
	public void keyTyped(KeyEvent e) {
		
	}

	public void keyPressed(KeyEvent e) 
	{
		
	}

	public void keyReleased(KeyEvent e) 
	{
		
	}

	public void run() {

	}
	
	public static void main(String[] args)
	{
		JFrame frame = new JFrame ("Pokemon");
		Driver2 panel = new Driver2();
		frame.add(panel);
		frame.addKeyListener(panel);
		frame.setVisible(true);
		frame.pack();
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		frame.setUndecorated(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
	}

}
