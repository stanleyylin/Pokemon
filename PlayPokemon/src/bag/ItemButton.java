package bag;

import javax.swing.*;

import getimages.LoadImage;

import java.awt.image.*;
import java.awt.*;
import java.io.IOException;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class ItemButton extends JPanel implements MouseListener {
	//class for button objects that items use
	
	private Bag bag; //players bag
	private Item item; //item object
	
	private static BufferedImage selected; //icon when selected
	private static BufferedImage deselected; //icon when not selected
	private BufferedImage icon; //icon generally
	
	private boolean select; //stores if an icon is selected
	private boolean visible; //stores if an icon is visible
	
	private Font font = new Font("Pokemon GB", Font.PLAIN, 22);
	
	//constructor
	public ItemButton(Bag bag)
	{
		this.bag = bag;
		setPreferredSize(new Dimension(441, 82));
		setBackground(new Color(0f, 0f, 0f, 0f));
		addMouseListener(this);
		setLayout(null);
	}
	
	//sets the images as selected or deselected
	public static void setImages(BufferedImage select, BufferedImage deselect)
	{
		selected = select;
		deselected = deselect;
	}

	//mouselistener methods
	public void mouseClicked(MouseEvent e) 
	{
		if(visible)
			bag.selected(e);
	}

	public void mousePressed(MouseEvent e) {}

	public void mouseReleased(MouseEvent e) {}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}

	public void updateButton(Item i, boolean selected)
	{
		this.item = i;
		icon = item.getSprite();
		select = selected;
	}
	
	//sets the icon visible and repaints it
	public void setVisible(boolean b)
	{
		visible = b;
		repaint();
		revalidate();
	}
	
	public void paintComponent(Graphics g) 
	{
		
		if(visible)
		{
			Graphics2D g2 = (Graphics2D) g;
			if(select)
				g2.drawImage(selected, 0, 0, null);
			else
				g2.drawImage(deselected, 0, 0, null);
			
			g2.setFont(font);
			if(select)
				g2.setColor(Color.WHITE);
			else
				g2.setColor(new Color(40, 48, 48));
			
			g2.drawString(item.getName(), 90, 50);
			g2.drawString(Integer.toString(item.getQuantity()), 380, 50);
			
			g2.drawImage(icon, 25, 15, null);
		}

	}
	public Item getItem()
	{
		return item;
	}
	public void setSelected(boolean set)
	{
		select = set;
	}
	
}
