package objects;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Drone extends LevelElement{
	private BufferedImage image;
	private int damage, health;
	
	public Drone(int x, int y) {
		//image = ImageIO.read(new File(""));
		//TODO add image
		
		this.setX(x);
		this.setY(y);
		this.setWidth(2);
		this.setHeight(2);
		
		this.setBoundBox(new Rectangle2D.Double(this.getX(), this.getY(), this.getWidth(), this.getHeight()));
		setDamage(1);
		setHealth(1);
		
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
