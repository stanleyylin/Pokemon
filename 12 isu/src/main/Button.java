package main;

import java.awt.*;
import java.awt.image.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Button extends JLabel implements MouseListener {
	private ImageIcon unselected;
	private ImageIcon selected;
	private boolean displayed;
	
	public Button(BufferedImage unselected, BufferedImage selected, int width, int height)
	{
		this.unselected = new ImageIcon(unselected);
		this.selected = new ImageIcon(selected);
		displayed = true;
		setPreferredSize(new Dimension(width, height));
		setIcon(new ImageIcon(unselected));
		// setBackground(new Color(0f, 0f, 0f, 0f));
		addMouseListener(this);
	}
	
	public void mouseClicked(MouseEvent e) {
		Battle.hideButtons();
		
	}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) 
	{
		if(displayed)
			setIcon(selected);
	}
	public void mouseExited(MouseEvent e) {
		if(displayed)
			setIcon(unselected);
	}
	
	public void display()
	{
		
	}
	
	public void displayed(boolean change)
	{
		displayed = change;
	}
}
