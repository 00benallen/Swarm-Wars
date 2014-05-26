package objects;

public class Player {
	private Base base;
	private String name;
	
	public Player(String name) {
		this.setName(name);
	}

	public Base getBase() {
		return base;
	}

	public void setBase(Base base) {
		this.base = base;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
