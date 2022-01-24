package box;

import javax.swing.*;

import entity.NPC;
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
import pokesetup.BlankMon;
import pokesetup.Pokemon;

public class Box extends JPanel implements MouseListener{
	
	private Player main;
	
	private BufferedImage GUI;
	private BufferedImage button;
	
	private boolean swapping;
	
	private Rectangle[] buttons;
	
	private BoxPokemon[][] boxButtons;
	private BoxPokemon[] partyButtons;
	private int selected = 0;
	private int boxNum = 0;
	
	private Font font = new Font("Pokemon GB", Font.PLAIN, 22);
	private Font title = new Font("Pokemon GB", Font.PLAIN, 30);
	private Font desFont = new Font("Pokemon GB", Font.PLAIN, 14);
	
	public Box(Player main)
	{
		this.main = main;
		setPreferredSize(new Dimension(1080, 720));
		setLayout(null);
		addMouseListener(this);
		loadImages();
		
		BoxPokemon.setHex();
		boxButtons = new BoxPokemon[5][5];
		partyButtons = new BoxPokemon[6];
		
		for(int i = 0; i < 5; i++)
		{
			for(int k = 0; k < 5; k++)
			{
				boxButtons[i][k] = new BoxPokemon(this, i*5+k);
				boxButtons[i][k].setBounds(473+114*k+1*k, 113+114*i+1*i, 114, 114);
				add(boxButtons[i][k]);
			}
		}
		for(int i = 0; i < 6; i++)
		{
			partyButtons[i] = new BoxPokemon(this, i);
			if(i <= 2)
				partyButtons[i].setBounds(50+114*i+13*i, 432, 114, 114);
			else
				partyButtons[i].setBounds(473+114*(i-3)+13*(i-3), 560, 114, 114);
			
			add(partyButtons[i]);
		}
		updateButtons();
		
		buttons = new Rectangle[4];
		buttons[0] = new Rectangle(39, 36, 167, 66); // back
		buttons[1] = new Rectangle(517, 29, 46, 54); // left
		buttons[2] = new Rectangle(950, 31, 46, 54); // right
		buttons[3] = new Rectangle(148, 271, 170, 70); // swap!
		swapping = false;
	}

	public void loadImages()
	{
		LoadImage loader = new LoadImage();
		try
		{
			GUI = loader.loadImage("res/box/box.png");
		} catch(IOException e) {}
		try
		{
			button = loader.loadImage("res/box/button.png");
			button = loader.resize(button, 165, 65);
		} catch(IOException e) {}
	}
	
	public void updateButtons()
	{
		for(int i = 0; i < 5; i++)
		{
			for(int k = 0; k < 5; k++)
			{
				if(main.getBox().size()-boxNum*25 > i*5+k)
					boxButtons[i][k].updatePokemon(main.getBox().get(boxNum*25+i*5+k), false);
				else
					boxButtons[i][k].hidePokemon();
			}
		}
		boxButtons[selected/5][selected%5].setSelected(true);
		
		for(int i = 0; i < 6; i++)
		{
			if(main.getParty()[i] != null)
				partyButtons[i].updatePokemon(main.getParty()[i], true);
			else
				partyButtons[i].hidePokemon();
		}
	}
	public void refresh()
	{
		revalidate();
		repaint();
	}
	
	public void selected(int index)
	{
		boxButtons[selected/5][selected%5].setSelected(false);
		selected = index;
		refresh();
	}
	
	public void swap(int partyIndex)
	{
		main.swapPokemon(boxButtons[selected/5][selected%5].getPokemon(), partyIndex);
		updateButtons();
		refresh();
		swapping = false;
	}
	

	public void displaySelected(Graphics2D g2)
	{
		g2.drawImage(main.getBox().get(selected+boxNum*25).getFront(), 0, 105, null);
		g2.setFont(desFont);
		g2.drawString("Name: " + main.getBox().get(selected+boxNum*25).getNickName(), 190, 150);
		g2.drawString("HP: " + main.getBox().get(selected+boxNum*25).getCurHP() + "/" + main.getBox().get(selected+boxNum*25).getHPStat(), 190, 190);
		g2.drawString("Level: " + main.getBox().get(selected+boxNum*25).getLevel(), 190, 230);
	
		g2.drawImage(button, 150, 275, null);
		g2.setFont(font);
		g2.drawString("Swap!", 185, 315);
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(GUI, 0, 0, null);
		g2.drawImage(button, 37, 32, null);
		g2.setColor(Color.WHITE);
		if(!swapping)
			displaySelected(g2);
		else
		{
			g2.setFont(font);
			g2.drawString("Select pokemon", 72, 150);
			g2.drawString("from party to", 72, 190);
			g2.drawString("swap with!", 72, 230);
			
		}
		g2.setFont(font);
		g2.drawString("Back", 76, 74);
		g2.drawString("Party", 172, 395);
		g2.setFont(title);
		g2.drawString("Box " + (boxNum+1) + ":", 680, 62);
	}
	
	public static void main(String[] args)
	{
		JFrame frame = new JFrame ("Pokemon");
		try {
			BlankMon.getAllMoves();
			BlankMon.getAllMoveLists();
			BlankMon.getAllAbilities();
			Pokemon.addAllPokemon();
		} 
		catch (IOException e) {}

		Player pranav = new Player(0,0);
		pranav.addPokemonToParty(new Pokemon("Charizard", "BBQ Dragon", 55));
		pranav.addPokemonToParty(new Pokemon("Persian", "Kiwi", 32));
		pranav.addToBox(new Pokemon("Bulbasaur", "Wet", 55));
		pranav.addToBox(new Pokemon("Pikachu", "Zappy", 55));
		pranav.addToBox(new Pokemon("Jigglypuff", "Puffy", 55));
		pranav.addToBox(new Pokemon("Bulbasaur", "Wet", 55));
		pranav.addToBox(new Pokemon("Pikachu", "Zappy", 55));
		pranav.addToBox(new Pokemon("Jigglypuff", "Puffy", 55));
		pranav.addToBox(new Pokemon("Bulbasaur", "Wet", 55));
		pranav.addToBox(new Pokemon("Pikachu", "Zappy", 55));
		pranav.addToBox(new Pokemon("Jigglypuff", "Puffy", 55));
		pranav.addToBox(new Pokemon("Bulbasaur", "Wet", 55));
		pranav.addToBox(new Pokemon("Pikachu", "Zappy", 55));
		pranav.addToBox(new Pokemon("Jigglypuff", "Puffy", 55));
		pranav.addToBox(new Pokemon("Bulbasaur", "Wet", 55));
		pranav.addToBox(new Pokemon("Pikachu", "Zappy", 55));
		pranav.addToBox(new Pokemon("Jigglypuff", "Puffy", 55));
		pranav.addToBox(new Pokemon("Bulbasaur", "Wet", 55));
		pranav.addToBox(new Pokemon("Pikachu", "Zappy", 55));
		pranav.addToBox(new Pokemon("Jigglypuff", "Puffy", 55));
		pranav.addToBox(new Pokemon("Bulbasaur", "Wet", 55));
		pranav.addToBox(new Pokemon("Pikachu", "Zappy", 55));
		pranav.addToBox(new Pokemon("Jigglypuff", "Puffy", 55));
		pranav.addToBox(new Pokemon("Bulbasaur", "Wet", 55));
		pranav.addToBox(new Pokemon("Pikachu", "Zappy", 55));
		pranav.addToBox(new Pokemon("Jigglypuff", "Puffy", 55));
		pranav.addToBox(new Pokemon("Bulbasaur", "Wet", 55));
		pranav.addToBox(new Pokemon("Pikachu", "Zappy", 55));
		pranav.addToBox(new Pokemon("Jigglypuff", "Puffy", 55));
		pranav.addToBox(new Pokemon("Bulbasaur", "Wet", 55));
		pranav.addToBox(new Pokemon("Pikachu", "Zappy", 55));
		pranav.addToBox(new Pokemon("Jigglypuff", "Puffy", 55));
		pranav.addToBox(new Pokemon("Bulbasaur", "Wet", 55));
		pranav.addToBox(new Pokemon("Pikachu", "Zappy", 55));
		pranav.addToBox(new Pokemon("Jigglypuff", "Puffy", 55));
		Box box = new Box(pranav);

		frame.setContentPane(box);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		frame.setLocationRelativeTo(null);
	}

	public void mouseClicked(MouseEvent e) 
	{
		int x = e.getX();
		int y = e.getY();
		if(buttons[3].contains(x, y)) // swap
		{
			swapping = true;
			repaint();
		}
		else if(buttons[0].contains(x, y)) // back
		{
		}
		else if(buttons[1].contains(x, y)) // left
		{
			if(boxNum > 0)
			{
				boxNum--;
				selected(0);
				updateButtons();
				refresh();
			}
		}
		else if(buttons[2].contains(x, y)) // right
		{
			if(main.getBox().size() > 25*(boxNum+1))
			{			
				boxNum++;
				selected(0);
				updateButtons();
				refresh();
			}
		}
	}

	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	
	public boolean isSwapping()
	{
		return swapping;
	}
}
