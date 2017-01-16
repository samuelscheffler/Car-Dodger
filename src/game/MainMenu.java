package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Cursor;


public class MainMenu extends JPanel implements Runnable
{
	private static final long serialVersionUID = 1L;
	
	private BufferedImage background;
	private JLabel start;
	private boolean run;
	
	public MainMenu(Dimension size, BufferedImage background)
	{
		super();
		
		setSize(size);
		setLayout(new FlowLayout());
		this.background=new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
		this.background.getGraphics().drawImage(background, 0, 0, null);
		
		start=new JLabel("Start");
		start.setForeground(Color.RED);
		start.setFont(new Font("Arial", Font.BOLD, 48));
		start.addMouseListener(new Listener());

		add(start);
	}
	
	public void paintComponent(Graphics g)
	{
		g.drawImage(background, 0, 0, null);
		g.setColor(new Color(0, 0, 0, 127));
		g.fillRect(0, 0, getWidth(), getHeight());
	}

	public void waitTillStart()
	{
		try{
			run=true;
			Thread t=new Thread(this);
			t.start();
			t.join();
		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}
	
	public void run()
	{
		while(run)
		{
			try{
				Thread.sleep(100);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}
	
	private class Listener implements MouseListener
	{
		public void mouseClicked(MouseEvent e)
		{

		}

		@Override
		public void mousePressed(MouseEvent e) 
		{
			
		}

		@Override
		public void mouseReleased(MouseEvent e) 
		{
			if(e.getSource()==start)
			{
				run=false;
			}			
		}

		@Override
		public void mouseEntered(MouseEvent e) 
		{
			if(e.getSource()==start)
			{
				start.setForeground(Color.YELLOW);
			}
			
			MainMenu.this.getParent().setCursor(new Cursor(Cursor.HAND_CURSOR));
		}

		@Override
		public void mouseExited(MouseEvent e)
		{
			if(e.getSource()==start)
			{
				start.setForeground(Color.RED);
			}	
			
			MainMenu.this.getParent().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
	}
}
