package pokesetup;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.TreeMap;

import getimages.LoadImage;

//pokemon class
public class Pokemon {

	public enum Status {
		BURN,
		FREEZE,
		PARALYSIS,
		POISON,
		SLEEP,
		CONFUSED
	}

	private int ID; //number of mon in dex
	private String name; //name of mon
	private String nickname; //u can give your pokemon nicknames
	private int expValue; //value of xp it gives when killed
	private int curExp; //curent exp pokemon has
	private int curExpThreshold; //exp for pokemon to level up
	private Ability ability;//ability class
	private int curHP; // current hp

	private LoadImage loader = new LoadImage();

	//all stats based on current level
	private 	int HPstat;
	private int atkStat;
	private int defStat;
	private int spAtkStat;
	private int spDefStat;
	private int spdStat;

	//base stats that are predetermined base on the species of pokemon
	private int[] baseStats = new int[6];
	//IVs are a random number generated from 1-31 that affect the actual pokestats
	private int[] IVs = new int[6];


	//	TreeMap<Integer,Move> possibleMoves = new TreeMap<Integer,Move>();
	private ArrayList<Pair<Integer,Move>> possibleMoves = new ArrayList<Pair<Integer,Move>>();
	private Move[] attacks = new Move[4]; //a pokemon can have 4 moves at any give time
	private int level; //level of the pokemon
	private BufferedImage pokeFront; //front sprite
	private BufferedImage pokeBack;  //back sprite
	private Type type; //type class
	private Random random = new Random();//random class (literally that) for the nextBoolean to function
	private boolean isFainted;
	private Status status;
	private boolean canEvolve; //if the pokemon can evolve	
	private int evolveLevel; //the level at which it evolves (if applicable)
	private int[] curMonStatMods= {0,0,0,0}; //if the pokemon has any current stat modifiers & what they are



	//hashmap of all predetermined pokemon stats sorted by their names (which are all unique)
	private static HashMap <String, BlankMon> pokeStats = new HashMap<String, BlankMon>();
	//hashmap of the same stats but indexed by ID no instead for getting evolution (cuz pokemon always evolve into the next numbered mon)
	private static TreeMap <Integer, BlankMon> statsForEvolve = new TreeMap<Integer,BlankMon>();

	public Pokemon (String name, String nickname, int level) {
		this.nickname = nickname;
		this.level = level;
		this.name = name;
		this.ID = pokeStats.get(name).getID();
		this.isFainted = false;
		this.status = null;

		//getting pokeimages
		try {
			this.pokeFront = loader.loadImage("black-white/" + ID + ".png");
			pokeFront = loader.resize(pokeFront, pokeFront.getWidth()*2, pokeFront.getHeight()*2);
			this.pokeBack = loader.loadImage("black-white/back/" + ID + ".png");
			pokeBack = loader.resize(pokeBack, pokeBack.getWidth()*3+pokeBack.getWidth()/2, pokeBack.getHeight()*3+pokeBack.getHeight()/2);
		} catch (IOException e) {}

		//setting base stats to the current stats
		this.HPstat = pokeStats.get(name).getBaseHP();
		this.atkStat = pokeStats.get(name).getBaseAttack();
		this.defStat = pokeStats.get(name).getBaseDefense();
		this.spAtkStat = pokeStats.get(name).getBaseSpAttack();
		this.spDefStat = pokeStats.get(name).getBaseSpDefense();
		this.spdStat = pokeStats.get(name).getBaseSpeed();		

		//setting base stats to the base stat array
		this.baseStats[0] = pokeStats.get(name).getBaseHP();
		this.baseStats[1] = pokeStats.get(name).getBaseAttack();
		this.baseStats[2] = pokeStats.get(name).getBaseDefense();
		this.baseStats[3] = pokeStats.get(name).getBaseSpAttack();
		this.baseStats[4] = pokeStats.get(name).getBaseSpDefense();
		this.baseStats[5] = pokeStats.get(name).getBaseSpeed();	

		//setting the pokemons base IVs
		this.IVs[0] = (int) Math.floor(Math.random()*(31)+1);
		this.IVs[1] = (int) Math.floor(Math.random()*(31)+1);
		this.IVs[2] = (int) Math.floor(Math.random()*(31)+1);
		this.IVs[3] = (int) Math.floor(Math.random()*(31)+1); 
		this.IVs[4] = (int) Math.floor(Math.random()*(31)+1);
		this.IVs[5] = (int) Math.floor(Math.random()*(31)+1);

		//grabbing pokemons Ability
		if (pokeStats.get(name).getAbility2() == null)
			this.ability =  pokeStats.get(name).getAbility1();
		else {
			if (random.nextBoolean())
				this.ability =  pokeStats.get(name).getAbility1();
			else
				this.ability =  pokeStats.get(name).getAbility2();
		}
		//getting the moveList
		this.possibleMoves = new ArrayList<Pair<Integer,Move>>(BlankMon.getMovesByMon().get(this.name));
		generateMoves();
		updateAllStats();

		//setting the HP
		this.curHP = this.HPstat;
		
		//setting up exp values
		this.expValue = pokeStats.get(name).getExpValue();
		this.curExp = 0;
		this.curExpThreshold = getNewExpThreshold ();

		//setting up evolution
		this.canEvolve = pokeStats.get(name).getIfEvolve();
		if (this.canEvolve)
			this.evolveLevel = pokeStats.get(name).getEvLvl();




	}

	//adds exp to this pokemonx xp tally when this pokemon beats another
	public void giveExp(Pokemon p1) {

		double expDrop  =  (    ((p1.getExpDrop() * p1.getLevel()) / 5) *  Math.pow((2.0 * p1.getLevel() + 10) / (p1.getLevel() + this.level + 10),2.5));
		this.curExp += expDrop;
	}

	//increments thie pokemons level nby 1 and updates the states acoordingly.
	public void levelUp() {
		System.out.println("pokemon has lvled up");
		int lostHP = this.HPstat-this.curHP;
		this.level++;
		generateMoves();
		updateAllStats();		
		this.heal();
		this.curHP = this.curHP-lostHP;
		this.curExp = this.curExp - this.curExpThreshold;
		this.curExpThreshold = getNewExpThreshold();
	}

	//finds the amount of exp required to get to the next level
	public int getNewExpThreshold() {
		return (12 * this.level * this.level) / 5;
	}


	public String toString () {

		return String.format("Your level '%d' %s is a %s has %d/%d, ID no: %d with stats: [%d/%d/%d/%d/%d/%d] and these IVs: [%d/%d/%d/%d/%d/%d] with %s\n"
				+ "current moves: [%s,%s,%s,%s]\n", 
				this.level, this.nickname, this.name, this.curHP,this.HPstat,      this.ID, 
				this.HPstat, this.atkStat, this.defStat, this.spAtkStat, this.spDefStat, this.spdStat,
				this.IVs[0], this.IVs[1], this.IVs[2], this.IVs[3], this.IVs[4], this.IVs[5],
				this.ability.toString(), attacks[0],attacks[1],attacks[2],attacks[3]);

	}

	public boolean equals (Object o) {
		Pokemon p = (Pokemon) o;
		if (this.name.equals(p.name) && this.level == p.level)
			return true;
		else 
			return false;
	}

	//takes the amount stats are to be modified and finds the actual multiplier
	public double getStatModifier(int n) {
		double d = (double)n;

		d = 1.0 + Math.abs(n) * 0.5;

		if (n < 0)
			return 1/d;
		return d;
	}

	//takes in which attack it is (from attack aray) and enemy in battle (sometimes it wont hit enemy and is a self move tho)
	public void attack (Integer attack, Pokemon enemy) {
		//both int arrays contains the status 
		Move curMove = attacks[attack];
		int A = 0;
		int D = 0;
		if (curMove.getCategory().equals("Special")) {
			A = this.spAtkStat;	
			A = (int) (A * getStatModifier(this.curMonStatMods[2]));

			D = enemy.getSpDefense();
			D = (int) (D * getStatModifier(enemy.getStatMods()[3]));
		}
		else if (curMove.getCategory().equals("Physical")){
			A = this.atkStat;
			A = (int) (A * getStatModifier(this.curMonStatMods[0]));

			D = enemy.getDefense();
			D = (int) (D * getStatModifier(enemy.getStatMods()[1]));
		}

		double STAB = 1.0;
		
		//this line could b problematic
//			if (this.type.equals(curMove.getType()))
//				STAB = 1.5;
		int damage = 0;
		if (curMove.getDamage() > 0)
			damage = (int) ((((((2*this.level)/5)+2)*curMove.getDamage()*A/D)/50+2) * STAB);

		if (damage > enemy.getCurHP())
			enemy.setCurHP(0);
		else {
			if (curMove.getStatus() != null)
				enemy.setStatus(curMove.getStatus());
			enemy.setCurHP(enemy.getCurHP() - damage);

			for (int i = 0; i < 4; i++ ) {

				enemy.getStatMods()[i] = enemy.getStatMods()[i] + curMove.getStatMod()[i];

			}

		}

	}



	//generates 4 best moves based on current pokeLevel
	public void generateMoves() {
		ArrayList<Move> tempMoves = new ArrayList<Move>();

		Collections.sort(this.possibleMoves);
		for (Pair p : this.possibleMoves) {
			if ((int) p.getInt() <= this.level)
				tempMoves.add((Move)p.getMove());
			if (tempMoves.size() == 4)
				break;
		}
		for (int i = 0; i < tempMoves.size(); i++) {
			attacks[i] = tempMoves.get(i);
		}
	}

	//heals pokemon to full
	public void heal() {
		//just in case hp stat is not updated
		this.updateHpStat();
		this.curHP = this.HPstat;
		
		for (int n : this.curMonStatMods)
			n = 0;
	}

	//heals pokemon given the amount to heal by
	public void heal(int amt) {
		if ((this.curHP + amt) > this.HPstat)
			this.heal();
		else
			this.setCurHP(this.curHP + amt);
	}

	//evolves the pokemon 
	public Pokemon evolve() {
		int pokeID = this.ID + 1;
		String newName = statsForEvolve.get(pokeID).getName();
		return (new Pokemon (newName, this.nickname, this.level));
	}

	//updates the HP stat based on formula
	public void updateHpStat () {
		int hp = (int) Math.floor(((2 * this.baseStats[0] + this.IVs[0])*this.level)/100) + this.level + 10;
		this.HPstat = hp;
	}

	//updates stat based on formula
	public void updateOtherStat (int stat) {
		//1 -- attack
		//2 -- defense
		//3 -- sp attack
		//4 -- sp defense
		//5 -- speed
		int newStat = (int) Math.floor(((2 * this.baseStats[stat] + this.IVs[stat]) * this.level)/100   +5);
		if (stat == 1)
			this.atkStat = newStat;
		else if (stat == 2)
			this.defStat = newStat;
		else if (stat == 3)
			this.spAtkStat = newStat;
		else if (stat == 4)
			this.spDefStat = newStat;
		else if (stat == 5)
			this.spdStat = newStat;
	}

	//updates all stats
	public void updateAllStats() {
		this.updateHpStat();
		this.updateOtherStat(1);
		this.updateOtherStat(2);
		this.updateOtherStat(3);
		this.updateOtherStat(4);
		this.updateOtherStat(5);

	}


	//must be done after all the blankmon methods
	//adds all BlankMons into the program
	public static void addAllPokemon () throws IOException, FileNotFoundException {

		BufferedReader br = new BufferedReader (new FileReader(new File("Pokemon.csv")));

		String curLine = br.readLine();

		int count = 0;
		while (curLine != null) {

			int curID = 0;
			String name = "";
			String type1 = "";
			String type2 = "";
			int totalStat = 0;
			int curHP = 0;
			int curAtk = 0;
			int curDef = 0;
			int curSpAtk = 0;
			int curSpDef = 0;
			int curSpeed = 0;
			int generation = 0;
			boolean isLegendary = false;
			String curAbilityStr = "";
			String curAbilityStr2 = "";
			int curExp = 0;
			int curEvLvl = 0;

			String[] curItems = curLine.split(",");
			curID = Integer.parseInt(curItems[0]);
			name = curItems[1];

			type1 = curItems[2];
			type2 = curItems[3];
			totalStat = Integer.parseInt(curItems[4]);
			curHP = Integer.parseInt(curItems[5]);
			curAtk = Integer.parseInt(curItems[6]);
			curDef = Integer.parseInt(curItems[7]);
			curSpAtk = Integer.parseInt(curItems[8]);
			curSpDef = Integer.parseInt(curItems[9]);
			curSpeed = Integer.parseInt(curItems[10]);
			generation = Integer.parseInt(curItems[11]);
			isLegendary = Boolean.parseBoolean(curItems[12]);
			curAbilityStr = curItems[13];
			if (curItems.length == 17) {
				curAbilityStr2 = curItems[14];
				curExp = Integer.parseInt(curItems[15]);
				curEvLvl = Integer.parseInt(curItems[16]);
			}
			else if (count <= 145) {
				curExp = Integer.parseInt(curItems[14]);
				curEvLvl = Integer.parseInt(curItems[15]);
			}

			Ability a1 = BlankMon.getAbilityList().get(curAbilityStr);
			Ability a2 = null;
			if (!curAbilityStr2.isEmpty()) {
				a2 = BlankMon.getAbilityList().get(curAbilityStr2);
			}
			count++;
			BlankMon cur =new BlankMon(curID, name, type1,type2, curHP, curAtk, curDef, curSpAtk, curSpDef, curSpeed, generation, isLegendary, a1, a2, curExp, curEvLvl);
			pokeStats.put(name, cur);
			statsForEvolve.put(curID, cur);
			curLine = br.readLine();
		}

	}

	
	//getters & setters
	public ArrayList getPossibleMoves() {
		return this.possibleMoves;
	}

	public int getDefense () {
		return this.defStat;
	}

	public int getSpDefense() {
		return this.spDefStat;
	}

	public Type getType() {
		return this.type;
	}

	public int getHPStat() {
		return this.HPstat;
	}

	public int getCurHP() {
		return this.curHP;
	}

	public void setCurHP(int i) {
		this.curHP  = i;
	}

	public boolean getIsFainted() {
		return isFainted;
	}

	public void setIsFainted(boolean b1) {
		this.isFainted = b1;
	}

	public BufferedImage getFront()
	{
		return pokeFront;
	}
	public BufferedImage getBack()
	{
		return pokeBack;
	}

	public Move[] getAttacks()
	{
		return attacks;
	}
	public String getName()
	{
		return this.name;
	}
	public String getNickName()
	{
		return this.nickname;
	}
	public int getLevel()
	{
		return this.level;
	}

	public Move[] getCurMoves() {
		return attacks;
	}

	public Status getStatus() {
		return this.status;
	}

	public void setStatus(Status s1) {
		this.status = s1;
	}

	public int getExpDrop() {
		return this.expValue;
	}

	public int getCurExp() {
		return this.curExp;
	}

	public void setCurExp(int n) {
		this.curExp = n;
	}
	public int getCurExpThreshold() {
		return this.curExpThreshold;
	}

	public boolean getIfEvolve() {
		return this.canEvolve;
	}

	public int getEvolveLvl() {
		return this.evolveLevel;
	}
	public void setIfEvolve(boolean b) {
		this.canEvolve = b;
	}

	public int[] getStatMods() {
		return this.curMonStatMods;
	}
	
	public static TreeMap<Integer,BlankMon> getStatsForEvolve(){
		return statsForEvolve;
	}
	
	public static HashMap<String, BlankMon> getPokeStats() {
		return pokeStats;
	}
	



}
