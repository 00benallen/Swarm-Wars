package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import objects.Base;
import objects.Level;
import objects.Wall;

public class LevelLoader {
	
	public static Level loadLevel(String fileName) {
		Scanner s = null;
		try {
			s = new Scanner(new File(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
		Level level = new Level();
		
		for(int i = 0; i < level.getHeight(); i++) {
			String line = s.nextLine();
			for(int j = 0; j < line.length(); j++) {
				if(line.charAt(j) == 'W') {
					level.addElement(new Wall(j*64, i*64));
				}
				else if(line.charAt(j) == 'O') {
					level.addElement(null);
				}
				else if(line.charAt(j) == 'B') {
					Base newBase = new Base(j*64 + 16, i*64 + 16);
					level.addElement(newBase);
					level.getBases().add(newBase);
				}
			}
		}
		
		return level;
	}

}
