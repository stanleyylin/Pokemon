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

@SuppressWarnings("serial")
public class Dialogue extends JPanel
{
	private GamePanel gamePanel;
	private Player player;
	private BufferedImage dialogueBox;
	private BufferedReader br;
	private String message = "";
	private boolean end;
	private Person npc;
	
	private Font font = new Font("Pokemon GB", Font.PLAIN, 22);
	
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
	
	public void startDialogue(Person p)
	{
		npc = p;
		end = false;
		try 
		{
			br = new BufferedReader(new FileReader("res/dialogue/"+npc.getLines()));
		} 
		catch (IOException e) {
		}
		nextLine();
	}
	
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
					((Nurse) npc).heal(player);
					message = "";
				}
				else if(line.equals("*battle*"))
				{
					// call gamePanel
					message = "";
				}
				else if(line.equals("*store*"))
				
				revalidate();
				repaint();
				gamePanel.revalidate();
				gamePanel.repaint();
			}
			else // ended
			{
				player.setInteracting(false);
				gamePanel.hideDialogue();
			}
		} 
		catch (IOException e) {}
		gamePanel.getKeyHandler().setXPressed(false);
	}
	
	public void paintComponent(Graphics g) 
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(dialogueBox, 0, 0, null);
		g2.setFont(font);
		g2.setColor(Color.BLACK);
		g2.drawString(message, 82, 82);
	}

}
