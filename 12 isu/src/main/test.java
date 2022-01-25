package main;

import java.io.IOException;

import java.awt.*;
import java.awt.image.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import entity.NPC;
import entity.Nurse;
import entity.Person;
import entity.Player;
import map.Building;
import map.Location;
import pokesetup.BlankMon;
import pokesetup.Pokemon;

public class test extends JPanel {
	public test()
	{
		 // 10. Hearthome City:
		// Collisions
		Rectangle[] collisions208 = new Rectangle[8];
		collisions208[0] = new Rectangle(0, 0, 217, 447); // top left
		collisions208[1] = new Rectangle(217*3, 146*3, 118*3, 807*3); // top
		collisions208[2] = new Rectangle(0, 146*3, 264, 897); // done
		collisions208[3] = new Rectangle(88*3, 313*3, 246*3, 133*3); 
		collisions208[4] = new Rectangle(175*3, 260*3, 82*3, 83*3);
		collisions208[5] = new Rectangle(199*3, 391*3, 59*3, 177*3); 
		collisions208[6] = new Rectangle(435*3, 118*3, 81*3, 44*3); 
		collisions208[7] = new Rectangle(118*3, 568*3, 15*3, 72*3); 
		collisions208[8] = new Rectangle(334*3, 362*3, 84*3, 290*3);
		collisions208[9] = new Rectangle(449*3, 297*3, 33*3, 105*3); 
		collisions208[10] = new Rectangle(568*3, 106*3, 14*3, 98*3); 
		collisions208[11] = new Rectangle(568*3, 215*3, 34*3, 31*3); 
		
		collisions208[12] = new Rectangle(580*3, 290*3, 35*3, 31*3); 
		collisions208[13] = new Rectangle(628*3, 188*3, 35*3, 195*3);
		
		collisions208[14] = new Rectangle(624*3, 383*3, 21*3, 79*3); 
		collisions208[15] = new Rectangle(624*3, 405*3, 42*3, 400*3);
		
		collisions208[16] = new Rectangle(862*3, 358*3, 162*3, 47*3);
		collisions208[17] = new Rectangle(961*3, 188*3, 63*3, 125*3);
		collisions208[18] = new Rectangle(703*3, 179*3, 25*3, 63*3); 
		collisions208[19] = new Rectangle(729*3, 116*3, 35*3, 126*3);
		collisions208[20] = new Rectangle(763*3, 116*3, 261*3, 72*3);
		// Buildings -----
		Building[] buildings10 = new Building[2];
		Person[] peoplePC = new Person[1];
		peoplePC[0] = new Nurse(new Rectangle(519, 225, 46, 55), "nurse.png", 17, 24, "nurse.txt");
		Rectangle[] collisionsPC = new Rectangle[0];
		buildings10[0] = new Building(new Rectangle(1052, 882, 62, 56), new Rectangle(540, 543, 5, 1), new Person[0], new Rectangle[0], house1);
		buildings10[1] = new Building(new Rectangle(818, 894, 62, 56), new Rectangle(535, 567, 81, 32), peoplePC, collisionsPC, pokecentre1);
		//-----
		//People
		Person[] people10 = new Person[1];
		people10[0] = new Person(new Rectangle(665, 922, 51, 72), "down", "nurse.png", 17, 24, "d1.txt");
		//Grass: None
		Location r208 = new Location("hearthome.png", collisions10, buildings10, people10, null);
	
		// 207
		Rectangle[] collisions207 = new Rectangle[16];
		collisions207[0] = new Rectangle(0, 0, 56*3, 194*3);
		collisions207[1] = new Rectangle(0, 194*3, 35*3, 227*3);
		collisions207[2] = new Rectangle(35*3, 90*3, 169*3, 13*3);
		collisions207[3] = new Rectangle(194*3, 104*3, 10*3, 100*3);
		collisions207[4] = new Rectangle(204*3, 192*2, 10*3, 79*3);
		collisions207[5] = new Rectangle(214*3, 261*3, 50*3, 10*3);
		collisions207[6] = new Rectangle(308*3, 192*3, 39*3, 44*3);
		collisions207[7] = new Rectangle(322*3, 236, 26*3, 75*3);
		collisions207[8] = new Rectangle(257*3, 305, 52*3, 11*3);
		collisions207[9] = new Rectangle(243*3, 339*3, 48*3, 77*3);
		collisions207[10] = new Rectangle(291*3, 316*3, 192*3, 100*3);
		collisions207[11] = new Rectangle(356*3, 273*3, 88*3, 44*3);
		collisions207[12] = new Rectangle(318*3, 0, 706*3, 65*3);
		collisions207[13] = new Rectangle(884*3, 65*3, 140*3, 354*3);
		collisions207[14] = new Rectangle(744*3, 272*3, 140*3, 144*3);
		collisions207[15] = new Rectangle(591, 316, 100, 153);
		// Buildings -----
		Building[] buildings207 = new Building[0];
		//-----
		//People
		Person[] people207 = new Person[1];
		people10[0] = new Person(new Rectangle(665, 922, 51, 72), "down", "nurse.png", 17, 24, "d1.txt");
		//Grass: None
		Location r207 = new Location("hearthome.png", collisions207, buildings10, people10, null);
	
	
	}
	
	public static void main(String[] args)
	{
		ArrayList<Integer> hello = new ArrayList<>();
		hello.add(1);
		System.out.println(hello.get(10));
	}
	
	

}
