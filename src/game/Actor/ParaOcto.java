package game.Actor;

import game.Display.Stage;
import game.MovementModule.HarmonicMover;
import game.MovementModule.LinearMover;
import game.MovementModule.ParabolicMover;

public class ParaOcto extends Octo
{
	//TODO FIX THIS: This distance isnt a judgeable value.
	private static final short Y_DISTANCE = 90;//135;//90;//110;//30;
	private static final int hz = 300;
	private static final short START_Y_POS = 110;
	private static final int	POINTS			= 	10;
	
	private float mRhythm;
	private short InitialY;
//	private int mIncrementY;
	
	public ParaOcto(int id,Stage stage, short clip_x, short clip_y, short level)
	{
		super(id,stage, clip_x, clip_y, level);
		init();
	}
	
	public ParaOcto(int id,Stage stage, short clip_x, short clip_y, short level, String[] spriteNames)
	{
		super(id,stage, clip_x, clip_y, level, spriteNames);
		init();
	}
	
	private void init()
	{
		mCount=0;
//		setFrameSpeed( 30);
		mRhythm=(float) mDna;
		
//		InitialY = (short) (((id/10+1) * height())-(mDna*2));
		
		InitialY = START_Y_POS;
		setY( InitialY );
//		setX( (short) ( width()+(mDna*clipX()) ) );
//		mIncrementY=0;
		
		setPoints(POINTS);
		
		setLifeCycle( State.Born);
	}
	
	public void act()
	{
		super.act();
		int rand;
		switch(getLifeCycle())
		{
			case Dead://dead
			break;
			case Born:
				movementModules[Y] = new ParabolicMover(Y, Y_DISTANCE, hz, this, true);
				setLifeCycle( State.Alive);
			break;
			case Alive://alive
				mRhythm = 0;
				if(mCount>150 /*&& think()*/ && mXP > 2)
				{
					rand = (int)(Math.random()*10);
					mRhythm += rand/1000;
					if(rand>5)
					{
						mCount=0;
						setLifeCycle( State.FlipoutInit);
					}
				}
				movementModules[Y].next();
			break;
			case FlipoutInit://flipping out
				rand = (int)(Math.random());
				//double xdistance = ;
				if( rand>.5)
				{
					movementModules[X] = new HarmonicMover(X, clipX()/2/*0*/, 12, this);
					movementModules[Y].next();
					setLifeCycle( State.Maniac);
				}else
				{
					movementModules[X] = new LinearMover(X, clipX()/2, 2, 20, this);
					movementModules[Y].next();
					setLifeCycle( State.Flipout);				
				}
			break;
			case Flipout:
				movementModules[X].next();
				movementModules[Y].next();
//				movementModules[Y].next();
				
//				if(!movementModules[X].next())//Movement finished
//				{
//					movementModules[Y].next(mIncrementY*10);
//				}
			break;
			case Maniac:
				movementModules[X].next();
				movementModules[Y].next();
//				movementModules[X] = new HarmonicMover(X, 0, 12, this);
//				movementModules[Y].next();
			break;
			default:/*invalid state.*/assert(getLifeCycle().ordinal()<State.MAX_STATES.ordinal());
		}
	}
	
}