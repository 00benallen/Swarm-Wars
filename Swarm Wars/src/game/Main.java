package game;

import graphics.GraphicsMain;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import objects.Base;
import objects.Drone;
import objects.Level;
import objects.LevelElement;
import objects.Player;


/**
 * Main class for Swarm Wars, acts as the backend, runs most of its methods once per tick, and also contains some methods run by the listener
 * @author Ben Pinhorn
 * @version 1.0
 */
public class Main {
	private static Level level;
	private static Player player1;
	private static ArrayList<Computer> computers = new ArrayList<Computer>();
	private static ArrayList<Base> bases;
	private static long lastDroneSpawn = System.nanoTime();
	private static ArrayList<LevelElement> selectedElements;
	private static int droneVelocity = 1;
	
	public static void init() {
		level = LevelLoader.loadLevel("resources/levels/level1");
		player1 = new Player("Player1");
		bases = level.getBases();
		selectedElements = new ArrayList<LevelElement>();
		
		for(int i = 0; i < bases.size(); i++) { //creates a computer list from the bases list
			if(bases.get(i).getTeamName().substring(0, 4).equals("Comp")) {
				computers.add(new Computer(bases.get(i).getTeamName(), bases.get(i)));
			}
		}
	}
	
	public static void update() {
		spawnDrones();
		checkArrived();
		moveAllTo();
		checkDamage();
		checkDeath();
		runAIs();
		checkWinOrLose();
	}
	
	private static void spawnDrones() { //spawns drones each second
		long curTime = System.nanoTime();
		
		if(curTime - lastDroneSpawn > 1000000000L) {
			for(int i = 0; i < bases.size(); i++) {
				bases.get(i).spawnDrones();
			}
			lastDroneSpawn = System.nanoTime();
		}
	}
	
	public static void selectItems() { //selects elements inside of the listener's select box
		if(GraphicsMain.listener.itemsSelected()) {
			Rectangle2D selectBox = GraphicsMain.listener.getSelectionBox();
			for(int j = 0; j < bases.size(); j++) {
				for(int k = 0; k < bases.get(j).getSwarmCount(); k++) {
 					if(bases.get(j).getTeamName().equals(player1.getName())) {
						if(selectBox.contains(bases.get(j).getBoundBox()) && !selectedElements.contains(bases.get(j))) {
							selectedElements.add(bases.get(j));
						}
						if(selectBox.contains(bases.get(j).getDrone(k).getBoundBox()) && !selectedElements.contains(bases.get(j).getDrone(k))) {
							selectedElements.add(bases.get(j).getDrone(k));
						}
					}
				}
			}
		}
	}
	
	public static void checkArrived() { //checks if an element has reached its destination or is colliding with something
		for(int i = 0; i < bases.size(); i++) {
			Base base = bases.get(i);
			if(base.isMoving()) {
				if(base.getCentreX() == base.getMoveX() && base.getCentreX() == base.getMoveX()) {
					if(base.getCentreY() == base.getMoveY() && base.getCentreY() == base.getMoveY()) {
						base.setMoving(false);
					}
				}
			}
		}
		
		for(int i = 0; i < bases.size(); i++) {
			Base element = bases.get(i);
			boolean collided = false;
			for(int j = 0; j < level.getLevelArray().size(); j++) {
				if(level.getLevelArray().get(j) != null && !(level.getLevelArray().get(j) instanceof Base)) {
					if(!element.isColliding()) {
						Rectangle2D boundBox = element.getBoundBox();
						Rectangle2D futureIntersection = new Rectangle2D.Double(boundBox.getX() - 1, boundBox.getY() - 1, boundBox.getWidth() + 1, element.getHeight() + 1);
						if(futureIntersection.intersects(level.getLevelArray().get(j).getBoundBox())) {
							element.setMoving(false);
							element.setColliding(true);
							collided = true;
						}
						for(int k = 0; k < element.getSwarmCount(); k++) {
							if(element.getDrone(k).getBoundBox().intersects(level.getLevelArray().get(j).getBoundBox())) {
								element.getDrone(k).setMoving(false);
							}
						}
					}
				}	
			}
			if(!collided) {
				element.setColliding(false);
			}
		}
	}
	
	private static void moveAllTo() { //performs calculations to move all elements one tick forward in their "flight path"
		for(int i = 0; i < bases.size(); i++) {
			LevelElement element = bases.get(i);
			if(element.isMoving()) {
				Vector2D moveVector = new Vector2D(element.getMoveX() - element.getX(), element.getMoveY() -  element.getY());
				Vector2D.normalizeVector(moveVector);
				Vector2D.multiplyByScalar(moveVector, droneVelocity);
				element.setX(element.getX() + Math.round(moveVector.getX()));
				element.setY(element.getY() + Math.round(moveVector.getY()));
			}
			
			Base baseElement = (Base) element;
			for(int j = 0; j < baseElement.getSwarmCount(); j++) {
				element = baseElement.getDrone(j);
				if(element.isMoving()) {
					Vector2D moveVector = new Vector2D(element.getMoveX() - element.getX(), element.getMoveY() -  element.getY());
					Vector2D.normalizeVector(moveVector);
					Vector2D.multiplyByScalar(moveVector, droneVelocity);
					element.setX(element.getX() + Math.round(moveVector.getX()));
					element.setY(element.getY() + Math.round(moveVector.getY()));
				}
			}
		}
	}
	
	public static void setMovePoints(int x, int y) { //run by listener, gives selected elements their move points and tells them to move
		for(int i = 0; i < selectedElements.size(); i++) {
			boolean dronesMove, baseMove;
			if(GraphicsMain.listener.rightClick()) {
				dronesMove = false;
				baseMove = true;
			}
			else {
				dronesMove = true;
				baseMove = false;
			}
			if((selectedElements.get(i) instanceof Base && baseMove) || (selectedElements.get(i) instanceof Drone && dronesMove)) {
				selectedElements.get(i).setMoveX(x);
				selectedElements.get(i).setMoveY(y);
				selectedElements.get(i).setMoving(true);
			}
		}
	}
	
	public static void checkDamage() { //checks if things are close enough for damage
		for(int i = 0; i < bases.size(); i++) {
			Base checkBase = bases.get(i);
			for(int j = 0; j < bases.size() - 1; j++) {
				for(int k = 0; k < bases.get(j).getSwarmCount(); k++) {
					Drone checkDrone = bases.get(j).getDrone(k);
					if(checkDrone.getX() > checkBase.getCentreX() - 25 && checkDrone.getX() < checkBase.getCentreX() + 25) {
						if(checkDrone.getY() > checkBase.getCentreY() - 25 && checkDrone.getY() < checkBase.getCentreY() + 25) {
							if(!checkDrone.getTeamName().equals(checkBase.getTeamName())) {
								checkBase.setHealth(checkBase.getHealth() - 1);
							}
						}
					}
				}
			}
		} 
		
		for(int i = 0; i < bases.size(); i++) {
			for(int j = 0; j < bases.get(i).getSwarmCount(); j++) {
				Drone drone = bases.get(i).getDrone(j);
				for(int k = i + 1; k < bases.size(); k++) {
					for(int l = 0; l < bases.get(k).getSwarmCount(); l++) {
						Drone secondDrone = bases.get(k).getDrone(l);
						if(drone.getX() > secondDrone.getX() - 10 && drone.getX() < secondDrone.getX() + 10) {
							if(drone.getY() > secondDrone.getY() - 10 && drone.getY() < secondDrone.getY() + 10) {
								drone.setHealth(drone.getHealth() - 1);
								drone.setMoving(false);
								secondDrone.setHealth(drone.getHealth() - 1);
								secondDrone.setMoving(false);
							}
						}
					}
				}
			}
		}
	}
	
	public static void checkDeath() { //checks if things have no health, and should be deleted
		for(int i = 0; i < bases.size(); i++) {
			for(int j = 0; j < bases.get(i).getSwarmCount(); j++) {
				if(bases.get(i).getDrone(j).getHealth() <= 0) {
					bases.get(i).killDrone(j);
				}
			}
			if(bases.get(i).getHealth() <= 0) {
				bases.remove(i);
			}
		} 
	}
	
	public static void runAIs() { //runs all the AIs
		for(int i = 0; i < computers.size(); i++) {
			computers.get(i).runAI();
		}
	}
	
	public static void checkWinOrLose() {
		boolean playerAlive = false;
		for(int i = 0; i < bases.size(); i++) {
			if(bases.get(i).getTeamName().equals(player1.getName())) {
				playerAlive = true;
			}
		}
		
		if(playerAlive && bases.size() == 1) {
			GraphicsMain.gameState = 2;
		}
		else if(!playerAlive) {
			GraphicsMain.gameState = 3;
		}
		
	}
	
	public static Level getLevel() {
		return level;
	}
	
	public static ArrayList<Base> getBases() {
		return bases;
	}
	
	public static void resetSelection() {
		selectedElements = new ArrayList<LevelElement>();
	}

}
