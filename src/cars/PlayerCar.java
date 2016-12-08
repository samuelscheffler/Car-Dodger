package cars;

import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class PlayerCar extends Car
{
	public PlayerCar()
	{
		this(0, 0);
	}
	
	public PlayerCar(int xPos, int yPos)
	{
		this(xPos, yPos, 1);
	}
	
	public PlayerCar(int xPos, int yPos, double movementSpeed)
	{
		this(xPos, yPos, movementSpeed, null);
	}
	
	public PlayerCar(int xPos, int yPos, double movementSpeed, Rectangle bounds)
	{
		super(getImage(), xPos, yPos, 60, 120, movementSpeed, bounds);
	}
	
	private static Image getImage()
	{
		Image car=null;
		
		try{
			car=ImageIO.read(new File("Blue Car.PNG"));
		}catch(IOException e){
			e.printStackTrace();
		}
		
		return car;
	}
}
