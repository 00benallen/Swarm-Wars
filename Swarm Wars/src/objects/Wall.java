package objects;

import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Wall extends LevelElement {
	private int x, y;
	private Rectangle2D boundBox;

	public Wall(int x, int y) {
		this.x = x;
		this.y = y;
		setBoundBox(new Rectangle2D.Double(this.getX(), this.getY(), this.getWidth(), this.getHeight()));
		
		try {
			setImage(ImageIO.read(new File("resources/images/wall.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Rectangle2D getBoundBox() {
		return boundBox;
	}

	public void setBoundBox(Rectangle2D boundBox) {
		this.boundBox = boundBox;
	}
}
