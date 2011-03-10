/**
 * 
 */
package util;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;

import com.sun.opengl.util.GLUT;

/**
 * @author pettit
 *
 */

public class H_DC
{
	public GLUT glut;
	private GL gl;
	public GLU glu;
	public float width;
	public float height;
	public float x;
	public float y;
	private Camera mCamera;
	
	public H_DC(Camera camera)
	{
		mCamera = camera;
	}
	/**
	 * @param gl the gl to set
	 */
	public void setGL(GL gl)
	{
		this.gl = gl;
	}
	/**
	 * @return the gl
	 */
	public GL getGL()
	{
		return gl;
	}
	
	public Camera getCamera()
	{
		return mCamera;
	}
}