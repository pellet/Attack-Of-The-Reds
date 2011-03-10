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

import math.Quaternion4f;
import math.Vector3f;

/**
 * Cone emitter along z direction
 */
public class ParticleConeEmitter implements ParticleSystem<MovingParticle>
{
	private MovingParticle[] particles = null;
	public MovingParticle[] getParticles() { return particles; }

	private Cone cone;
	public ParticleConeEmitter(int n, Cone cone)
	{
		this.cone = cone;
		this.particles = new MovingParticle[n];
		for(int i = 0; i < particles.length; i++)
		{
			particles[i] = new MovingParticle();
			regenerateParticle(particles[i]);
		}
	}

	private void regenerateParticle(MovingParticle p)
	{
		//Generate random particle
		float cutOff   = (float)Math.random() * cone.getCutOff();
		float angle    = (float)Math.random() * 2 * (float)Math.PI;
		float speed    = (float)Math.random()* 5f + 1.5f;

		//Particle direction
		Quaternion4f rot =
				cone.getOrientation().clone().
				multiply(Quaternion4f.fromAxisAngle(0, 0, 1, angle)).
				multiply(Quaternion4f.fromAxisAngle(1, 0, 0, (float)Math.toRadians(cutOff)));

		//Calculate position/speed
		p.position = cone.getPosition().clone();
		p.speed = rot.rotate(new Vector3f(0, 0, speed));
	}

	public void update(long timePassed)
	{
		for(int i = 0; i < particles.length; i++)
		{
			MovingParticle p = particles[i];
			//Update particle
			p.update(timePassed);
			//Re-generate particle ?
			if(p.position.getY() < 0)
				regenerateParticle(p);
		}
	}
}
