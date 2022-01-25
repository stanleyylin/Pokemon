// Bag displays items. Based on bagState, player can use items.

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

import main.Battle;
import main.GamePanel;
import main.Main;

public class Bag extends JPanel implements MouseListener
{
	private Main main;
	private Player player; // The player
	
	private BufferedImage bg; // Background
	private BufferedImage[] bagSprites; // left, right, down, up, back buttons
	private BufferedImage selected; // Button png
	private BufferedImage deselected; // Button png
	
	private BufferedImage yellowBox; // The GUI png
	private BufferedImage bag; // The bag png
	private BufferedImage textBox; // The item description png
	private BufferedImage sortButton; // The sort button
	private BufferedImage useButton; // The use button
	
	private Item selectedItem; // The current selected item
	private Battle battle;
	

	private int bagState; // BAG STATES: 0 - out of battle, 1 - NPC battle, 2 - wild Pokemon battle
	private int itemType; // ITEM STATES: 0 - pokeball, 1 - medicine, 2 - key
	private boolean firstMed; // true - first 5 of medicine, false - last 3 of medicine
	private boolean visible; // If this bag panel is visible
	
	private Rectangle[] buttons; // Coordinates/dimensions of the buttons
	private ItemButton[] items; // The items displayed on the right
	
	// Fonts
	private Font font = new Font("Pokemon GB", Font.PLAIN, 22);
	private Font desFont = new Font("Pokemon GB", Font.PLAIN, 12);
	
	// Constructor
	public Bag(Main main, Player player, Battle battle)
	{
		this.main = main;
		this.player = player;
		this.battle = battle;
		visible = false;
		loadImages();
		loadButtons();
		setPreferredSize(new Dimension(1080, 720));
		setBackground(new Color(120, 168, 192));
		setLayout(null);
		addMouseListener(this);
		ItemButton.setImages(selected, deselected);
		items = new ItemButton[5];
		for(int i = 0; i < items.length; i++)
		{
			items[i] = new ItemButton(this);
			items[i].setBounds(530, 100+i*82+25*i, 443, 82);
			add(items[i]);
		}
	}
	
	// loadButtons creates all the rectangles for the buttons
	// void, no parameters
	public void loadButtons()
	{
		buttons = new Rectangle[8];
		buttons[0] = new Rectangle(135, 74, 24, 31); // left
		buttons[1] = new Rectangle(416, 74, 24, 31); // right
		buttons[2] = new Rectangle(53, 176, 171, 61); // Sort by Name
		buttons[3] = new Rectangle(263, 176, 171, 61); // Sort by Value
		buttons[4] = new Rectangle(27, 617, 80, 80); // Back
		
		// Conditional Buttons
		buttons[5] = new Rectangle(732, 62, 45, 40); // up
		buttons[6] = new Rectangle(732, 645, 45, 40); // down
		buttons[7] = new Rectangle(169, 377, 140, 55); // use item
	}
	
	// loadImages loads all the images
	// void, no parameters
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
			sortButton = loader.loadImage("res/bag/textbox.png");
			sortButton = sortButton.getSubimage(27, 72, 50, 17);
			sortButton = loader.resize(sortButton, 180, 65);
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
		
		try
		{
			useButton = loader.loadImage("res/bag/bagsprites.png");
			useButton = useButton.getSubimage(16, 127, 105, 42);
			useButton = loader.resize(useButton, 140, 55);
			
		}
		catch(IOException e) {}
	}

	// loadScreen loads the screen based on a given bagState
	// parameters: int state - the bagState (0 - not in battle, 1 - npc battle, 2 - wild
	// void
	public void loadScreen(int state)
	{
		itemType = 1;
		firstMed = true;
		bagState = state;
		selectedItem = null;
		visible = true;
		updateItems();
		repaint();
	}
	
	// updateItems updates all the items on the right
	// void, no parameters
	public void updateItems()
	{	
		// medicine items > 5 displayed
		if(itemType == 1)
		{
			// display the first five
			if(firstMed)
			{
				for(int i = 0; i < 5; i++)
				{
					if(selectedItem == null) // set first item as selected
						selectedItem = player.getBag(itemType).get(i);
					if(player.getBag(itemType).get(i).equals(selectedItem))
						items[i].updateButton(player.getBag(itemType).get(i), true);
					else
						items[i].updateButton(player.getBag(itemType).get(i), false);
					items[i].setVisible(true);
				}
			}
			// display the last 2
			else
			{
				for(int i = 0; i < 2; i++)
				{
					if(selectedItem == null)
						selectedItem = player.getBag(itemType).get(i+5);
					if(player.getBag(itemType).get(i+5).equals(selectedItem))
						items[i].updateButton(player.getBag(itemType).get(i+5), true);
					else
						items[i].updateButton(player.getBag(itemType).get(i+5), false);
					items[i].setVisible(true);
				}
				items[2].setVisible(false);
				items[3].setVisible(false);
				items[4].setVisible(false);
			}
		}
		// otherwise, display normally
		else
		{
			// display the item buttons
			for(int i = 0; i < player.getBag(itemType).size(); i++)
			{
				if(selectedItem == null)
					selectedItem = player.getBag(itemType).get(i);
				
				if(player.getBag(itemType).get(i).equals(selectedItem))
					items[i].updateButton(player.getBag(itemType).get(i), true);
				else
					items[i].updateButton(player.getBag(itemType).get(i), false);
				items[i].setVisible(true);
			}
			// hide the rest of the buttons
			for(int i = player.getBag(itemType).size(); i < 5; i++)
				items[i].setVisible(false);
		}
		this.repaint();
		this.revalidate();
	}

	// selected is called by itemButton objects when an item is clicked
	// parameters: MouseEvent e (to get the source of the mouse click)
	// void
	public void selected(MouseEvent e)
	{
		for(int i = 0; i < items.length; i++)
		{
			if(e.getSource().equals(items[i]))
			{
				selectedItem = items[i].getItem();
				items[i].setSelected(true);
				updateItems();
			}		
		}
	}
	
	// canUseItem checks if the selected item can be used
	// parameters: Item i - the item to check
	// void
	public boolean canUseItem(Item i)
	{
		if(i == null)
			return false;
		if(i.getQuantity() <= 0)
			return false;
		if(bagState == 0 && (itemType == 0 || itemType == 1))
			return false;
		if(bagState == 1 && (itemType == 0 || itemType == 2))
			return false;
		if(bagState == 2 && itemType == 2)
			return false;
		return true;
	}
	
	
	// For drawing: paintComponent, takes in Graphics g, returns void
	public void paintComponent(Graphics g) 
	{
		super.paintComponent(g);

		if(visible)
		{
			Graphics2D g2 = (Graphics2D) g;
			g2.drawImage(bg, 0, 0, null);
			g2.drawImage(yellowBox, 28, 30, null);
			g2.drawImage(bagSprites[0], 130, 64, null);
			g2.drawImage(bagSprites[1], 403, 64, null);
			g2.drawImage(sortButton, 50, 176, null);
			if(itemType != 2)
				g2.drawImage(sortButton, 260, 176, null);
			g2.drawImage(bag, 175, 455, null);
			g2.drawImage(bagSprites[4], 24, 613, null);

			g2.setFont(font);
			g2.setColor(new Color(40, 48, 48));
			g2.drawString("Sort by...", 122, 155);
			g2.drawString("Name", 90, 215);
			if(itemType != 2)
				g2.drawString("Value", 294, 215);

			// up/down buttons
			if(itemType == 1 && !firstMed)
				g2.drawImage(bagSprites[3], 732, 62, null);
			else if(itemType == 1 && firstMed)
				g2.drawImage(bagSprites[2], 732, 645, null);

			drawCategory(g2);
			if(selectedItem != null)
				drawSelected(g2);
		}
	}
	// draws the selected item info
	// Graphics2D g2, void
	public void drawSelected(Graphics2D g2)
	{
		g2.drawImage(textBox, 28, 265, null);
		g2.drawImage(selectedItem.getSprite(), 50, 290, null);
		String effect = selectedItem.getEffect();
		int x = 130;
		int y = 0;
		if(effect.length()>60)
			y = 290;
		else
			y = 300;
		String[] words = effect.split(" ");
		String line = "";
		for(String s : words)
		{
			line += s + " ";
			if(line.length() >= 18)
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

		if(canUseItem(selectedItem))
		{
			g2.drawImage(useButton, 169, 377, null);
			g2.setFont(font);
			g2.setColor(Color.WHITE);
			g2.drawString("Use", 208, 413);
		}
	}
	// drawCategory draws the appropriate category title
	// Graphics2D g2, void
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
	
	// MouseListener Methods
	// All take in MouseEvent e, returns void
	// PRANAV go to the buttons[7] (last if statement for using items,
	// go to buttons[4] if statement for going back
	
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
	
		if(buttons[0].contains(x, y)) // left
		{
			if(itemType == 0)
				itemType = 2;
			else if(itemType == 1)
				itemType = 0;
			else
				itemType = 1;
			firstMed = true;
			selectedItem = null;
			updateItems();
		}
		else if(buttons[1].contains(x, y))
		{
			if(itemType == 0)
				itemType = 1;
			else if(itemType == 1)
				itemType = 2;
			else
				itemType = 0;
			firstMed = true;
			selectedItem = null;
			updateItems();
		}
		else if(buttons[2].contains(x, y)) // sort by name
		{
			if(itemType == 0)
				player.sortByName(0);
			else if(itemType == 1)
				player.sortByName(1);
			else if(itemType == 2)
				player.sortByName(2);
			firstMed = true;
			selectedItem = null;
			updateItems();
		}
		else if(buttons[3].contains(x, y)) // sort by cost, n/a for keyItems
		{
			if(itemType == 0)
				player.sortByCost(0);
			else if(itemType == 1)
				player.sortByCost(1);
			firstMed = true;
			selectedItem = null;
			updateItems();
		}
		else if(buttons[4].contains(x, y))
		{
			if(bagState == 0) // not in battle, in the map, stanley will do dis
			{
				main.openGamePanel();
			}
			if(bagState == 1) // nPC battle
			{
				battle.backToMain();
			
			}
			if(bagState == 2) // wild pokemon battle
			{
				battle.backToMain();
			}
		}
		else if(itemType == 1 && !firstMed && buttons[5].contains(x, y))
		{
			firstMed = true;
			updateItems();
		}
		else if(itemType == 1 && firstMed && buttons[6].contains(x, y))
		{
			firstMed = false;
			updateItems();
		}
		else if(canUseItem(selectedItem) && buttons[7].contains(x, y))
		{
			if(bagState == 0) // not in battle, in the map, stanley will do dis
			{
				
			}
			if(bagState == 1) // nPC battle
			{
				battle.useItem(selectedItem.getName());
				main.openBattle();
			}
			if(bagState == 2) // wild pokemon battle
			{
				battle.useItem(selectedItem.getName());
				main.openBattle();
			}
			
			
		}
	
	}
	
	
	// Unused mouselistener methods.
	public void mousePressed(MouseEvent e) {}

	public void mouseReleased(MouseEvent e) {}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}

}
