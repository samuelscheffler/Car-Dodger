package cars;

import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class EnemyCar extends Car 
{
	public EnemyCar()
	{
		this(0, 0);
	}
	
	public EnemyCar(int xPos, int yPos)
	{
		this(xPos, yPos, 1);
	}
	
	public EnemyCar(int xPos, int yPos, double movementSpeed)
	{
		this(xPos, yPos, movementSpeed, null);
	}
	
	public EnemyCar(int xPos, int yPos, double movementSpeed, Rectangle bounds)
	{
		super(getImage(), xPos, yPos, 60, 120, movementSpeed, bounds);
	}
	
	private static Image getImage()
	{
		Image car=null;
		
		try{
			car=ImageIO.read(new File("Red Car.PNG"));
		}catch(IOException e){
			e.printStackTrace();
		}
		
		return car;
	}
}
