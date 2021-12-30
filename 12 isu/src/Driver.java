import java.awt.Dimension;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;

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
			Pokemon.addAllPokemon();
			BlankMon.getAllAbilities();
		} catch (IOException e) {
		}
		Scanner in = new Scanner (System.in);
		
//		String temp = in.nextLine();
//		System.out.println(Pokemon.pokeStats.get(temp));
		
		
		ArrayList <Pokemon> myPokemon = new ArrayList<Pokemon>();
		
		myPokemon.add(new Pokemon ("Bulbasaur", "leaf", 80));
		System.out.println(myPokemon.get(0));
		myPokemon.get(0).updateAllStats();
		System.out.println(myPokemon.get(0));

		

	}

}
