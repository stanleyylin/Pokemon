package bag;

import javax.swing.*;

import entity.Player;
import getimages.LoadImage;

import java.awt.image.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import main.GamePanel;

public class Bag extends JPanel implements MouseListener{
	
	private Player main;
	
	private BufferedImage bg;
	private BufferedImage[] bagSprites; // left, right, down, up, back
	private BufferedImage selected;
	private BufferedImage deselected;
	private BufferedImage test;
	
	private BufferedImage yellowBox;
	private BufferedImage bag;
	private BufferedImage textBox;
	private BufferedImage button;
	
	private Item selectedItem;
	
	private int bagState; // 0 - out of battle, 1 - NPC battle, 3 - wild Pokemon battle
	private int itemType; // 0 - pokeball, 1 - medicine, 2 - key
	private boolean firstMed; // true - first 5 of medicine, false - last 3 of medicine
	
	private Rectangle[] buttons;
	private ItemButton[] items;
	private Font font = new Font("Pokemon GB", Font.PLAIN, 22);
	private Font desFont = new Font("Pokemon GB", Font.PLAIN, 15);
	
	public Bag(Player p)
	{
		this.main = p;
		loadImages();
		
		setPreferredSize(new Dimension(1080, 720));
		setBackground(new Color(120, 168, 192));
		setLayout(null);
		
		ItemButton.setImages(selected, deselected);
		items = new ItemButton[5];
		for(int i = 0; i < items.length; i++)
		{
			items[i] = new ItemButton();
			items[i].setBounds(530, 100+i*82+25*i, 443, 82);
			add(items[i]);
		}
		
		itemType = 0;
		updateItems();
	
	}
	
	public void loadButtons()
	{
		buttons = new Rectangle[8];
	}
	public void loadImages()
	{
		LoadImage loader = new LoadImage();
		try
		{
			BufferedImage bagSpriteSheet = loader.loadImage("res/bag/bagsprites.png");
			bagSprites = new BufferedImage[5];
			// left
			bagSprites[0] = bagSpriteSheet.getSubimage(20, 16, 20, 20);
			bagSprites[0] = loader.resize(bagSprites[0], 44, 44);
			// right
			bagSprites[1] = bagSpriteSheet.getSubimage(43, 16, 20, 20);
			bagSprites[1] = loader.resize(bagSprites[1], 44, 44);
			// up
			bagSprites[2] = bagSpriteSheet.getSubimage(55, 49, 12, 12);
			bagSprites[2] = loader.resize(bagSprites[2], 45, 40);
			// down
			bagSprites[3] = bagSpriteSheet.getSubimage(84, 49, 12, 12);
			bagSprites[3] = loader.resize(bagSprites[3], 45, 40);
			// back
			bagSprites[4] = bagSpriteSheet.getSubimage(22, 43, 25, 23);
			bagSprites[4] = loader.resize(bagSprites[4], 92, 87);
			
			
			// selected
			deselected = bagSpriteSheet.getSubimage(126, 14, 236, 43);
			deselected = loader.resize(deselected, 441, 82);
			selected = bagSpriteSheet.getSubimage(130, 63, 236, 43);
			selected = loader.resize(selected, 441, 82);

		}
		catch(IOException e) {}
		
		try
		{
			yellowBox = loader.loadImage("res/bag/box.png");
			yellowBox = yellowBox.getSubimage(12, 26, 234, 155);
			yellowBox = loader.resize(yellowBox, 1000, 670);
		}
		catch(IOException e) {}
		
		try
		{
			bag = loader.loadImage("res/bag/pokebag.png");
			bag = bag.getSubimage(161, 15, 170, 193);
			bag = loader.resize(bag, 224, 239);
		}
		catch(IOException e) {}
		
		try
		{
			textBox = loader.loadImage("res/bag/textbox.png");
			textBox = textBox.getSubimage(3, 0, 253, 46);
			textBox = loader.resize(textBox, 421, 103);
		}
		catch(IOException e) {}
		
		try
		{
			button = loader.loadImage("res/bag/textbox.png");
			button = button.getSubimage(27, 72, 50, 17);
			button = loader.resize(button, 180, 65);
		}
		catch(IOException e) {}
		
		try
		{
			bg = loader.loadImage("res/bag/bagBG.png");
			bg = bg.getSubimage(1, 1, 156, 115);
			bg = loader.resize(bg, 1080, 796);
			bg = bg.getSubimage(0, 76, 1080, 720);
			
		}
		catch(IOException e) {}
	}

	public void updateItems()
	{	
		for(int i = 0; i < Math.min(5, main.getBag(itemType).size()); i++)
		{
			if(selectedItem == null)
			{
				selectedItem = main.getBag(itemType).get(i);
			}
			
			if(main.getBag(itemType).get(i).equals(selectedItem))
			{
				items[i].updateButton(main.getBag(itemType).get(i), true);
			}
			else
				items[i].updateButton(main.getBag(itemType).get(i), false);
			items[i].setVisible(true);
		}
		
		if(main.getBag(itemType).size() < 5)
		{
			for(int i = main.getBag(itemType).size(); i < 5; i++)
			{
				items[i].setVisible(false);
			}
		}
		this.repaint();
		this.revalidate();
	}
	
	public void updateSelected(Graphics2D g2)
	{
		g2.drawImage(textBox, 28, 265, null);
		g2.drawImage(selectedItem.getSprite(), 50, 290, null);
		String effect = selectedItem.getEffect();
		int x = 130;
		int y = 300;
		String[] words = effect.split(" ");
		String line = "";
		for(String s : words)
		{
			line += s + " ";
			if(line.length() >= 20)
			{
				g2.setFont(desFont);
				g2.drawString(line, x, y);
				y += 20;
				line = "";
			}
		}
		if(line != "")
		{
			g2.setFont(desFont);
			g2.drawString(line, x, y);
		}
	}
	
	public void drawCategory(Graphics2D g2)
	{
		g2.setFont(font);
		g2.setColor(new Color(40, 48, 48));
		if(itemType == 0)
			g2.drawString("Pokeballs", 190, 99);
		else if(itemType == 1)
			g2.drawString("Medicine", 200, 99);
		else
			g2.drawString("Key Items", 188, 99);
	}
	public void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(bg, 0, 0, null);
		g2.drawImage(yellowBox, 28, 30, null);
		g2.drawImage(bagSprites[0], 130, 64, null);
		g2.drawImage(bagSprites[1], 403, 64, null);
		g2.drawImage(button, 50, 175, null);
		g2.drawImage(button, 260, 175, null);
		g2.drawImage(bag, 175, 455, null);
		g2.drawImage(bagSprites[4], 24, 613, null);
		
		g2.setFont(font);
		g2.setColor(new Color(40, 48, 48));
		g2.drawString("Sort by...", 122, 155);
		g2.drawString("Name", 90, 215);
		g2.drawString("Value", 294, 215);
		drawCategory(g2);
		updateSelected(g2);
	}
	
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		
	}

	public void mousePressed(MouseEvent e) {}

	public void mouseReleased(MouseEvent e) {}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}
	
	public static void main(String[] args)
	{
		JFrame frame = new JFrame ("Pokemon");
		Player play = new Player(0, 0);
		
		Bag panel = new Bag(play);
		
		frame.add(panel);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
