package pokesetup;


//a pokemon has 4 MOVES/attacks and they are split into physical attacks, special attacks, and status moves
//physical/special attacks do damage and status moves can apply a status to the enemy such as poison or burn
//or it can increase/lower their stats (decrease their defense or attack, etc)
public class Move {



	private String name;
	private Type type; //moves have types
	private String category;
	//it does a status effect (like increases attack or decreases defense)
	private String effect; // description of the move	
	private int damage; // base damage of the move
	private int accuracy;
	private int curPP; //pp is how many times a move can be used
	private int pp; 
	private String TM;
	private int prob; //the move can have a probability to do an alternative effect eg. burn or poison
	private int gen;
	private Pokemon.Status curStatus; //status the move applies
	private int[] statMod; //stat modifiers the move applies

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
		this.statMod = new int[4];
	}
	public Move (String name, Type type, String category, int damage, int accuracy, int pp, String TM, int prob, int gen, Pokemon.Status status, int[] statMods) {
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
		this.statMod = statMods;
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
		if (n > this.pp)
			this.curPP = this.pp;
		else
			this.curPP = n;
	}
	public void useMove() {
		this.curPP--;
	}
	public Pokemon.Status getStatus() {
		return this.curStatus;
	}
	public int[] getStatMod() {
		return this.statMod;
	}



}
