package pokesetup;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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
	int baseExp;
	
	
	//hashmap contains ability name,ability
	static HashMap<String, Ability> abilityList = new HashMap<String, Ability>();
	//hashmap contains movename,move
	static HashMap<String, Move> moveList = new HashMap<String, Move>();
	//hashmap contains the name of pokemon, (list of [level u get move, actual move])
	static HashMap<String,ArrayList<Pair<Integer,Move>>> movesByMon = new HashMap<String,ArrayList<Pair<Integer,Move>>>();
	
	
	
	
	
	
//	static HashMap<Pair<String,Integer>, Move> movesByMon = new HashMap<Pair<String,Integer>,Move>();
	
	
	
	

	public BlankMon(int ID, String name, String type1, String type2, int HP, int attack, int defense, int sp_attack, int sp_defense, int speed, 
			int generation, boolean isLegendary, Ability ability1, Ability ability2, int baseExp) {
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
		this.baseExp = baseExp;
//		try {
//			this.pokeFront = loader.loadImage("black-white/" + ID + ".png");
//			this.pokeBack = loader.loadImage("black-white/back/" + ID + ".png");
//		} catch (IOException e) {}
	}
	
	public String toString () {
		return String.format("No: %d, Name: %s(%d), Type: %s, %d, %d, %d, %d, %d, %d, gen %d, legendary? %b", 
				this.ID, this.name, this.baseExp, this.type.toString(), this.HP, this.attack, this.defense, this.sp_attack, this.sp_defense, this.speed, this.generation, this.isLegendary);
	}
	
	
	

	
	public static void getAllMoveLists() throws IOException, FileNotFoundException {
		//must be done AFTER getAllMoves
		//ideally this should be redone with pair arraylists BUT its fine if not....
		//otherwise the text file can be changed so there is just no lvl1  moves
		//or this can just have a seperate list of lvl 1 moves (since i change to treemap)
		BufferedReader br = new BufferedReader (new FileReader (new File ("movesByMon.txt")));
		String curLine = "";
		String curName = "";
		int curLevel = 0;
//		String curMove = "";
		Move curMove = null;
		String[] curMoveArray;
		TreeMap<Integer,Move> curMap = new TreeMap<Integer,Move>();
		ArrayList<Pair<Integer,Move>> curAL = new ArrayList<Pair<Integer,Move>>();
		
//		for (int i = 0; i < 100; i++) {
//			curLine = br.readLine();
//			
//			curName = curLine.substring(0,curLine.indexOf("-"));
//			curLine = curLine.substring(curLine.indexOf("-")+1);
//
//			
//			curMoveArray = curLine.split(";");
//			for (String s : curMoveArray) {
////				System.out.println(s);
//				curLevel = s.substring(0,s.indexOf(","));
//				s = s.substring(s.indexOf(",")+1);
//				curMove = moveList.get(s);
//				curAL.add(new Pair<Integer, Move>(Integer.parseInt(curLevel), curMove));
////				curMap.put(Integer.parseInt(curLevel),moveList.get(curMove));
//			}
//			System.out.println(curAL + "________");
//			movesByMon.put(curName.trim(), new ArrayList<Pair<Integer,Move>>(curAL));
//			curAL.clear();
//		}
		
		
		for (int i = 0; i< 100; i++) {
			curLine = br.readLine();
			curName = curLine.substring(0,curLine.indexOf("-")).trim();
			curLine = curLine.substring(curLine.indexOf("-")+1);
			curMoveArray = curLine.split(";");
			for (String s: curMoveArray) {
				curLevel = Integer.parseInt(s.substring(0,s.indexOf(",")).trim());
//				System.out.print(curLevel + " -- ");
				curMove = moveList.get(s.substring(s.indexOf(",")+1));
//				System.out.println(curMove);
				curAL.add(new Pair<Integer,Move>(curLevel,curMove));
			}
			movesByMon.put(curName, new ArrayList(curAL));
			curAL.clear();
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
			
			Pokemon.Status curStat = null;
			//applying poison stat to move
			if (curName.equals("Cross Poison") ||  curName.equals("Gunk Shot") || curName.equals("Poison Gas") || curName.equals("Poison Powder") ||
					curName.equals("Poison Sting") || curName.equals("Poison Tail") || curName.equals("Sludge") || curName.equals("Sludge Bomb") || 
					curName.equals("Sludge Wave") || curName.equals("Smog") || curName.equals("Toxic Spikes") || curName.equals("Twineedle"))
				curStat = Pokemon.Status.POISON;
			
			if (curName.equals("Blaze Kick") || curName.equals("Ember") || curName.equals("Fire Blast") || curName.equals("Fire Fang") ||
					curName.equals("Fire Punch") || curName.equals("Flame Wheel") || curName.equals("Flamethrower") || curName.equals("Flare Blitz") || curName.equals("Heat Wave") ||
					curName.equals("Inferno") || curName.equals("Scald") || curName.equals("Searing Shot") || curName.equals("Will-O-Wisp") || curName.equals("Sacred Fire"))
				curStat = Pokemon.Status.BURN;
			
			if (curName.equals("Blizzard") || curName.equals("Ice Beam") || curName.equals("Ice Fang") || curName.equals("Ice Punch") || curName.equals("Powder Snow"))
				curStat = Pokemon.Status.FREEZE;
			
			if (curName.equals("Body Slam") || curName.equals("Bolt Strike") || curName.equals("Discharge") || curName.equals("Force Palm") || curName.equals("Lick") ||
					curName.equals("Nuzzle") || curName.equals("Spark") || curName.equals("Stun Spore") || curName.equals("Thunder") || curName.equals("Thunder Fang") ||
					curName.equals("Thunder Punch") || curName.equals("Thunder Shock") || curName.equals("Thunder Wave") || curName.equals("Thunderbolt") ||
					curName.equals("Volt Tackle") || curName.equals("Zap Cannon"))
				curStat = Pokemon.Status.PARALYSIS;
			
			if (curName.equals("Hypnosis") || curName.equals("Rest") || curName.equals("Sleep Powder") || curName.equals("Spore") || curName.equals("Yawn"))
				curStat = Pokemon.Status.SLEEP;
			
			if (curName.equals("Confuse Ray") || curName.equals("Confusion") || curName.equals("Dizzy Punch") || curName.equals("Psybeam") || curName.equals("Confuse Ray") ||  
					curName.equals("Signal Beam") || curName.equals("Supersonic") || curName.equals("Swagger") ||  curName.equals("Water Pulse"))
				curStat = Pokemon.Status.CONFUSED;
			
			moveList.put(curName, new Move(curName, curType, curCategory, Integer.parseInt(curPower), 
					Integer.parseInt(curAccuracy), Integer.parseInt(curPP), curTM, Integer.parseInt(curProb), curGen, curStat));
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
	
	public int getExpValue() {
		return this.baseExp;
	}
	
	
}
