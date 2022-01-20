package main;

import java.awt.*;
import java.awt.image.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Button extends JLabel implements MouseListener {
	private ImageIcon unselected;
	private ImageIcon selected;
	private boolean displayed;
	private Battle battle;
	private PokeSelect pokeSelect;
	
	public Button(Battle battle, BufferedImage unselected, BufferedImage selected, int width, int height)
	{
		this.battle = battle;
		this.unselected = new ImageIcon(unselected);
		this.selected = new ImageIcon(selected);
		displayed = true;
		
		setPreferredSize(new Dimension(width, height));
		setIcon(new ImageIcon(unselected));
		// setBackground(new Color(0f, 0f, 0f, 0f));
		addMouseListener(this);
	}
	
	public Button(PokeSelect pokeSelect, BufferedImage unselected, BufferedImage selected, int width, int height)
	{
		this.pokeSelect = pokeSelect;
		this.unselected = new ImageIcon(unselected);
		this.selected = new ImageIcon(selected);
		displayed = true;
		
		setPreferredSize(new Dimension(width, height));
		setIcon(new ImageIcon(unselected));
		// setBackground(new Color(0f, 0f, 0f, 0f));
		addMouseListener(this);
	}
	
	public void mouseClicked(MouseEvent e) 
	{
		if(battle == null)
			pokeSelect.buttonClick(e);
		else
			battle.buttonClick(e);
		setIcon(unselected);
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
	
//	public void display()
//	{
//		displayed = true;
//		setIcon(unselected);
//	}
//	
//	public void hide()
//	{
//		displayed = false;
//		setIcon(null);
//	}
}
