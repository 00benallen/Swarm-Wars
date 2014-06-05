package objects;

import java.util.ArrayList;

import graphics.GraphicsMain;

public class Level { //stores the initial level data from the text file
	private int width = GraphicsMain.WIDTH/64,  height = GraphicsMain.HEIGHT/64;
	private ArrayList<LevelElement> level;
	private ArrayList<Base> bases;
	
	public Level() {
		level = new ArrayList<LevelElement>();
		setBases(new ArrayList<Base>());
	}

	public ArrayList<LevelElement> getLevelArray() {
		return level;
	}

	public void setLevelArray(ArrayList<LevelElement> level) {
		this.level = level;
	}
	
	public void addElement(LevelElement newElement) {
		level.add(newElement);
	}
	
	public void removeElement(int index) {
		level.remove(index);
	}
	
	public void removeElement(LevelElement element) {
		level.remove(element);
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public ArrayList<Base> getBases() {
		return bases;
	}

	public void setBases(ArrayList<Base> bases) {
		this.bases = bases;
	}
	
	
	
	
}
