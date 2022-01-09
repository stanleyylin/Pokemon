

//a pokemon has 4 MOVES/attacks
public class Move {
	
	String name;
	Type type; //moves have types
	String category;
	//it does a status effect (like increases attack or decreases defense)
	String effect;
	int damage;
	int accuracy;
	int pp; //pp is how many times a move can be used
	String TM;
	int prob; //the move can have a probability to do an alternative effect eg. burn or poison
	int gen;
	
	public Move (String name, Type type, String category, int damage, int accuracy, int pp, String TM, int prob, int gen) {
		this.name = name;
		this.type = type;
		this.category = category;
		this.damage = damage;
		this.accuracy = accuracy;
		this.pp = pp;
		this.TM = TM;
		this.prob = prob;
		this.gen = gen;
	}
	
	public String toString() {
		return String.format("{%s,%s,%s,%d dmg,%d%%,%d pp}",
				this.name,this.type,this.category,this.damage,this.accuracy,this.pp);
				
	}
	
	public Type getType() {
		return this.type;
	}
	
	public int getDamage() {
		return this.damage;
	}
	public String getCategory() {
		return this.category;
	}
	
	

}
