package movable;

public class Position 
{
	private int xPos;
	private int yPos;
	
	public Position()
	{
		setXPos(0);
		setYPos(0);
	}
	
	public Position(int xPos, int yPos)
	{
		setXPos(xPos);
		setYPos(yPos);
	}
	
	public void setXPos(int xPos)
	{
		this.xPos=xPos;
	}
	
	public void setYPos(int yPos)
	{
		this.yPos=yPos;
	}
	
	public int getXPos()
	{
		return xPos;
	}
	
	public int getYPos()
	{
		return yPos;
	}
}
