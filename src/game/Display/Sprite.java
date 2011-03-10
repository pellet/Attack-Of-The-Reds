package game.Display;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.media.opengl.GL;

import effect.EffectManager;
import game.Display.Enum.Alignment;
import game.Display.InvadersFrame.GfxPriority;
import util.H_DC;

public class Sprite {

	public static final short X = 0;	/*	X Co-ordinate	*/
	public static final short Y = 1;	/*	Y Co-ordinate	*/
	protected static final short MAX_COORD = 2;
	
	//Identity number
	protected final short mId;
	
	//Stage(canvas information)
	private Stage mStage;
	
	//Current Position;
	private short mX, mY;

	//Priority
	protected GfxPriority mPriority;

	//Dimensions
	private short mWidth, mHeight;
	
	//Current hitbox width;
	private short mXClip, mYClip;
	
	//Sprite Cache
	private SpriteCache mSpriteCache;
	protected String[] mSpriteNames;
	
	//Effect Manager
	protected EffectManager mEffects;
	
	//Flags
	protected boolean markedForRemoval;
	private boolean mSolid = true;		//whether the actor has a hitbox
	
	protected int mCurrentFrame;
	
	public Sprite(int id, Stage stage, int x_clip, int y_clip, String[] sprites)
	{
		mSpriteCache = stage.getSpriteCache();
		mEffects = stage.getEffectManager();
		mStage = stage;
		mId = (short) id;
		
		initSpriteNames(sprites, x_clip, y_clip);
		
		mPriority = GfxPriority.BACKGROUND;
		mCurrentFrame = 0;
	}
	

	protected void setSpriteNames(String[] names){	mSpriteNames = names;	}
	private void initSpriteNames(String[] names, int j, int k )
	{
		setSpriteNames(names);
		mHeight = 0;
		mWidth = 0;
		
		//Get width,height from first sprite frame
		BufferedImage image = mSpriteCache.getSprite(mSpriteNames[0]);
		setHeight( (short)Math.max(height(), image.getHeight()) );
		setWidth( (short)Math.max(width(), image.getWidth()) );
		
		mXClip = (short) (j-mWidth-1);
		mYClip = (short) (k-mHeight-1);
	}
	
	public void remove()
	{
		markedForRemoval = true;
	}
	public boolean isMarkedForRemoval()
	{
		return markedForRemoval;
	}
	
	public boolean SOLID(){ return mSolid; }
	protected void setSolid(boolean solid){	mSolid = solid;	}
	
	public short x(){	return mX;	}
	public void setX(int i){	mX = (short) i;	}
	
	public short y(){	return mY;	}
	public void setY(int i){	mY = (short) i;	}
	public int getCoord(short axis)
	{
		if(axis==X)
			return x();
		else
			return y();
	}
	public void setCoord(short axis, short value)
	{
		if(axis==X)
			setX(value);
		else
			setY(value);
	}
	
	public void collision(Sprite a)
	{
		//Do nothing on collision
	}
	
	public short height()
	{
		return mHeight;
	}
	public void setHeight(short height)
	{
		this.mHeight = height;
	}
	public short width()
	{
		return mWidth;
	}
	public void setWidth(short width)
	{
		this.mWidth = width;
	}
	
	public Rectangle getBounds()
	{
		return new Rectangle(x(), y(), width(), height());
	}
	
	public void paint(H_DC dc, InvadersFrame i)
	{
		GL gl = dc.getGL();
		
		gl.glPushMatrix();	//Restrict affect of glScale and glRotate to the rectangle.
		{
			//Translate to current position
			gl.glTranslated(-mX-width(), -mY-height(), 0);
			String textureName=null;
			try
			{
				textureName = mSpriteNames[mCurrentFrame];
			}catch(Exception ArrayIndexOutOfBoundsException)
			{
				System.err.println("Invalid frame number "+mCurrentFrame+" for sprite "/*+nameOf(mSpriteNames)*/);
				System.exit(-1);
			}
				
			if(textureName!=null)//If a texture exists for this frame create a poly and texture otherwise nothing
			{
				mSpriteCache.bindTexture(textureName, dc);
				//Create and texture model
				createModel(dc);
			}
		
		}gl.glPopMatrix();	//Restrict affect of glScale and glRotate to the rectangle.
	}
	
	public void setPriority(GfxPriority prio)
	{
		mPriority = prio;
	}
	public GfxPriority priority()
	{
		return mPriority;
	}
//	public int priorityNum()
//	{
//		return mPriority.ordinal();
//	}
	

	public short clipX() {	return mXClip;	}
	public void setClipX(short clip) {	mXClip = clip;	}
	
	public short clipY() {	return mYClip;	}
	public void setClipY(short clip) {	mYClip = clip;	}
	
	public int clipCoordinate(short axis)
	{
		if(axis==X)
			return clipX();
		else
			return clipY();
	}

	public Stage stage() {
		return mStage;
	}

	public void setStage(Stage stage) {
		mStage = stage;
	}
	
	
	protected void createModel(H_DC dc)
	{
		float width, height;
		String textureName = mSpriteNames[mCurrentFrame];
		width = mSpriteCache.getSprite(textureName).getWidth();
		height = mSpriteCache.getSprite(textureName).getHeight();
			
		GL gl = dc.getGL();
		
		float[] backGround = new float[]{0f, 0f, 0f, 1.0f};
		
		//Texture Parameters
		gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_CLAMP);
	    gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_CLAMP);
	    gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER,
	        GL.GL_NEAREST);
	    gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER,
	        GL.GL_NEAREST);
//TODO:Set a texture mode not affected by the particle lights.	    
	    gl.glTexEnvf(GL.GL_TEXTURE_ENV, GL.GL_TEXTURE_ENV_MODE, GL.GL_DECAL);
	    
		//Alpha textures
		gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE);  //define blending factors
		
		float material2[] = {0f, 0f, 0f, 1f};		//Material type
		gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_AMBIENT_AND_DIFFUSE, material2, 0);

		//Back to 0 emission colour.
		gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_EMISSION,
				backGround
				,0);
		gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_SPECULAR, backGround,0);
		
		//Draw square and texture it
		gl.glBegin(GL.GL_QUADS);
//		gl.glColor4f(0f, 0f, 0f, 1f);
		int priority = priority().ordinal();
		//top left
	    gl.glTexCoord2d(1f, 1f);gl.glVertex3d(0f, 0f, priority);
	    //top right
	    gl.glTexCoord2d(0f, 1f);gl.glVertex3d(width,0f, priority);
	    //bottom right
	    gl.glTexCoord2d(0f, 0f);gl.glVertex3d(width, height, priority);
	    //bottom left
	    gl.glTexCoord2d(1f, 0f);gl.glVertex3d(0f, height, priority);
	    gl.glEnd();
	    
	    
	}
	
	public void act()
	{
	}
	
	public void setAlignment(Alignment alignment)
	{
		switch(alignment)
		{
			case CENTER:
			{
				Dimension dimension		=	this.stage().getDimension();
				int x	=	dimension.width/2	-	this.width()/2;
				int y	=	dimension.height/2	-	this.height()/2;
				setX(x);
				setY(y);
			}break;
		}
	}
	
//	
//	protected void createQuadric(H_DC dc)
//	{
//		GL gl = dc.getGL();
//		GLU glu = dc.glu;
//		String textureName = mSpriteName;
//		
//		//Rotate Z axis 
//		gl.glRotatef(90f, 1, 0, 0);
//		
////		//New quadric object
//		GLUquadric quadric = glu.gluNewQuadric();
//		//Turn on the texture mode for quadrics
//		glu.gluQuadricTexture(quadric, true);
//		//sphere render
//		glu.gluQuadricDrawStyle(quadric, GLU.GLU_FILL);
//		//Create solid Sphere and draw the textures on it.
//
//
////		glut.glutSolidCube(mSpriteCache.getSprite(textureName).getWidth());
//		glu.gluSphere(quadric,  mSpriteCache.getSprite(textureName).getWidth(),  3,  15);
//		int width = mSpriteCache.getSprite(textureName).getWidth();
//		glu.gluOrtho2D(0, 0, width, width);
//		//Delete the quadric object
//		glu.gluDeleteQuadric(quadric);
//	}
}
