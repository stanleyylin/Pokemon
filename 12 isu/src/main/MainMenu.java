package main;

import java.awt.Color;
import java.awt.*;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

import getimages.LoadImage;

import java.awt.image.*;
import java.io.IOException;

public class MainMenu extends JPanel implements MouseListener
{
	private Main main;
	
	// Images
	BufferedImage title;
	BufferedImage subtitle;
	BufferedImage[] buttons;
	BufferedImage bg;
	BufferedImage credit;
	BufferedImage[] currButtons;
	
	static Timer timer; // Timer for animation
	static boolean timerOn; // Is the timer on?
	static int counter;
	static float[] opacity;
	static JFrame frame;
	static JLabel[] label;
	
	public MainMenu(Main main)
	{
		this.main = main;
		setPreferredSize(new Dimension(GamePanel.screenWidth, GamePanel.screenHeight));
	    setBackground(Color.BLACK);
	    
		LoadImage loader = new LoadImage();
		try
		{
			title = loader.loadImage("res/mainmenu/title.png");
		}
		catch(IOException e) {}
		
		try
		{
			subtitle = loader.loadImage("res/mainmenu/subtitle.png");
			subtitle = loader.resize(subtitle, 437, 122);
		}
		catch(IOException e) {}
		
		try
		{
			bg = loader.loadImage("res/mainmenu/main.png");
		}
		catch(IOException e) {}
		
		try
		{
			bg = loader.loadImage("res/mainmenu/main.png");
		}
		catch(IOException e) {}
		
		buttons = new BufferedImage[6];
		
		for(int i = 1; i <= 6; i++)
		{
			try
			{
				buttons[i-1] = loader.loadImage("res/mainmenu/" + i + ".png");
			}
			catch(IOException e) {}
		}
		currButtons = new BufferedImage[3];
		currButtons[0] = buttons[0];
		currButtons[1] = buttons[2];
		currButtons[2] = buttons[4];
		
		try
		{
			credit = loader.loadImage("res/mainmenu/credit.png");
		}
		catch(IOException e) {}
		
		opacity = new float[6];
		for(int i = 0; i < 6; i++)
			opacity[i] = 0.09f;
		
		label = new JLabel[3];
		label[0] = new JLabel();
		label[1] = new JLabel();
		label[2] = new JLabel();
		
		counter = 0;
		timer = new Timer(35, new TimerEventHandler ());
		timerOn = true;
		timer.start();
	}
	
	// TimeEventHandler class is for the timer.
	private class TimerEventHandler implements ActionListener
	{
		// This method is called by the Timer, taking an ActionEvent as a parameter and returning void.
		public void actionPerformed (ActionEvent event)
		{
			repaint();
			counter++;
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		if(x>=365 && x<=365+currButtons[0].getWidth() && y>=310 && y <=310+currButtons[0].getHeight())
		{
			currButtons[0] = buttons[0];
			repaint();
			main.openGamePanel();
		}
		else if(x>=340 && x<=340+currButtons[0].getWidth() && y>=390 && y <=390+currButtons[0].getHeight())
		{
			currButtons[1] = buttons[2];
			repaint();
		}
		else if(x>=285 && x<=285+currButtons[0].getWidth() && y>=470 && y <=470+currButtons[0].getHeight())
		{
			currButtons[2] = buttons[4];
			repaint();
		}
		
	}

	public void mousePressed(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		if(x>=365 && x<=365+currButtons[0].getWidth() && y>=310 && y <=310+currButtons[0].getHeight())
		{
			currButtons[0] = buttons[1];
			repaint();
		}
		else if(x>=340 && x<=340+currButtons[0].getWidth() && y>=390 && y <=390+currButtons[0].getHeight())
		{
			currButtons[1] = buttons[3];
			repaint();
		}
		else if(x>=285 && x<=285+currButtons[0].getWidth() && y>=470 && y <=470+currButtons[0].getHeight())
		{
			currButtons[2] = buttons[5];
			repaint();
		}
	}

	public void mouseReleased(MouseEvent e) {
		currButtons[0] = buttons[0];
		currButtons[1] = buttons[2];
		currButtons[2] = buttons[4];
		repaint();
	}

	public void mouseEntered(MouseEvent e) {
		
	}

	public void mouseExited(MouseEvent e) {}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(bg, 0, 0, null);
		if(timerOn)
		{
			// title
			if(counter >= 10 && counter <= 18)
			{
				g2.drawImage(title, 247, 30, null);	
			}
			else if(counter >= 18)
			{
				g2.drawImage(title, 247, 30, null);	
			}
			
			// subtitle
			if(counter >= 18 && counter <= 26)
			{
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity[1]));
				g2.drawImage(subtitle, 328, 150, null);
				opacity[1] += 0.1f;
			}
			else if(counter >= 26)
			{
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity[1]));
				g2.drawImage(subtitle, 328, 150, null);
			}
			
			// button 1/3
			if(counter >= 26 && counter <= 34)
			{
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity[2]));
				g2.drawImage(currButtons[0], 365, 310, null);
				opacity[2] += 0.1f;
			}
			else if(counter >= 34)
			{
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity[2]));
				g2.drawImage(currButtons[0], 365, 310, null);
			}
			
			// button 2/3
			if(counter >= 34 && counter <= 42)
			{
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity[3]));
				g2.drawImage(currButtons[1], 340, 390, null);
				opacity[3] += 0.1f;
			}
			else if(counter >= 42)
			{
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity[3]));
				g2.drawImage(currButtons[1], 340, 390, null);
			}
			
			// button 3/3
			if(counter >= 42 && counter <= 50)
			{
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity[4]));
				g2.drawImage(currButtons[2], 285, 470, null);
				opacity[4] += 0.1f;
			}
			else if(counter >= 50)
			{
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity[4]));
				g2.drawImage(currButtons[2], 285, 470, null);
			}
			
			// credit
			if(counter >= 50 && counter <= 58)
			{
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity[5]));
				g2.drawImage(credit, 310, 641, null);
				opacity[5] += 0.1f;
			}
			else if(counter >= 58)
			{
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity[5]));
				g2.drawImage(credit, 310, 641, null);
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
				addMouseListener(this);
				timerOn = false;
				timer.stop();
			}
		}
		else
		{
			g2.drawImage(title, 247, 30, null);	
			g2.drawImage(subtitle, 328, 150, null);
			g2.drawImage(currButtons[0], 365, 310, null);
			g2.drawImage(currButtons[1], 340, 390, null);
			g2.drawImage(currButtons[2], 285, 470, null);
			g2.drawImage(credit, 310, 641, null);
		}
	}

}
