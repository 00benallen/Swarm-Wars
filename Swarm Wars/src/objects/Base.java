package objects;
import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;


public class Base extends LevelElement{
	private int health, spawnNum = 10;
	private int swarmCount;
	private ArrayList<Drone> drones;
	private String name;
	private Color color;
	
	public Base(int x, int y, String teamName, Color color) {
		this.setX(x);
		this.setY(y);
		this.setWidth(32);
		this.setHeight(32);
		this.setBase(true);
		this.setTeamName(teamName);
		this.setCentreX(this.getX() + this.getWidth()/2);
		this.setCentreY(this.getY() + this.getHeight()/2);
		try {
			this.setImage(ImageIO.read(new File("resources/images/base.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		setBoundBox(new Rectangle2D.Double(this.getX(), this.getY(), this.getWidth(), this.getHeight()));
		setSwarmCount(0);
		drones = new ArrayList<Drone>();
		setHealth(500);
		this.color = color;
	}

	public Drone getDrone(int index) {
		
		return drones.get(index);
	}
	
	public void killDrone(int index) {
		drones.remove(index);
		swarmCount--;
	}
	
	public void spawnDrones() {
		Random rand = new Random();
		for(int i = 0; i < spawnNum; i++) {
			int x = rand.nextInt(this.getWidth()) + this.getX();
			int y = rand.nextInt(this.getHeight()) + this.getY();
			Drone newDrone = new Drone(x, y, name);
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

	public String getTeamName() {
		return name;
	}

	public void setTeamName(String teamName) {
		this.name = teamName;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}
