package game;

import graphics.GraphicsMain;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import objects.Base;
import objects.Level;
import objects.LevelElement;
import objects.Player;

public class Main {
	private static Level level;
	private static Player player1;
	private static ArrayList<Base> bases;
	private static long lastDroneSpawn = System.nanoTime();
	private static ArrayList<LevelElement> selectedElements;
	
	
	public static void init() {
		level = LevelLoader.loadLevel("resources/levels/level1");
		player1 = new Player("Standin");
		bases = level.getBases();
		player1.setBase(bases.get(0));
	}
	
	public static void update() {
		spawnDrones();
		selectItems();
	}
	
	public static void spawnDrones() {
		long curTime = System.nanoTime();
		
		if(curTime - lastDroneSpawn > 1000000000L) {
			for(int i = 0; i < bases.size(); i++) {
				bases.get(i).spawnDrones();
			}
			lastDroneSpawn = System.nanoTime();
		}
	}
	
	public static Level getLevel() {
		return level;
	}
	
	public static void selectItems() {
		if(GraphicsMain.listener.itemsSelected()) {
			Rectangle2D selectBox = GraphicsMain.listener.getSelectionBox();
			for(int i = 0; i < level.getLevelArray().size(); i++) {
				if(selectBox.contains(level.getLevelArray().get(i).getBoundBox())) {
					selectedElements.add(level.getLevelArray().get(i));
				}
			}
		}
	}

}
