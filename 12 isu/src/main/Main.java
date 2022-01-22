package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.JFrame;

import entity.Player;

public class Main {
	static JFrame frame;
	static GamePanel mainGame;
	static MainMenu menu;
	static KeyHandler keyHandler;
	private static Player main;
	public final static int screenWidth = 1080;
	public final static int screenHeight = 720;
	
	public static void loadGame()
	{
		keyHandler = new KeyHandler(mainGame.getPlayer(), mainGame.getMoving());
		frame.setContentPane(mainGame);
		frame.setVisible(true);
		frame.pack();
		frame.addKeyListener(keyHandler);
	}
	
	public static void returnMainMenu()
	{
		frame.setContentPane(menu);
		frame.setVisible(true);
		frame.pack();
	}
	
	
	static HashMap<String,Item> itemList = new HashMap<String,Item>();
	
	public static void addAllItems() throws IOException , FileNotFoundException{
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
			itemList.put(curName, new Item(curName, curEffect, curCost, curChance));
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
			itemList.put(curName, new Item(curName, curEffect,curCost, curHealing, curStatus, curPP, curRevive));
			
		}
		
		br.readLine();
		//getting key items
		for (int i = 0; i < 4; i++) {
			curLine = br.readLine();
			String curName = curLine.substring(0, curLine.indexOf(","));
			curLine = curLine.substring(curLine.indexOf(",")+1);
			String curEffect = curLine;
			itemList.put(curName, new Item(curName, curEffect));
		}
		System.out.println(itemList);
	}

	public static void main(String[] args) {
		try {
			addAllItems();
		} catch (IOException e) {
		}
		frame = new JFrame ("Pokemon: Wong Edition");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainGame = new GamePanel();
		menu = new MainMenu();
		frame.setContentPane(menu);
		frame.setVisible(true);
		frame.pack();
		frame.setLocationRelativeTo(null);
	}

}
