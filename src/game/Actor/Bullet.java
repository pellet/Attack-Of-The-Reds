package game.Actor;

import game.Display.Sprite;
import game.Display.Stage;
import game.Display.InvadersFrame.GfxPriority;

public class Bullet extends Sprite
{
	protected final static String[] NEUTRAL_SPRITES = new String[] {"playerBullet.gif"};
	protected final float PLAYERBULLETSPEED = 5;
	protected final short MONSTERBULLETSPEED = 7;
	protected final short REMOVE_THRESHOLD = 50;
	protected boolean mPlayerBullet;
	
	public Bullet(boolean playerBullet, Stage stage, short clip_x, short clip_y, String[] bulletSprites)
	{
		super((short)-1,stage, clip_x, clip_y, bulletSprites);
		init(playerBullet);
	}

	public Bullet(boolean playerBullet, Stage stage, short clip_x, short clip_y)
	{
		super((short)-1,stage, clip_x, clip_y, NEUTRAL_SPRITES);
		init(playerBullet);
	}
	
	private void init(boolean playerBullet)
	{
		mPlayerBullet = playerBullet;
		
		setPriority(GfxPriority.BULLET);
	}
	
	public void collision(Sprite a)
	{
		if (mPlayerBullet && a instanceof Monster)
		{
			remove();
			setSolid(false);
		}
		else if (!mPlayerBullet && a instanceof Player)
		{
			remove();
			setSolid(false);
		}
	}
	
	public void act()
	{
		if(mPlayerBullet)
		{
			setY( (short) (y() - PLAYERBULLETSPEED) );
			
			if (y() < -REMOVE_THRESHOLD)
			{
				remove();
			}
		}else
		{
			setY( (short)(y()+ MONSTERBULLETSPEED) );
			if (y() > this.clipY() + REMOVE_THRESHOLD)
			{
				remove();
			}
		}
	}
	
	public boolean isPlayerBullet(){ return mPlayerBullet; }

}