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
		player1.setBase(bases.get(0));
		selectedElements = new ArrayList<LevelElement>();
	}
	
	public static void update() {
		spawnDrones();
		selectItems();
		moveSelectedTo();
		checkArrived();
		checkDamage();
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
	
	private static void selectItems() {
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
				if(element.getX() > element.getMoveX() - 5 && element.getX() < element.getMoveX() + 5) {
					if(element.getY() > element.getMoveY() - 5 && element.getY() < element.getMoveY() + 5) {
						element.setMoving(false);
					}
				}
			}
			
			Base baseElement = (Base) element;
			for(int j = 0; j < baseElement.getSwarmCount(); j++) {
				element = baseElement.getDrone(j);
				if(element.isMoving()) {
					if(element.getX() > element.getMoveX() - 30 && element.getX() < element.getMoveX() + 30) {
						if(element.getY() > element.getMoveY() - 30 && element.getY() < element.getMoveY() + 30) {
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
	
	public static void setMovePoints(int x, int y) {
		for(int i = 0; i < selectedElements.size(); i++) {
			selectedElements.get(i).setMoveX(x);
			selectedElements.get(i).setMoveY(y);
			selectedElements.get(i).setMoving(true);
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
						//damage both!
					}
				}
				
			}
		}
	}

}
