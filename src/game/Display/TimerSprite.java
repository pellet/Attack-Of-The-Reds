package game.Display;


public class TimerSprite extends Sprite
{
	static final int DEFAULT_FRAME_SPEED = 100;
	
	//Current frame time
	private int mTime;
	
	private int frameSpeed;

	public TimerSprite(int id, Stage stage, short clip_x, short clip_y, String[] sprites)
	{
		super(id, stage, clip_x, clip_y, sprites);
		setFrameSpeed(DEFAULT_FRAME_SPEED);
	}
	
	public int getFrame()
	{
		return mTime;
	}
	
	public int getFrameSpeed()
	{
		return frameSpeed;
	}
	public void setFrameSpeed(int speed)
	{
		frameSpeed = speed;
	}
	
	public void act()
	{
		mTime++;
		if (mTime % frameSpeed == 0)
		{
			mTime = 0;
			mCurrentFrame = (mCurrentFrame + 1) % mSpriteNames.length;
//			if(mCurrentFrame==1)
//				System.out.println("mCurrentFrame = "+mCurrentFrame);
		}
	}

}
