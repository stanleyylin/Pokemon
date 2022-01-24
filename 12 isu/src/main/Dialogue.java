package main;

import java.awt.*;
import javax.swing.*;

import entity.Nurse;
import entity.Person;
import getimages.LoadImage;

import java.awt.image.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Dialogue extends JPanel
{
	private GamePanel p;
	private BufferedImage dialogueBox;
	private BufferedReader br;
	private String message = "";
	private boolean end;
	private Person npc;
	
	private Font font = new Font("Pokemon GB", Font.PLAIN, 22);
	
	public Dialogue(GamePanel p)
	{
		this.p = p;
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
					((Nurse) npc).heal(p.getPlayer());
					message = "";
				}
				revalidate();
				repaint();
				p.revalidate();
				p.repaint();
			}
			else // ended
			{
				p.getPlayer().setInteracting(false);
				p.hideDialogue();
			}
			p.getKeyHandler().setXPressed(false);
		} 
		catch (IOException e) {}
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
