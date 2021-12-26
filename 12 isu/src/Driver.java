import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		try {
			Pokemon.addAllPokemon();
		} catch (IOException e) {
		}
		Scanner in = new Scanner (System.in);
		
		String temp = in.nextLine();
		System.out.println(Pokemon.pokeStats.get(temp));
		
		
		ArrayList <Pokemon> myPokemon = new ArrayList<Pokemon>();
		
		myPokemon.add(new Pokemon ("Charizard", "fire", 25));
		System.out.println(myPokemon.get(0));
		
		System.out.println("test");

	}

}
