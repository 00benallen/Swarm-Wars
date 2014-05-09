package objects;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class Base {
	BufferedImage image;
	int x, y, width = 32, height = 32, health;
	Rectangle2D boundBox;
	int swarmCount;
	ArrayList<Drone> drones;
	
	public Base(int x, int y) {
		//image = ImageIO.read(new File(""));
		//TODO create image
		
		this.x = x;
		this.y = y;
		boundBox = new Rectangle2D.Double(this.x, this.y, this.width, this.height);
		swarmCount = 0;
		drones = new ArrayList<Drone>();
		health = 50;
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
	
	public Drone getDrone(int index) {
		return drones.get(index);
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
	
	public void spawnDrones() {
		
	}
	
	

}
