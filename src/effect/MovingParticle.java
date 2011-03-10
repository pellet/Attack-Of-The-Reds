/**
 * ========================================
 * Tutorial 15 : Light & Material - Part II
 * ========================================
 *
 * WANT TO CONTACT ME ?
 * @author Jérôme JOUVIE (Jouvieje)
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

import math.Vector3f;

public class MovingParticle implements Particle
{
	protected Vector3f position = null;
	protected Vector3f speed = null;

	public MovingParticle()
	{
		this.position = new Vector3f();
		this.speed = new Vector3f();
	}
	public MovingParticle(Vector3f position, Vector3f speed)
	{
		this.position = position;
		this.speed = speed;
	}

	public void update(long timePassed)
	{
		position.add(speed.clone().multiply(timePassed / 1000.f));
	}

	public Vector3f getPosition(){ return position; }
	public void setPosition(Vector3f position) { this.position = position; }
	public Vector3f getSpeed() { return speed; }
	public void setSpeed(Vector3f speed) { this.speed = speed; }
}
