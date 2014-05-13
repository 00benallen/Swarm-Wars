package game;

import java.util.ArrayList;

import objects.Base;
import objects.Level;
import objects.Player;

public class Main {
	private static Level level;
	private static Player player1;
	private static ArrayList<Base> bases;
	private static long lastDroneSpawn = System.nanoTime();
	
	
	public static void init() {
		level = LevelLoader.loadLevel("resources/levels/level1");
		player1 = new Player("Standin");
		bases = level.getBases();
		player1.setBase(bases.get(0));
	}
	
	public static void update() {
		spawnDrones();
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

}
