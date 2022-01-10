package map;

import entity.NPC;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Building {
	
	private int[] startingPoint; // contains: screenX, screenY, cameraX, cameraY
	private NPC[] npcs;
	private Rectangle[] collisions;
	private boolean edgeReachedX;
	private boolean edgeReachedY;
	private BufferedImage bg;
	
	public Building(int[] sP, NPC[] npcs, Rectangle[] collisions, boolean eRX, boolean eRY, BufferedImage bg)
	{
		startingPoint = sP;
		this.npcs = npcs;
		this.collisions = collisions;
		edgeReachedX = eRX;
		edgeReachedY = eRY;
		this.bg = bg;
	}
}
