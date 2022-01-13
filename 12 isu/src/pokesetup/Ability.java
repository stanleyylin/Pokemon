package pokesetup;

public class Ability {
	
	private String name;
	private int gen;
	private String description;
	
	public Ability(String name, int gen, String description) {
		this.name = name;
		this.gen = gen;
		this.description = description;
	}
	
	public String toString () {
		return String.format("%s(gen %d): %s", this.name, this.gen, this.description);
	}

}
