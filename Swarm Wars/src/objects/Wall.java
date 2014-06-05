package objects;

import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Defines the parameters for a wall object, these outline the edge of the level so objects cannot leave the screen
 * @author Ben Pinhorn
 * @version 1.0
 */
public class Wall extends LevelElement {

	public Wall(int x, int y) {
		this.setX(x);
		this.setY(y);
		this.setWidth(64);
		this.setHeight(64);
		setBoundBox(new Rectangle2D.Double(this.getX(), this.getY(), this.getWidth(), this.getHeight()));
		
		try {
			setImage(ImageIO.read(new File("resources/images/wall.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
