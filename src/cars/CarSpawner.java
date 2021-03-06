package cars;

import movable.MovableObject;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import movable.Position;
import movable.Renderable;

public class CarSpawner implements Renderable
{
	private ArrayList <EnemyCar> cars; //Holds spawned cars
	private Queue <EnemyCar> spawnQueue; //Holds cars waiting to be spawned
	private Position [] lanePosition; 
	private int laneLength;			  //Lanes are the straight paths that the cars follow.	
	private int counter;
	private double spawnDistance;	//The distance that the last spawned car has to be from the spawn point before the next one can spawn.
	private double distanceCounter; //Keeps track of the distance a car is from spawn.
	private double carSpeed;
	
	public CarSpawner(double carSpeed, int numOfCars, Position [] lanePosition, int laneLength, double spawnDistance)
	{
		this.carSpeed=carSpeed;
		this.lanePosition=lanePosition;
		this.laneLength=laneLength;
		this.spawnDistance=spawnDistance; 
		
		distanceCounter=0.0;
		counter=0;
		
		cars=new ArrayList<EnemyCar>();
		spawnQueue=new LinkedList<EnemyCar>();
		
		Collections.shuffle(Arrays.asList(lanePosition));
		
		for(int i=0; i<numOfCars; i++)
		{
			spawnQueue.add(new EnemyCar(0, 0, carSpeed));
		}
		
		cars.add(getCarFromQueue());
	}	
	
	private EnemyCar getCarFromQueue() //Takes the next car in queue and assigns it a lane.
	{
		EnemyCar temp=spawnQueue.poll();
		
		if(temp!=null)
		{
			setCarInfo(temp);
		}
		
		return temp;
	}
	
	private void setCarInfo(Car car) //Sets a car's lane and movement speed.
	{	
		car.setXPos(lanePosition[counter].getXPos());
		car.setYPos(lanePosition[counter].getYPos());
		car.setMovementSpeed(carSpeed);
		car.setBounds(getBounds(lanePosition[counter], car.getWidth(), laneLength));
		
		counter++;
		
		if(counter==lanePosition.length)
		{
			Collections.shuffle(Arrays.asList(lanePosition));
			counter=0;
		}
	}
	
	private static Rectangle getBounds(Position lanePosition, int laneWidth, int laneLength) //Used to check when car when car is at the end of a lane.
	{
		return new Rectangle(lanePosition.getXPos(), lanePosition.getYPos(), laneWidth, laneLength);
	}
	
	
	public void setMovementSpeeds(double movementSpeed)
	{
		for(int i=0; i<cars.size(); i++)
		{
			cars.get(i).setMovementSpeed(movementSpeed);
		}
		
		carSpeed=movementSpeed;
	}
	
	public boolean checkCollision(MovableObject object) //Checks whether a movable object has collided with any of the cars.
	{	
		for(int i=cars.size()-1; i>=0; i--)
		{
			if(cars.get(i).intersects(object))
			{
				return true;
			}
		}
	
		return false;
	}
	
	public void moveCars() 
	{	
		if(!spawnQueue.isEmpty() && distanceCounter>=spawnDistance)
		{
			EnemyCar temp;
			temp=getCarFromQueue();
			
			if(distanceCounter>spawnDistance)
			{
				temp.setYPos(temp.getBounds().getY()+(distanceCounter-spawnDistance));
			}
			
			cars.add(temp);
			
			distanceCounter=0.0;
		}
		
		for(int i=0; i<cars.size(); i++)
		{
			if(!cars.get(i).moveDown())
			{
				spawnQueue.add(cars.remove(i));
			}
		}
	
		distanceCounter+=carSpeed;
	}

	public void render(Graphics g)
	{
		for(int i=0; i<cars.size(); i++)
		{
			cars.get(i).render(g);
		}
		
	}
}
