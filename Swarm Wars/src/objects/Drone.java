package objects;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Drone {
	BufferedImage image;
	int x, y, width = 2, height = 2, damage, health;
	Rectangle2D boundBox;
	
	public Drone(int x, int y) {
		//image = ImageIO.read(new File(""));
		//TODO add image
		
		this.x = x;
		this.y = y;
		
		boundBox = new Rectangle2D.Double(this.x, this.y, this.width, this.height);
		damage = 1;
		health = 1;
		
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
	
	
	

}
