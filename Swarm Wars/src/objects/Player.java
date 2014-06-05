package objects;

/**
 * Defines the parameters for a Player object, simply an easy way to store team names for players
 * @author Ben Pinhorn
 * @version 1.0
 */
public class Player {
	private String name;
	
	public Player(String name) {
		this.setName(name);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
