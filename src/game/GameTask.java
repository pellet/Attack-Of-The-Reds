/**
 * 
 */
package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.TimerTask;
import javax.media.opengl.GLCanvas;

import game.Display.Sprite;
import game.Display.Enum.Alignment;
import game.Enum.GameState;
import highscore.XMLReader;

/**
 * @author pettit
 *
 */
public class GameTask extends TimerTask  implements KeyListener
{

	protected Aotr game;
	protected GLCanvas glcanvas;
//TODO:Remove	protected long timeExpired;
	protected static final long INTRO_LENGTH = 250;
//TODO:Remove	protected static final long START_GAME = 500;
	private static final long	GAME_OVER_LENGTH	=	500;
	
	protected final String[] titleSprites = {"title.png"};
	protected final String[] scoreSprites = {"score.gif"};
	
	
	/**
	 * 
	 * @param game
	 * @param glcanvas
	 */
	GameTask(Aotr game, GLCanvas glcanvas)
	{
		this.game 		= game;
		this.glcanvas 	= glcanvas;
//TODO:remove		timeExpired = 0;
		game.setGameState( GameState.INTRO );
	}
	
	
	/* (non-Javadoc)
	 * @see java.util.TimerTask#run()
	 */
	@Override
	public void run()
	{		
		//Set paused when canvas not active(focused)
		if(glcanvas.isVisible())// && game.isActive())
		{
			game.incrementGameStateAge();
//TODO:remove			timeExpired++;
			
			GameState state = game.getGameState();
			
			switch(state)
			{
				/**
				 * 10 second title intro
				 */
				case INTRO:
				{
					if(game.getGameStateAge()==1)
					{
						game.clearGfx();
						Sprite title = new Sprite(0, game, 0, 0, titleSprites);
						title.setAlignment(Alignment.CENTER);
						game.addSprite(title);
					}else if(game.getGameStateAge()==INTRO_LENGTH)//10 seconds
					{
						game.setGameState( GameState.SCORE );
					}
					glcanvas.repaint();
				}break;
				
				/**
				 * 10 second score display
				 */
				case SCORE:
				{
					if(game.getGameStateAge()==1)
					{
						game.clearGfx();
						Sprite score = new Sprite(0, game, 0, 0, scoreSprites);
						score.setAlignment(Alignment.CENTER);
						score.setX(score.x()-25);
						game.addSprite(score);
						
						//Load scores from xml.
						XMLReader scores = new XMLReader();
						game.setScores(scores.getScores());
					}else if(game.getGameStateAge()==INTRO_LENGTH)//10 seconds
					{
						game.setGameState( GameState.INTRO );
					}
					glcanvas.repaint();
				}break;
				
				/**
				 * Main game loop
				 */
				case GAME:
				{
					game.updateWorld();
					game.checkCollisions();
					glcanvas.repaint();
				}break;
				
				/**
				 * 10 second score display
				 */
				case GAME_OVER:
				{
					if(game.getGameStateAge()==1)
					{
						game.clearGfx();
//						Sprite score = new Sprite(0, game, 0, 0, scoreSprites);
//						score.setAlignment(Alignment.CENTER);
//						score.setX(score.x()-25);
//						game.addSprite(score);
						
						//Load scores from xml.
//						XMLReader scores = new XMLReader();
//						game.setScores(scores.getScores());
					}else if(game.getGameStateAge()==GAME_OVER_LENGTH)//5 seconds
					{
						game.setGameState( GameState.INTRO );
					}
					glcanvas.repaint();
				}break;
					
				/**
				 * Enter score loop
				 */
//				case ENTER_SCORE:
//				{
//					if(game.getGameStateAge()==1)
//					{
//						game.clearGfx();
//						Sprite title = new Sprite(0, game, 0, 0, scoreSprites);
//						title.setX(0);
//						title.setY(0);
//						game.addSprite(title);
//						
//						//Load scores from xml.
//						XMLReader xml = new XMLReader();
//						Score[] highScores = xml.getScores();
//						
//						int currentScore = game.getPlayer().getScore();
//						for(int i=0;i<highScores.length;i++)
//						{
//							//Did you beat this score?
//							if(currentScore>highScores[i].score)
//							{
//								//Enter Comment?
//								highScores[i].score		 = currentScore;
//								highScores[i].name		 = "Mooley";
//								highScores[i].facebookId = "696969";
//								break;
//							}
//						}
//						game.setScores(highScores);
//					}
//					
//				}break;
			}//end gameState switch
		}
	}
	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	public void keyPressed(KeyEvent arg0)
	{
		GameState gameState = game.getGameState();
		switch(gameState)
		{
			case INTRO:
			case SCORE:
			{
			}break;
			
			case GAME:
			{
				//Pass keys to game
				game.keyPressed(arg0);
			}break;
		}
	}
	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	public void keyReleased(KeyEvent arg0)
	{	
		GameState gameState = game.getGameState();
		switch(gameState)
		{
			case INTRO:
			case SCORE:
			{
//TODO: Remove?				if(timeExpired>START_GAME)
				{
					game.restartGame();
					//Start game
					game.setGameState( GameState.GAME );
				}
			}break;
			
			case GAME:
			case GAME_OVER:
			{
				//Pass keys to game
				game.keyReleased(arg0);
			}break;
		}
		
	}
	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	public void keyTyped(KeyEvent arg0)
	{
//		//Pass keys to game
//		game.keyTyped(arg0);
	}
	
}