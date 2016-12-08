package cars;

import movable.MovableObject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class Car extends MovableObject 
{
	private Image car;
	
	public Car(Image car)
	{
		this(car, 0, 0);
	}
	
	public Car(Image car, int xPos, int yPos)
	{
		this(car, xPos, yPos, 120, 60);
	}
	
	public Car(Image car, int xPos, int yPos, int carWidth, int carHeight)
	{
		this(car, xPos, yPos, carWidth, carHeight, 1);
	}
	
	public Car(Image car, int xPos, int yPos, int carWidth, int carHeight, double movementSpeed)
	{
		this(car, xPos, yPos, carWidth, carHeight, movementSpeed, null);
	}
	
	public Car(Image car, int xPos, int yPos, int carWidth, int carHeight, double movementSpeed, Rectangle bounds)
	{
		super(xPos, yPos, carWidth, carHeight, movementSpeed, bounds);
		
		this.car=resizeImage(car, carWidth, carHeight);
	}
	
	public static Image resizeImage(Image image, int width, int height)
	{
		return image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
	}
	
	public void setWidth(int width)
	{
		super.setWidth(width);
		
		if(car!=null)
		{
			car=resizeImage(car, getWidth(), getHeight());
		}
	}
	
	public void setHeight(int height)
	{
		super.setHeight(height);
		
		if(car!=null)
		{
			car=resizeImage(car, getWidth(), getHeight());
		}
	}
	
	public void render(Graphics g)
	{
		g.setColor(Color.YELLOW);
		g.drawRect(getXPos(), getYPos(), getWidth(), getHeight());
		g.drawImage(car, getXPos(), getYPos(), null);
	}
}
