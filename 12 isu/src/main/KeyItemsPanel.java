package main;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JPanel;

import entity.Player;
import getimages.LoadImage;

public class KeyItemsPanel extends JPanel{

	
	Main main;
	Player player;
	LoadImage loader = new LoadImage();
	
	BufferedImage RegionMap;
	BufferedImage BadgeCase;
	
	public KeyItemsPanel(Main main, Player player) {
		
		this.main = main;
		this.player = player;
		
		try {
			RegionMap = loader.loadImage("regionMap.png");
			BadgeCase = loader.loadImage("badges0.png");
		} catch (IOException e) {
		}
		
		
		
	}
	
	
	
	public void updateBadgeCount() {
		try {
			BadgeCase = loader.loadImage("badges"  + player.getBadges()+ ".png" );
		} catch (IOException e) {
		}
	}
	
	
}
