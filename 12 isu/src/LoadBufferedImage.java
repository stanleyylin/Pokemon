import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class LoadBufferedImage {
	private BufferedImage image;
	
	public BufferedImage loadImage(String pathway) throws IOException
	{
		image = ImageIO.read(getClass().getResource(pathway));
		return image;
	}
}
