/**********************************
 * Car Dodger
 * Programmed by: Samuel Scheffler
 **********************************/


package game;

import game.GamePanel;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

public class GameTester
{
	public static void main(String [] args)
	{
		Dimension size=new Dimension(300, 695);
		BufferedImage background=new BufferedImage((int)size.getWidth(), (int)size.getHeight(), BufferedImage.TYPE_INT_ARGB);
		
		GamePanel game=new GamePanel(size);
		game.getRender(background.getGraphics());
		MainMenu menu=new MainMenu(size, background);
		
		game.frame.setResizable(false);
		game.frame.setTitle("Car Crasher Alpha");
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		menu.setFocusable(true);
		menu.requestFocus();
		game.frame.add(menu);
		game.frame.add(game);
		game.frame.setVisible(true);
		
		menu.waitTillStart();
		
		game.setFocusable(true);
		game.requestFocus();
		menu.setVisible(false);
		
		game.render();
		
		game.start();
		
		System.out.println("End Game");
	}
}

