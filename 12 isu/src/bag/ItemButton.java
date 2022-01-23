package bag;

import javax.swing.*;

import getimages.LoadImage;

import java.awt.image.*;
import java.awt.*;
import java.io.IOException;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class ItemButton extends JPanel implements MouseListener {
	
	private Item item;
	
	private BufferedImage selected;
	private BufferedImage deselected;
	
	private boolean select;
	private boolean visible;
	
	public ItemButton(BufferedImage selected, BufferedImage deselected)
	{
		setPreferredSize()
	}

	public void mouseClicked(MouseEvent e) {
		
	}

	public void mousePressed(MouseEvent e) {}

	public void mouseReleased(MouseEvent e) {}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}

	public void updateButton(Item i)
	{
		
	}
	
	public void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		
		if(visible)
		{
			Graphics2D g2 = (Graphics2D) g;
		}

	}
	
	public boolean getSelected()
	{
		return select;
	}
	public void setSelected(boolean set)
	{
		select = set;
	}
	public void setVisible(boolean set)
	{
		visible = set;
	}
}
