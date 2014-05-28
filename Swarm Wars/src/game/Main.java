package game;

import graphics.GraphicsMain;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import objects.Base;
import objects.Drone;
import objects.Level;
import objects.LevelElement;
import objects.Player;

public class Main {
	private static Level level;
	private static Player player1;
	private static ArrayList<Base> bases;
	private static long lastDroneSpawn = System.nanoTime();
	private static ArrayList<LevelElement> selectedElements;
	private static int droneVelocity = 1;
	
	public static void init() {
		level = LevelLoader.loadLevel("resources/levels/level1");
		player1 = new Player("Player1");
		bases = level.getBases();
		selectedElements = new ArrayList<LevelElement>();
	}
	
	public static void update() {
		spawnDrones();
		moveSelectedTo();
		checkArrived();
		checkDamage();
		checkDeath();
	}
	
	private static void spawnDrones() {
		long curTime = System.nanoTime();
		
		if(curTime - lastDroneSpawn > 1000000000L) {
			for(int i = 0; i < bases.size(); i++) {
				bases.get(i).spawnDrones();
			}
			lastDroneSpawn = System.nanoTime();
		}
	}
	
	public static void selectItems() {
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
	
	private static void moveSelectedTo() {
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
	
	public static void checkArrived() {
		for(int i = 0; i < bases.size(); i++) {
			LevelElement element = bases.get(i);
			if(element.isMoving()) {
				if(element.getX() == element.getMoveX() && element.getX() == element.getMoveX()) {
					if(element.getY() == element.getMoveY() && element.getY() == element.getMoveY()) {
						element.setMoving(false);
					}
				}
			}
			
			Base baseElement = (Base) element;
			for(int j = 0; j < baseElement.getSwarmCount(); j++) {
				element = baseElement.getDrone(j);
				if(element.isMoving()) {
					if(element.getX() == element.getMoveX() && element.getX() == element.getMoveX()) {
						if(element.getY() == element.getMoveY() && element.getY() == element.getMoveY()) {
							element.setMoving(false);
						}
					}
				}
			}
		}
	}
	
	
	
	public static Level getLevel() {
		return level;
	}
	
	public static ArrayList<Base> getBases() {
		return bases;
	}
	
	public static void setMovePoints(int x, int y) {
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
	
	public static void resetSelection() {
		selectedElements = new ArrayList<LevelElement>();
	}
	
	public static void checkDamage() {
		for(int i = 0; i < bases.size(); i++) {
			for(int j = 0; j < bases.get(i).getSwarmCount(); j++) {
				Drone drone = bases.get(i).getDrone(j);
				for(int k = i + 1; k < bases.size(); k++) {
					for(int l = 0; l < bases.get(k).getSwarmCount(); l++) {
						Drone secondDrone = bases.get(k).getDrone(l);
						if(drone.getX() > secondDrone.getX() - 10 && drone.getX() < secondDrone.getX() + 10) {
							if(drone.getY() > secondDrone.getY() - 10 && drone.getY() < secondDrone.getY() + 10) {
								drone.setHealth(drone.getHealth() - 1);
								secondDrone.setHealth(drone.getHealth() - 1);
							}
						}
					}
				}
			}
		}
		
		for(int i = 0; i < bases.size(); i++) {
			Base checkBase = bases.get(i);
			for(int j = 0; j < bases.size() - 1; j++) {
				for(int k = 0; k < bases.get(j).getSwarmCount(); k++) {

					Drone checkDrone = bases.get(j).getDrone(k);

					if(checkDrone.getX() > checkBase.getX() - 50 && checkDrone.getX() < checkBase.getX() + 50) {
						if(checkDrone.getY() > checkBase.getY() - 50 && checkDrone.getY() < checkBase.getY() + 50) {
							checkBase.setHealth(checkBase.getHealth() - 1);
						}
					}
				}
			}
		} 
	}
	
	public static void checkDeath() {
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

}
