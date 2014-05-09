package objects;

import java.awt.image.BufferedImage;

public abstract class LevelElement {
	private int x, y, width = 64, height = 64;
	private BufferedImage image;
	
	public LevelElement(int x, int y) {
		this.setX(x);
		this.setY(y);
		
		//TODO
		//image = ImageIO.read(new File());
	}

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

}
