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
import map.Gate;
import map.Location;
import pokesetup.BlankMon;
import pokesetup.Pokemon;

public class test extends JPanel {
	public test()
	{
		 // 10. Hearthome City:
		// Collisions
		Rectangle[] collisions208 = new Rectangle[21];
		collisions208[0] = new Rectangle(0, 0, 217*3, 149*3);
		collisions208[1] = new Rectangle(217*3, 84*3, 807*3, 118*3);
		collisions208[2] = new Rectangle(0, 149*3, 88*3, 299*3); 
		collisions208[3] = new Rectangle(88*3, 315*3, 246*3, 133*3);
		collisions208[4] = new Rectangle(260*3, 175*3, 82*3, 83*3);
		collisions208[5] = new Rectangle(391*3, 199*3, 59*3, 177*3);
		collisions208[6] = new Rectangle(435*3, 118*3, 44*3, 81*3);
		collisions208[7] = new Rectangle(568*3, 118*3, 14*3, 98*3);
		collisions208[8] = new Rectangle(512*3, 331*3, 33*3, 43*3);
		collisions208[9] = new Rectangle(449*3, 297*3, 105*3, 33*3);
		collisions208[10] = new Rectangle(334*3, 364*3, 290*3, 84*3); 
		collisions208[11] = new Rectangle(568*3, 215*3, 34*3, 31*3);
		collisions208[12] = new Rectangle(580*3, 290*3, 35*3, 31*3); 
		collisions208[13] = new Rectangle(628*3, 188*3, 35*3, 195*3);
		collisions208[14] = new Rectangle(624*3, 385*3, 79*3, 21*3);
		collisions208[15] = new Rectangle(624*3, 405*3, 400*3, 42*3);
		collisions208[16] = new Rectangle(862*3, 358*3, 162*3, 47*3);
		collisions208[17] = new Rectangle(961*3, 188*3, 63*3, 125*3);
		collisions208[18] = new Rectangle(703*3, 179*3, 25*3, 63*3); 
		collisions208[19] = new Rectangle(729*3, 116*3, 35*3, 126*3);
		collisions208[20] = new Rectangle(763*3, 116*3, 261*3, 72*3);
		// Buildings -----
		Building[] buildings208 = new Building[1];
		buildings208[0] = new Building(new Rectangle(2726, 574, 62, 56), new Rectangle(540, 543, 81, 32), new Person[0], new Rectangle[0], house1);
		//-----
		//People
		Person[] people208 = new Person[0];
		people208[0] = null;
		//Grass
		Rectangle[] grass208 = new Rectangle[1];
		grass208[0] = new Rectangle(2007, 738, 480, 471);
		Location r208 = new Location("208.png", collisions208, buildings208, people208, null);
	
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
		collisions207[15] = new Rectangle(591*3, 316*3, 100*3, 153*3);
		// Buildings -----
		Building[] buildings207 = new Building[0];
		//-----
		//People
		Person[] people207 = new Person[0];
		// people10[0] = new Person(new Rectangle(665, 922, 51, 72), "down", "nurse.png", 17, 24, "d1.txt");
		//Grass
		Rectangle[] grass207 = new Rectangle[1];
		grass207[0] = new Rectangle(49*3, 141*3, 132*3, 141*3);
		Location r207 = new Location("207.png", collisions207, buildings207, people207, grass207);
	
		// 203
		Rectangle[] collisions203 = new Rectangle[15];
		collisions203[0] = new Rectangle(0, 0, 82*3, 260*3);
		collisions203[1] = new Rectangle(82*3, 0*3, 894*3, 86*3);
		collisions203[2] = new Rectangle(82*3, 86*3, 227*3, 15*3);
		collisions203[3] = new Rectangle(309*3, 86*3, 79*3, 44*3);
		collisions203[4] = new Rectangle(388*3, 86*3, 100*3, 54*3);
		collisions203[5] = new Rectangle(906*3, 86*3, 14*3, 44*3);
		collisions203[6] = new Rectangle(920*3, 86*3, 56*3, 282*3);
		collisions203[7] = new Rectangle(906*3, 163*3, 14*3, 97*3);
		collisions203[8] = new Rectangle(614*3, 260*3, 307*3, 108*3);
		collisions203[9] = new Rectangle(394*3, 296*3, 219*3, 72*3);
		collisions203[10] = new Rectangle(0, 320*3, 394*3, 48*3);
		collisions203[11] = new Rectangle(155*3, 144*3, 26*3, 87*3);
		collisions203[12] = new Rectangle(241*3, 144*3, 49*3, 81*3);
		collisions203[13] = new Rectangle(462*3, 206*3, 86*3, 36*3);
		collisions203[14] = new Rectangle(796*3, 187*3, 27*3, 37*3);
		// Buildings -----
		Building[] buildings203 = new Building[0];
		//-----
		//People
		Person[] people203 = new Person[0];
		// people10[0] = new Person(new Rectangle(665, 922, 51, 72), "down", "nurse.png", 17, 24, "d1.txt");
		//Grass
		Rectangle[] grass203 = new Rectangle[4];
		grass203[0] = new Rectangle(90*3, 164*3, 60*3, 72*3);
		grass203[1] = new Rectangle(438*3, 256*3, 144*3, 37*3);
		grass203[2] = new Rectangle(618*3, 94*3, 172*3, 67*3);
		grass203[3] = new Rectangle(680*3, 185*3, 138*3, 42*3);
		Location r203 = new Location("hearthome.png", collisions203, buildings203, people203, null);
	
		// 204
		Rectangle[] collisions204 = new Rectangle[16];
		collisions204[0] = new Rectangle(0, 0, 151*3, 530*3);
		collisions204[1] = new Rectangle(0, 530*3, 24*3, 156*3);
		collisions204[2] = new Rectangle(24*3, 530*3, 35*3, 67*3);
		collisions204[3] = new Rectangle(0, 686*3, 182*3, 136*3);
		collisions204[4] = new Rectangle(213*3, 32*3, 174*3, 64*3);
		collisions204[5] = new Rectangle(386*3, 32*3, 121*3, 404*3);
		collisions204[6] = new Rectangle(353*3, 64*3, 33*3, 63*3);
		collisions204[7] = new Rectangle(208*3, 142*3, 55*3, 83*3);
		collisions204[8] = new Rectangle(263*3, 165*3, 37*3, 55*3);
		collisions204[9] = new Rectangle(208*3, 280*3, 22*3, 24*3);
		collisions204[10] = new Rectangle(208*3, 304*3, 175*3, 32*3);
		collisions204[11] = new Rectangle(361*3, 256*3, 26*3, 48*3);
		collisions204[12] = new Rectangle(353*3, 336*3, 33*3, 48*3);
		collisions204[13] = new Rectangle(204*3, 401*3, 22*3, 35*3);
		collisions204[14] = new Rectangle(226*3, 401*3, 36*3, 241*3);
		collisions204[15] = new Rectangle(263*3, 401*3, 242*3, 429*3);
		// Buildings -----
		Building[] buildings204 = new Building[0];
		//-----
		//People
		Person[] people204 = new Person[0];
		// people10[0] = new Person(new Rectangle(665, 922, 51, 72), "down", "nurse.png", 17, 24, "d1.txt");
		//Grass
		Rectangle[] grass204 = new Rectangle[4];
		grass204[0] = new Rectangle(158*3, 51*3, 47*3, 73*3);
		grass204[1] = new Rectangle(300*3, 165*3, 83*3, 59*3);
		grass204[2] = new Rectangle(59*3, 537*3, 161*3, 104*3);
		grass204[3] = new Rectangle(29*3, 646*3, 71*3, 35*3);
		Location r204 = new Location("204.png", collisions204, buildings204, people204, null);
		
		// 201
		Rectangle[] collisions201 = new Rectangle[12];
		collisions201[0] = new Rectangle(0, 0, 674*3, 127*3);
		collisions201[1] = new Rectangle(674*3, 0, 366*3, 78*3);
		collisions201[2] = new Rectangle(77*3, 129*3, 53*3, 24*3);
		collisions201[3] = new Rectangle(296*3, 129*3, 90*3, 24*3);
		collisions201[4] = new Rectangle(591*3, 129*3, 53*3, 28*3);
		collisions201[5] = new Rectangle(826*3, 116*3, 31*3, 75*3);
		collisions201[6] = new Rectangle(0, 245*3, 130*3, 24*3);
		collisions201[7] = new Rectangle(0, 269*3, 230*3, 83*3);
		collisions201[8] = new Rectangle(331*3, 245*3, 154*3, 25*3);
		collisions201[9] = new Rectangle(297*3, 270*3, 438*3, 83*3);
		collisions201[10] = new Rectangle(734*3, 294*3, 206*3, 58*3);
		collisions201[11] = new Rectangle(940*3, 137*3, 100*3, 215*3);
		// Buildings -----
		Building[] buildings201 = new Building[0];
		//-----
		//People
		Person[] people201 = new Person[0];
		// people10[0] = new Person(new Rectangle(665, 922, 51, 72), "down", "nurse.png", 17, 24, "d1.txt");
		//Grass
		Rectangle[] grass201 = new Rectangle[2];
		grass201[0] = new Rectangle(331*3, 215*3, 154*3, 27*3);
		grass201[1] = new Rectangle(680*3, 89*3, 139*3, 120*3);
		Location r201 = new Location("201.png", collisions201, buildings201, people201, null);
	
		// 202
		Rectangle[] collisions202 = new Rectangle[15];
		collisions202[0] = new Rectangle(0, 0, 162*3, 88*3);
		collisions202[1] = new Rectangle(0, 88*3, 61*3, 264*3);
		collisions202[2] = new Rectangle(162*3, 29*3, 27*3, 59*3);
		collisions202[3] = new Rectangle(179*3, 0, 60*3, 20*3);
		collisions202[4] = new Rectangle(290*3, 0, 60*3, 20*3);
		collisions202[5] = new Rectangle(327*3, 0, 188*3, 88*3);
		collisions202[6] = new Rectangle(515*3, 29*3, 95*3, 452*3);
		collisions202[7] = new Rectangle(61*3, 155*3, 68*3, 58*3);
		collisions202[8] = new Rectangle(129*3, 155*3, 27*3, 34*3);
		collisions202[9] = new Rectangle(61*3, 155*3, 68*3, 58*3);
		collisions202[10] = new Rectangle(243*3, 138*3, 123*3, 139*3);
		collisions202[11] = new Rectangle(129*3, 270*3, 27*3, 84*3); // 
		collisions202[12] = new Rectangle(244*3, 315*3, 121*3, 38*3);
		collisions202[13] = new Rectangle(61*3, 402*3, 288*3, 50*3);
		collisions202[14] = new Rectangle(349*3, 421*3, 65*3, 31*3);
		// Buildings------
		Building[] buildings202 = new Building[0];
		//-----
		//People
		Person[] people202 = new Person[0];
		// people10[0] = new Person(new Rectangle(665, 922, 51, 72), "down", "nurse.png", 17, 24, "d1.txt");
		//Grass
		Rectangle[] grass202 = new Rectangle[4];
		grass202[0] = new Rectangle(81*3, 97*3, 75*3, 53*3);
		grass202[1] = new Rectangle(366*3, 97*3, 55*3, 69*3);
		grass202[2] = new Rectangle(382*3, 190*3, 133*3, 80*3);
		grass202[3] = new Rectangle(61*3, 286*3, 68*3, 116*3);
		Location r202 = new Location("hearthome.png", collisions203, buildings203, people203, null);
	}
	
	public void hello()
	{
		Gate gate11 = new Gate(0, T208.getBG().getWidth()-screenWidth, T208.getBG().getHeight()-screenHeight, heartHome, new Rectangle(283, 1973, 54, 109), T208, new Rectangle(3021, 975, 52, 93));
		twinLeaf.addGate(gate11);
		T208.addGate(reverseGate(gate11, 0, heartHome.getBG().getHeight()-screenHeight));		
	}
	
	public Gate reverseGate(Gate g, int camX, int camY)
	{
		int edge = 0;
		if(g.getAxis() == 0)
			edge = 1;
		else if(g.getAxis() == 1)
			edge = 0;
		else if(g.getAxis() == 2)
			edge = 3;
		else 
			edge = 2;

		return new Gate(edge, camX, camY, g.getL2(), g.getR2(), g.getL1(), g.getR1());
	}
	
	public static void main(String[] args)
	{
		ArrayList<Integer> hello = new ArrayList<>();
		hello.add(1);
		System.out.println(hello.get(10));
	}
	
	

}
