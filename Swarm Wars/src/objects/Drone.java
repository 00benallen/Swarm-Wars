package objects;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Drone extends LevelElement{
	private BufferedImage image;
	private int damage, health;
	private String name;
	private int colorID;
	
	public Drone(int x, int y, String teamName) {
		//image = ImageIO.read(new File(""));
		//TODO add image
		
		this.setX(x);
		this.setY(y);
		this.setWidth(4);
		this.setHeight(4);
		this.setTeamName(teamName);
		this.setColorID((Integer.parseInt(name.substring(name.length() - 1, name.length()))));
		if(name.substring(0, 4).equals("Comp")) {
			setColorID(getColorID() + 6);
		}
		
		this.setBoundBox(new Rectangle2D.Double(this.getX(), this.getY(), this.getWidth(), this.getHeight()));
		setDamage(1);
		setHealth(2);
		
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

	public int getColorID() {
		return colorID;
	}

	public void setColorID(int colorID) {
		this.colorID = colorID;
	}
}
