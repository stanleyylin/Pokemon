

//a pokemon has 4 MOVES/attacks
public class Move {
	
	String name;
	boolean isStatus;
	//it does a status effect (like increases attack or decreases defense)
	int damage;
	int pp; //pp is how many times a move can be used
	Type type; //moves have types
	
	public Move (String name, int damage, int pp, Type type, boolean isStatus) {
		this.name = name;
		this.damage = damage;
		this.pp = pp;
		this.type = type;
		this.isStatus = isStatus;
	}
	
	

}
