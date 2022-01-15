package main;

import java.awt.*;
import java.awt.image.*;
import java.io.IOException;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import getimages.LoadImage;

public class MoveSelect extends JPanel implements MouseListener{
	private static ImageIcon unselected;
	private static ImageIcon selected;
	private boolean displayed = false;
	
	public final int width = 256;
	public final int height = 98;
	
	public MoveSelect(String name, String type, int pp)
	{
		setPreferredSize(new Dimension(width, height));
		setIcon(unselected);
		addMouseListener(this);
		setFont(Battle.font);
	}
	
	public static void setImages()
	{
		LoadImage loader = new LoadImage();
		BufferedImage temp1 = null;
		BufferedImage temp2 = null;
		try
		{
			temp1 = loader.loadImage("res/battle/deselectedmove.png");
			temp2 = loader.loadImage("res/battle/selectedmove.png");
			// temp1 = loader.resize(temp1, 442, 62);
		}
		catch(IOException e) {}
		
		unselected = new ImageIcon(temp1);
		selected = new ImageIcon(temp2);
	}
	
	public void mouseClicked(MouseEvent e) {
		
	}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) 
	{
	}
	public void mouseExited(MouseEvent e) {
		
	}

}
