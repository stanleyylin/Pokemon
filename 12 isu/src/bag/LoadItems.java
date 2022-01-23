package bag;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class LoadItems {
	
	private static HashMap<String,Item> itemList = new HashMap<String,Item>();
	
	public LoadItems()
	{
		try 
		{
			addAllItems();
		} 
		catch (IOException e) {}
	}
	
	public static void addAllItems() throws IOException , FileNotFoundException
	{
		BufferedReader br = new BufferedReader(new FileReader(new File("items.txt")));
		
		String curLine = br.readLine();
		
		//getting poke balls
		for (int i = 0; i < 4; i++) {
			curLine = br.readLine();
			String curName = curLine.substring(0, curLine.indexOf(","));
			curLine = curLine.substring(curLine.indexOf(",")+1);
			String curEffect = curLine.substring(0, curLine.indexOf(","));
			curLine = curLine.substring(curLine.indexOf(",")+1);
			int curCost = Integer.parseInt(curLine.substring(0,curLine.indexOf(",")));
			curLine = curLine.substring(curLine.indexOf(",")+1);
			double curChance = Double.parseDouble(curLine);
			itemList.put(curName, new Item(curName, curEffect, curCost, curChance, curName+".png"));
		}

		br.readLine();
		//getting medicine items
		for (int i = 0; i < 9; i++) {
			curLine = br.readLine();
			String curName = curLine.substring(0, curLine.indexOf(","));
			curLine = curLine.substring(curLine.indexOf(",")+1);
			String curEffect = curLine.substring(0, curLine.indexOf(","));
			curLine = curLine.substring(curLine.indexOf(",")+1);
			int curCost = Integer.parseInt(curLine.substring(0,curLine.indexOf(",")));
			curLine = curLine.substring(curLine.indexOf(",")+1);
			int curHealing = Integer.parseInt(curLine.substring(0,curLine.indexOf(",")));
			curLine = curLine.substring(curLine.indexOf(",")+1);
			boolean curStatus = Boolean.parseBoolean(curLine.substring(0,curLine.indexOf(",")));
			curLine = curLine.substring(curLine.indexOf(",")+1);
			int curPP = Integer.parseInt(curLine.substring(0,curLine.indexOf(",")));
			curLine = curLine.substring(curLine.indexOf(",")+1);
			boolean curRevive = Boolean.parseBoolean(curLine);
			itemList.put(curName, new Item(curName, curEffect,curCost, curHealing, curStatus, curPP, curRevive, curName+".png"));
			
		}
		
		br.readLine();
		//getting key items
		for (int i = 0; i < 4; i++) {
			curLine = br.readLine();
			String curName = curLine.substring(0, curLine.indexOf(","));
			curLine = curLine.substring(curLine.indexOf(",")+1);
			String curEffect = curLine;
			itemList.put(curName, new Item(curName, curEffect, curName+".png"));
		}
	}
	
	public Item getItem(String s)
	{
		return itemList.get(s);
				
	}

}
