package game.MovementModule;

import game.Actor.Actor;
import game.Actor.Monster;


// generic movement module used to set and get x, y coordinates

public class LinearMover implements MovementModule 
{
	private final short Axis;
	private int mCentre, mVel;
	private final int LoopTime;
	private final Actor mActor;
	
	private int mT;	//Current frame time
	
	public LinearMover(short axis, int centre, int vel, int loopTime, Actor a)
	{
		Axis = axis;
		mCentre = centre;
		mVel = vel;
		LoopTime = loopTime;
		
		mActor = a;
		
		mT = 0;
	}
	
	public boolean next()
	{
		boolean notClipped=true;
		
		if(mT>LoopTime)
		{
			notClipped=false;
			mVel = -mVel;
			mT = 0;
		}else
			mT++;
		
		short pos = ( Axis==Monster.X ?  mActor.x() : mActor.y() );
		if (pos < mCentre)
			pos += mVel;
		else if (pos > mCentre)
			pos -= mVel;
		mActor.setX(pos);
		return notClipped;
	}

	public void setT(int t){};   //sets time variable
	
	public void reset(){}

	public boolean next(double distance) {
		// TODO Auto-generated method stub
		return false;
	};
}
