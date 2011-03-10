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

import math.Quaternion4f;
import math.Vector3f;

public class Cone
{
	/** Cone position  */
	private Vector3f position;
	/** Cone direction orientation  */
	private Quaternion4f orientation;
	/** Cone cut-off */
	private float cutOff;

	public Cone(Vector3f position, Quaternion4f q, float cutOff)
	{
		this.position = position;
		this.orientation = q;
		this.cutOff = cutOff;
	}

	public Vector3f getPosition() { return position; }
	public void setPosition(Vector3f pos) { this.position = pos; }

	public Quaternion4f getOrientation() { return orientation; }
	public void setOrientation(Quaternion4f q) { this.orientation = q; }

	public float getCutOff() { return cutOff; }
	public void setCutOff(float cutOff) { this.cutOff = cutOff; }
}
