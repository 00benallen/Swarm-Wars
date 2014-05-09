package objects;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Drone {
	private BufferedImage image;
	private int x, y, width = 2, height = 2, damage, health;
	private Rectangle2D boundBox;
	
	public Drone(int x, int y) {
		//image = ImageIO.read(new File(""));
		//TODO add image
		
		this.x = x;
		this.y = y;
		
		boundBox = new Rectangle2D.Double(this.x, this.y, this.width, this.height);
		setDamage(1);
		setHealth(1);
		
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public Rectangle2D getBoundBox() {
		return boundBox;
	}
	
	public void left() {
		x--;
	}
	
	public void right() {
		x++;
	}
	
	public void up() {
		y--;
	}
	
	public void down() {
		y++ ;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}
	
	
	

}
