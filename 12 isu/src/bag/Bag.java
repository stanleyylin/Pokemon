package bag;

import javax.swing.*;

import getimages.LoadImage;

import java.awt.Dimension;
import java.awt.image.*;
import java.awt.*;
import java.io.IOException;

import main.GamePanel;

public class Bag extends JPanel {
	
	BufferedImage[] bagSprites; // left, right, down, up, back
	BufferedImage selected;
	BufferedImage deselected;
	
	BufferedImage yellowBox;
	BufferedImage bag;
	BufferedImage textBox;
	BufferedImage button;
	
	public Bag()
	{
		loadImages();
		
		setPreferredSize(new Dimension(1080, 720));
		setBackground(new Color(120, 168, 192));
		setLayout(null);
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
			bag = loader.resize(bag, 258, 292);
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
	}

	
	public static void main(String[] args)
	{
		JFrame frame = new JFrame ("Pokemon");
		GamePanel panel = new GamePanel();
		
		frame.add(panel);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
