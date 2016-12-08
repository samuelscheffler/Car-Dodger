package movable;

import java.awt.Rectangle;

public abstract class MovableObject implements Renderable
{
	private Rectangle bounds;
	private double movementSpeed;
	private double xPos;
	private double yPos;
	private int width;
	private int height;
	

	public MovableObject(int xPos, int yPos, int width, int height)
	{
		this(xPos, yPos, width, height, 1);
	}
	
	public MovableObject(int xPos, int yPos, int width, int height, double movementSpeed)
	{
		this(xPos, yPos, width, height, movementSpeed, null);
	}
	
	public MovableObject(int xPos, int yPos, int width, int height, double movementSpeed, Rectangle bounds)
	{
		//super(xPos, yPos);
		
		setXPos(xPos);
		setYPos(yPos);
		setWidth(width);
		setHeight(height);
		setBounds(bounds);
		setMovementSpeed(movementSpeed);
	}
	
	public void setBounds(Rectangle bounds)
	{
		if(bounds!=null)
		{
			this.bounds=bounds.getBounds();
		}
	}
	
	public Rectangle getBounds()
	{
		return bounds.getBounds();
	}
	
	public void setMovementSpeed(double movementSpeed)
	{
		this.movementSpeed=movementSpeed;
	}
	
	public double getMovementSpeed()
	{
		return movementSpeed;
	}
	
	public void setXPos(double xPos)
	{
		this.xPos=xPos;
	}
	
	public void setYPos(double yPos)
	{
		this.yPos=yPos;
	}
	
	public int getXPos()
	{
		return (int)xPos;
	}
	
	public int getYPos()
	{
		return (int)yPos;
	}
	
	public double getExactXPos()
	{
		return xPos;
	}
	
	public double getExactYPos()
	{
		return yPos;
	}
	
	public void setWidth(int width)
	{
		this.width=width;
	}
	
	public void setHeight(int height)
	{
		this.height=height;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public boolean moveUp()
	{	
		return translate(0, -movementSpeed);
	}
	
	public boolean moveDown()
	{
		return translate(0, movementSpeed);
	}
	
	public boolean moveLeft()
	{	
		return translate(-movementSpeed, 0);
	}
	
	public boolean moveRight()
	{
		return translate(movementSpeed, 0);
	}
	
	public boolean translate(double x, double y)
	{
		boolean inBounds=true;
		
		if(bounds!=null && !bounds.contains(xPos+x, yPos+y, getWidth(), getHeight()))
		{
			inBounds=false;
			
			if(x<0)
			{	
				xPos=bounds.getMinX();
			}
			
			else if(x>0)
			{
				xPos=bounds.getMaxX()-getWidth();
			}
			
			if(y<0)
			{	
				yPos=bounds.getMinY();
			}
			
			else if(y>0)
			{
				yPos=bounds.getMaxY()-getHeight();
			}
		}
		
		else
		{
			xPos+=x;
			yPos+=y;
		}
	
		return inBounds;
	}
	
	public boolean intersects(MovableObject object)
	{	
		Rectangle currentObject=new Rectangle(getXPos(), getYPos(), getWidth(), getHeight());
		Rectangle otherObject=new Rectangle(object.getXPos(), object.getYPos(), object.getWidth(), object.getHeight());
		
		return currentObject.intersects(otherObject);
	}
}
