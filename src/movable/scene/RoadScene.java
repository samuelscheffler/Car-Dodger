package movable.scene;

import java.awt.Color;
import java.awt.Graphics;

public class RoadScene extends MovableScene
{
	public RoadScene(int sceneWidth, int sceneHeight)
	{
		super(sceneWidth, sceneHeight);
	}
	
	public void drawBackground(Graphics g)
	{
		g.setColor(Color.BLACK);
		g.fillRect(getXPos(), getYPos(), getWidth(), getHeight());
	}
	
	public void drawForeground(Graphics g)
	{
		g.setColor(Color.YELLOW);
		
		final int width=14;
		final int height=35;
		
		for(int y=getYPos(); y<getHeight()+getYPos(); y+=height*2)
		{
			g.fillRect((getXPos()+(getWidth()/3)-(width/2)), y, width, height);
		}
		
		for(int y=getYPos(); y<getHeight()+getYPos(); y+=height*2)
		{
			g.fillRect((getXPos()+((getWidth()*2)/3)-width/2), y, width, height);
		}
	}
	
	public MovableScene getCopy()
	{
		MovableScene copy=new RoadScene(getWidth(), getHeight());
		
		return copy;
	}
}
