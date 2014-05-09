package game;

import objects.Level;

public class Main {
	private static Level level1;
	
	public static void init() {
		level1 = LevelLoader.loadLevel("resources/levels/level1");
	}
	
	public static void update() {
		
	}
	
	public static Level getLevel1() {
		return level1;
	}

}
