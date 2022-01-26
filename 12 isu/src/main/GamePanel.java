// GamePanel displays the player in the map. It allows the player
// to interact with other people and objects.

package main;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.io.*;
import javax.swing.*;

import entity.Moving;
import entity.NPC;
import entity.Nurse;
import entity.Person;
import entity.Player;
import getimages.LoadImage;
import getimages.SpriteSheet;
import map.Building;
import map.Camera;
import map.Gate;
import map.Location;
import pokesetup.Pokemon;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable
{
	private Main main; // Main class
	private Thread gameThread; // thread
	private static KeyHandler keyHandler; // key input
	
	private Dialogue dialogue; // Dialogue screen
	private Camera camera; // Camera to display map
	private Moving moving;
	private final int FPS = 60;
	public final static int screenWidth = 1080;
	public final static int screenHeight = 720;
	private Font font = new Font("Pokemon GB", Font.PLAIN, 22);
	private boolean test = true;
	private boolean interact;
	
	private boolean battling;
	// Player Variables
	
	private Player player = new Player(screenWidth/2-Player.width/2, screenHeight/2-Player.height/2);
	
	public GamePanel(Main main, Player player)
	{	
		this.main = main;
		this.player = player;
		
		// Setting up the panel
		setFocusable(true);
		setPreferredSize(new Dimension(screenWidth, screenHeight));
	    setBackground(Color.BLACK);
	    BufferedImage pokecentre1 = null, house1 = null, pokeMart1 = null, lab = null, playerHouse = null;
	    BufferedImage gym1 = null, gym2 = null, gym3 = null, champ = null;
	    // Loading the images
	    LoadImage loader = new LoadImage();
		
	    // Common Buildings
	    try
		{
			pokecentre1 = loader.loadImage("res/pokecentre.png");
			pokecentre1 = loader.resize(pokecentre1, 739, 550);
			house1 = loader.loadImage("res/house1.jpeg");
			house1 = loader.resize(house1, 582, 436);
			pokeMart1 = loader.loadImage("res/pokemart.png");
			pokeMart1 = loader.resize(pokeMart1, 430, 493);
			lab = loader.loadImage("res/lab.png");
			lab = loader.resize(lab, 769, 513);
			playerHouse = loader.loadImage("res/playerHouse.png");
			playerHouse = loader.resize(playerHouse, 410*2, 218*2);
			gym1 = loader.loadImage("res/gym1.png");
			gym1 = loader.resize(gym1, gym1.getWidth()*3, gym1.getHeight()*3);
			gym2 = loader.loadImage("res/gym2.png");
			gym2 = loader.resize(gym2, gym2.getWidth()*3, gym2.getHeight()*3);
			gym3 = loader.loadImage("res/gym3.png");
			gym3 = loader.resize(gym3, gym3.getWidth()*3, gym3.getHeight()*3);
			champ = loader.loadImage("res/champ.png");
			champ = loader.resize(champ, champ.getWidth()*3, champ.getHeight()*3);
		}
		catch(IOException e) {}
		
	    Rectangle[] centCollision = new Rectangle[5];
	    centCollision[0] = new Rectangle(0, 0, 43, 720);
	    centCollision[1] = new Rectangle(191, 594, 697, 223);
	    centCollision[2] = new Rectangle(385, 225, 300, 126);
	    centCollision[3] = new Rectangle(191, 0, 697, 223);
	    centCollision[4] = new Rectangle(888, 112, 192, 720);
	    Person[] pokeNurse = new Person[1];
	    pokeNurse[0] = new Nurse(new Rectangle(530, 225, 51, 72), "nurse.png", 17, 24, "nurse.txt");
	   
	    Rectangle[] storeCollisions = new Rectangle[6];
	    storeCollisions[0] = new Rectangle(323, 114, 124, 255);
	    storeCollisions[1] = new Rectangle(461, 114, 293, 35);
	    storeCollisions[2] = new Rectangle(572, 150, 182, 55);
	    storeCollisions[3] = new Rectangle(715, 256, 67, 114);
	    storeCollisions[4] = new Rectangle(580, 343, 67, 113);
	    storeCollisions[5] = new Rectangle(720, 456, 68, 115);
	    Person[] merchants = new Person[1];
	    merchants[0] = new Person(new Rectangle(373, 189, 51, 90), "right", "merchant.png", 24, 37, "merchant.txt");
	    
	    Person[] trainer1 = new NPC[1];
	    
	    trainer1[0] = new NPC("Trainer", new Rectangle(561,302,50,63),"down", "trainer1.txt", "trainer2.txt",null);
	    ((NPC) trainer1[0]).addPokemonToParty(new Pokemon ("Geodude","Geodude",9));
	    ((NPC) trainer1[0]).addPokemonToParty(new Pokemon ("Onix","Onix",13));
	    
	    Person[] trainer2 = new NPC[1];
	    trainer2[0] = new NPC("Trainer", new Rectangle(561,302,50,63), "down", "trainer1.txt", "trainer2.txt",null);
	    ((NPC) trainer2[0]).addPokemonToParty(new Pokemon ("Starmie","Starmie",14));
	    ((NPC) trainer2[0]).addPokemonToParty(new Pokemon ("Wartortle","Wartortle",16));

	    Person[] trainer3 = new NPC[1];
	    trainer3[0] = new NPC("Trainer", new Rectangle(561,302,50,63), "down", "trainer1.txt", "trainer2.txt",null);
	    ((NPC) trainer3[0]).addPokemonToParty(new Pokemon ("Kadabra","Kadabra",21));
	    ((NPC) trainer3[0]).addPokemonToParty(new Pokemon ("Slowbro","Slowbro",18));
	    ((NPC) trainer3[0]).addPokemonToParty(new Pokemon ("Snorlax","Snorlax",23));


	    
	    Person[] champs = new NPC[1];
	    champs[0] = new NPC("Champ", new Rectangle(510,223,54,74), "down", "trainer1.txt", "trainer2.txt",null);
	    ((NPC) champs[0]).addPokemonToParty(new Pokemon ("Gyarados","Gyarados",27));
	    ((NPC) champs[0]).addPokemonToParty(new Pokemon ("Aerodactyl","Aerodactyl",31));
	    ((NPC) champs[0]).addPokemonToParty(new Pokemon ("Charizard","Charizard",33));
	    ((NPC) champs[0]).addPokemonToParty(new Pokemon ("Dragonite","Dragonite",36));


	    
	//1. Twinleaf City
	    Rectangle[] collisions1 = new Rectangle[12];
	    collisions1[0] = new Rectangle(0,0,520,120);
	    collisions1[1] = new Rectangle(0,0,255,1131);
	    collisions1[2] = new Rectangle(0,975,1470,180);
	    collisions1[3] = new Rectangle(960,0,540,150);
	    collisions1[4] = new Rectangle(1224,0,300,1116);
	    collisions1[5] = new Rectangle(360,210,255,195);
	    collisions1[6] = new Rectangle(900,270,185,165);
	    collisions1[7] = new Rectangle(390,630,185,165);
	    collisions1[8] = new Rectangle(885,570,255,195);
	    collisions1[9] = new Rectangle(600,340,45,60);
	    collisions1[10] = new Rectangle(840,720,45,60);
	    collisions1[11] = new Rectangle(720,480,75,50);

	    Building[] buildings1 = new Building[4];
	    Rectangle[] houseCollisions = new Rectangle[8];
	    houseCollisions[0] = new Rectangle(0, 143, 184, 411);
	    houseCollisions[1] = new Rectangle(184, 143, 290, 39);
	    houseCollisions[2] = new Rectangle(297, 191, 116, 80);
	    houseCollisions[3] = new Rectangle(474, 152, 461, 108);
	    houseCollisions[4] = new Rectangle(881, 260, 54, 195);
	    houseCollisions[5] = new Rectangle(141, 534, 188, 40);
	    houseCollisions[6] = new Rectangle(474, 534, 131, 40);
	    houseCollisions[7] = new Rectangle(605, 521, 333, 34);
	    Person[] mom = new Person[1];
	    mom[0] = new Person(new Rectangle(378, 280, 48, 54), "down", "mom.png", 1, 58, 66, "mom.txt");

		buildings1[0] = new Building(new Rectangle(390, 411, 45, 45), new Rectangle(327, 522, 147, 52), mom, houseCollisions, playerHouse);
		buildings1[1] = new Building(new Rectangle(930, 390, 50, 60), new Rectangle(540, 543, 5, 1), new Person[0], new Rectangle[0], house1);
		buildings1[2] = new Building(new Rectangle(450, 780, 45, 30), new Rectangle(540, 543, 5, 1), new Person[0], new Rectangle[0], house1);
		buildings1[3] = new Building(new Rectangle(905, 780, 45, 30), new Rectangle(540, 543, 5, 1), new Person[0], new Rectangle[0], house1);

	    Person[] people1 = new Person[1];
		people1[0] = new Person(new Rectangle(665, 922, 51, 72), "down", "nurse.png", 17, 24, "d1.txt");
		
	    Location twinLeaf = new Location("twinleaf2.png",collisions1,buildings1,people1,null);
	    
	    
	//2.Sandgem City
	    
	    Rectangle[] collisions2 = new Rectangle[14];
	    collisions2[0] = new Rectangle(0,0,930,360);
	    collisions2[1] = new Rectangle(0,585,255,1025);
	    collisions2[2] = new Rectangle(0,0,510,390);
	    collisions2[3] = new Rectangle(1218,0,189,105);
	    collisions2[4] = new Rectangle(1410,0,300,1200);
	    collisions2[5] = new Rectangle(1230,960,500,500);
	    collisions2[6] = new Rectangle(285,1011,669,500);
	    collisions2[7] = new Rectangle(690,375,300,60);
	    collisions2[8] = new Rectangle(1215,195,400,180);
	    collisions2[9] = new Rectangle(276,675,180,185);
	    collisions2[10] = new Rectangle(684,640,267,210);
	    collisions2[11] = new Rectangle(654,819,36,46);
	    collisions2[12] = new Rectangle(1032,816,36,49);
	    collisions2[13] = new Rectangle(1170,372,36,46);


	    Building[] buildings2 = new Building[5];
		buildings2[0] = new Building(new Rectangle(300, 890, 45, 28), new Rectangle(540, 543, 5, 1), new Person[0], new Rectangle[0], house1);
		buildings2[1] = new Building(new Rectangle(345, 399, 45, 28), new Rectangle(520, 580, 5, 1), new Person[0], new Rectangle[0], lab);
		buildings2[2] = new Building(new Rectangle(780, 420, 45, 34), new Rectangle(540, 543, 5, 1), pokeNurse, centCollision, pokecentre1);
		buildings2[3] = new Building(new Rectangle(419*3, 125*3, 26*3, 19*3), new Rectangle(435, 585, 18, 1), merchants, storeCollisions, pokeMart1); //DOESNT WORK
		buildings2[4] = new Building(new Rectangle(738, 900, 45, 28), new Rectangle(540, 543, 5, 1), new Person[0], new Rectangle[0], house1);

	    Person[] people2 = new Person[0];
		

	    Location sandGem = new Location("sandgem.png",collisions2,buildings2,people2,null);

	 //3. Jubilife City
	    Rectangle[] collisions3 = new Rectangle[38];
	    collisions3[0] = new Rectangle(0,0,143*3,240*3);
	    collisions3[1] = new Rectangle(143*3,0*3,300*3,112*3);
	    collisions3[2] = new Rectangle(185*3,111*3,139*3,53*3);
	    collisions3[3] = new Rectangle(185*3,162*3,15*3,32*3);
	    collisions3[4] = new Rectangle(248*3,162*3,13*3,26*3);
	    collisions3[5] = new Rectangle(310*3,162*3,13*3,26*3);
	    collisions3[6] = new Rectangle(369*3,114*3,79*3,122*3);
	    collisions3[7] = new Rectangle(447*3,0*3,252*3,91*3);
	    collisions3[8] = new Rectangle(521*3,91*3,136*3,83*3);
	    collisions3[9] = new Rectangle(786*3,0*3,270*3,91*3);
	    collisions3[10] = new Rectangle(808*3,91*3,248*3,97*3);
	    collisions3[11] = new Rectangle(808*3,188*3,79*3,52*3);
	    collisions3[12] = new Rectangle(921*3,188*3,135*3,72*3);
	    collisions3[13] = new Rectangle(921*3,306*3,134*3,288*3);
	    collisions3[14] = new Rectangle(808*3,323*3,63*3,33*3);
	    collisions3[15] = new Rectangle(808*3,356*3,12*3,24*3);
	    collisions3[16] = new Rectangle(845*3,356*3,26*3,18*3);
	    collisions3[17] = new Rectangle(808*3,429*3,79*3,48*3);
	    collisions3[18] = new Rectangle(808*3,477*3,25*3,17*3);
	    collisions3[19] = new Rectangle(862*3,477*3,25*3,20*3);
	    collisions3[20] = new Rectangle(858*3,956*3,198*3,122*3);
	    collisions3[21] = new Rectangle(808*3,718*3,248*3,80*3);
	    collisions3[22] = new Rectangle(0*3,718*3,693*3,80*3);
	    collisions3[23] = new Rectangle(0*3,320*3,153*3,392*3);
	    collisions3[24] = new Rectangle(155*3,325*3,87*3,296*3);
	    collisions3[25] = new Rectangle(170*3,387*3,64*3,42*3);
	    collisions3[26] = new Rectangle(243*3,308*3,42*3,250*3);
	    collisions3[27] = new Rectangle(285*3,308*3,49*3,213*3);
	    collisions3[28] = new Rectangle(334*3,308*3,104*3,186*3);
	    collisions3[29] = new Rectangle(438*3,288*3,64*3,63*3);
	    collisions3[30] = new Rectangle(438*3,356*3,83*3,154*3);
	    collisions3[31] = new Rectangle(547*3,433*3,92*3,61*3);
	    collisions3[32] = new Rectangle(616*3,408*3,77*3,61*3);
	    collisions3[33] = new Rectangle(673*3,464*3,21*3,39*3);
	    collisions3[34] = new Rectangle(443*3,538*3,137*3,175*3);
	    collisions3[35] = new Rectangle(600*3,550*3,73*3,77*3);
	    collisions3[36] = new Rectangle(155*3,614*3,34*3,29*3);
	    collisions3[37] = new Rectangle(222*3,619*3,22*3,34*3);

	    Building[] buildings3 = new Building[5];
		buildings3[0] = new Building(new Rectangle(199*3, 629*3, 21*3, 23*3), new Rectangle(540, 543, 5, 1), new Person[0], new Rectangle[0], house1); // bottom left house
		buildings3[1] = new Building(new Rectangle(346*3, 504*3, 33*3, 30*3), new Rectangle(520, 580, 100, 10), trainer1, new Rectangle[0], gym1); // gym
		buildings3[2] = new Building(new Rectangle(840*3, 484*3, 31*3, 28*3), new Rectangle(540, 543, 5, 1), pokeNurse, centCollision, pokecentre1); // pokecenter
		buildings3[3] = new Building(new Rectangle(822*3, 360*3, 21*3, 21*3), new Rectangle(435, 585, 18, 1), merchants, storeCollisions, pokeMart1); // mart
		buildings3[4] = new Building(new Rectangle(629*3, 629*3, 22*3, 22*3), new Rectangle(540, 543, 5, 1), new Person[0], new Rectangle[0], house1); // house
		buildings3[4] = new Building(new Rectangle(837*3, 222*3, 22*3, 22*3), new Rectangle(540, 543, 5, 1), new Person[0], new Rectangle[0], house1); // house
	   
		Person[] people3 = new Person[0];

	    Location jubilife = new Location("jubilife.png",collisions3,buildings3,people3,null);
	  
	    
	 //4. Oreburgh City
	    
	    Rectangle[] collisions4 = new Rectangle[13];
	    collisions4[0] = new Rectangle(350,0,1560,400);
	    collisions4[1] = new Rectangle(0,0,330,450);
	    collisions4[2] = new Rectangle(0,690,426,456);
	    collisions4[3] = new Rectangle(426,1065,2500,2500);
	    collisions4[4] = new Rectangle(2385,696,2500,2500);
	    collisions4[5] = new Rectangle(259*3,226*3,62*3,43*3);
	    collisions4[6] = new Rectangle(1740,489,180,240);
	    collisions4[7] = new Rectangle(2760,465,1000,1000);
	    collisions4[8] = new Rectangle(2130,222,1000,354);
	    collisions4[9] = new Rectangle(2265,0,1000,195);
	    collisions4[10] = new Rectangle(2604,600,1000,1000);
	    collisions4[11] = new Rectangle(2175,708,1000,70);
	    collisions4[12] = new Rectangle(370*3,210*3,107*3,59*3);
	    Building[] buildings4 = new Building[3];
	    
		buildings4[0] = new Building(new Rectangle(1230, 807, 30*3, 32*3), new Rectangle(520, 580, 100, 10), trainer2, new Rectangle[0], gym2);//should b gym
		buildings4[1] = new Building(new Rectangle(462*3, 151*3, 21*3, 20*3), new Rectangle(540, 543, 15, 1), pokeNurse, centCollision, pokecentre1);
		buildings4[2] = new Building(new Rectangle(1380, 1395, 45, 50), new Rectangle(435, 585, 18, 1), new Person[0], new Rectangle[0], pokeMart1);

	    Person[] people4 = new Person[0];
		
		Location oreburg = new Location("oreburgh.png",collisions4,buildings4,people4,null);
	// 5: Floarama City
		
		Rectangle[] collisions5 = new Rectangle[9];
		collisions5[0] = new Rectangle(0,900,1545,390);
		collisions5[1] = new Rectangle(1440,1140,400,1700);
		collisions5[2] = new Rectangle(620,2283,400,800);
		collisions5[3] = new Rectangle(0,2280,440,240);
		collisions5[4] = new Rectangle(678,2016,255,180);
		collisions5[5] = new Rectangle(1089,2004,219,185);
		collisions5[6] = new Rectangle(480,1590,219,185);
		collisions5[7] = new Rectangle(1110,1710,180,165);
		collisions5[8] = new Rectangle(810,1410,270,210);

	    Building[] buildings5 = new Building[3];
		buildings5[0] = new Building(new Rectangle(930, 1605, 45, 60), new Rectangle(540, 543, 5, 1), champs, new Rectangle[0], champ);//should b champion battle
		buildings5[1] = new Building(new Rectangle(750, 2175, 45, 34), new Rectangle(540, 543, 5, 1), pokeNurse, centCollision, pokecentre1);
		buildings5[2] = new Building(new Rectangle(380*3, 611*3, 24*3, 21*3), new Rectangle(435, 585, 18, 1), merchants, storeCollisions, pokeMart1);

	    Person[] people5 = new Person[0];

		Location floaroma = new Location("floaroma.png",collisions5,buildings5,people5,null);

		
		
	// 6. Hearthome City:
		// Collisions
		// Use canva, multiply by 3, subtract the height by 30 and width by 10 or so
		Rectangle[] collisions10 = new Rectangle[18];
		collisions10[0] = new Rectangle(0, 0, 366, 1911); // trees
		collisions10[1] = new Rectangle(366, 0, 288, 396); // top left building
		collisions10[2] = new Rectangle(656, 0, 480, 648); // trees next to ^
		collisions10[3] = new Rectangle(1153, 0, 1297, 396); // trees next to ^
		collisions10[4] = new Rectangle(2433, 0, 260, 415); // right most building
		collisions10[5] = new Rectangle(2727, 0, 377, 1877); // trees below right ^
		collisions10[6] = new Rectangle(724, 732, 228, 181); // pokecentre
		collisions10[7] = new Rectangle(0, 651, 284, 1438); // leftmost building
		collisions10[8] = new Rectangle(0, 1635, 1460, 210); // trees on left above gate
		collisions10[9] = new Rectangle(0, 2097, 3100, 1000); // bnottom trees
		collisions10[10] = new Rectangle(2070, 1665, 1000, 180); // chairs onr ightv
		collisions10[11] = new Rectangle(1860,1170,540,245); // two right houses
		collisions10[12] = new Rectangle(1095,1215,450,195); // two middle houses
		collisions10[13] = new Rectangle(450,1110,330,300); // big left house 
		collisions10[14] = new Rectangle(720,750,495,180); // two left (pokecentre) houses
		collisions10[15] = new Rectangle(1740,720,225,165); // big middle house
		collisions10[16] = new Rectangle(1140,540,450,45); // coliseum
		collisions10[17] = new Rectangle(2280,720,360,210); // gym


		// Buildings -----
		Building[] buildings10 = new Building[4];
		buildings10[0] = new Building(new Rectangle(1052, 882, 62, 56), new Rectangle(540, 543, 5, 1), new Person[0], new Rectangle[0], house1);
		buildings10[1] = new Building(new Rectangle(818, 894, 62, 56), new Rectangle(535, 567, 81, 32), pokeNurse, centCollision, pokecentre1);
		buildings10[2] = new Building(new Rectangle(1380, 1395, 45, 50), new Rectangle(435, 585, 18, 1), merchants, storeCollisions, pokeMart1);
		buildings10[3] = new Building(new Rectangle(2430, 915, 45, 60), new Rectangle(540, 543, 5, 1), trainer3, new Rectangle[0], gym3);//should b GYM

		Person[] people10 = new Person[1];
		people10[0] = new Person(new Rectangle(665, 922, 51, 72), "down", "nurse.png", 17, 24, "d1.txt");

		Location heartHome = new Location("hearthome.png", collisions10, buildings10, people10, null);
		
		// ROUTES
	// Route 208
		Rectangle[] collisions208 = new Rectangle[12];
		collisions208[0] = new Rectangle(0*3, 0*3, 217*3, 251*3);
		collisions208[1] = new Rectangle(0*3, 241*3, 108*3, 63*3);
		collisions208[2] = new Rectangle(0*3, 297*3, 551*3, 151*3);
		collisions208[3] = new Rectangle(217*3, 0*3, 807*3, 131*3);
		collisions208[4] = new Rectangle(257*3, 182*3, 84*3, 79*3);
		collisions208[5] = new Rectangle(389*3, 131*3, 166*3, 130*3);
		collisions208[6] = new Rectangle(770*3, 131*3, 254*3, 65*3);
		collisions208[7] = new Rectangle(944*3, 196*3, 80*3, 106*3);
		collisions208[8] = new Rectangle(550*3, 387*3, 152*3, 23*3);
		collisions208[9] = new Rectangle(550*3, 411*3, 474*3, 37*3);
		collisions208[10] = new Rectangle(865*3, 364*3, 159*3, 46*3);
		collisions208[11] = new Rectangle(770*3, 196*3, 31*3, 36*3);
		Building[] buildings208 = new Building[1];
		buildings208[0] = new Building(new Rectangle(2726, 574, 62, 56), new Rectangle(540, 543, 81, 32), new Person[0], new Rectangle[0], house1);

		Person[] people208 = new Person[2];
		people208[0] = new NPC("hiker", new Rectangle(570*3,237*3,45,75),"down", "npc1.txt", "npc2.txt",null);
		((NPC) people208[0]).generateParty(2,14,17);
		people208[1] = new NPC("Jett", new Rectangle(880*3,295*3,45,75),"left", "npc1.txt", "npc2.txt",null);
		((NPC) people208[1]).generateParty(3,13,17);


		Rectangle[] grass208 = new Rectangle[1];
		grass208[0] = new Rectangle(2007, 738, 480, 471);
		
		Location r208 = new Location("208.png", collisions208, buildings208, people208, grass208);
	
		
		
	// Route 207
		Rectangle[] collisions207 = new Rectangle[11];
		collisions207[0] = new Rectangle(0, 0, 220*3, 52*3);

		collisions207[1] = new Rectangle(0, 61*3, 42*3, 355*3);
		collisions207[2] = new Rectangle(42*3, 94*3, 160*3, 13*3);
		collisions207[3] = new Rectangle(320*3, 0, 567*3, 85*3);
		collisions207[4] = new Rectangle(888*3, 0, 227*3, 52*3);
		collisions207[5] = new Rectangle(135*3, 888*3, 136*3, 270*3);
		collisions207[6] = new Rectangle(194*3, 106*3, 13*3, 92*3);
		
		collisions207[7] = new Rectangle(207*3, 195*3, 75*3, 13*3);
		
		collisions207[8] = new Rectangle(42*3, 285*3, 100*3, 131*3);
		collisions207[9] = new Rectangle(285*3, 343*3, 779*3, 73*3);
		collisions207[10] = new Rectangle(752*3, 270*3, 272*3, 73*3);
		
		Building[] buildings207 = new Building[0];

		Person[] people207 = new Person[2];
		people207[0] = new NPC("hiker", new Rectangle(655*3,135*3,45,75),"up", "npc1.txt", "npc2.txt.",null);
		((NPC) people207[0]).generateParty(2,13,16);
		people207[1] = new NPC("scout", new Rectangle(655*3,80*3,45,75),"down", "npc1.txt", "npc2.txt",null);
		((NPC) people207[1]).generateParty(2,14,16);

		Rectangle[] grass207 = new Rectangle[1];
		grass207[0] = new Rectangle(49*3, 141*3, 132*3, 141*3);
		Location r207 = new Location("207.png", collisions207, buildings207, people207, grass207);
	
	//  Route 203
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

		Building[] buildings203 = new Building[0];

		Person[] people203 = new Person[3];
		people203[0] = new NPC("joanna", new Rectangle(250*3,260*3,45,75),"down", "npc1.txt", "npc2.txt",null);
		((NPC) people203[0]).generateParty(2,9,13);
		people203[1] = new NPC("ken", new Rectangle(500*3,240*3,45,75),"down", "npc1.txt", "npc2.txt",null);
		((NPC) people203[1]).generateParty(2,10,14);
		people203[2] = new NPC("quavo", new Rectangle(695*3,225*3,45,75),"left", "npc1.txt", "npc2.txt",null);
		((NPC) people203[2]).generateParty(2,11,15);


		Rectangle[] grass203 = new Rectangle[4];
		grass203[0] = new Rectangle(90*3, 164*3, 60*3, 72*3);
		grass203[1] = new Rectangle(438*3, 256*3, 144*3, 37*3);
		grass203[2] = new Rectangle(618*3, 94*3, 172*3, 67*3);
		grass203[3] = new Rectangle(680*3, 185*3, 138*3, 42*3);
		
		Location r203 = new Location("203.png", collisions203, buildings203, people203, grass203);
	
		
		
	//  Route 204
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

		Building[] buildings204 = new Building[0];
		
		Person[] people204 = new Person[5];
		people204[0] = new NPC("lass", new Rectangle(190*3,672*3,45,75),"right", "npc1.txt", "npc2.txt",null);
		((NPC) people204[0]).generateParty(2,9,13);
		people204[1] = new NPC("sam", new Rectangle(205*3,440*3,45,75),"left", "npc1.txt", "npc2.txt",null);
		((NPC) people204[1]).generateParty(2,9,13);
		people204[2] = new NPC("sam", new Rectangle(252*3,340*3,45,75),"right", "npc1.txt", "npc2.txt",null);
		((NPC) people204[2]).generateParty(3,14,16);
		people204[3] = new NPC("michelle", new Rectangle(270*3,222*3,45,75),"down", "npc1.txt", "npc2.txt",null);
		((NPC) people204[3]).generateParty(3,15,18);
		people204[4] = new NPC("dababy", new Rectangle(245*3,67*3,45,75),"down", "npc1.txt", "npc2.txt",null);
		((NPC) people204[4]).generateParty(3,17,21);

		Rectangle[] grass204 = new Rectangle[4];
		grass204[0] = new Rectangle(158*3, 51*3, 47*3, 73*3);
		grass204[1] = new Rectangle(300*3, 165*3, 83*3, 59*3);
		grass204[2] = new Rectangle(59*3, 537*3, 161*3, 104*3);
		grass204[3] = new Rectangle(29*3, 646*3, 71*3, 35*3);
		
		Location r204 = new Location("204.png", collisions204, buildings204, people204, null);
		
		
		
	//  Route 201
		Rectangle[] collisions201 = new Rectangle[13];
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
		collisions201[12] = new Rectangle(78*3, 179*3, 53*3, 36*3);
		Building[] buildings201 = new Building[0];

		Person[] people201 = new Person[2];
		people201[0] = new NPC ("gary", new Rectangle(710*3, 235*3,60,75),"left","So this is your first battle eh..?","Wow, you're a natural!",null);
		((NPC) people201[0]).generateParty(1,5,8);
		people201[1] = new NPC("reginald", new Rectangle(920*3,135*3,45,76),"left","npc1.txt","npc2.txt",null);
		((NPC) people201[1]).generateParty(1,5,8);


		Rectangle[] grass201 = new Rectangle[2];
		grass201[0] = new Rectangle(331*3, 215*3, 154*3, 27*3);
		grass201[1] = new Rectangle(680*3, 89*3, 139*3, 120*3);
		
		Location r201 = new Location("201.png", collisions201, buildings201, people201, grass201);
	
	//  Route 202
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
		collisions202[11] = new Rectangle(129*3, 270*3, 27*3, 84*3); 
		collisions202[12] = new Rectangle(244*3, 315*3, 121*3, 38*3);
		collisions202[13] = new Rectangle(61*3, 402*3, 288*3, 50*3);
		collisions202[14] = new Rectangle(349*3, 421*3, 65*3, 31*3);
		
		Building[] buildings202 = new Building[0];

		Person[] people202 = new Person[3];
		people202[0] = new NPC("Joe", new Rectangle(370*3,260*3,45,75), "down", "npc1.txt", "npc2.txt",null);
		((NPC) people202[0]).generateParty(2,6,11);
		people202[1]= new NPC ("Jon", new Rectangle(125*3,200*3,45,75), "down", "npc1.txt", "npc2.txt",null);
		((NPC) people202[1]).generateParty(2,6,11);
		people202[2] = new NPC("Mikey", new Rectangle(430*3,85*3,45,75), "down", "npc1.txt", "npc2.txt",null);
		((NPC) people202[2]).generateParty(2,7,13);

		Rectangle[] grass202 = new Rectangle[4];
		grass202[0] = new Rectangle(81*3, 97*3, 75*3, 53*3);
		grass202[1] = new Rectangle(366*3, 97*3, 55*3, 69*3);
		grass202[2] = new Rectangle(382*3, 190*3, 133*3, 80*3);
		grass202[3] = new Rectangle(61*3, 286*3, 68*3, 116*3);
		
		Location r202 = new Location("202.png", collisions202, buildings202, people202, grass202);

		// GATES
		Gate gate1 = new Gate(0,true,112*3,true,250*3,160*3,new Rectangle(219*3,0,60*3,40*3),r201);
		twinLeaf.addGate(gate1);
		Gate gate1b = new Gate(60*3,true,0,true,188*3,97*3,new Rectangle(229*3,310*3,67*3,42*3),twinLeaf);
		r201.addGate(gate1b);
		
		Gate gate2 = new Gate(0,true,0,true,52*3,171*3,new Rectangle(1005*3,95*3,35*3,46*3),sandGem);
		r201.addGate(gate2);
		Gate gate2b = new Gate(2000,false,0,false,265*3,100*3,new Rectangle(0,151*3,31*3,37*3),r201);
		sandGem.addGate(gate2b);
		
		Gate gate3 = new Gate(250*3,true,212*3,true,192*3,167*3,new Rectangle(317*3,0,90*3,31*3),r202);
		sandGem.addGate(gate3);
		Gate gate3b = new Gate(137*3,true,0,true,209*3,97*3,new Rectangle(418*3,415*3,90*3,37*3),sandGem);
		r202.addGate(gate3b);
		
		Gate gate4 = new Gate(567*3,false,558*3,true,173*3,145*3,new Rectangle(240*3,0,59*3,27*3),jubilife);
		r202.addGate(gate4);
		Gate gate4b = new Gate(86*3,false,0,true,171*3,90*3,new Rectangle(691*3,759*3,110*3,39*3),r202);
		jubilife.addGate(gate4b);
		
		Gate gate5 = new Gate(0,true,132*3,true,74*3,190*3,new Rectangle(984*3,266*3,72*3,48*3),r203);
		jubilife.addGate(gate5);
		Gate gate5b = new Gate(627*3,true,104*3,false,290*3,165*3,new Rectangle(0,274*3,70*3,42*3),jubilife);
		r203.addGate(gate5b);
		
		Gate gate6 = new Gate(0,true,30*3,true,62*3,170*3,new Rectangle(905*3,132*3,18*3,24*3),oreburg);
		r203.addGate(gate6);
		Gate gate6b = new Gate(615*3,true,0,true,232*3,132*3,new Rectangle(0,170*3,19*3,24*3),r203);
		oreburg.addGate(gate6b);
		
		Gate gate7 = new Gate(0,true,176*3,true,180*3,133*3,new Rectangle(651*3,0*3,43*3,80*3),r207);
		oreburg.addGate(gate7);
		Gate gate7b = new Gate(497*3,true,0,true,180*3,150*3,new Rectangle(153*3,375*3,70*3,42*3),oreburg);
		r207.addGate(gate7b);
			
		Gate gate8 = new Gate(0,true,209*3,true,170*3,120*3,new Rectangle(866*3,92*3,16*3,40*3),r208);
		r207.addGate(gate8);
		Gate gate8b = new Gate(663*3,true,0,true,133*3,125*3,new Rectangle(105*3,258*3,22*3,30*3),r207);
		r208.addGate(gate8b);
			
		Gate gate9 = new Gate(r208.getBG().getWidth()-screenWidth, true, r208.getBG().getHeight()-screenHeight, true, 120*3, 150*3,new Rectangle(283, 1973, 54, 109), r208);
		heartHome.addGate(gate9);
		Gate gate9b = new Gate(0, true,heartHome.getBG().getHeight()-screenHeight, true,140*3, 150*3, new Rectangle(3021, 975, 52, 93), heartHome);		
		r208.addGate(gate9b);
		
		Gate gate10 = new Gate(0,true,582*3,true,208*3,152*3,new Rectangle(702*3,0,78*3,36*3),r204);
		jubilife.addGate(gate10);
		Gate gate10b = new Gate(568*3,true,0,true,180*3,119*3,new Rectangle(186*3,786*3,67*3,36*3),jubilife);
		r204.addGate(gate10b);
		
		Gate gate11 = new Gate(0,true,568*3,true,177*3,120*3,new Rectangle(158*3,0,50*3,25*3),floaroma);
		r204.addGate(gate11);
		Gate gate11b = new Gate(0,true,0,true,180*3,123*3,new Rectangle(135*3,743*3,83*3,65*3),r204);
		floaroma.addGate(gate11b);
		
		// Functionalities
		dialogue = new Dialogue(this, player);
		dialogue.setBounds(8, 8, 1064, 172);
		camera = new Camera(twinLeaf,50,200);   
		moving = new Moving(this, player, camera);
	    keyHandler = new KeyHandler(this, player, moving);
	    interact = false;
	    gameThread = new Thread(this);
		gameThread.start();
	}

	// this is da threading?
	public void run() 
	{
		double paintInterval = 1000000000/FPS;
		double nextPaint = System.nanoTime() + paintInterval;
		
		while(true) 
		{
			update();
			repaint();
			
			//add start battle if bool == true here
			
			try
			{
				double remaining = nextPaint - System.nanoTime();
				remaining /= 1000000;
				
				if(remaining < 0)
					remaining = 0;
					
				Thread.sleep((long) remaining);
				
				nextPaint += paintInterval;
			}
			catch(InterruptedException e) {}
		}
	}
	
	public void wildEncounter(NPC n, boolean b) {
		main.startBattle(n, b);
	}
	
	public void update() 
	{
		moving.changeSprite();
		if(player.direction.equals("left") || player.direction.equals("right"))
		{
			if(camera.getBuilding() == null && !camera.getLocation().getEdgeReachedX())
				moving.moveCameraX();
			else
				moving.movePlayerX();
		}
		else if(player.direction.equals("up") || player.direction.equals("down"))
		{
			if(camera.getBuilding() == null && !camera.getLocation().getEdgeReachedY())
				moving.moveCameraY();
			else
				moving.movePlayerY();
		}
	}
	
//	public Gate reverseGate(Gate g, int camX, int camY)
//	{
//		int edge = 0;
//		if(g.getAxis() == 0)
//			edge = 1;
//		else if(g.getAxis() == 1)
//			edge = 0;
//		else if(g.getAxis() == 2)
//			edge = 3;
//		else 
//			edge = 2;
//
//		return new Gate(edge, camX, camY, g.getL2(), g.getR2(), g.getL1(), g.getR1(),true);
//	}
	public void interactMessage(Graphics g)
	{
		g.setFont(font);
		g.setColor(Color.WHITE);
		g.drawString("Press X to interact!", 375, 623);
	}
	
	public void startNPCBattle(NPC npc)
	{
		main.startBattle(npc, false);
	}
	
	public void showDialogue()
	{
		this.add(dialogue);
		dialogue.startDialogue(player.getTalkingTo());
	}
	public void hideDialogue()
	{
		Container parent = dialogue.getParent();
		parent.remove(dialogue);
		parent.revalidate();
		parent.repaint();
	}
	
	public void openBox()
	{
		main.openBox();
	}
	public void openMart()
	{
		main.openMart();
	}
	public void openBag()
	{
		main.openBag(0);
	}
	public void openMainMenu()
	{
		main.openMainMenu();
	}
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		if(test)
		{
			g2.drawString("test", 0, 0);
			test = false;
		}

		camera.draw(g2);
		moving.draw(g2);
		
		if(interact)
		{
			interactMessage(g);
		}
//		if(!player.getWon())
//		{
//			interactMessage(g);
//			player.setInteracting(false);
//			hideDialogue();
//			keyHandler.setXPressed(false);
//		}
	}
	
	// Getters and Setters
	public KeyHandler getKeyHandler()
	{
		return keyHandler;
	}
	public Moving getMoving()
	{
		return moving;
	}
	public Dialogue getDialogue()
	{
		return dialogue;
	}
	public boolean getInteract()
	{
		return interact;
	}
	public Player getPlayer()
	{
		return player;
	}
	public void setTrainer()
	{
		main.setTrainer();
	}
	public void setInteract(boolean set)
	{
		interact = set;
	}
	
//	public static void main(String[] args)
//	{
//		JFrame frame = new JFrame ("Pokemon");
//		GamePanel panel = new GamePanel();
//		
//		frame.add(panel);
//		frame.addKeyListener(keyHandler);
//		frame.setVisible(true);
//		frame.setResizable(false);
//		frame.pack();
//		frame.setLocationRelativeTo(null);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	}

}
