package pokesetup;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.TreeMap;

import getimages.LoadImage;

public class BlankMon {
	
	static LoadImage loader = new LoadImage();
	
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
	Ability ability1;
	Ability ability2;
	HashMap<Integer,Move> possibleMoves = new HashMap<Integer,Move>();
	Image pokeFront;
	Image pokeBack;
	
	//hashmap contains ability name,ability
	static HashMap<String, Ability> abilityList = new HashMap<String, Ability>();
	//hashmap contains movename,move
	static HashMap<String, Move> moveList = new HashMap<String, Move>();
	//hashmap contains the name of pokemon, (list of [level u get move, actual move])
	static HashMap<String,TreeMap<Integer,Move>> movesByMon = new HashMap<String,TreeMap<Integer,Move>>();
	
	
//	static HashMap<Pair<String,Integer>, Move> movesByMon = new HashMap<Pair<String,Integer>,Move>();
	
	
	
	

	public BlankMon(int ID, String name, String type1, String type2, int HP, int attack, int defense, int sp_attack, int sp_defense, int speed, 
			int generation, boolean isLegendary, Ability ability1, Ability ability2) {
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
		this.ability1 = ability1;
		if (ability2!=null)
			this.ability2 = ability2;
		try {
			this.pokeFront = loader.loadImage("black-white/" + ID + ".png");
			this.pokeBack = loader.loadImage("black-white/back/" + ID + ".png");
		} catch (IOException e) {}
	}
	
	public String toString () {
		return String.format("No: %d, Name: %s, Type: %s, %d, %d, %d, %d, %d, %d, gen %d, legendary? %b", 
				this.ID, this.name, this.type.toString(), this.HP, this.attack, this.defense, this.sp_attack, this.sp_defense, this.speed, this.generation, this.isLegendary);
	}
	
	
	

	
	public static void getAllMoveLists() throws IOException, FileNotFoundException {
		//must be done AFTER getAllMoves
		//ideally this should be redone with pair arraylists BUT its fine if not....
		//otherwise the text file can be changed so there is just no lvl1  moves
		//or this can just have a seperate list of lvl 1 moves (since i change to treemap)
		BufferedReader br = new BufferedReader (new FileReader (new File ("movesByMon.txt")));
		String curLine = "";
		String curName = "";
		String curLevel = "";
		String curMove = "";
		String[] curMoveArray;
		TreeMap<Integer,Move> curMap = new TreeMap<Integer,Move>();
		
		for (int i = 0; i < 100; i++) {
			curLine = br.readLine();
			
			curName = curLine.substring(0,curLine.indexOf("-"));
			curLine = curLine.substring(curLine.indexOf("-")+1);

			
			curMoveArray = curLine.split(";");
			for (String s : curMoveArray) {
//				System.out.println(s);
				curLevel = s.substring(0,s.indexOf(","));
				s = s.substring(s.indexOf(",")+1);
				curMove = s;
				curMap.put(Integer.parseInt(curLevel),moveList.get(curMove));
			}
			movesByMon.put(curName.trim(), new TreeMap<Integer,Move>(curMap));
			curMap.clear();
		}
		
		
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
	
	public static void getAllMoves() throws IOException, FileNotFoundException{
		BufferedReader br = new BufferedReader (new FileReader (new File("All_Moves.csv")));
		String curLine = "";
		String[] curLineSplit;
		for (int i = 0; i <622; i++) {
			curLine = br.readLine();
			
//			curLineSplit = curLine.split(",");
//			System.out.print(curLineSplit.length + "  ");
//			
//			StringTokenizer st = new StringTokenizer(curLine, ",");
//			System.out.println(st.countTokens());
			
			
			String curEffect = "";
			
			String curName = curLine.substring(0,curLine.indexOf(","));
			curLine = curLine.substring(curLine.indexOf(",")+1);
			Type curType = new Type(curLine.substring(0,curLine.indexOf(",")));
			curLine = curLine.substring(curLine.indexOf(",")+1);
			String curCategory = curLine.substring(0,curLine.indexOf(","));
			curLine = curLine.substring(curLine.indexOf(",")+1);
			
			if (curLine.substring(0,1).equals("\"")) {
				curLine = curLine.substring(1);
				 curEffect = curLine.substring(0,curLine.indexOf("\""));
				 curLine = curLine.substring(curLine.indexOf("\"")+2);
			}
			else {
				 curEffect = curLine.substring(0,curLine.indexOf(","));
					curLine = curLine.substring(curLine.indexOf(",")+1);
			}
			
			String curPower = curLine.substring(0,curLine.indexOf(","));
			curLine = curLine.substring(curLine.indexOf(",")+1);
			String curAccuracy = curLine.substring(0,curLine.indexOf(","));
			curLine = curLine.substring(curLine.indexOf(",")+1);
			String curPP = curLine.substring(0,curLine.indexOf(","));
			curLine = curLine.substring(curLine.indexOf(",")+1);
			String curTM = curLine.substring(0,curLine.indexOf(","));
			curLine = curLine.substring(curLine.indexOf(",")+1);
			String curProb = curLine.substring(0,curLine.indexOf(","));
			curLine = curLine.substring(curLine.indexOf(",")+1);
			int curGen = Integer.parseInt(curLine);
			
			if (curPower.trim().equals("-"))
				curPower = "0";
			if (curAccuracy.trim().equals("-"))
				curAccuracy = "-1";
			else if (curAccuracy.trim().equals("∞"))
				curAccuracy = "10000";
			if (curProb.trim().equals("-"))
				curProb = "-1";
			if (curPP.trim().equals("-"))
				curPP = "-1";
//			System.out.println(i);
//			System.out.println(curName+ ",," +curType+ ",," +curCategory + ",," + curEffect+",," +curPower+",," +curAccuracy+",," +curPP+",," +curTM+",," +curProb+",," +curGen);

			
			moveList.put(curName, new Move(curName, curType, curCategory, Integer.parseInt(curPower), 
					Integer.parseInt(curAccuracy), Integer.parseInt(curPP), curTM, Integer.parseInt(curProb), curGen));
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
	
	public Ability getAbility1() {
		return this.ability1;
	}
	
	public Ability getAbility2() {
		return this.ability2;
	}
	
	
}
