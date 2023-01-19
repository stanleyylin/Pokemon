package pokemart;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import bag.Bag;
import bag.Item;

public class BuyItem extends JPanel implements MouseListener {
	//class that allows you to buy items
	
	private PokeMart pokeMart;
	private Item item;
	
	private static BufferedImage selected; //if an item is selected
	private static BufferedImage deselected; //if an item is deselected
	private BufferedImage icon; //an item icon
	
	private boolean select;
	private boolean visible;
	
	private Font font = new Font("Pokemon GB", Font.PLAIN, 18);
	
	//constructor
	public BuyItem(PokeMart pokeMart)
	{
		this.pokeMart = pokeMart;
		setPreferredSize(new Dimension(441, 82));
		setBackground(new Color(0f, 0f, 0f, 0f));
		addMouseListener(this);
		setLayout(null);
	}
	
	//sets images to selected or deselected
	public static void setImages(BufferedImage select, BufferedImage deselect)
	{
		selected = select;
		deselected = deselect;
	}

	//mouse listener methods
	public void mouseClicked(MouseEvent e) 
	{
		if(visible)
			pokeMart.selected(e);
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
	
	//paint component
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
			
			g2.drawString(item.getName(), 90, 53);
			g2.drawString("$" + item.getCost(), 320, 50);
			
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
