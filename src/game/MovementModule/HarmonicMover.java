package game.MovementModule;

import game.Actor.Actor;
import game.Actor.Monster;


// generic movement module used to set and get x, y coordinates

public class HarmonicMover implements MovementModule 
{
	private final short Axis;
	private double mVel;
	private double mult = -0.0000000025;
	private final int LoopTime;
	private final Actor mActor;
	
	private int mT;	//Current frame time
	
	public HarmonicMover(short axis, double vel, int loopTime, Actor a)
	{
		Axis = axis;
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
			reset();
		}else
			mT++;
		
		short pos = ( Axis==Monster.X ?  mActor.x() : mActor.y() );
		double acc;
		
		acc = pos * mult;
		mVel += acc;
		pos += mVel;
		 
		if(Axis==Monster.X)
			mActor.setX(pos);
		else
			mActor.setY(pos);
		return notClipped;
	}

	public void setT(int t){};   //sets time variable
	
	public void reset()
	{
		mVel = -mVel;
		mult = -mult;
		mT = 0;
	}

	public boolean next(double distance) {
		// TODO Auto-generated method stub
		return false;
	};
}
