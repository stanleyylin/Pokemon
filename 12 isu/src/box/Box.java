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
	
	private boolean selection;
	private boolean swapping;
	
	private Font font = new Font("Pokemon GB", Font.PLAIN, 22);
	private Font title = new Font("Pokemon GB", Font.PLAIN, 30);
	private Font desFont = new Font("Pokemon GB", Font.PLAIN, 12);
	
	public Box(Player main)
	{
		this.main = main;
		setPreferredSize(new Dimension(1080, 720));
		setLayout(null);
		addMouseListener(this);
		
		loadImages();
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
	
	public void refresh()
	{
		revalidate();
		repaint();
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(GUI, 0, 0, null);
		g2.drawImage(button, 37, 32, null);
		g2.setFont(font);
		g2.setColor(Color.WHITE);
		g2.drawString("Back", 76, 74);
		g2.drawString("Party", 172, 395);
		g2.setFont(title);
		g2.drawString("Box", 715, 60);
		
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
		Box box = new Box(pranav);

		frame.setContentPane(box);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		frame.setLocationRelativeTo(null);
	}

	public void mouseClicked(MouseEvent e) {}

	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
}
