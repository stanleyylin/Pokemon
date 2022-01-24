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


//all pokemon have A LOT of predetermined stats so we decided to collect all of them here and 
//have the pokemon class access them when creating a new Pokemon object
//another way of looking at this is like the pokemon skeleton class (morbid) as it contains the pokemon
//object without its personal details (nickname, level, etc)
public class BlankMon {

	private static LoadImage loader = new LoadImage();

	private int ID;
	private String name;
	//make the types here
	private Type type;
	private int HP;
	private int attack;
	private int defense;
	private int sp_attack;
	private int sp_defense;
	private int speed;
	private int generation;
	private boolean isLegendary;
	private Ability ability1;
	private Ability ability2;
	private HashMap<Integer,Move> possibleMoves = new HashMap<Integer,Move>();
	private Image pokeFront;
	private Image pokeBack;
	private int baseExp;
	private int evolveLevel;
	private boolean canEvolve;

	//hashmap contains ability name,ability
	private static HashMap<String, Ability> abilityList = new HashMap<String, Ability>();
	//hashmap contains movename,move
	private static HashMap<String, Move> moveList = new HashMap<String, Move>();
	//hashmap contains the name of pokemon, (list of [level u get move, actual move])
	private static HashMap<String,ArrayList<Pair<Integer,Move>>> movesByMon = new HashMap<String,ArrayList<Pair<Integer,Move>>>();


	public BlankMon(int ID, String name, String type1, String type2, int HP, int attack, int defense, int sp_attack, int sp_defense, int speed, 
			int generation, boolean isLegendary, Ability ability1, Ability ability2, int baseExp, int evolveLvl) {
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
		this.evolveLevel = evolveLvl;
		if (this.evolveLevel != -1)
			this.canEvolve = true;
		else
			this.canEvolve = false;
	}

	public String toString () {
		return String.format("No: %d, Name: %s(%d), Type: %s, %d, %d, %d, %d, %d, %d, gen %d, legendary? %b", 
				this.ID, this.name, this.baseExp, this.type.toString(), this.HP, this.attack, this.defense, this.sp_attack, this.sp_defense, this.speed, this.generation, this.isLegendary);
	}




	//must be done AFTER getAllMoves
	//gets all the movelists for all the pokemon 
	public static void getAllMoveLists() throws IOException, FileNotFoundException {
		BufferedReader br = new BufferedReader (new FileReader (new File ("movesByMon.txt")));
		String curLine = "";
		String curName = "";
		int curLevel = 0;
		Move curMove = null;
		String[] curMoveArray;
		TreeMap<Integer,Move> curMap = new TreeMap<Integer,Move>();
		ArrayList<Pair<Integer,Move>> curAL = new ArrayList<Pair<Integer,Move>>();


		//NOTE ***IDEALLY I ADD THE NEXT 50***
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

	//gets all abilities from list (abilities are not implemented but ya there are there)
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

	//gets all possible moves from list
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

			Pokemon.Status curStat = null;
			//applying poison status to move
			if (curName.equals("Cross Poison") ||  curName.equals("Gunk Shot") || curName.equals("Poison Gas") || curName.equals("Poison Powder") ||
					curName.equals("Poison Sting") || curName.equals("Poison Tail") || curName.equals("Sludge") || curName.equals("Sludge Bomb") || 
					curName.equals("Sludge Wave") || curName.equals("Smog") || curName.equals("Toxic Spikes") || curName.equals("Twineedle"))
				curStat = Pokemon.Status.POISON;

			//applying burn status to moves
			if (curName.equals("Blaze Kick") || curName.equals("Ember") || curName.equals("Fire Blast") || curName.equals("Fire Fang") ||
					curName.equals("Fire Punch") || curName.equals("Flame Wheel") || curName.equals("Flamethrower") || curName.equals("Flare Blitz") || curName.equals("Heat Wave") ||
					curName.equals("Inferno") || curName.equals("Scald") || curName.equals("Searing Shot") || curName.equals("Will-O-Wisp") || curName.equals("Sacred Fire"))
				curStat = Pokemon.Status.BURN;

			//applying frozen status to moves
			if (curName.equals("Blizzard") || curName.equals("Ice Beam") || curName.equals("Ice Fang") || curName.equals("Ice Punch") || curName.equals("Powder Snow"))
				curStat = Pokemon.Status.FREEZE;

			//applying paralyzed status to moves
			if (curName.equals("Body Slam") || curName.equals("Bolt Strike") || curName.equals("Discharge") || curName.equals("Force Palm") || curName.equals("Lick") ||
					curName.equals("Nuzzle") || curName.equals("Spark") || curName.equals("Stun Spore") || curName.equals("Thunder") || curName.equals("Thunder Fang") ||
					curName.equals("Thunder Punch") || curName.equals("Thunder Shock") || curName.equals("Thunder Wave") || curName.equals("Thunderbolt") ||
					curName.equals("Volt Tackle") || curName.equals("Zap Cannon"))
				curStat = Pokemon.Status.PARALYSIS;

			//applying asleep status to moves
			if (curName.equals("Hypnosis") || curName.equals("Rest") || curName.equals("Sleep Powder") || curName.equals("Spore") || curName.equals("Yawn"))
				curStat = Pokemon.Status.SLEEP;

			//applying confused status to moves
			if (curName.equals("Confuse Ray") || curName.equals("Confusion") || curName.equals("Dizzy Punch") || curName.equals("Psybeam") || curName.equals("Confuse Ray") ||  
					curName.equals("Signal Beam") || curName.equals("Supersonic") || curName.equals("Swagger") ||  curName.equals("Water Pulse"))
				curStat = Pokemon.Status.CONFUSED;

			int[] statMod = new int[4];
			
			//applies attack stat modifier
			if (curName.equals("Curse") || curName.equals("Growth") || curName.equals("Hone Claws") || curName.equals("Howl") || curName.equals("Meditate") || curName.equals("Metal Claw") || 
					curName.equals("Rage") || curName.equals("Sharpen") || curName.equals("Growth"))
				statMod[0] =1;
			if (curName.equals("Swagger") || curName.equals("Swords Dance"))
				statMod[0] = 2;
			if (curName.equals("Aurora Beam") || curName.equals("Growl") || curName.equals("Tickle"))
				statMod[0] = -1;
			
			//applies defense stat modifier
			if (curName.equals("Curse") || curName.equals("Defense Curl") || curName.equals("Harden") || curName.equals("Withdraw"))
				statMod[1] = 1;
			if (curName.equals("Acid Armor") || curName.equals("Barrier"))
				statMod[1] = 2;
			if (curName.equals("Acid") || curName.equals("Crunch") || curName.equals("Leer") || curName.equals("Tail Whip") || curName.equals("Screech"))
				statMod[1] = -1;
			
			//applies special attack stat modifier
			if (curName.equals("Screech") || curName.equals("Growth") )
				statMod[2] = 1;
			if(curName.equals("Memento"))
				statMod[2] = -1;
			
			//applies special defense stat modifier
			if (curName.equals("Calm Mind") || curName.equals("Cosmic Power") || curName.equals("Stockpile") || curName.equals("Amnesia")  )
				statMod[3] = 1;
			if (curName.equals("Bug Buzz") || curName.equals("Crunch") || curName.equals("Screech") || curName.equals("Psychic") || curName.equals("Shadow Ball") || 
					curName.equals("Spit Up") || curName.equals("Fake Tears") )
				statMod[3] = -1;
			
			moveList.put(curName, new Move(curName, curType, curCategory, Integer.parseInt(curPower), 
					Integer.parseInt(curAccuracy), Integer.parseInt(curPP), curTM, Integer.parseInt(curProb), curGen, curStat, statMod));
		}
	}








	//getters
	public String getName() {
		return this.name;
	}
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
	
	public boolean getIfEvolve() {
		return this.canEvolve;
	}
	public int getEvLvl() {
		return this.evolveLevel;
	}
	
	public static HashMap<String,Move> getMoveList() {
		return moveList;
	}
	public static HashMap<String, ArrayList<Pair<Integer, Move>>> getMovesByMon() {
		return movesByMon;
	}
	public static HashMap<String,Ability> getAbilityList(){
		return abilityList;
	}


}
