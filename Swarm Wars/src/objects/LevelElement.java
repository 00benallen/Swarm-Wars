package objects;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * Abstract class for all objects that exist within a level, allows them to be stored together in lists and differentiated later
 * @author Ben Pinhorn
 * @version 1.0
 */
public abstract class LevelElement {
	private int x, y, width, height, moveX, moveY, centreX, centreY;
	private BufferedImage image;
	private Rectangle2D boundBox;
	private boolean isBase = false, isMoving = false;
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
		boundBox = new Rectangle2D.Double(this.x, this.y, this.width, this.height);
		this.centreX = this.getX() + this.getWidth()/2;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
		boundBox = new Rectangle2D.Double(this.x, this.y, this.width, this.height);
		this.centreY = this.getY() + this.getHeight()/2;
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

	public int getMoveX() {
		return moveX;
	}

	public void setMoveX(int moveX) {
		this.moveX = moveX;
	}

	public int getMoveY() {
		return moveY;
	}

	public void setMoveY(int moveY) {
		this.moveY = moveY;
	}

	public boolean isMoving() {
		return isMoving;
	}

	public void setMoving(boolean isMoving) {
		this.isMoving = isMoving;
	}
	
	public int getCentreX() {
		return centreX;
	}

	public void setCentreX(int centreX) {
		this.centreX = centreX;
	}

	public int getCentreY() {
		return centreY;
	}

	public void setCentreY(int centreY) {
		this.centreY = centreY;
	}
}
