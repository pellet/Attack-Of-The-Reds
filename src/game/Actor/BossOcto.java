/**
 * 
 */
package game.Actor;
import game.Display.Stage;

/**
 * @author pettit
 *
 */
public class BossOcto extends ParaOcto
{
	
	protected final static String[] NEUTRAL_SPRITES = new String[] {"bigOcto0.gif", "bigOcto1.gif"};
	protected static final short MONSTER_HEALTH = 200;
	protected static final short INITIAL_Y = 0;
	protected static final int		POINTS = 200;
	
	/**
	 * @param i
	 * @param stage
	 * @param clip_x
	 * @param clip_y
	 * @param level
	 */
	public BossOcto(int i, Stage stage, short clip_x, short clip_y, short level)
	{
		super(i, stage, clip_x, clip_y, level, NEUTRAL_SPRITES);
		setPoints(POINTS);
		setHealth(mHealth = (short) (MONSTER_HEALTH+((level*2)*mDna)));
		setY(INITIAL_Y);
	}

	/**
	 * 
	 */
	public void act()
	{
		super.act();
	}

}
