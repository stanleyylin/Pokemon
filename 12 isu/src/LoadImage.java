import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

public class LoadImage {
	private BufferedImage image;
	
	public BufferedImage loadImage(String pathway) throws IOException
	{
		image = ImageIO.read(new File(pathway));
		return image;
	}
}
