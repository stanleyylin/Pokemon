package main;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;

import entity.Player;
import getimages.LoadImage;

public class KeyItemsPanel extends JPanel implements MouseListener{
	
	//panel for key items(badge case & town map)

	private Main main;
	private Player player;
	private LoadImage loader = new LoadImage();
	private Font font = new Font("Pokemon GB", Font.PLAIN, 30);
	private BufferedImage RegionMap;
	private BufferedImage BadgeCase;
	boolean curImg;
	
	//cosntructor
	public KeyItemsPanel(Main main, Player player) {
		
		this.main = main;
		this.player = player;
		
		setPreferredSize(new Dimension (1080,720));
		
		try {
			RegionMap = loader.loadImage("regionMap.png");
			BadgeCase = loader.loadImage("badges0.png");
		} catch (IOException e) {
		}
		curImg = true;
		
		addMouseListener(this);
		//true means map
		//false means badge case
	}
	
	
	//updates the badge count and image accordingly
	public void updateBadgeCount() {
		try {
			BadgeCase = loader.loadImage("badges"  + player.getBadges()+ ".png" );
		} catch (IOException e) {
		}
	}
	
	//sets the current image as either map or case
	public void setCurImg(boolean b) {
		curImg = b;
	}
	
	//paint component
	public void paintComponent (Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		if (curImg)
		{
			g2.drawImage(RegionMap, 0,0, null);
			g2.setFont(font);
			g2.drawString("Click anywhere to go back!", 200, 600);
		}
		else {
			updateBadgeCount();
			g2.drawImage(BadgeCase, 0,0, null);
		}
	}


	public void mouseClicked(MouseEvent e) {
		main.openGamePanel();
	}

	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	
	
}

//add method in itemButton to do main.openMap and main.OpenBadgeCase
//method in main that does it and sets curImg to whatever and then sets content pane to this
