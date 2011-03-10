package game.Actor;

import game.Display.Stage;

public class Octo extends Monster
{	
	protected final static String[] NEUTRAL_SPRITES = new String[] {"octo0.gif", "octo1.gif"};
	protected final static int		POINTS			=	5;
	protected short seed;
	
	
	/**
	 * 
	 * @param id
	 * @param stage
	 * @param clip_x
	 * @param clip_y
	 * @param level
	 */
	public Octo(int id, Stage stage, short clip_x, short clip_y, short level)
	{
		super(id,stage, clip_x, clip_y, level, NEUTRAL_SPRITES);
		init(id);
	}
	
	
	/**
	 * 
	 * @param id
	 * @param stage
	 * @param clip_x
	 * @param clip_y
	 * @param level
	 * @param spriteNames
	 */
	public Octo(int id, Stage stage, short clip_x, short clip_y, short level, String[] spriteNames)
	{
		super(id,stage, clip_x, clip_y, level, spriteNames);
		init(id);
	}	
	
	
	/**
	 * 
	 */
	private void init(int id)
	{
//		setFrameSpeed( 30);
		
		setIncrementY( height() );
		
		short xClip = clipX();
		int xPos = (id+1)*width();
		if(xPos > xClip )
			xPos = (xPos%(xClip-width()));
		setX( (short)xPos );
		setClipX((short) (width() + (xClip-width())) );
		setY( (short)(clipY()/2) );
		seed = (short)(Math.random()*10);
		if(seed>5)
			setIncrementX( (short) -incrementX() );
		setPoints(POINTS);
	}
	
	public void act()
	{
		super.act();
		if (getLifeCycle()!=State.Dead)
		{
			if ( x() < 0 || x() > clipX() )
			{
				setIncrementX( (short) -incrementX() );
			}
		
			setX( (short) (incrementX() + x()) );
		}
		
	}
}
