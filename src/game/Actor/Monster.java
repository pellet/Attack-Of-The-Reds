package game.Actor;

import effect.EffectManager.Effect;
import game.Display.Sprite;
import game.Display.Stage;
import game.Display.InvadersFrame.GfxPriority;
import game.MovementModule.MovementModule;
import math.Vector3f;

public class Monster extends Actor
{
	protected static final short MONSTER_HEALTH = 2;
	
	protected double mDna;
	protected double mXP;
	protected int mCount;
	protected MovementModule[] movementModules = new MovementModule[MAX_COORD];
	protected short mHealth;
	private		int		points;
	
	public Monster(int id,Stage stage, short clip_x, short clip_y, short level, String[] spriteNames)
	{
		super(id,stage, clip_x, clip_y, spriteNames);
		mHealth = (short) (MONSTER_HEALTH+((level*2)*mDna));
		mCount = 0;
		mDna = Math.random();
		mXP = mDna * level;
		
		setPriority(GfxPriority.MONSTER);
	}

	public void act()
	{
		mCount++;
		if(getLifeCycle()==State.Dive)
			setY( (short) (incrementY() + y()) );

		else if(getLifeCycle()==State.Dead)
		{
			checkRemove();
		}
		super.act();
	}

	protected void checkRemove()
	{
		if (getLifeCycleClock()==DECOMPOSITION_TIME)
		{
			remove();
		}
	}
	
	public boolean NotCollide( Actor a )
	{
		if(a instanceof Monster)
			return true;
		else
			return false;
	}
	
	/**
	 * 
	 */
	public void collision(Sprite sprite)
	{
		switch(getLifeCycle())
		{
			case Dead://dead
			break;
			case Alive:
			case FlipoutInit:
			case Flipout:
				if (sprite instanceof Player && !(this instanceof BossOcto))
				{
					kill();
				}else if (sprite instanceof Bullet)
				{
					Bullet b = (Bullet) sprite;
					if(b.isPlayerBullet())
					{
						if(mHealth==0)
						{
							kill();
						}else
						{
							mHealth--;
							
							int x	=	b.x();
							int y	=	b.y();
							
							if(b instanceof DiagonalBullet)
							{
								DiagonalBullet diagB	=	(DiagonalBullet) b;
								Vector3f v	=	diagB.getVector();
								if(v.getX()>0)
								{
									x	+=	0;
								}else
								{
									x	+=	b.width();
								}
								
							}else//Basic bullet
							{
								x	+=	b.width()/2;
							}
							
							//sparks
							mEffects.create(Effect.SPARK, x, y);
						}
					}
				}
			break;
			default://invalid state.
				assert(getLifeCycle().ordinal()<State.MAX_STATES.ordinal());
		}
	}

	/**
	 * @return the mHealth
	 */
	protected short getHealth()
	{
		return this.mHealth;
	}

	/**
	 * @param health the mHealth to set
	 */
	protected void setHealth(short health)
	{
		this.mHealth = health;
	}
	
	protected void setPoints(int points) {
		// TODO Auto-generated method stub
		this.points = points;
	}

	public int getPoints() {
		// TODO Auto-generated method stub
		return this.points;
	}

}