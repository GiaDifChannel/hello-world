package it.giadif.supermario;

import javax.swing.JFrame;

public class Main {
	
	public static final JFrame frame = new JFrame();

	public static void main(String[] args)
	{
		Game game = new Game(400, 200, 4);
		frame.add(game);
		frame.setResizable(false);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		game.start();
	}

}
