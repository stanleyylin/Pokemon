package main;

import javax.swing.JFrame;

public class Main {
	static JFrame frame;
	static Driver2 mainGame;
	static MainMenu menu;
	static KeyHandler keyHandler;
	public static void loadGame()
	{
		keyHandler = new KeyHandler(mainGame.getPlayer());
		frame.setContentPane(mainGame);
		frame.setVisible(true);
		frame.pack();
		frame.addKeyListener(keyHandler);
	}

	public static void main(String[] args) {
		frame = new JFrame ("Pokemon: Wong Edition");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainGame = new Driver2();
		menu = new MainMenu();
		frame.setContentPane(menu);
		frame.setVisible(true);
		frame.pack();
		frame.setLocationRelativeTo(null);
	}

}
