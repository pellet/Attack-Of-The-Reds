/**
 * 
 */
package game;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Timer;

import javax.media.opengl.GLCanvas;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import effect.EffectManager;
import game.Actor.Actor;
import game.Actor.BossOcto;
import game.Actor.Curveo;
import game.Actor.Monster;
import game.Actor.Octo;
import game.Actor.ParaOcto;
import game.Actor.Player;
import game.Display.InvadersFrame;
import game.Display.InvadersGL;
import game.Display.Sprite;
import highscore.Score;


/**
 * @author pettit
 *
 */
public class Aotr extends InvadersFrame
{
	private static final long serialVersionUID = 1L;
	

	/**
	 * 
	 */
	protected static final boolean CHEAT_MODE = true;
	private static final int NUM_OF_INIT_MONSTERS = 2;
	private int 	mCurrentMonsters;
	protected static final short LIVES_TO_START_WITH = 3;
	
	private Timer timer;
	
	protected int numOfSprites;
	
	private static GLCanvas glcanvas;

	protected int getNumOfSprites() {
		return numOfSprites;
	}


	protected void setNumOfSprites(int numOfSprites) {
		this.numOfSprites = numOfSprites;
	}

	protected boolean debugMode = false;
	
	
	public static void main(String[] args)
	{
	    //set the JFrame title
		final JFrame frame = new JFrame("Attack Of The Reds");
	    
	    //only three JOGL lines of code ... and here they are
	    glcanvas = new GLCanvas();

	    final Aotr game = new Aotr(frame, glcanvas, frame);
	    
	    //Create OpenGL
	    InvadersGL view = new InvadersGL(game);
	    glcanvas.addGLEventListener(view);
	    
	    //add the GLCanvas just like we would any Component
    	frame.getContentPane().add(glcanvas, BorderLayout.CENTER);
	    //kill the process when the jframe is closed
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
	    // show what we've done
	    SwingUtilities.invokeLater (
	      new Runnable()
	      {
	        public void run()
	        {
	        	glcanvas.requestFocusInWindow();
	        	frame.setVisible(true);
	        }
	      }
	    );
	}
	
	
	/**
	 * @param canvas 
	 * @param applet 
	 * 
	 */
	public Aotr(Component frame, GLCanvas glcanvas, Component keylistener)
	{	
		super(frame);
		
		//Restart Game
		this.restartGfx();
		
		GameTask tasker = new GameTask(this, glcanvas);
		
	    //Create game process timer.
	    timer = new Timer();
	    //we schedule the Timer to wake up
	    timer.scheduleAtFixedRate(tasker, 0, SPEED);

		//Controls
		keylistener.addKeyListener(tasker);
		
		mHighScores = null;
	}
	
	
	public void clearGfx()
	{
		//Initialise sprite list.
		for(int i=0;i<PrioritySize;i++)
		{
			mSprites[i].clear();
		}
		//Initialise Effects Manager.
		mEffects.clear();
	}
	
	protected void addSprite(GfxPriority p, Sprite s)
	{
		mSprites[p.ordinal()].add(s);
		numOfSprites++;
	}
	
	/**
	 * 
	 */
	public void initWorld()
	{
		//Clear all sprites and effects.
		clearGfx();
		
		int nextLevel = getCurrentLevel() % 5;
		final int BOSS_LEVEL = 0;
		
		setNumOfSprites(0);
		//Player is created elsewhere
		addSprite(getPlayer().priority(), getPlayer());
		getPlayer().spawn();
		
		switch(nextLevel)
		{
			case BOSS_LEVEL:
			{
				makeBossLevel();
			}break;
			
			default:
			{
				makeStandardLevel();
			}break;
		}
		
		mMonstersRemainingDisplay 	= mCurrentMonsters;
		
	}
	
	/**
	 * 
	 */
	protected void makeBossLevel()
	{
		Monster monster = new BossOcto(getNumOfSprites(),this, X_CLIP, Y_CLIP, getCurrentLevel());
		addSprite(monster.priority(), monster);
		mCurrentMonsters 			= getNumOfSprites()-1;
	}
	
	/**
	 * 
	 */
	protected void makeStandardLevel()
	{	
//		Add all monsters according to level and rate
		int initialMonsters = NUM_OF_INIT_MONSTERS * (1+(getCurrentLevel()*NUM_OF_INIT_MONSTERS));
		
		for ( int i=0; i < initialMonsters; i++ )
		{
			Monster monster;
//			Use prime numbers to allocate different enemy classes.
			if(i%5==0)
			{
				monster = new ParaOcto(getNumOfSprites(),this, X_CLIP, Y_CLIP, getCurrentLevel());
				addSprite(monster.priority(), monster);
			}
			if(i%7==0)
			{
				monster = new Curveo(getNumOfSprites(),this, X_CLIP, Y_CLIP, getCurrentLevel());
				addSprite(monster.priority(), monster);
			}
			if(i%3==0)
			{
				monster = new Octo(getNumOfSprites(),this, X_CLIP, Y_CLIP, getCurrentLevel());
				addSprite(monster.priority(), monster);
			}
		}

		mCurrentMonsters 			= getNumOfSprites()-1;
	}
	
	
	protected void updateWorld()//Update actors from highest to lowest priority.
	{
		for (int priority = 0; priority < GfxPriority.BACKGROUND.ordinal(); priority++) try
		{
			ArrayList<Sprite> TList = (ArrayList<Sprite>)mSprites[priority];
			for(int i=0;i<TList.size();i++)
			{
				Sprite sprite = TList.get(i);
				if (sprite.isMarkedForRemoval())
				{
					TList.remove(sprite);
					if(sprite instanceof Monster)
					{
						mCurrentMonsters--;
					}
				}else
				{
					//sprite update
					sprite.act();
				}
			}
			//particle update
			mEffects.update();
		}catch(Exception e){
			System.err.println("ActorList crash.\n");
			e.printStackTrace();
			System.exit(-1);
		}
		
		//No monsters left, increment level counter.
		if(mCurrentMonsters<1)
		{
			mCurrentLevelDisplay++;
			initWorld();
		}
		
		//Update display values.
		mCurrentScoreDisplay 		= this.getPlayer().getScore();
		mCurrentLivesDisplay		= this.getPlayer().getCurrentLives();
	}
	
	
	protected void checkCollisions()
	{
		int collisionList[]= { GfxPriority.PLAYER.ordinal(), GfxPriority.BULLET.ordinal() };
		int collisionList2[]= { GfxPriority.MONSTER.ordinal(), GfxPriority.MONSTER2.ordinal(), GfxPriority.BULLET.ordinal() };
		for (int priority=0;priority<collisionList.length;priority++)//Check for bounding box collisions.
		{
			for (int priority2=0; priority2<collisionList2.length; priority2++)
			{
				int p1 = collisionList[priority];
				int p2 = collisionList2[priority2];
				if(p1!=p2)
				{
					for(int i=0;i<mSprites[p1].size();i++)
					{	
						Sprite a = mSprites[p1].get(i);
						Rectangle aHitBox = a.getBounds();
						
						for(int j=0;j<mSprites[p2].size();j++)
						{
							Sprite b = mSprites[p2].get(j);
							Rectangle bHitBox = b.getBounds();
							
							if (aHitBox.intersects(bHitBox) && a.SOLID() && b.SOLID())
							{
								a.collision(b);
								b.collision(a);
							}
						}
					}
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private void restartGfx()
	{
		mSprites = new ArrayList[PrioritySize];
		for(int i=0;i<PrioritySize;i++)//Initialise actor list.
		{
			mSprites[i] = new ArrayList<Sprite>();
		}
		mEffects = new EffectManager();//Initialise Effect Manager.
	}
	
	

	public void decrementActorCount(Actor a)
	{
		if(a instanceof Monster)
		{
			Monster m	=	(Monster)	a;
			mMonstersRemainingDisplay--;
			getPlayer().addToScore( m.getPoints() );
		}
	}
	
	public void keyPressed(KeyEvent e)
	{
		final char RESET_KEY 	= 'r';
		final char DEBUG_KEY 	= 'd';
		final char LEVEL_UP		= 'l';
		
		char key = e.getKeyChar();
		switch(key)
		{
			case RESET_KEY:
			{
				restartGame();
			}break;
			
			case DEBUG_KEY:
			{
				boolean debugMode = !this.isDebugMode();
				this.setDebugMode(debugMode);
				
			}break;
			
			case LEVEL_UP:
			{
				if(isDebugMode())
				{
					restartLevel(10);
				}
			}break;
			
			default:
			{
				getPlayer().keyPressed(e);
			}break;
		}
	}
	public void keyReleased(KeyEvent e)
	{
		this.getPlayer().keyReleased(e);
	}
	
	protected void setLevel(int l)
	{
		mCurrentLevelDisplay=(short) l;
	}

	private void restartLevel(int level)
	{
		setLevel(level);
		//Initialize game world
		initWorld();
	}
	/**
	 * 
	 */
	public void restartGame()
	{
		this.setPlayer( new Player((short) 0,this, X_CLIP, Y_CLIP, LIVES_TO_START_WITH) );
		restartLevel(1);
	}
	
	/**
	 * @return the debugMode
	 */
	public boolean isDebugMode()
	{
		return debugMode;
	}
	
	public void setDebugMode(boolean debugMode)
	{
		this.debugMode = debugMode;
	}


	public void setScores(Score[] scores) 
	{
		mHighScores = scores;
	}


}
