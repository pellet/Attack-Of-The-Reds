package game.MovementModule;


// generic movement module used to set and get x, y coordinates

public interface MovementModule 
{	
	public boolean next(double distance);//Add distance
	public boolean next();

	public void setT(int t);   //sets time variable
	
	public void reset();
}
