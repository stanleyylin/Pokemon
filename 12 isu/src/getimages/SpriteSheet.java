package getimages;
import java.awt.image.*;
import java.io.IOException;

public class SpriteSheet {

	private BufferedImage image; // Entire sheet
	private BufferedImage[] sprites; // Individual Sprites

	// Constructor
	public SpriteSheet(String file)
	{
		if(file.equals("char1.png"))
		{
			sprites = new BufferedImage[12];
			LoadImage loader = new LoadImage();
			try 
			{
				image = loader.loadImage("res/"+file);
				// front, rest
				sprites[0] = image.getSubimage(4, 5, 23, 27);
				sprites[0] = loader.resize(sprites[0], 57, 67);
				// front, move1
				sprites[1] = image.getSubimage(36, 5, 23, 27);
				sprites[1] = loader.resize(sprites[1], 57, 67);
				// front, move2
				sprites[2] = image.getSubimage(68, 5, 23, 27);
				sprites[2] = loader.resize(sprites[2], 57, 67);
				
				// back, rest
				sprites[3] = image.getSubimage(4, 38, 23, 27);
				sprites[3] = loader.resize(sprites[3], 57, 67);
				// back, move1
				sprites[4] = image.getSubimage(35, 38, 23, 27);
				sprites[4] = loader.resize(sprites[4], 57, 67);
				// back, move2
				sprites[5] = image.getSubimage(67, 38, 23, 27);
				sprites[5] = loader.resize(sprites[5], 57, 67);
				
				
				// right, rest
				sprites[6] = image.getSubimage(3, 69, 23, 27);
				sprites[6] = loader.resize(sprites[6], 57, 67);
				// right, move1
				sprites[7] = image.getSubimage(35, 69, 23, 27);
				sprites[7] = loader.resize(sprites[7], 57, 67);
				// right, move2
				sprites[8] = image.getSubimage(67, 69, 23, 27);
				sprites[8] = loader.resize(sprites[8], 57, 67);
				
				// left, rest
				sprites[9] = image.getSubimage(4, 101, 23, 27);
				sprites[9] = loader.resize(sprites[9], 57, 67);
				// left, move1
				sprites[10] = image.getSubimage(36, 101, 23, 27);
				sprites[10] = loader.resize(sprites[10], 57, 67);
				// left, move2
				sprites[11] = image.getSubimage(68, 101, 23, 27);
				sprites[11] = loader.resize(sprites[11], 57, 67);
			} catch (IOException e) {}
		}
	}
	
	public BufferedImage[] getSprites()
	{
		return this.sprites;
	}
	
	
}
