package objects;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Drone extends LevelElement{
	private BufferedImage image;
	private int damage, health;
	private String name;
	
	public Drone(int x, int y, String teamName) {
		this.setX(x);
		this.setY(y);
		this.setWidth(4);
		this.setHeight(4);
		this.setTeamName(teamName);
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

	public String getTeamName() {
		return name;
	}

	public void setTeamName(String teamName) {
		this.name = teamName;
	}
}
