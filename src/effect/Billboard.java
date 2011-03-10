/**
 * ========================================
 * Tutorial 15 : Light & Material - Part II
 * ========================================
 *
 * WANT TO CONTACT ME ?
 * @author J�r�me JOUVIE (Jouvieje)
 * @mail   jerome.jouvie@gmail.com
 * @site   http://jerome.jouvie.free.fr/
 *
 * HOW TO RUN ?
 * You need java one of those OpenGL API : Jogl-JSR231,
 * Jogl 'Old' or Gl4java installed on you computer.
 * For more informations, read Tutorial 1 or 2.
 *
 * IMPROVEMENTS
 * If you think this tutorial can be improved, don't
 * hesitate to send me any feedback.
 *
 * LICENSE
 * This code is made for learning purpose, you can use
 * it in anyway you want. Anyway, if it is usefull for
 * you, a small thanks is always welcome.
 *
 * LINKS
 * Java                http://java.sun.com/ or http://ww.java.com/
 * JOGL (JSR231 & Old) https://jogl.dev.java.net/
 * GL4JAVA             http://www.jausoft.com/gl4java.html
 * Eclipse             http://www.eclipse.org/
 */

package effect;

//import org.jouvieje.camera.ViewerCamera;
import javax.media.opengl.GL;

import math.Vector3f;
import util.Camera;
import util.H_DC;
//import org.jouvieje.renderer.IGL;

public class Billboard
{
	protected Vector3f position = null;
//	private float halfSize = 0.05f;
	private float halfSize = 0.5f;
	public Billboard()
	{
		position = new Vector3f();
	}
	public Billboard(Vector3f position)
	{
		this.position = position;
	}

	public void render(H_DC dc)
	{
		GL gl = dc.getGL();
		Camera camera = dc.getCamera();
		
		Vector3f vec = camera.getRotation();
		gl.glPushMatrix();
		gl.glTranslatef(position.getX(), position.getY(), position.getZ());
		gl.glRotatef(-vec.getY(), 0, 1, 0);
		gl.glRotatef(-vec.getX(), 1, 0, 0);
		gl.glBegin(GL.GL_QUADS);
			gl.glTexCoord2d(0, 0); gl.glVertex3f(-halfSize,-halfSize, 0.0f);
			gl.glTexCoord2d(1, 0); gl.glVertex3f( halfSize,-halfSize, 0.0f);
			gl.glTexCoord2d(1, 1); gl.glVertex3f( halfSize, halfSize, 0.0f);
			gl.glTexCoord2d(0, 1); gl.glVertex3f(-halfSize, halfSize, 0.0f);
		gl.glEnd();
		gl.glPopMatrix();
	}

	public Vector3f getPosition(){ return position; }
	public void setPosition(Vector3f position) { this.position = position; }
}
