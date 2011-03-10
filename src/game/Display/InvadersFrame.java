package game.Display;

import java.applet.Applet;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.media.opengl.GL;
import javax.swing.JFrame;
import com.sun.opengl.util.GLUT;

import game.Actor.Actor;
import game.Actor.Player;
import game.Enum.GameState;
import highscore.Score;
import effect.EffectManager;
import util.H_DC;


public class InvadersFrame implements Stage
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 2D Sprite graphic layers
	 */
	public enum GfxPriority
	{
		BULLET,
		PLAYER,
		EFFECT,
		MONSTER,
		MONSTER2,
		BACKGROUND,
		
		TOTAL_PRIORITY,
	};
	
	
	protected final int PrioritySize = GfxPriority.TOTAL_PRIORITY.ordinal()+1;
	protected long 	mMonstersRemainingDisplay;
	protected int	mCurrentScoreDisplay;
	protected short mCurrentLevelDisplay;
	protected short	mCurrentLivesDisplay;
	private SpriteCache spriteCache;
	protected short X_CLIP;
	protected short Y_CLIP;
	
	protected ArrayList<Sprite> mSprites[];			//List containing all sprites
	protected EffectManager mEffects;				//Effects manager.

	protected Score[] mHighScores;

	private Player mPlayer;
	
	/**
	 * @param player the mPlayer to set
	 */
	public void setPlayer(Player player)
	{
		this.mPlayer = player;
	}


	protected GameState gameState;
	
	private int gameStateAge;
  
	
	/**
	 * Takes either a JFrame or a Applet
	 * @param frame
	 */
	public InvadersFrame(Component frame)
	{
	    assert(frame instanceof Applet || frame instanceof JFrame);
	    
		X_CLIP = Stage.WIDTH;
		Y_CLIP = Stage.HEIGHT;
	    frame.setSize(X_CLIP, Y_CLIP);
	
	    //center the JFrame on the screen
//	    centerWindow(this);
	    
		spriteCache = new SpriteCache();
		frame.setBounds(0, 0, X_CLIP, Stage.HEIGHT);
		frame.requestFocus();

	}
	
	
//	  public void centerWindow(Component frame)
//	  {
//		    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//		    Dimension frameSize  = frame.getSize();
//		
//		    if (frameSize.width  > screenSize.width ) frameSize.width  = screenSize.width;
//		    if (frameSize.height > screenSize.height) frameSize.height = screenSize.height;
//		
//		    frame.setLocation (
//		      (screenSize.width  - frameSize.width ) >> 1, 
//		      (screenSize.height - frameSize.height) >> 1
//		    );
//	  }


	
	/**
	 * Add a sprite to be drawn.
	 */
	public void addSprite(Sprite sprite)
	{
		int priority = sprite.priority().ordinal();
		ArrayList<Sprite> mTList;
		mTList = mSprites[priority];
		mTList.add(sprite);
		mSprites[priority] = mTList;
	}

	
	/**
	 * Paint function
	 * @param dc
	 */
	public void paintWorld(H_DC dc)//Composite actors from lowest to highest priority
	{
		GL gl = dc.getGL();
		GLUT glut = dc.glut;

	    //Notice the depth buffer bit is also being cleared.
	    gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

		/**
		 * Draw actors in order of priority
		 */
		if(mSprites!=null)
		{
			for(int priority=GfxPriority.TOTAL_PRIORITY.ordinal()-1;priority>-1;priority--)
			{
				ArrayList<Sprite>TList = mSprites[priority];
				for(int i=0;i<TList.size();i++)
				{
					Sprite a = TList.get(i);
					a.paint(dc, this);
				}
			}
		}

		gl.glDisable(GL.GL_BLEND);
		gl.glDisable(GL.GL_TEXTURE_2D);
		/**
		 * Draw particles
		 */
		mEffects.drawParticles(dc);
		
		switch(this.getGameState())
		{
			case INTRO:
			{
				//do nothing
			}break;
			
			case GAME_OVER:
				{
					drawGameOver(gl, glut);
				}break;
				
			case SCORE:
				{
				    drawScore(gl, glut);
				}break;

			default:
				if(mCurrentLevelDisplay>0)
				{
					drawPlayerStatus(gl, glut);
				}break;
		}
		gl.glEnable(GL.GL_TEXTURE_2D);
		gl.glEnable(GL.GL_BLEND);
	}
	
	
	/**
	 * 
	 * @param gl
	 * @param glut
	 */
	private void drawGameOver(GL gl, GLUT glut)
	{
	    int font = GLUT.BITMAP_HELVETICA_18;

		float[] white = new float[]{1f, 1f, 1f, 1.0f};

		//Back to 1 emission colour.
		gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_EMISSION,
				white
				,0);
		gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_SPECULAR, white,0);
		
		//TODO: find out why this fixes the font from being affected on my ATI card
		gl.glRasterPos3i(1, -Stage.HEIGHT, 0);
	    glut.glutBitmapString(font, " ");//reset this?
	    
//	    if(mHighScores!=null)
	    {
		    int rasterX = 	-235;//-135;
		    int rasterY	=	-Stage.HEIGHT+70;//250;
//		    for(int i=0;i<mHighScores.length;i++)
		    {
				gl.glRasterPos3i(rasterX, rasterY, 0);
				glut.glutBitmapString(font, "Your score is: "+this.getPlayer().getScore());//+mHighScores[i].score+" \t Picture: "+mHighScores[i].facebookId);
				rasterY += 20;
				
				gl.glRasterPos3i(rasterX, rasterY, 0);
				glut.glutBitmapString(font, "GAME OVER");//: "+mHighScores[i].name);
				rasterY += 35;
		    }
	    }
	}


	
	/**
	 * @param gl
	 * @param glut
	 */
	private void drawPlayerStatus(GL gl, GLUT glut)
	{
	    int font = GLUT.BITMAP_HELVETICA_10;

		float[] backGround = new float[]{1f, 1f, 1f, 1.0f};

		//Back to 1 emission colour.
		gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_EMISSION,
				backGround
				,0);
		gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_SPECULAR, backGround,0);

	    
	    //Positions
	    int score_y = -15;
	    int level_y = -2;
	    
		//TODO: find out why this fixes the font from being affected on my ATI card
		gl.glRasterPos3i(1, -Stage.HEIGHT-score_y, 0);
	    glut.glutBitmapString(font, " ");//reset this?
	    
		gl.glRasterPos3i(1, -Stage.HEIGHT-score_y, 0);
		glut.glutBitmapString(font, "Score:\t"+mCurrentScoreDisplay);
		gl.glRasterPos3i(1, -Stage.HEIGHT-level_y, 0);
		glut.glutBitmapString(font, "Level:\t"+mCurrentLevelDisplay+"\t \t"+mMonstersRemainingDisplay+" monsters left \t \t"+mCurrentLivesDisplay+" lives left");
		
	}

	
	/**
	 * 
	 * @param gl
	 * @param glut
	 */
	private void drawScore(GL gl, GLUT glut)
	{

	    int font = GLUT.BITMAP_HELVETICA_10;

		float[] backGround = new float[]{0f, 0f, 0f, 1.0f};

		//Back to 1 emission colour.
		gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_EMISSION,
				backGround
				,0);
		gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_SPECULAR, backGround,0);
		
		//TODO: find out why this fixes the font from being affected on my ATI card
		gl.glRasterPos3i(1, -Stage.HEIGHT, 0);
	    glut.glutBitmapString(font, " ");//reset this?
	    
	    if(mHighScores!=null)
	    {
		    int rasterX = -205;//-235;//-135;
		    int rasterYStart	=	-Stage.HEIGHT+40;//+70;//250;
		    for(int i=0, rasterY=rasterYStart;i<mHighScores.length;i++)
		    {
				gl.glRasterPos3i(rasterX, rasterY, 0);
				glut.glutBitmapString(font, "Score: "+mHighScores[i].score+" \t Picture: "+mHighScores[i].facebookId);
				rasterY += 20;
				
				gl.glRasterPos3i(rasterX, rasterY, 0);
				glut.glutBitmapString(font, "Name: "+mHighScores[i].name);
				rasterY += 30;
		    }
	    }
	}
	
	/**
	 * Current Effects Manager
	 */
	public EffectManager getEffectManager()
	{
		return mEffects;
	}
	
	

	/**
	 * 
	 */
	public SpriteCache getSpriteCache()
	{
		return spriteCache;
	}
	
	public Player getPlayer()
	{
		return mPlayer;
	}
	

	/* (non-Javadoc)
	 * @see game.Stage#decrementActorCount(game.Actor.Actor)
	 */
	public void decrementActorCount(Actor a)
	{
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see game.Stage#isDebugMode()
	 */
	public boolean isDebugMode()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see game.Stage#setDebugMode(boolean)
	 */
	public void setDebugMode(boolean debugMode)
	{
		// TODO Auto-generated method stub
		
	}

	/**
	 * @return the mCurrentLevelDisplay
	 */
	public short getCurrentLevel()
	{
		return this.mCurrentLevelDisplay;
	}
	
	/**
	 * 
	 * @param state
	 */
	public void setGameState(GameState state) {
		this.gameState = state;
		this.gameStateAge = 0;
	}
	
	/**
	 * 
	 * @return
	 */
	public GameState getGameState()
	{
		return this.gameState;
	}


	/**
	 * 
	 */
	public void incrementGameStateAge()
	{
		this.gameStateAge++;
	}
	

	/**
	 * @return
	 */
	public int getGameStateAge()
	{
		// TODO Auto-generated method stub
		return this.gameStateAge;
	}
	
	/**
	 * 
	 */
	public Dimension getDimension()
	{
		return new Dimension(this.width(), this.height());
	}
	
	public int	width()
	{
		return this.X_CLIP;
	}
	
	public int	height()
	{
		return this.Y_CLIP;
	}
}