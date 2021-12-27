import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;

//pokemon class
public class Pokemon {

	int ID; //number of mon in dex
	String name; //name of mon
	String nickname; //u can give your pokemon nicknames
	int xpValue; //value of xp it gives when killed
	String ability; //*******MAKE ABILITY CLASS


	int HPstat;
	int atkStat;
	int defStat;
	int spAtkStat;
	int spDefStat;
	int spdStat;

	int[] baseStats = new int[6];
	//IVs are a random number generated from 1-31 that affect the actual pokestats
	int[] IVs = new int[6];

	/*
	 * 
	 * 
	 * theres a lot of stuff here with EV's & IV's that determine the final attack/defense/hp/speed etc
	 * i think the best strategy is to make a seperate Blankmon class with the unchangable poke info (ex. bulbasaur by default is grass, etc)
	 */

	Move[] attacks = new Move[4]; //a pokemon can have 4 moves at any give time
	int level;
	Type type; //maybe make a type class


	//hashmap of all predetermined pokemon stats sorted by their names (which are all unique)
	static HashMap <String, BlankMon> pokeStats = new HashMap<String, BlankMon>();

	public Pokemon (String name, String nickname, int level) {
		this.nickname = nickname;
		this.level = level;
		this.name = name;
		this.ID = pokeStats.get(name).getID();
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

		this.IVs[0] = (int) Math.floor(Math.random()*(31)+1);
		this.IVs[1] = (int) Math.floor(Math.random()*(31)+1);
		this.IVs[2] = (int) Math.floor(Math.random()*(31)+1);
		this.IVs[3] = (int) Math.floor(Math.random()*(31)+1); 
		this.IVs[4] = (int) Math.floor(Math.random()*(31)+1);
		this.IVs[5] = (int) Math.floor(Math.random()*(31)+1);


	}

	public String toString () {
		return String.format("Your level %d %s is a %s, ID no: %d with stats: [%d/%d/%d/%d/%d/%d] and these IVs: [%d/%d/%d/%d/%d/%d]", 
				this.level, this.nickname, this.name, this.ID, 
				this.HPstat, this.atkStat, this.defStat, this.spAtkStat, this.spDefStat, this.spdStat,
				this.IVs[0], this.IVs[1], this.IVs[2], this.IVs[3], this.IVs[4], this.IVs[5]);
	}

	public void updateHpStat () {
		int hp = (int) Math.floor(((2 * this.baseStats[0] + this.IVs[0])*this.level)/100) + this.level + 10;
		this.HPstat = hp;
	}
	
	public void updateOtherStat (int stat) {
		//1 -- attack
		//2 -- defense
		//3 -- sp attack
		//4 -- sp defense
		//5 -- speed
		int newStat = (int) Math.floor(((2 * baseStats[stat] + IVs[stat]) * this.level)/100   +5);
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
	
	public void updateAllStats() {
		this.updateHpStat();
		this.updateOtherStat(1);
		this.updateOtherStat(2);
		this.updateOtherStat(3);
		this.updateOtherStat(4);
		this.updateOtherStat(5);

	}



	public static void addAllPokemon () throws IOException, FileNotFoundException {

		BufferedReader br = new BufferedReader (new FileReader(new File("Pokemon.csv")));

		String curLine = br.readLine();

		System.out.println("hello world");

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

			pokeStats.put(name, new BlankMon(curID, name, type1,type2, curHP, curAtk, curDef, curSpAtk, curSpDef, curSpeed, generation, isLegendary));

			curLine = br.readLine();

		}



	}



}
