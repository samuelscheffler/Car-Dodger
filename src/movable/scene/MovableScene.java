package movable.scene;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import movable.MovableObject;
import movable.Renderable;

public abstract class MovableScene extends MovableObject implements Renderable
{	
	BufferedImage scene;
	
	public MovableScene(int width, int height)
	{
		super(0, 0, width, height);
		
		scene=new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
		drawBackground(scene.getGraphics());
		drawForeground(scene.getGraphics());
	}
	
	public void render(Graphics g)
	{
		g.drawImage(scene, getXPos(), getYPos(), null);
	}
	
	public abstract void drawBackground(Graphics g);
	public abstract void drawForeground(Graphics g);
	public abstract MovableScene getCopy();
}
