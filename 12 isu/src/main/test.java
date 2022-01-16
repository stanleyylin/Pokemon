package main;

import java.io.IOException;

import java.awt.*;
import java.awt.image.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
import entity.Player;
import pokesetup.BlankMon;
import pokesetup.Pokemon;

public class test extends JPanel {
	public test()
	{
		
	}
	
	public static void main(String[] args)
	{
		JFrame frame = new JFrame ("Pokemon");
		test panel = new test();
		frame.setContentPane(panel);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
	}
	
	

}
