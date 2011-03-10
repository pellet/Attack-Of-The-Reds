package game.Actor;

import game.Display.Sprite;
import game.Display.Stage;
import game.Display.TimerSprite;
import game.Display.InvadersFrame.GfxPriority;
import effect.EffectManager.Effect;

public class Actor extends TimerSprite
{
	protected static final String[] SPLODESPRITES = new String[] {null};//{"octoSplode.gif"};

	static final int DECOMPOSITION_TIME = 100;
	
	private short mIncrementX = 1;
	private short mIncrementY;
	
	protected enum State
	{
		Dead,
		Born,
		Alive,
		FlipoutInit,
		Flipout,
		Dive,
		Maniac,
		
		MAX_STATES
	}
	
	private State mLifeCycle;
	private int mLifeCycleClock;
	
	public Actor(int id, Stage stage, short clip_x, short clip_y, String[] sprites)
	{
		super(id,stage, clip_x, clip_y, sprites);
		setLifeCycle(State.Alive);
	}
	
	protected void setLifeCycle(State state)
	{
		mLifeCycle = state;
		mLifeCycleClock = 0;
		mCurrentFrame = 0;
	}
	
	protected State getLifeCycle()
	{
		return mLifeCycle;
	}
	
	protected int getLifeCycleClock()
	{
		return mLifeCycleClock;
	}
	
	//Generic setting for collision detection against other gfx priority
	public boolean NotCollide( Sprite sprite ){	return true;	}
	
	protected short incrementX(){	return mIncrementX;	}
	protected void setIncrementX(short newIncrement){	mIncrementX = newIncrement;	}
	
	protected short incrementY(){	return mIncrementY;	}
	protected void setIncrementY(short newIncrement){	mIncrementY = newIncrement;	}
	

	
	protected void kill()
	{
		setLifeCycle(State.Dead);
		setSolid(false);
		
		setPriority(GfxPriority.EFFECT);
		mSpriteNames = SPLODESPRITES;
		
		mEffects.create(Effect.EXPLODE, this.x()+this.width(), this.y()+this.height());
		
		//Remove sprites, and check for monster collisions to add points
		Stage stage = this.stage();
		stage.decrementActorCount(this);
	}

//	public boolean think()
//	{
//		return mTime==0;
//	}
	
	public void act()
	{
		super.act();
		mLifeCycleClock++;
	}

}
