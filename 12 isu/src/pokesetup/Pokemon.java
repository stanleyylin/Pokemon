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

	int ID; //number of mon in dex
	String name; //name of mon
	String nickname; //u can give your pokemon nicknames
	int expValue; //value of xp it gives when killed
	int curExp;
	int curExpThreshold;
	Ability ability;//ability class
	int curHP;

	LoadImage loader = new LoadImage();

	int HPstat;
	int atkStat;
	int defStat;
	int spAtkStat;
	int spDefStat;
	int spdStat;

	int[] baseStats = new int[6];
	//IVs are a random number generated from 1-31 that affect the actual pokestats
	int[] IVs = new int[6];


//	TreeMap<Integer,Move> possibleMoves = new TreeMap<Integer,Move>();
	ArrayList<Pair<Integer,Move>> possibleMoves = new ArrayList<Pair<Integer,Move>>();
	Move[] attacks = new Move[4]; //a pokemon can have 4 moves at any give time
	int level;
	BufferedImage pokeFront;
	BufferedImage pokeBack;
	Type type; //type class
	Random random = new Random();//random class (literally that) for the nextBoolean to function
	boolean isFainted;
	Status status;


	//hashmap of all predetermined pokemon stats sorted by their names (which are all unique)
	static HashMap <String, BlankMon> pokeStats = new HashMap<String, BlankMon>();

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
		this.possibleMoves = new ArrayList<Pair<Integer,Move>>(BlankMon.movesByMon.get(this.name));
		generateMoves();
		updateAllStats();

		this.curHP = this.HPstat;
		
		this.expValue = pokeStats.get(name).getExpValue();
		this.curExp = 0;
		this.curExpThreshold = getNewExpThreshold ();
		
		
	}
	
	public void giveExp(Pokemon p1) {
		
//		System.out.println(curExpThreshold);
		double expDrop  =  (    ((p1.getExpDrop() * p1.getLevel()) / 5) *  Math.pow((2.0 * p1.getLevel() + 10) / (p1.getLevel() + this.level + 10),2.5));
		this.curExp += expDrop;
//		System.out.println(this.curExp + "----" + this.curExpThreshold);
	}

	public void levelUp() {
		System.out.println("pokemon has lvled up");
		this.level++;
		
		updateAllStats();
		this.curExp = this.curExp - this.curExpThreshold;
		this.curExpThreshold = getNewExpThreshold();
	}
	
	
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
		if (this.name.equals(p.name))
			return true;
		else 
			return false;
	}

	//takes in which attack it is (from attack aray) and enemy in battle (sometimes it wont hit enemy and is a self move tho)
	public void attack (Integer attack, Pokemon enemy) {
		Move curMove = attacks[attack];
		int A = 0;
		int D = 0;
		if (curMove.getCategory().equals("Special")) {
			A = this.spAtkStat;
			D = enemy.getSpDefense();
		}
		else if (curMove.getCategory().equals("Physical")){
			A = this.atkStat;
			D = enemy.getDefense();
		}

		double STAB = 1.0;
		//		if (this.type.equals(enemy.getType()))
		//			STAB = 1.5;
		int damage = 0;
		if (curMove.getDamage() > 0)
			damage = (int) ((((((2*this.level)/5)+2)*curMove.getDamage()*A/D)/50+2) * STAB);
		
		if (damage > enemy.getCurHP())
			enemy.setCurHP(0);
		else {
			enemy.setStatus(curMove.getStatus());
			enemy.setCurHP(enemy.getCurHP() - damage);
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
	}

	public void heal(int amt) {
		this.setCurHP(this.curHP + amt);
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
			
			if (curItems.length == 16) {
				curAbilityStr2 = curItems[14];
				curExp = Integer.parseInt(curItems[15]);
			}
			else if (count <= 151)
				curExp = Integer.parseInt(curItems[14]);

			Ability a1 = BlankMon.abilityList.get(curAbilityStr);
			Ability a2 = null;
			if (!curAbilityStr2.isEmpty()) {
				a2 = BlankMon.abilityList.get(curAbilityStr2);
			}
			
			count++;

			pokeStats.put(name, new BlankMon(curID, name, type1,type2, curHP, curAtk, curDef, curSpAtk, curSpDef, curSpeed, generation, isLegendary, a1, a2, curExp));




			curLine = br.readLine();
		}

	}

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



}
