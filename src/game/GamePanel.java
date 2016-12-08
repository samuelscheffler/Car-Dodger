package game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;
import cars.CarSpawner;
import cars.PlayerCar;
import movable.Position;
import movable.scene.RoadScene;
import movable.scene.SceneScroller;

public class GamePanel extends Canvas implements Runnable
{
	private static final long serialVersionUID = 1L;
	
	private Listener listener;
	private CarSpawner spawner;
	private PlayerCar playerCar;
	private RoadScene road;
	private SceneScroller scroller;
	private Thread gameLoop;
	
	public JFrame frame;
	
	private volatile boolean run;
	private volatile boolean paused;
	
	private static final int TARGET_FPS=30;
	private static final int REFRESH_SPEED=1000/TARGET_FPS;
	private static final double MOVEMENT_SPEED=(100*(REFRESH_SPEED/1000.0)); //Movement speed is the speed the cars will be traveling.
	private static final double SCROLL_SPEED=(150*(REFRESH_SPEED/1000.0)); //Scroll speed is the speed at which the background will be scrolling.
	private static final int NUM_OF_ENEMY_CARS=5;
	private static final double DISTANCE_BETWEEN_ENEMY_CARS=360; //This is how far away an enemy car has to be before another one is spawned.
	private static final int CAR_WIDTH=60;
	private static final int CAR_HEIGHT=120;
	
	public GamePanel(Dimension size)
	{	
		setSize(size);
		frame=new JFrame();
		frame.setSize(getWidth(), getHeight());
	
		int startXPos=(int)((getBounds().getWidth()/2)-(CAR_WIDTH/2));
		int startYPos=(int)(getBounds().getHeight()-CAR_HEIGHT-30);
	
		Position [] lanePositions={new Position((int)(getWidth()*(1/6.0)-(CAR_WIDTH/2)), -CAR_HEIGHT),
								  new Position((getWidth()/2)-CAR_WIDTH/2, -CAR_HEIGHT),
								  new Position((int)(getWidth()*(5/6.0))-CAR_WIDTH/2, -CAR_HEIGHT)};
			
		playerCar=new PlayerCar(startXPos, startYPos, MOVEMENT_SPEED, getBounds());
		spawner=new CarSpawner(SCROLL_SPEED*2, NUM_OF_ENEMY_CARS, lanePositions, getHeight()+CAR_HEIGHT*2, DISTANCE_BETWEEN_ENEMY_CARS);
		
		road=new RoadScene(getWidth(), getHeight());
		scroller=new SceneScroller(road, SceneScroller.DOWN, SCROLL_SPEED);
		
		listener=new Listener();
		
		addKeyListener(listener);
	}
	
	public void start()
	{
		run=true;
		paused=false;
		
		gameLoop=new Thread(this);
		gameLoop.start();

		try{
			gameLoop.join();
		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}
	
	public void stop()
	{
		run=false;
	}
	
	public void pause()
	{
		paused=true;
	}
	
	public void unpause()
	{
		paused=false;
	}
	
	public void run() //This is the game loop
	{
		final long TARGET_TIME=REFRESH_SPEED*1000000;
		long now;
		long lastUpdate=System.nanoTime();
		int fps=0;
		long time=0;
		
		while(run)
		{
			now=System.nanoTime();
			double delta=(now-lastUpdate)/(double)TARGET_TIME; //The delta is calculated by calculating how long the last loop took and dividing it
																//the target time. The delta is then used to adjust the speed of all moving objects accordingly.
			time+=now-lastUpdate;
			lastUpdate=now;
			
			if(time>1000000000)
			{
				System.out.println(fps); 						//Used to test the games frames per second.
				fps=0;
				time=0;
			}
			
			if(!paused)
			{
				update(delta);
				
				render();
			}
			
			fps++;
			
			try{
				long sleepTime=(lastUpdate-System.nanoTime()+TARGET_TIME)/1000000;
				
				if(sleepTime<0)
				{
					sleepTime=0;
				}

				Thread.sleep(sleepTime);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
	
		}
	}
	
	public void update(double delta) //This updates the games logic after every iteration of the game loop.
	{
		playerCar.setMovementSpeed(MOVEMENT_SPEED*delta);   //
		scroller.setScrollSpeed(SCROLL_SPEED*delta);		//Movement speed being adjusted based on delta.
		spawner.setMovementSpeeds((SCROLL_SPEED*2)*delta);	//
		
		listener.update();	//Moves player car based on user input.
		scroller.scroll();	//Scrolls background.
		spawner.moveCars();	//Moves all spawned enemy cars.
		spawner.checkCollision(playerCar); //Checks whether player collides with an enemy car.
	}
	
	public void render() //This updates the graphics after every iteration of the game loop.
	{
		if(getBufferStrategy()==null)
		{
			createBufferStrategy(3);
		}
		
		BufferStrategy bs=getBufferStrategy();
		
		Graphics g=bs.getDrawGraphics();
		
		scroller.render(g);		
		playerCar.render(g);
		spawner.render(g);
		
		if(spawner.checkCollision(playerCar)) //Used to make sure collision detection work properly.
		{
			g.setColor(Color.RED);
			g.setFont(new Font("Times New Roman", Font.BOLD, 24));
			g.drawString("Crashed!", (getWidth()/2)-24, getHeight()/2);
		}
		
		g.dispose();
		bs.show();
	}

	public void getRender(Graphics g) //Used for pre-rendering for the menu.
	{
		scroller.render(g);		
		playerCar.render(g);
		spawner.render(g);
	}
	
	private class Listener implements KeyListener
	{
		private boolean up=false;
		private boolean down=false;
		private boolean right=false;
		private boolean left=false;
		
		public void keyPressed(KeyEvent e)
		{	
			int code=e.getKeyCode();
			
			if(code==KeyEvent.VK_W || code==KeyEvent.VK_UP)
			{
				up=true;
			}
			
			else if(code==KeyEvent.VK_S || code==KeyEvent.VK_DOWN)
			{
				down=true;
			}
			
			else if(code==KeyEvent.VK_D || code==KeyEvent.VK_RIGHT)
			{
				right=true;
			}
			
			else if(code==KeyEvent.VK_A || code==KeyEvent.VK_LEFT)
			{
				left=true;
			}
			
			else if(code==KeyEvent.VK_ESCAPE)
			{
				stop();
				System.out.println("Escape");
			}
			
			else if(code==KeyEvent.VK_SPACE)
			{
				if(paused)
				{
					unpause();
				}
				
				else
				{
					pause();
				}
			}
		}
		
		public void keyReleased(KeyEvent e)
		{
			int code=e.getKeyCode();
			
			if(code==KeyEvent.VK_W || code==KeyEvent.VK_UP)
			{
				up=false;
			}
			
			else if(code==KeyEvent.VK_S || code==KeyEvent.VK_DOWN)
			{
				down=false;
			}
			
			else if(code==KeyEvent.VK_D || code==KeyEvent.VK_RIGHT)
			{
				right=false;
			}
			
			else if(code==KeyEvent.VK_A || code==KeyEvent.VK_LEFT)
			{
				left=false;
			}
				
		}
		
		public void keyTyped(KeyEvent e)
		{
			
		}
		
		public void update()
		{
			if(up)
			{
				playerCar.moveUp();
			}
			
			else if(down)
			{
				playerCar.moveDown();
			}
			
			else if(left)
			{
				playerCar.moveLeft();
			}
			
			else if(right)
			{
				playerCar.moveRight();
			}
		}
	}	
}