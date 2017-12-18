import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

//This class creates a JPanel with the image painted onto it with given dimensions
class PicPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage image,temp;

	public BufferedImage getImage()
	{
		return image;
	}

	public PicPanel(String fname,int a, int b)
	{

		//reads the image
		try {
			//Takes in the image for the board
			temp = ImageIO.read(new File(fname));
			//Resizes the image then paints it onto the pic panel
			Image dimg = temp.getScaledInstance(a,b, Image.SCALE_SMOOTH);
			image = new BufferedImage(a,b,BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = image.createGraphics();
			g.drawImage(dimg,0,0, null);
			g.dispose();


		} catch (IOException ioe) {
			//Prints warning if the image cannot be found
			System.out.println("Could not read in the pic");
			//System.exit(0);
		}

	}

	//this will draw the image
	public void paintComponent(Graphics g){
		super.paintComponent(g);

		g.drawImage(image,0,0,this);
	}
}