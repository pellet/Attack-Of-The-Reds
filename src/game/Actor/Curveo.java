package game.Actor;

import game.Display.Stage;
import game.Display.InvadersFrame.GfxPriority;

public class Curveo extends Monster
{
	protected final int FireRate = 100;
	protected final static String[] NEUTRAL_SPRITES = new String[] {"octo.gif"};
	protected static final int	POINTS	=	20;
	protected static final String[]	BULLET_SPRITES = new String[] {"monsterBullet.gif"};
	
	public Curveo(int id,Stage stage, short clip_x, short clip_y, short level)
	{
		super(id,stage,clip_x,clip_y, level, NEUTRAL_SPRITES);
//		setFrameSpeed( 30);
		short xClip = clipX();
		int xPos = (id+1)*width();
		if(xPos > xClip )
			xPos = (xPos%(xClip-width()));
		setX(xPos);

		setPoints(POINTS);
		
		setPriority(GfxPriority.MONSTER2);
	}
	
	public void act()
	{
		super.act();
		if (getLifeCycle()!=State.Dead)
		{
			if (x() < 0 || x() > clipX())
				setIncrementX( (short)-incrementX() );
		
			setX( (short) (incrementX() + x()));
			
			if((mCount>FireRate-mXP))
			{
				double rand = Math.random();
				if(rand>.7)
				{
					mCount = 0;
					Bullet b = new Bullet(false, stage(), clipX(), clipY(),BULLET_SPRITES);
					b.setX((short)(x() + 16));
					b.setY((short)(y() + b.height()));
					stage().addSprite(b);
				}else if(rand <.2)
					mCount = 0;
				else
					mCount = (int)(rand*mXP);
			}
		}
	}
	
	
}
