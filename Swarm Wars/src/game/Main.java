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
	private static ArrayList<LevelElement> selectedElements, movingElements;
	private static int droneVelocity = 1;
	private static Vector2D moveVector;
	private static Point2D movePoint;
	
	public static void init() {
		level = LevelLoader.loadLevel("resources/levels/level1");
		player1 = new Player("Standin");
		bases = level.getBases();
		player1.setBase(bases.get(0));
		selectedElements = new ArrayList<LevelElement>();
		movingElements = new ArrayList<LevelElement>();
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
			for(int j = 0; j < bases.size(); j++) {
				for(int k = 0; k < bases.get(j).getSwarmCount(); k++) {
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
	
	private static void moveSelectedTo() {
		if(movePoint != null) {
			for(int i = 0; i < selectedElements.size(); i++) {
				LevelElement element = selectedElements.get(i);
				moveVector = new Vector2D((float)movePoint.getX() - element.getX(), (float)movePoint.getY() -  element.getY());
				Vector2D.normalizeVector(moveVector);
				Vector2D.multiplyByScalar(moveVector, droneVelocity);
				element.setX(element.getX() + Math.round(moveVector.getX()));
				element.setY(element.getY() + Math.round(moveVector.getY()));
			}
		}
	}
	
	public static Level getLevel() {
		return level;
	}
	
	public static void setMovePoint(int x, int y) {
		movePoint = new Point2D.Float(x, y);
	}
	
	public static void resetSelection() {
		selectedElements = new ArrayList<LevelElement>();
	}

}
