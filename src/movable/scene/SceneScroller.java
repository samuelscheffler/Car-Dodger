package movable.scene;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import movable.Renderable;

public class SceneScroller implements Renderable
{
	public static final int UP=1;
	public static final int DOWN=2;
	public static final int RIGHT=3;
	public static final int LEFT=4;
	
	private MovableScene sceneOne;
	private MovableScene sceneTwo;
	
	private final int DIRECTION;
	
	private BufferedImage buffer;
	
	public SceneScroller(MovableScene scene, int DIRECTION, double scrollSpeed)
	{
		sceneOne=scene.getCopy(); 
		sceneTwo=scene.getCopy();
		
		sceneOne.setMovementSpeed(scrollSpeed);
		sceneTwo.setMovementSpeed(scrollSpeed);
		this.DIRECTION=DIRECTION;
		
		setStartLocation();
		setSceneBounds();
		
		buffer=new BufferedImage(sceneOne.getWidth(), sceneTwo.getHeight(), BufferedImage.TYPE_INT_ARGB);
		
		sceneOne.render(buffer.getGraphics());
		sceneTwo.render(buffer.getGraphics());
	}
	
	private void setStartLocation()
	{
		if(DIRECTION==UP)
		{
			sceneTwo.setYPos(sceneOne.getHeight());
		}
		
		else if(DIRECTION==DOWN)
		{
			sceneTwo.setYPos(-sceneOne.getHeight());
		}
		
		else if(DIRECTION==RIGHT)
		{
			sceneTwo.setXPos(-sceneOne.getWidth());
		}
		
		else if(DIRECTION==LEFT)
		{
			sceneTwo.setXPos(sceneOne.getWidth());
		}
	}
	
	private void setSceneBounds()
	{
		if(DIRECTION==UP || DIRECTION==DOWN)
		{
			sceneOne.setBounds(new Rectangle(0, -sceneOne.getHeight(), sceneOne.getWidth(), sceneOne.getHeight()*3));
			sceneTwo.setBounds(sceneOne.getBounds());
		}
		
		else if(DIRECTION==LEFT || DIRECTION==RIGHT)
		{
			sceneOne.setBounds(new Rectangle(-sceneOne.getWidth(), 0, sceneOne.getWidth()*3, sceneOne.getHeight()));
			sceneTwo.setBounds(sceneOne.getBounds());
		}
	}
	
	public void setScrollSpeed(double scrollSpeed)
	{
		sceneOne.setMovementSpeed(scrollSpeed);
		sceneTwo.setMovementSpeed(scrollSpeed);
	}
	
	public double getScrollSpeed()
	{
		return sceneOne.getMovementSpeed();
	}
	
	public void scroll()
	{	
		buffer=new BufferedImage(sceneOne.getWidth(), sceneTwo.getHeight(), BufferedImage.TYPE_INT_ARGB);
		
		boolean sceneOneInBounds=true;
		boolean sceneTwoInBounds=true;
		
		if(DIRECTION==UP)
		{
			sceneOneInBounds=sceneOne.moveUp();
			sceneTwoInBounds=sceneTwo.moveUp();
			
			if(!sceneOneInBounds)
			{
				sceneOne.setYPos(sceneTwo.getYPos()+sceneTwo.getHeight());
			}
			
			else if(!sceneTwoInBounds)
			{
				sceneTwo.setYPos(sceneOne.getYPos()+sceneOne.getHeight());
			}
		}
		
		else if(DIRECTION==DOWN)
		{
			sceneOneInBounds=sceneOne.moveDown();
			sceneTwoInBounds=sceneTwo.moveDown();
			
			if(!sceneOneInBounds)
			{
				sceneOne.setYPos(sceneTwo.getYPos()-sceneTwo.getHeight());
			}
			
			else if(!sceneTwoInBounds)
			{
				sceneTwo.setYPos(sceneOne.getYPos()-sceneTwo.getHeight());
			}
		}
		
		else if(DIRECTION==RIGHT)
		{
			sceneOneInBounds=sceneOne.moveRight();
			sceneTwoInBounds=sceneTwo.moveRight();
			
			if(!sceneOneInBounds)
			{
				sceneOne.setXPos(sceneTwo.getXPos()-sceneTwo.getWidth());
			}
			
			else if(!sceneTwoInBounds)
			{
				sceneTwo.setXPos(sceneOne.getXPos()-sceneOne.getWidth());
			}
		}
		
		else if(DIRECTION==LEFT)
		{
			sceneOneInBounds=sceneOne.moveLeft();
			sceneTwoInBounds=sceneTwo.moveLeft();
			
			if(!sceneOneInBounds)
			{
				sceneOne.setXPos(sceneTwo.getXPos()+sceneTwo.getWidth());
			}
			
			else if(!sceneTwoInBounds)
			{
				sceneTwo.setXPos(sceneOne.getXPos()+sceneOne.getWidth());
			}
		}
		
		sceneOne.render(buffer.getGraphics());
		sceneTwo.render(buffer.getGraphics());
	}

	public void render(Graphics g)
	{	
		/*sceneOne.render(g);
		sceneTwo.render(g);*/
		
		g.drawImage(buffer, 0, 0, null);
	}
}
