package game.Display;
import java.awt.Component;
import javax.media.opengl.*;
import javax.media.opengl.glu.*;

import com.sun.opengl.util.GLUT;

import game.Display.InvadersFrame.GfxPriority;
import math.Vector3f;
import util.Camera;
import util.H_DC;
/* 
 * Author: Ben Pettit
 * Program Title: Attack of the Reds
*/


/**
 * For our purposes only two of the 
 * GLEventListeners matter. Those would 
 * be init() and display().
 */
public class InvadersGL implements GLEventListener
{	

	//FIXME:	When  ZDEPTH is set to 570, text dissapears.
	  private static int MAX_Z_DEPTH	= 571;//570;//630;//700;
	  
	  //Current camera Position and View Target.
	  private static int xPosition = 0;
	  private static int yPosition = 0;
	  private static int zPosition = -MAX_Z_DEPTH + GfxPriority.TOTAL_PRIORITY.ordinal();//-525;
	  
	  //GL
	  private GL gl;
	  protected GLU glu;
	  protected GLUT glut;
	  
	  protected H_DC dc;
	  
	  //Game class
	  protected InvadersFrame game;
	  
	  public InvadersGL(InvadersFrame game)
	  {
		  this.game = game;
	  }
  
  /**
   * Take care of initialization here.
   */
  public void init(GLAutoDrawable drawable) {
	  
    gl = drawable.getGL();
    glu = new GLU();
    
    glut = new GLUT();
    
     //We're changing the mode to GL.GL_PROJECTION
    //This is where we set up the camera
    gl.glMatrixMode(GL.GL_PROJECTION);
    //gl.glLoadIdentity();
    
    // Aspect is width/height
    double w = ((Component) drawable).getWidth();
    double h =  ((Component) drawable).getHeight();
    double aspect = w/h;
    //When using gluPerspective near and far need 
    //to be positive.
    //The arguments are:
    //fovy, aspect, near, far
    glu.gluPerspective(60.0, aspect, 2.0, zPosition);
	
	gl.glShadeModel(GL.GL_SMOOTH);							//Smooth color shading
	gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
	gl.glClearDepth(1.0);									//Enable Clearing of the Depth buffer
	gl.glDepthFunc(GL.GL_LEQUAL);							//Type of Depth test
	gl.glEnable(GL.GL_DEPTH_TEST);							//Enable Depth Testing
//	
//	//Define the correction done to the perspective calculation (perspective looks a it better)
	gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
//
    gl.glMatrixMode(GL.GL_MODELVIEW);

    gl.glDisable(GL.GL_DEPTH_TEST);
    gl.glEnable(GL.GL_TEXTURE_2D);
	gl.glEnable(GL.GL_BLEND);
    
    //Lighting
    float position[] = {0f, 15f, -30f, 0f};	//position of light
    gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, position, 0);
    
    float diffuse[] = {.7f, .7f, .7f, 0f};	//red, green, blue
    gl.glLightfv(GL.GL_LIGHT0, GL.GL_DIFFUSE, diffuse, 0);
    
    float ambient[] = {.2f, .2f, .2f, 0f};	//usually similar to light sources
    gl.glLightfv(GL.GL_LIGHT0, GL.GL_AMBIENT, ambient, 0);
    
    gl.glEnable(GL.GL_LIGHTING);	//Enable Lighting
    gl.glEnable(GL.GL_LIGHT0);		//Turn Light0 on.
    
	
    gl.glLoadIdentity();
    
    //Define points for eye, at and up.
    //This is your camera. It ALWAYS goes
    //in the GL_MODELVIEW matrix.
    glu.gluLookAt(
            xPosition, yPosition, zPosition,
            0, 0, 0,
            0, 1, 0
           );
    
    //Intitalise Device Context
    Camera camera = new Camera(new Vector3f(xPosition,yPosition,zPosition), new Vector3f(0,1,0));
    dc = new H_DC(camera);
    dc.setGL(gl);
    dc.glut = glut;
    dc.glu = glu;

	gl.glTranslated(Stage.WIDTH/2, Stage.HEIGHT/2, 0);//Topleft
	
  }
  
  
  /**
   * Take care of drawing here.
   */
  public void display(GLAutoDrawable drawable)
  {

	  game.paintWorld(dc);
   
  }

 
  public void reshape(
                        GLAutoDrawable drawable, 
                        int x, 
                        int y, 
                        int width, 
                        int height
                      ) {}
                      
  
  public void displayChanged(
                              GLAutoDrawable drawable, 
                              boolean modeChanged, 
                              boolean deviceChanged
                            ) {}
  

}