import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class BlankMon {
	
	
	int ID;
	String name;
	//make the types here
	Type type;
	int HP;
	int attack;
	int defense;
	int sp_attack;
	int sp_defense;
	int speed;
	int generation;
	boolean isLegendary;
	
	static HashMap<String, Ability> abilityList = new HashMap<String, Ability>();
	
	

	public BlankMon(int ID, String name, String type1, String type2, int HP, int attack, int defense, int sp_attack, int sp_defense, int speed, int generation, boolean isLegendary) {
		this.ID = ID;
		this.name = name;
		this.type = new Type(type1, type2);
		this.HP = HP;
		this.attack = attack;
		this.defense = defense;
		this.sp_defense = sp_defense;
		this.sp_attack = sp_attack;
		this.speed = speed;
		this.generation = generation;
		this.isLegendary = isLegendary;
	}
	
	public String toString () {
		return String.format("No: %d, Name: %s, Type: %s, %d, %d, %d, %d, %d, %d, gen %d, legendary? %b", 
				this.ID, this.name, this.type.toString(), this.HP, this.attack, this.defense, this.sp_attack, this.sp_defense, this.speed, this.generation, this.isLegendary);
	}
	
	public static void getAllAbilities() throws IOException, FileNotFoundException{
		
		BufferedReader br = new BufferedReader (new FileReader (new File("abilities.yaml")));
		String curLine = "";
		String curName = "";
		int curGen = 0;
		String curDescription = "";
		for (int i = 0; i < 266; i++) {
			curLine = br.readLine();
			for (int j = 0; j < 3; j++) {
			curLine = br.readLine().trim();
			curLine = curLine.substring(curLine.indexOf(" ")).trim();
			if (j == 0) 
				curName = curLine;
			if (j == 1)
				curGen = Integer.parseInt(curLine);
			if (j == 2)
				curDescription = curLine;
			}
			abilityList.put(curName, new Ability(curName, curGen, curDescription));
		}
	}
	
	
	
	
	
	
	
	//getters
	public int getID () {
		return this.ID;
	}
	
	public Type getType() {
		return this.type;
	}
	
	public int getBaseHP() {
		return this.HP;
	}
	
	public int getBaseAttack() {
		return this.attack;
	}
	
	public int getBaseDefense() {
		return this.defense;
	}
	
	public int getBaseSpAttack() {
		return this.sp_attack;
	}
	
	public int getBaseSpDefense() {
		return this.sp_defense;
	}
	
	public int getBaseSpeed() {
		return this.speed;
	}
	
	
	
}
