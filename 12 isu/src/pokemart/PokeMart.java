package pokemart;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JPanel;

import bag.Item;
import bag.ItemButton;
import bag.LoadItems;
import bag.SortItemByCost;
import entity.Player;
import getimages.LoadImage;
import main.Battle;

public class PokeMart extends JPanel implements MouseListener{
	
	private Player player; // The player
	
	private BufferedImage bg; // Background
	private BufferedImage[] storeSprites; // left, right, down, up, back buttons
	private BufferedImage selected; // Button png
	private BufferedImage deselected; // Button png
	
	private BufferedImage yellowBox; // The GUI png
	private BufferedImage textBox; // The item description png
	private BufferedImage sortButton; // The sort button
	private BufferedImage buyButton; // The buy button
	private BufferedImage coin;
	
	private Item selectedItem; // The current selected item

	private int itemType; // ITEM STATES: 0 - pokeball, 1 - medicine
	private boolean firstMed; // true - first 5 of medicine, false - last 3 of medicine
	private boolean visible; // If this store panel is visible
	private boolean tooPoor;
	private boolean purchased;
	private String message;
	
	private Rectangle[] buttons; // Coordinates/dimensions of the buttons
	private BuyItem[] items; // The items displayed on the right
	
	private Item[] pokeballs;
	private Item[] medicine;
	
	// Fonts
	private Font font = new Font("Pokemon GB", Font.PLAIN, 22);
	private Font desFont = new Font("Pokemon GB", Font.PLAIN, 11);
	
	// Constructor
	public PokeMart(Player player)
	{
		this.player = player;
		visible = false;
		tooPoor = false;
		purchased = false;
		loadImages();
		loadItems();
		loadButtons();
		setPreferredSize(new Dimension(1080, 720));
		setBackground(new Color(120, 168, 192));
		setLayout(null);
		addMouseListener(this);
		BuyItem.setImages(selected, deselected);
		items = new BuyItem[5];
		for(int i = 0; i < items.length; i++)
		{
			items[i] = new BuyItem(this);
			items[i].setBounds(530, 100+i*82+25*i, 443, 82);
			add(items[i]);
		}
		loadScreen();
	}
	
	public void loadItems()
	{
		LoadItems loadItems = new LoadItems();
		try {
			LoadItems.addAllItems();
		} catch (IOException e) {}
		// Pokeballs
		pokeballs = new Item[3];
		pokeballs[0] = loadItems.getItem("Poke Ball");
		pokeballs[1] = loadItems.getItem("Great Ball");
		pokeballs[2] = loadItems.getItem("Ultra Ball");
		
		// Medicine
		medicine = new Item[7];
		medicine[0] = loadItems.getItem("Potion");
		medicine[1] = loadItems.getItem("Super Potion");
		medicine[2] = loadItems.getItem("Hyper Potion");
		medicine[3] = loadItems.getItem("Full Restore");
		medicine[4] = loadItems.getItem("Full Heal");
		medicine[5] = loadItems.getItem("Ether");
		medicine[6] = loadItems.getItem("Elixir");
		
	}
	
	// loadButtons creates all the rectangles for the buttons
	// void, no parameters
	public void loadButtons()
	{
		buttons = new Rectangle[8];
		buttons[0] = new Rectangle(135, 74, 24, 31); // left
		buttons[1] = new Rectangle(416, 74, 24, 31); // right
		buttons[2] = new Rectangle(53, 176, 171, 61); // Sort by Name
		buttons[3] = new Rectangle(263, 176, 171, 61); // Sort by Cost
		buttons[4] = new Rectangle(27, 617, 80, 80); // Back
		
		// Conditional Buttons
		buttons[5] = new Rectangle(732, 62, 45, 40); // up
		buttons[6] = new Rectangle(732, 645, 45, 40); // down
		buttons[7] = new Rectangle(169, 377, 140, 55); // buy item
	}
	
	// loadImages loads all the images
	// void, no parameters
	public void loadImages()
	{
		LoadImage loader = new LoadImage();
		try
		{
			BufferedImage storeSpritesheet = loader.loadImage("res/bag/bagSprites.png");
			storeSprites = new BufferedImage[5];
			// left
			storeSprites[0] = storeSpritesheet.getSubimage(20, 16, 20, 20);
			storeSprites[0] = loader.resize(storeSprites[0], 44, 44);
			// right
			storeSprites[1] = storeSpritesheet.getSubimage(43, 16, 20, 20);
			storeSprites[1] = loader.resize(storeSprites[1], 44, 44);
			// up
			storeSprites[2] = storeSpritesheet.getSubimage(55, 49, 12, 12);
			storeSprites[2] = loader.resize(storeSprites[2], 45, 40);
			// down
			storeSprites[3] = storeSpritesheet.getSubimage(84, 49, 12, 12);
			storeSprites[3] = loader.resize(storeSprites[3], 45, 40);
			// back
			storeSprites[4] = storeSpritesheet.getSubimage(22, 43, 25, 23);
			storeSprites[4] = loader.resize(storeSprites[4], 92, 87);
			
			// selected
			deselected = storeSpritesheet.getSubimage(126, 14, 236, 43);
			deselected = loader.resize(deselected, 441, 82);
			selected = storeSpritesheet.getSubimage(130, 63, 236, 43);
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
			bg = loader.loadImage("res/store/bg.png");	
		}
		catch(IOException e) {}
		
		try
		{
			buyButton = loader.loadImage("res/bag/bagSprites.png");
			buyButton = buyButton.getSubimage(16, 127, 105, 42);
			buyButton = loader.resize(buyButton, 140, 55);
		}
		catch(IOException e) {}
		
		try
		{
			coin = loader.loadImage("res/store/coin.png");
			coin = loader.resize(coin, 51, 51);
		}
		catch(IOException e) {}
	}

	// loadScreen loads the screen based on a given bagState
	// parameters: int state - the bagState (0 - not in battle, 1 - npc battle, 2 - wild
	// void
	public void loadScreen()
	{
		itemType = 0;
		firstMed = true;
		selectedItem = null;
		visible = true;
		updateItems();
		repaint();
	}
	
	// updateItems updates all the items on the right
	// void, no parameters
	public void updateItems()
	{	
		// pokeballs
		if(itemType == 0)
		{
			for(int i = 0; i < pokeballs.length; i++)
			{
				if(selectedItem == null)
					selectedItem = pokeballs[i];
				if(selectedItem == pokeballs[i])
					items[i].updateButton(pokeballs[i], true);
				else
					items[i].updateButton(pokeballs[i], false);
				items[i].setVisible(true);
			}
			items[3].setVisible(false);
			items[4].setVisible(false);
		}
		else
		{
			if(firstMed)
			{
				for(int i = 0; i < 5; i++)
				{
					if(selectedItem == null)
						selectedItem = medicine[i];
					if(selectedItem == medicine[i])
						items[i].updateButton(medicine[i], true);
					else
						items[i].updateButton(medicine[i], false);
					items[i].setVisible(true);
				}
			}
			else
			{
				for(int i = 0; i < 2; i++)
				{
					if(selectedItem == null)
						selectedItem = medicine[i+5];
					if(selectedItem == medicine[i+5])
						items[i].updateButton(medicine[i+5], true);
					else
						items[i].updateButton(medicine[i+5], false);
					items[i].setVisible(true);
				}
				items[2].setVisible(false);
				items[3].setVisible(false);
				items[4].setVisible(false);
			}
			
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
	
	// For drawing: paintComponent, takes in Graphics g, returns void
	public void paintComponent(Graphics g) 
	{
		super.paintComponent(g);

		if(visible)
		{
			Graphics2D g2 = (Graphics2D) g;
			g2.drawImage(bg, 0, 0, null);
			g2.drawImage(yellowBox, 28, 30, null);
			g2.drawImage(storeSprites[0], 130, 64, null);
			g2.drawImage(storeSprites[1], 403, 64, null);
			g2.drawImage(sortButton, 50, 176, null);
			if(itemType != 2)
				g2.drawImage(sortButton, 260, 176, null);
			g2.drawImage(storeSprites[4], 24, 613, null);

			g2.drawImage(coin, 172, 612, null);
			g2.setFont(font);
			g2.setColor(Color.WHITE);
			g2.drawString("" + player.getPokeDollars(), 290, 646);
			g2.setColor(Color.BLACK);
			g2.drawString("$", 190, 644);
			
			g2.setColor(Color.WHITE);
			g2.drawString("Sort by...", 122, 155);
			g2.setColor(new Color(40, 48, 48));
			g2.drawString("Name", 90, 215);
			if(itemType != 2)
				g2.drawString("Cost", 296, 215);

			// up/down buttons
			if(itemType == 1 && !firstMed)
				g2.drawImage(storeSprites[3], 732, 62, null);
			else if(itemType == 1 && firstMed)
				g2.drawImage(storeSprites[2], 732, 645, null);
			
			if(tooPoor)
			{
				g2.setColor(Color.white);
				g2.setFont(font);
				g2.drawString("Insufficient", 70, 500);
				g2.drawString("funds!", 70, 540);
			}
			else if(purchased)
			{
				g2.setColor(Color.white);
				g2.setFont(font);
				g2.drawString(message + " was", 70, 500);
				g2.drawString("added!", 70, 540);
			}
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

		g2.drawImage(buyButton, 169, 377, null);
		g2.setFont(font);
		g2.setColor(Color.WHITE);
		g2.drawString("Buy!", 206, 413);
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
			if(itemType == 1)
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
			else
				itemType = 0;

			firstMed = true;
			selectedItem = null;
			updateItems();
		}
		else if(buttons[2].contains(x, y)) // sort by name
		{
			if(itemType == 0)
				Arrays.sort(pokeballs);
			else if(itemType == 1)
				Arrays.sort(medicine);
			firstMed = true;
			selectedItem = null;
			updateItems();
		}
		else if(buttons[3].contains(x, y)) // sort by cost, n/a for keyItems
		{
			if(itemType == 0)
				Arrays.sort(pokeballs, new SortItemByCost());
			else if(itemType == 1)
				Arrays.sort(medicine, new SortItemByCost());
			firstMed = true;
			selectedItem = null;
			updateItems();
		}
		else if(buttons[4].contains(x, y)) // back
		{
			
	
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
		else if(buttons[7].contains(x, y))
		{
			if(selectedItem != null && player.getPokeDollars() >= selectedItem.getCost())
			{
				player.setPokeDollars(player.getPokeDollars()-selectedItem.getCost());
				if(itemType == 0)
					player.addOnItem(selectedItem.getName(), 0, 1);
				else if(itemType == 1)
					player.addOnItem(selectedItem.getName(), 1, 1);
				purchased = true;
				tooPoor = false;
				message = selectedItem.getName();
				repaint();
			}
			else if(selectedItem != null)
			{
				tooPoor = true;
				purchased = false;
				repaint();
			}
		}
	
	}
	
//	public static void main(String[] args) 
//	{
//		JFrame frame = new JFrame ("Pokemon");
//		Player play = new Player(0, 0);
//		play.addOnItem("Potion", 1, 5);
//		play.addOnItem("Master Ball", 0, 2);
//		play.addKeyItem("Badge Case");
//		play.addKeyItem("Town Map");
//		play.setPokeDollars(10000);
//		PokeMart panel = new PokeMart(play);
//		frame.add(panel);
//		frame.setVisible(true);
//		frame.setResizable(false);
//		frame.pack();
//		frame.setLocationRelativeTo(null);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	}
	
	// Unused mouselistener methods.
	public void mousePressed(MouseEvent e) {}

	public void mouseReleased(MouseEvent e) {}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}
	
}