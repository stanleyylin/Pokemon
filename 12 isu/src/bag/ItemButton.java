package bag;

import javax.swing.*;

import getimages.LoadImage;

import java.awt.image.*;
import java.awt.*;
import java.io.IOException;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class ItemButton extends JPanel implements MouseListener {
	private Bag bag;
	private Item item;
	
	private static BufferedImage selected;
	private static BufferedImage deselected;
	private BufferedImage icon;
	
	private boolean select;
	private boolean visible;
	
	private Font font = new Font("Pokemon GB", Font.PLAIN, 22);
	
	public ItemButton(Bag bag)
	{
		this.bag = bag;
		setPreferredSize(new Dimension(441, 82));
		setBackground(new Color(0f, 0f, 0f, 0f));
		addMouseListener(this);
		setLayout(null);
	}
	
	public static void setImages(BufferedImage select, BufferedImage deselect)
	{
		selected = select;
		deselected = deselect;
	}

	public void mouseClicked(MouseEvent e) 
	{
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
