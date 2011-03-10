package game.Display;

import java.awt.Dimension;

import effect.EffectManager;
import game.Actor.Actor;
import game.Enum.GameState;

public interface Stage
{
	public static final int WIDTH = 600;//400;
	public static final int HEIGHT = 600;//720;//600;
	public static final int SPEED = 10;
	
	public SpriteCache getSpriteCache();
	public void addSprite(Sprite sprite);
	
	public EffectManager getEffectManager();
	
	
	/**
	 * 
	 */
	public void decrementActorCount(Actor a);
	
	
	/**
	 * @return the debugMode
	 */
	public boolean isDebugMode();
	
	public void setDebugMode(boolean debugMode);
	
	public void setGameState(GameState state);
	
	public Dimension getDimension();
	
	public int	width();
	public int	height();
	
}
