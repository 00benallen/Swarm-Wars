package objects;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;


public class Base extends LevelElement{
	private int health, spawnNum = 10;
	private int swarmCount;
	private ArrayList<Drone> drones;
	
	public Base(int x, int y) {
		this.setX(x);
		this.setY(y);
		this.setWidth(32);
		this.setHeight(32);
		this.setBase(true);
		try {
			this.setImage(ImageIO.read(new File("resources/images/base.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		setBoundBox(new Rectangle2D.Double(this.getX(), this.getY(), this.getWidth(), this.getHeight()));
		setSwarmCount(0);
		drones = new ArrayList<Drone>();
		setHealth(50);
	}
	
	public Drone getDrone(int index) {
		return drones.get(index);
	}
	
	public void spawnDrones() {
		Random rand = new Random();
		for(int i = 0; i < spawnNum; i++) {
			int x = rand.nextInt(this.getWidth()) + this.getX();
			int y = rand.nextInt(this.getHeight()) + this.getY();
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
}
