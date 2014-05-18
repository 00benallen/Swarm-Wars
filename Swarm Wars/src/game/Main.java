package game;

import graphics.GraphicsMain;

import java.awt.geom.Point2D;
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
	private static Vector2D moveVector;
	private static Point2D movePoint;
	
	public static void init() {
		level = LevelLoader.loadLevel("resources/levels/level1");
		player1 = new Player("Standin");
		bases = level.getBases();
		player1.setBase(bases.get(0));
		selectedElements = new ArrayList<LevelElement>();
	}
	
	public static void update() {
		spawnDrones();
		selectItems();
		moveSelectedTo();
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
			for(int i = 0; i < level.getLevelArray().size(); i++) {
				LevelElement element = level.getLevelArray().get(i);
				if(element != null) {
					if(!selectedElements.contains(element)) {
						if(selectBox.contains(element.getBoundBox())) {
							selectedElements.add(element);
						}
					}
					if(element.isBase()) {
						for(int j = 0; j < ((Base)element).getSwarmCount(); j++) {
							Drone drone = ((Base)element).getDrone(j);
							if(selectBox.contains(drone.getBoundBox()) && !selectedElements.contains(drone)) {
								selectedElements.add(drone);
							}
						}
					}
				}
			}
		}
		else {
			selectedElements = new ArrayList<LevelElement>();
		}
	}
	
	private static void moveSelectedTo() {
		if(movePoint != null) {
			moveVector = new Vector2D((float)movePoint.getX(), (float)movePoint.getY());
			Vector2D.normalizeVector(moveVector);
			Vector2D.multiplyByScalar(moveVector, droneVelocity);
			for(int i = 0; i < selectedElements.size(); i++) {
				LevelElement element = selectedElements.get(i);
				if(moveVector.getX() > element.getX()) {
					element.setX(element.getX() + Math.round(moveVector.getX()));
				}
				else {
					element.setX(element.getX() - Math.round(moveVector.getX()));
				}
				
				if(moveVector.getY() > element.getY()) {
					element.setY(element.getY() + Math.round(moveVector.getY()));
				}
				else {
					element.setY(element.getY() - Math.round(moveVector.getY()));
				}
				
			}
		}
	}
	
	public static Level getLevel() {
		return level;
	}
	
	public static void setMovePoint(int x, int y) {
		movePoint = new Point2D.Float(x, y);
	}

}
