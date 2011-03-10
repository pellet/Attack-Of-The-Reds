package game.Actor;
import game.Display.Sprite;
import game.Display.Stage;
import game.Display.InvadersFrame.GfxPriority;
import game.Enum.GameState;
import math.Vector3f;

import java.awt.event.KeyEvent;

/**
 * @author Ben Pettit
 *
 */
public class Player extends Actor 
{
	protected enum Weapon
	{
		NONE,
		MACHINE_GUN,
		DIAGONAL,
		SHOTGUN,
		
		NUM_OF_WEAPONS,
	}
	
	private static final int PLAYERSPEED = 5;
	private short vX, vY;						//define the increments for x, y position values per frame
	private boolean up, down, left, right;
	protected Weapon mFireKeyDown;
	private long mFireClock;
	
	//defines the time in milliseconds between shots fired
	private		final	static	int	STRAIGHT_BULLET_RATE = 10;
	private static final int FIRE_RATE = 12;//25;//7;
	public static final short FIRE_KEY_DELAY = 10;//100;

	protected short mCurrentLives;				//current lives left
	protected int	mCurrentScore;				//current score.
	static final int SPAWN_TIME = 200;			//spawn time (invincibility)
	
	//define different sprite names for different actions
	private static final String[] NEUTRAL_SPRITES = new String[] {"neutralShip.gif"};
	private static final String[] NEUTRAL_SPRITES_SPAWN = new String[] {"neutralShip.gif", null};
	private static final String[] LEFT_SPRITES = new String[] {"rightShip.gif"};
	private static final String[] LEFT_SPRITES_SPAWN = new String[] {"rightShip.gif", null};
	private static final String[] RIGHT_SPRITES = new String[] {"leftShip.gif"};
	private static final String[] RIGHT_SPRITES_SPAWN = new String[] {"leftShip.gif", null};
	
	//shootie speed
	protected final short PLAYERBULLETSPEED_X = 2;
	protected final short PLAYERBULLETSPEED_Y = 5;
	
	//Last shoot release
	private long[] mReleaseTime;
//	private int mBulletCount;
	
	
	public Player(short id,Stage stage, short clip_x, short clip_y, short startingLives)
	{
		super(id,stage, clip_x, clip_y, NEUTRAL_SPRITES);

		setX((short)(clipX()/2));
		setY((short)(clipY()-height()));
		vX = 0;
		vY = 0;
		//Spawn flash speed
		setFrameSpeed(15);
		mFireKeyDown = Weapon.NONE;
		mFireClock = 0;
		mCurrentLives = startingLives;
		mReleaseTime	=	new long[Weapon.NUM_OF_WEAPONS.ordinal()];
//		mBulletCount = 0;
		
		setPriority(GfxPriority.PLAYER);
	}
	
	public void keyPressed(KeyEvent e)
	{
		switch (e.getKeyCode())
		{
			case KeyEvent.VK_DOWN : down = true; break;
			case KeyEvent.VK_UP : up = true; break;
			case KeyEvent.VK_LEFT : left = true; break;
			case KeyEvent.VK_RIGHT : right = true; break;
			case KeyEvent.VK_Z :
			{
				if(this.getLifeCycle()!=State.Dead)
				{
					mFireKeyDown = Weapon.MACHINE_GUN;
				}
			}break;
			case KeyEvent.VK_X :
			{
				if(this.getLifeCycle()!=State.Dead)
				{
					mFireKeyDown = Weapon.DIAGONAL;
				}
			}break;
			case KeyEvent.VK_C :
			{
				if(this.getLifeCycle()!=State.Dead)
				{
					mFireKeyDown = Weapon.SHOTGUN;
				}
			}break;
		}
		//TODO: Fixes bug where java thinks key is released when it isnt-weird???
		long currentTime = System.currentTimeMillis();
		if(currentTime>mReleaseTime[mFireKeyDown.ordinal()]+FIRE_KEY_DELAY && mFireKeyDown!=Weapon.MACHINE_GUN)//==Weapon.NONE)
		{
//			if(debugMode)
//				System.out.println("bonzaii!");
			mFireClock = 0;
		}
		updateSpeed();
	}
	
	public void keyReleased(KeyEvent e)
	{
		Weapon fireKeyReleased		=	null;
		switch (e.getKeyCode())
		{
			case KeyEvent.VK_DOWN : down = false; break;
			case KeyEvent.VK_UP : up = false; break;
			case KeyEvent.VK_LEFT : left = false; break;
			case KeyEvent.VK_RIGHT : right = false; break;
			case KeyEvent.VK_Z :
				fireKeyReleased		=	Weapon.MACHINE_GUN;
			break;
			case KeyEvent.VK_X :
				fireKeyReleased		=	Weapon.DIAGONAL;
			break;
			case KeyEvent.VK_C :
				fireKeyReleased		=	Weapon.SHOTGUN;
			break;
		}
		
		if(fireKeyReleased!=null)
		{
			mReleaseTime[fireKeyReleased.ordinal()] = System.currentTimeMillis();
			//Current weapon released.
			if(mFireKeyDown==fireKeyReleased)
				mFireKeyDown = Weapon.NONE;
		}
		
		updateSpeed();
	}
	
	/**
	 * 
	 */
	private void updateSpeed()
	{
		vX = 0;
		vY = 0;
		switch(getLifeCycle())
		{
			case Alive:
			{
				if (down) 
				{
					vY = PLAYERSPEED;
				}
				if (up) vY = -PLAYERSPEED;
				if (left) 
				{
					vX = -PLAYERSPEED;
					setSpriteNames(LEFT_SPRITES);
					mCurrentFrame = 0;
				}
				if (right) 
				{
					vX = PLAYERSPEED;
					setSpriteNames(RIGHT_SPRITES);
				}
				if (left && right)
				{
					vX = 0;
					setSpriteNames(NEUTRAL_SPRITES);
				}
				if (up && down)
					vY = 0;
				if (!left && !right)
				{
					setSpriteNames(NEUTRAL_SPRITES);
				}
			}break;
			
			case Born:
			{
				if (down) 
				{
					vY = PLAYERSPEED;
				}
				if (up) vY = -PLAYERSPEED;
				if (left) 
				{
					vX = -PLAYERSPEED;
					setSpriteNames(LEFT_SPRITES_SPAWN);
					mCurrentFrame = 0;
				}
				if (right) 
				{
					vX = PLAYERSPEED;
					setSpriteNames(RIGHT_SPRITES_SPAWN);
				}
				if (left && right)
				{
					vX = 0;
					setSpriteNames(NEUTRAL_SPRITES_SPAWN);
				}
				if (up && down)
					vY = 0;
				if (!left && !right)
				{
					setSpriteNames(NEUTRAL_SPRITES_SPAWN);
				}
			}break;
		}
		
	}
	
	public void collision(Sprite sprite)
	{
		switch(getLifeCycle())
		{
			case Dead://dead
			break;
			case Alive:
				if (sprite instanceof Monster)
				{
					kill();
				}else if (sprite instanceof Bullet)
				{
					Bullet b = (Bullet) sprite;
					if(!b.isPlayerBullet())
						kill();
//TODO: Friendly Fire.
//					else
//						kill();
				}
			break;
			default://invalid state.
				assert(getLifeCycle().ordinal()<State.MAX_STATES.ordinal());
		}
	}
	
	public void fire()
	{
//		if(!mEffects.effectActive(Effect.BLUE_FLAME) && mFireClock==0);
//		{
//			mEffects.create(Effect.BLUE_FLAME, x(),y());
//		}
//		mBulletCount = -1;
		if(mFireKeyDown==Weapon.MACHINE_GUN)
		{
			Bullet b = new Bullet(true, stage(), clipX(), clipY());
			b.setX((short)(x() + (width()/2)));
			b.setY((short)( y() - b.height()) );
			stage().addSprite(b);
		}else if(mFireKeyDown==Weapon.DIAGONAL)
		{
			Vector3f leftVector = new Vector3f(PLAYERBULLETSPEED_X, -PLAYERBULLETSPEED_Y, 0);
			Bullet leftBullet = new DiagonalBullet(true, stage(), clipX(), clipY(), leftVector);
			leftBullet.setX((short)(x() + (width()/2)));
			leftBullet.setY((short)( y() - leftBullet.height()) );
			stage().addSprite(leftBullet);
			

			Vector3f rightVector = new Vector3f(-PLAYERBULLETSPEED_X, -PLAYERBULLETSPEED_Y, 0);
			Bullet rightBullet = new DiagonalBullet(true, stage(), clipX(), clipY(), rightVector);
			rightBullet.setX((short)(x() + (width()/2)));
			rightBullet.setY((short)( y() - rightBullet.height()) );
			stage().addSprite(rightBullet);	
		}
	}
	
	protected boolean activePlayerState()
	{
		short x = x();
		short y = y();
		x = (short)(x + vX);
		y = (short)(y + vY);
		
		if (x <= 0)
			x = 1;
		if (y <= 0)
			y = 1;
		if (x > clipX())
			x = (short)(clipX() - 1);
		if (y > clipY())
			y = (short)(clipY() - 1);
		setX(x);
		setY(y);
		
		//Fire
//		if (mFireKeyDown!=Weapon.NONE && mFireClock % FIRE_RATE == 0)
//		{
//			fire();
//		}
		if (mFireKeyDown==Weapon.DIAGONAL && mFireClock % FIRE_RATE == 0)
		{
			fire();
		}else if (mFireKeyDown==Weapon.MACHINE_GUN && mFireClock % STRAIGHT_BULLET_RATE == 0)
		{
			fire();
		}
		return true;
	}
	
	public void act()
	{
		super.act();
		switch(getLifeCycle())
		{
			case Alive:
			{
				activePlayerState();
			}break;
		
			case Dead:
			{
				if(finishedDying())
				{
					if(getCurrentLives()>0)
					{
						mCurrentLives--;
						this.spawn();
					}else//Game Over
					{
						remove();
						//TODO:If score good enough, place on high score with comment
						//Go to high scores screen.
						Stage stage = this.stage();
						
						stage.setGameState( GameState.GAME_OVER );
					}
				}
			}break;
			
			case Born://Spawn time
			{
				activePlayerState();
				
				//Back to normal player state?
				if(finishedSpawn())
					makeAlive();
			}break;
		}
		//Tick the clock.
		mFireClock++;
	}
	
	protected boolean finishedDying()
	{
		if(getLifeCycleClock() == DECOMPOSITION_TIME)
			return true;
		else
			return false;
	}
	
	protected boolean finishedSpawn()
	{
		if(this.getLifeCycleClock()>SPAWN_TIME)
			return true;
		else
			return false;
	}
	
	
	/**
	 * Start hurting lol
	 * @return
	 */
	protected boolean makeAlive()
	{
		setSpriteNames(NEUTRAL_SPRITES);
		setSolid(true);
		setLifeCycle(State.Alive);
		return true;
	}
	
	public void spawn()
	{
		setPriority(GfxPriority.PLAYER);
		setSpriteNames(NEUTRAL_SPRITES_SPAWN);
		setLifeCycle(State.Born);
	}
	
	protected void kill()
	{
		super.kill();
	}
	
	public short getCurrentLives()
	{
		return mCurrentLives;
	}

	/**
	 * @param livesToStartWith
	 */
	public void setCurrentLives(short livesToStartWith)
	{
		// TODO Auto-generated method stub
		mCurrentLives = livesToStartWith;
	}

	/**
	 * @return
	 */
	public int getScore()
	{
		// TODO Auto-generated method stub
		return mCurrentScore;
	}

	/**
	 * @param currentScore the mCurrentScore to set
	 */
	public void setScore(int currentScore)
	{
		this.mCurrentScore = currentScore;
	}
	
	/**
	 * @param currentScore the mCurrentScore to set
	 */
	public void addToScore(int currentScore)
	{
		this.mCurrentScore += currentScore;
	}
}