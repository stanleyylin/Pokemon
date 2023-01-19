package main;


import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;
import java.awt.*;

import getimages.LoadImage;

@SuppressWarnings("serial")
public class Congratulations extends JPanel implements MouseListener 
{
	private Main main;
	private BufferedImage image;
	private Font font = new Font("Pokemon GB", Font.PLAIN, 30);
	
	public Congratulations(Main main)
	{
		setPreferredSize(new Dimension (1080,720));
		setLayout(null);
		addMouseListener(this);
		this.main = main;
		
		LoadImage loader = new LoadImage();
		try
		{
			image = loader.loadImage("res/congratulations.png");			
		}
		catch(IOException e) {}
		repaint();
		revalidate();
	}
	
	public void paintComponent (Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(image, 0, 0, null);
		g2.setFont(font);
		g2.setColor(Color.WHITE);
		g2.drawString("Click anywhere to end game!", 40, 40);
	}

	public void mouseClicked(MouseEvent e) {
		System.exit(0);
	}

	public void mousePressed(MouseEvent e) {}

	public void mouseReleased(MouseEvent e) {}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}
	
	

}