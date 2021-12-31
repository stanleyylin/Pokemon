package getimages;
import java.awt.image.*;

public class SpriteSheet {

	private BufferedImage image; // Entire sheet
	
	private BufferedImage[] sprites; // Individual Sprites
	
	// Number of columns/rows in the spritesheet
	private int cols; 
	private int rows;
	
	// Constructor
	public SpriteSheet(BufferedImage image, int cols, int rows)
	{
		this.image = image;
		sprites = new BufferedImage[cols*rows];
		this.cols = cols;
		this.rows = rows;
	}
	
	// Putting all the sprites in an array for convenience?
	public BufferedImage[] getSprites(int width, int height)
	{
		int counter = 0;
		for(int i = 0; i < rows; i++)
		{
			for(int k = 0; k < cols; k++)
			{
				sprites[counter] = getImage(k, i, width, height);
				counter++;
			}
		}
		return sprites;
	}
	
	// Getting an individual sprite, used by the above method
	public BufferedImage getImage(int col, int row, int width, int height)
	{
		BufferedImage sprite = image.getSubimage(col*32, row*32, width, height);
		return sprite;
	}
}
