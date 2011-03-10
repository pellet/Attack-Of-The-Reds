package game.Actor;

import game.Display.Stage;
import math.Vector3f;

public class DiagonalBullet extends Bullet
{
	final	String[]	BULLET_TOP_LEFT	=	{"playerBulletTopLeft.gif"};
	final	String[]	BULLET_TOP_RIGHT	=	{"playerBulletTopRight.gif"};
	
	protected Vector3f v;
	/**
	 * @param playerBullet
	 * @param stage
	 * @param clip_x
	 * @param clip_y
	 */
	public DiagonalBullet(boolean playerBullet, Stage stage, short clip_x,
			short clip_y, Vector3f v)
	{
		super(playerBullet, stage, clip_x, clip_y);
		// TODO Auto-generated constructor stub
		this.v = v;
	}
	
	public void act()
	{
//		if(mPlayerBullet)
//		{
			if(v.getX()<0)
			{
				this.setSpriteNames(BULLET_TOP_LEFT);
			}else
			{
				this.setSpriteNames(BULLET_TOP_RIGHT);
			}
		
			setX( x() + (int)v.getX());
			setY( y() + (int)v.getY());
//			setY( (short) (y() - PLAYERBULLETSPEED) );
			
			if (y() < -REMOVE_THRESHOLD || x() < -REMOVE_THRESHOLD || x() > clipX()+REMOVE_THRESHOLD )
			{
				remove();
			}
//		}else
//		{
//			setY( (short)(y()+ MONSTERBULLETSPEED) );
//			if (y() > this.clipY() + REMOVE_THRESHOLD)
//			{
//				remove();
//			}
//		}
	}

	public Vector3f getVector()
	{
		return v;
	}

}