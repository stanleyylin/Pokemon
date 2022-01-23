package pokesetup;


//a pokemon has 4 MOVES/attacks
public class Move {
	
//	enum Status {
//		BURN,
//		FREEZE,
//		PARALYSIS,
//		POISON,
//		SLEEP,
//		CONFUSED
//	}
	
	String name;
	Type type; //moves have types
	String category;
	//it does a status effect (like increases attack or decreases defense)
	String effect;
	int damage;
	int accuracy;
	int curPP; //pp is how many times a move can be used
	int pp;
	String TM;
	int prob; //the move can have a probability to do an alternative effect eg. burn or poison
	int gen;
	Pokemon.Status curStatus;
	
	public Move (String name, Type type, String category, int damage, int accuracy, int pp, String TM, int prob, int gen) {
		this.name = name;
		this.type = type;
		this.category = category;
		this.damage = damage;
		this.accuracy = accuracy;
		this.pp = pp;
		this.curPP = pp;
		this.TM = TM;
		this.prob = prob;
		this.gen = gen;
		this.curStatus = null;
	}
	public Move (String name, Type type, String category, int damage, int accuracy, int pp, String TM, int prob, int gen, Pokemon.Status status) {
		this.name = name;
		this.type = type;
		this.category = category;
		this.damage = damage;
		this.accuracy = accuracy;
		this.pp = pp;
		this.curPP = pp;
		this.TM = TM;
		this.prob = prob;
		this.gen = gen;
		this.curStatus = status;
	}
	
	public String toString() {
		return String.format("{%s,%s,%s,%d dmg,%d%%,%d pp}",
				this.name,this.type,this.category,this.damage,this.accuracy,this.pp);
				
	}
	
	public String getName()
	{
		return this.name;
	}
	public Type getType() {
		return this.type;
	}
	public int getPP()
	{
		return this.pp;
	}
	public int getCurPP()
	{
		return this.curPP;
	}
	public int getDamage() {
		return this.damage;
	}
	public String getCategory() {
		return this.category;
	}
	
	public void setCurPP(int n) {
		this.curPP = n;
	}
	public void useMove() {
		this.curPP--;
	}
	public Pokemon.Status getStatus() {
		return this.curStatus;
	}
	
	

}
