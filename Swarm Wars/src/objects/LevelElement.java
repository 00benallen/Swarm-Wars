package objects;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public abstract class LevelElement {
	private int x, y, width, height;
	private BufferedImage image;
	private Rectangle2D boundBox;
	private boolean isBase = false;
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
		boundBox = new Rectangle2D.Double(this.x, this.y, this.width, this.height);
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
		boundBox = new Rectangle2D.Double(this.x, this.y, this.width, this.height);
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
		boundBox = new Rectangle2D.Double(this.x, this.y, this.width, this.height);
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
		boundBox = new Rectangle2D.Double(this.x, this.y, this.width, this.height);
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

	public boolean isBase() {
		return isBase;
	}

	public void setBase(boolean isBase) {
		this.isBase = isBase;
	}
}
