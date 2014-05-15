package objects;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public abstract class LevelElement {
	private int x, y, width, height;
	private BufferedImage image;
	private Rectangle2D boundBox;
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}
	
	public Rectangle2D getBoundBox() {
		return boundBox;
	}
	
	public void setBoundBox(Rectangle2D.Double boundBox) {
		this.boundBox = boundBox;
	}
}
