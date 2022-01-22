// PokeSelect allows the player to view their party of up to 6 Pokemons and
// if they are in battle, they are able to select and call one of these Pokemons

package main;

import java.awt.*;

import getimages.LoadImage;
import main.Button;
import pokesetup.BlankMon;
import pokesetup.Move;
import pokesetup.Pokemon;

import java.awt.event.MouseEvent;
import javax.swing.*;

import entity.Player;

import java.awt.image.*;
import java.io.IOException;

@SuppressWarnings("serial")
public class PokeSelect extends JPanel {

	private Player player; // The player object to access pokemon party
	private int curr; // The current pokemon on screen

	private BufferedImage bg; // The background
	private Button pokemonBack; // The back button, pranav: for now
	// this will just call Battle but later on it will call main to switch screens

	private boolean inBattle;
	private Font font = new Font("Pokemon GB", Font.PLAIN, 30);

	private PokemonButton[] pokemons;
	private Battle battle;


	public PokeSelect(Battle battle, Player player, int curr, boolean inBattle)
	{
		setPreferredSize(new Dimension(GamePanel.screenWidth, GamePanel.screenHeight));
		setLayout(null);
		setBackground(Color.BLACK);

		LoadImage loader = new LoadImage();

		try
		{
			bg = loader.loadImage("res/battle/grass.png");
		}
		catch(IOException e) {}

		this.player = player;
		this.curr = curr;
		this.inBattle = inBattle;
		this.battle = battle;
		PokemonButton.setImages();

		pokemons = new PokemonButton[6];
		for(int i = 0; i < 6; i++)
		{
			pokemons[i] = new PokemonButton(this);

			if(i%2==0)
				pokemons[i].setBounds(20, 15+178*(i/2)+4*(i/2), PokemonButton.width, PokemonButton.height);
			else
				pokemons[i].setBounds(544, 40+178*(i/2)+4*(i/2), PokemonButton.width, PokemonButton.height);
		}

		// Back Button
		try
		{
			BufferedImage back1 = loader.loadImage("res/battle/back1.png");
			back1 = back1.getSubimage(0, 0, 1050, 219);
			back1 = loader.resize(back1, 194, 40);
			BufferedImage back2 = loader.loadImage("res/battle/back2.png");
			back2 = back2.getSubimage(0, 0, 1050, 219);
			back2 = loader.resize(back2, 194, 40);
			pokemonBack = new Button(this, back1, back2, 194, 40);
		}
		catch(IOException e) {}
		pokemonBack.setBounds(767, 630, 194, 40);

		updatePokemon();
	}

	// buttonClick is for the back button and is called to switch back to the previous
	// screen
	// Parameters: MouseEvent e to get the source of the mouse click (back button in
	// this case
	// Returns: void
	public void buttonClick(MouseEvent e)
	{
		// This will eventually call main to switch screen
		if(e.getSource().equals(pokemonBack))
		{
			System.out.println("go back");	
		}
	}

	// This is called when the player clicks on a pokemon to select it.
	// Note that this can only called if a battle is in progress. See PokemonButton
	// class - it has a boolean that needs to be updated when a battle in progress.
	// Parameters: MouseEvent e - when a player clicks on a Pokemon to choose it
	// Returns: void
	public void pokemonSelected(MouseEvent e)
	{
		PokemonButton curButton = (PokemonButton) e.getSource();
		Pokemon curMon = curButton.getPoke();

		if (curMon != null && curMon.getIsFainted())
			System.out.println("cant do that");
		
		else {
			battle.setNextMon( getPartyNo(curMon));
//			System.out.println(curr);
		}
		
	}
	
	public int getCur() {
		return curr;
	}
	
	
	//just finds the party number that the pokemon is 
	public int getPartyNo(Pokemon p1) {
		
		for (int i = 0; i < 6; i++) {
			if (this.player.getParty()[i].equals(p1))
				return i;
		}
		return -1;
	}


	// Updates the pokemons and displays them. 
	// Parameters: none
	// Returns: void
	public void updatePokemon()
	{
		for(int i = 0; i < 6; i++)
		{
			// If the pokemon is the current one in battle
			// If not in battle, it'll just be the first pokemon
			if(i == curr) 
			{
				pokemons[i].update(player.getParty()[i], true); 
				this.add(pokemons[i]);

			}
			else
			{
				pokemons[i].update(player.getParty()[i], false);
				this.add(pokemons[i]);
			}
		}
		this.add(pokemonBack); // Add the back button

		// These 2 lines are necessary to ensure everything is displayed properly
		this.revalidate();
		this.repaint();
	}

	public int getCurr()
	{
		return curr;
	}
	public boolean getInBattle()
	{
		return inBattle;
	}

	// refresh is called by PokemonButton to ensure that the buttons are displayed properly
	// No parameters/returns void
	public void refresh()
	{
		repaint();
	}

	// paintComponent to draw the screen
	// P: Graphics g
	// R: void
	public void paintComponent(Graphics g) {
		//static
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(bg, 0, 0, null);

		g2.setColor(Color.BLACK);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
		g2.fillRect(0, 595, GamePanel.screenWidth, 125);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

		g2.setFont(font);
		g2.setColor(Color.white);
		if(inBattle)
			g2.drawString("Choose a Pokemon!", 90, 660);
		else
			g2.drawString("Your Pokemon Party!", 90, 660);

	}
	
	public void setupFrame() {
		
	}

	// PRANAV: delete this, this is just for testing
//	public static void main(String[] args)
//	{
//		JFrame frame = new JFrame ("Pokemon");
//		frame.setLocationRelativeTo(null);
//		try {
//			BlankMon.getAllMoves();
//			BlankMon.getAllMoveLists();
//			BlankMon.getAllAbilities();
//			Pokemon.addAllPokemon();
//		} 
//		catch (IOException e) {}
//
//		Player pranav = new Player(0,0);
//		pranav.addPokemonToParty(new Pokemon("Charizard", "BBQ Dragon", 69));
//		pranav.getParty()[0].setCurHP(100);
//		pranav.addPokemonToParty(new Pokemon("Persian", "Kiwi", 32));
//		pranav.addPokemonToParty(new Pokemon("Machamp", "Punch", 54));
//		pranav.getParty()[2].setIsFainted(true);;
//		pranav.addPokemonToParty(new Pokemon("Bulbasaur", "Light Bulb", 54));
//		pranav.addPokemonToParty(new Pokemon("Jigglypuff", "Jiggle", 44));
//		pranav.addPokemonToParty(new Pokemon("Pikachu", "Zappy", 14));
//		PokeSelect panel = new PokeSelect(pranav, 0, true);
//
//		frame.setContentPane(panel);
//		frame.setVisible(true);
//		frame.setResizable(false);
//		frame.pack();
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
//		frame.setLocationRelativeTo(null);
//	}



}
