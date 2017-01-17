package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public class GameOver extends JPanel implements Runnable
{
	private static final long serialVersionUID = 1L;
	
	private BufferedImage background;
	private JLabel end;
	private JLabel startOver;
	private Listener listener;
	private boolean run;
	
	public GameOver(Dimension size)
	{
		super();
		
		setSize(size);
		setLayout(new FlowLayout());

		startOver=new JLabel("Start Over");
		startOver.setForeground(Color.RED);
		startOver.setFont(new Font("Arial", Font.BOLD, 48));
		
		end=new JLabel("End Game");
		end.setForeground(Color.RED);
		end.setFont(new Font("Arial", Font.BOLD, 48));
		
		add(startOver);
		add(end);
		
		listener=new Listener();
		
		startOver.addMouseListener(listener);
		end.addMouseListener(listener);
	}
	
	public void paintComponent(Graphics g)
	{
		g.drawImage(background, 0, 0, null);
		g.setColor(new Color(0, 0, 0, 127));
		g.fillRect(0, 0, getWidth(), getHeight());
	}
	
	public void setBackground(BufferedImage background)
	{
		this.background=new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
		this.background.getGraphics().drawImage(background, 0, 0, null);
	}
	
	public void Wait()
	{
		try {
			run=true;
			Thread t=new Thread(this);
			t.start();
			t.join();
		} catch (InterruptedException e) {
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

		public void mousePressed(MouseEvent e)
		{
			
		}

		public void mouseReleased(MouseEvent e)
		{
			if(e.getSource()==startOver)
			{
				run=false;
			}
			
			else if(e.getSource()==end)
			{
				System.exit(0);
			}
		}

		public void mouseEntered(MouseEvent e)
		{
			if(e.getSource()==startOver)
			{
				startOver.setForeground(Color.YELLOW);
			}
			
			else if(e.getSource()==end)
			{
				end.setForeground(Color.YELLOW);
			}
			
			GameOver.this.getParent().setCursor(new Cursor(Cursor.HAND_CURSOR));
		}

		public void mouseExited(MouseEvent e) 
		{
			if(e.getSource()==startOver)
			{
				startOver.setForeground(Color.RED);
			}
			
			else if(e.getSource()==end)
			{
				end.setForeground(Color.RED);
			}
			
			GameOver.this.getParent().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		
	}
}
