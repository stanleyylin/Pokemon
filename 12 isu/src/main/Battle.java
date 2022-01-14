package main;

import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;

import getimages.LoadImage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import java.awt.image.*;
import java.io.IOException;

public class Battle extends JPanel implements MouseListener {
	
	BufferedImage background;
	
	public Battle()
	{
		setPreferredSize(new Dimension(Driver2.screenWidth, Driver2.screenHeight));
	    setBackground(Color.BLACK);
		LoadImage loader = new LoadImage();
		try
		{
			background = loader.loadImage("res/battle/grass.png");
		}
		catch(IOException e) {}
		repaint();
	}
	
	public void mouseClicked(MouseEvent e) 
	{	
		
	}

	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(background, 0, 0, null);
	}
	
	public static void main(String[] args)
	{
		JFrame frame = new JFrame ("Pokemon");
		Battle panel = new Battle();
		frame.setContentPane(panel);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
