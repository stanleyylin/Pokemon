package main;

import java.awt.*;
import java.awt.image.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import entity.NPC;
import entity.Player;
import getimages.LoadImage;
import pokesetup.BlankMon;
import pokesetup.Pokemon;
import pokesetup.Type;

public class MoveSelect extends JPanel {
	// Visibility
	private boolean displayed = false;
	
	// Types Icon
	private static Map<String, BufferedImage> typeIcons = new HashMap<>();
	
	// Background of the Button
	private BufferedImage bg;
	private static BufferedImage unselected;
	private static BufferedImage selected;
	private BufferedImage typeIcon;
	private JLabel button;
	// Info
	private String name;
	private Type type;
	private int curPP;
	private int pp;
	
	// Dimensions (same for all move buttons)
	public final static int width = 268;
	public final static int height = 102;
	// Font
	private Font font1 = new Font("Pokemon GB", Font.BOLD, 13);
	
	public MoveSelect(Battle battle)
	{
		setPreferredSize(new Dimension(width, height));
		setBackground(new Color(0f, 0f, 0f, 0f));
		setLayout(null);
		
		button = new JLabel();
		button.setPreferredSize(new Dimension(268, 102));
		button.addMouseListener(new MouseListener() {

            public void mouseEntered(MouseEvent e) 
            {
            	if(displayed)
            	{
	            	bg = selected;
					repaint();
            	}
            }

			public void mouseClicked(MouseEvent e) {
				battle.buttonClick(e);
				bg = unselected;
			}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}

			public void mouseExited(MouseEvent e) 
			{
				if(displayed)
            	{
					bg = unselected;
					repaint();
            	}
			}
        });
		button.setBounds(5, 5, 263, 98);
		add(button);
		bg = unselected;
		
		repaint();
	}
	
	public static void setImages()
	{
		LoadImage loader = new LoadImage();
		try
		{
			unselected = loader.loadImage("res/battle/deselectedmove.png");
			unselected = loader.resize(unselected, width, height);
			selected = loader.loadImage("res/battle/selectedmove.png");
			selected = loader.resize(selected, width, height);
		}
		catch(IOException e) {}
		BufferedImage getIcon = null;
		BufferedImage tempIcons[] = new BufferedImage[18];
		try
		{
			getIcon = loader.loadImage("res/battle/types.png");
		}
		catch(IOException e) {}
		for(int i = 0; i < 6; i++)
		{
			for(int k = 0; k < 3; k++)
			{
				tempIcons[i*3+k] = getIcon.getSubimage(48 + 267*k + 60*k, 34 + 101*i + 66*i, 267, 101);
				tempIcons[i*3+k] = loader.resize(tempIcons[i*3+k], 63, 24);
			}
		}
		
		try
		{
			BufferedReader br = new BufferedReader(new FileReader("res/battle/types.txt"));
			for(int i = 0; i < 18; i++)
			{
				String addType = br.readLine();
				typeIcons.put(addType, tempIcons[i]);
			}
			br.close();
		}
		catch(IOException e) {}	
	
	}
	
	
	public void updatePokemon(String name, Type type, int curPP, int pp)
	{
		this.name = name;
		this.type = type;
		typeIcon = typeIcons.get(type.getType1());
		this.curPP = curPP;
		this.pp = pp;
	}
	
	public void reset()
	{
		bg = unselected;
	}
	
	public void setDisplayed(boolean set)
	{
		displayed = set;
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		if(!displayed)
			super.paintComponent(g);
		else
		{
			g2.drawImage(bg, 0, 0, this);
			g2.setFont(font1.deriveFont(Font.BOLD, 12f));
			// g2.setFont(font1);
			g2.setColor(Color.white);
			g2.drawString(name,  134 - ((int)Math.floor(name.length()/2))*13, 30);
			
			g2.setColor(Color.white);
			g2.drawImage(typeIcon, 14, 66, this);
			g2.drawString("PP: " + curPP + "/" + pp, 120, 70);
			
		}
	}


}
