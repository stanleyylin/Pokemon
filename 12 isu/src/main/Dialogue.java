package main;

import java.awt.*;
import javax.swing.*;

import entity.NPC;
import entity.Nurse;
import entity.Person;
import entity.Player;
import getimages.LoadImage;

import java.awt.image.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

// This class is to display dialogue
@SuppressWarnings("serial")
public class Dialogue extends JPanel
{
	private GamePanel gamePanel; // the gamePanel
	private Player player; // player
	private BufferedImage dialogueBox; // box for text
	private BufferedReader br; // Read in text file
	private String message = ""; // message to display
	private Person npc; // The person interacting with
	
	private Font font = new Font("Pokemon GB", Font.PLAIN, 20);
	
	// Constructor
	public Dialogue(GamePanel gamePanel, Player player)
	{
		this.gamePanel = gamePanel;
		this.player = player;
		setPreferredSize(new Dimension(1064, 172));
		setLayout(null);
		LoadImage loader = new LoadImage();
		try
		{
			dialogueBox = loader.loadImage("res/dialogue.png");
			dialogueBox = dialogueBox.getSubimage(0, 0, 1051, 175);
			dialogueBox = loader.resize(dialogueBox, 1064, 172);
		} catch(IOException e) {}
	}
	
	// Starting the dialogue with someone
	// takes in the person
	public void startDialogue(Person p)
	{
		npc = p;
		try 
		{
			br = new BufferedReader(new FileReader("res/dialogue/"+npc.getLines()));
		} 
		catch (IOException e) {
		}
		nextLine();
	}
	// Display the next lien
	public void nextLine()
	{
		String line;
		try 
		{	
			if((line = br.readLine()) != null)
			{
				message = line;
				if(line.equals("*heal*"))
				{
					player.healParty();
					message = "";
				}
				else if(line.equals("*battle*"))
				{
					gamePanel.startNPCBattle((NPC)npc);
					message = "See you later!";
				}
				else if(line.equals("*store*"))
				{
					message = "Come back soon!";
					gamePanel.openMart();
				}
				else if(line.equals("*trainer*"))
				{
					gamePanel.setTrainer();
					gamePanel.startNPCBattle((NPC)npc);
					message = "Good battle!";
				}
				revalidate();
				repaint();
				gamePanel.revalidate();
				gamePanel.repaint();
				if(line.equals("See you around!") || line.equals("Good battle!"))
				{
					player.healParty();
					player.setInteracting(false);
					gamePanel.hideDialogue();
				}
			}
			else // ended
			{
				player.healParty();
				player.setInteracting(false);
				gamePanel.hideDialogue();
			}
		} 
		catch (IOException e) {}
		gamePanel.getKeyHandler().setXPressed(false);
	}
	
	// Painting
	public void paintComponent(Graphics g) 
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(dialogueBox, 0, 0, null);
		g2.setFont(font);
		g2.setColor(Color.BLACK);
		g2.drawString(message, 82, 82);
	}

}
