package objects;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;


public class Base extends LevelElement{
	private BufferedImage image;
	private int x, y, width = 32, height = 32, health, spawnNum = 10;
	private Rectangle2D boundBox;
	private int swarmCount;
	private ArrayList<Drone> drones;
	
	public Base(int x, int y) {
		this.x = x;
		this.y = y;
		//image = ImageIO.read(new File(""));
		//TODO create image
		boundBox = new Rectangle2D.Double(this.x, this.y, this.width, this.height);
		setSwarmCount(0);
		drones = new ArrayList<Drone>();
		setHealth(50);
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
		Random rand = new Random();
		for(int i = 0; i < spawnNum; i++) {
			int x = rand.nextInt(this.width) + this.x;
			int y = rand.nextInt(this.height) + this.y;
			Drone newDrone = new Drone(x, y);
			drones.add(newDrone);
			setSwarmCount(getSwarmCount() + 1);
		}
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getSwarmCount() {
		return swarmCount;
	}

	public void setSwarmCount(int swarmCount) {
		this.swarmCount = swarmCount;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}
	
	

}
