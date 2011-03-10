package game.MovementModule;

import game.Display.Sprite;
import game.Actor.Actor;

public class ParabolicMover implements MovementModule
{
	private final short Axis;
	private final int InitialValue;
	private final double InitialLength;
	private final double NaturalLine;
	private final int InitialTime;
	private final Actor mActor;
	
	private int mT;
	private double mDivisor = 400 / 3;
	private double mMaxLine = 300;
	private boolean mForwards;
	
	public ParabolicMover(short axis, double length, int time, Actor actor, boolean forwards)
	{		
		if(length<1)
		{
			assert(length<1);
			System.err.println("Use a length above 0, See ya!");
			System.exit(-1);
		}
		NaturalLine = (time /2) * (time / 2);
		InitialLength = length;
		Axis = axis;
		mActor = actor;
			
		InitialValue = ( Axis==Sprite.X ?  mActor.x() : mActor.y() );
		InitialTime = time;
		
		mT=0;
		mForwards = forwards;
		mMaxLine = InitialValue;
	}

	public void setMaxLine(double line)
	{
		mMaxLine += line;
	}
	public void setDirection(boolean ltor)
	{
		mForwards = ltor;
	}
	
	public boolean next(){	return next(0);	}
	public boolean next(double distance)
	{
		mMaxLine += distance;
		
		boolean notClipped=true;
		if(mForwards)
		{
			if(mT>InitialTime)
			{
				notClipped=false;
				mForwards=false;
			}else
				mT++;
		}
		else
		{
			if(-InitialTime>mT)
			{
				mForwards = true;
				notClipped = false;
			}else
				mT--;
		}
		
		mDivisor = (NaturalLine / InitialLength );
		double velocity = - ((mT * mT) / mDivisor);
		double newCoord = InitialValue + (mMaxLine - velocity);
		mActor.setCoord(Axis, (short) newCoord);
		return notClipped;
	}

	public void setT(int t){	mT = t;	}
	public void reset(){	setT(InitialTime);	}
	
}
