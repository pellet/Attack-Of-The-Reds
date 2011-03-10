package game.Display;

import java.applet.*;
import java.awt.*;
import javax.media.opengl.*;
import game.Aotr;

/** Shows how to deploy an applet using JOGL. This demo must be
    referenced from a web page via an &lt;applet&gt; tag. */

public class InvadersApplet extends Applet
{
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Aotr game;
	private GLCanvas canvas;

  public void init() 
  {
    setLayout(new BorderLayout());
    canvas = new GLCanvas();
    game = new Aotr(this, canvas, canvas);
    canvas.addGLEventListener(new InvadersGL(game));
    canvas.setSize(game.getDimension());
    add(canvas, BorderLayout.CENTER);
    
    canvas.requestFocusInWindow();
  }

  public void start() {
		this.setVisible(true);
		canvas.setVisible(true);
  }

  public void stop() {
		this.setVisible(false);
		canvas.setVisible(false);
  }
}
