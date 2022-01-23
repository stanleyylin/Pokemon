package pokesetup;
import java.awt.Dimension;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;

import entity.NPC;
import entity.Player;

public class Driver extends JPanel{
	
	static JFrame frame;
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

//		frame = new JFrame ("Pokemon");
//		Driver panel = new Driver();
//		panel.setPreferredSize(new Dimension(1920, 1080));
//		frame.add(panel);
//		frame.pack();
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setVisible(true);
//		
		

		
		try {
			BlankMon.getAllMoves();
			BlankMon.getAllMoveLists();
			BlankMon.getAllAbilities();
			Pokemon.addAllPokemon();
		} catch (IOException e) {
		}

		Scanner in = new Scanner (System.in);
		
		Player pranav = new Player(0,0);
		
		pranav.addPokemonToParty(new Pokemon("Charizard", "fye", 48));
//		System.out.println(pranav.getParty()[0].getPossibleMoves());
		pranav.addPokemonToParty(new Pokemon("Persian", "catty", 32));
		pranav.addPokemonToParty(new Pokemon("Machamp", "strong", 54));
		
		pranav.printPokemon();
		
		NPC gary = new NPC(0,0, null);
		
		gary.addPokemonToParty(new Pokemon ("Fearow", "birdy", 36));
		gary.addPokemonToParty(new Pokemon("Seel", "woop", 66));
		
		gary.printPokemon();
		
		
		
		
//		ArrayList <Pokemon> myPokemon = new ArrayList<Pokemon>();
//		
//		myPokemon.add(new Pokemon ("Oddish", "leaf", 21));
//		System.out.println(myPokemon.get(0));
//		myPokemon.get(0).updateAllStats();
//		System.out.println("\n\n\n");
//		
//		Pokemon purr = new Pokemon("Meowth", "purr", 30);
//		
//		Pokemon vul = new Pokemon("Vulpix", "flam", 34);
//		
//		System.out.println(purr);		
//		System.out.println(vul);
//
//		vul.attack(0, purr);
//		
//		System.out.println(purr);		
//		System.out.println(vul);
//
//		purr.heal();
//		
//		System.out.println(purr);		
//		System.out.println(vul);
//
//		
////		System.out.println(myPokemon.get(0).getPossibleMoves());
//		
//		
////		myPokemon.add(new Pokemon ("Litwick", "candle", 40));
////		System.out.println(myPokemon.get(1));
////		myPokemon.get(1).updateAllStats();
////		System.out.println(myPokemon.get(1));
//

		

	}

}
