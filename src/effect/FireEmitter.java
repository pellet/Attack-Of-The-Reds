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

public class FireEmitter implements ParticleSystem<LivingParticle>
{
	public LivingParticle[] particles = null;
	public LivingParticle[] getParticles() { return particles; }

	private Cone cone;
	private float SPEED;
	private float ACCELERATION;
	public FireEmitter(int n, Cone cone, float speed, float acceleration)
	{
		this.cone = cone;
		this.SPEED = speed;
		this.ACCELERATION = acceleration;
		this.particles = new LivingParticle[n];
		for(int i = 0; i < particles.length; i++)
		{
			particles[i] = new LivingParticle();
			regenerateParticle(particles[i]);
		}
	}

//	private float SPEED = 1.0f;
//	private float SPREAD = Math.toRadians(15.0f);	//15�

	private void regenerateParticle(LivingParticle p)
	{
		//Particle direction
		float spread   = (float)Math.random() * cone.getCutOff();		//Random spread along the cone cuttoff angle
		float angle    = (float)Math.random() * 2 * (float)Math.PI;		//Random rotation angle along the cone direction
		Quaternion4f rot =
				cone.getOrientation().clone().
				multiply(Quaternion4f.fromAxisAngle(0, 0, 1, angle)).
				multiply(Quaternion4f.fromAxisAngle(1, 0, 0, (float)Math.toRadians(spread)));

		//Random
		float speed = (float)Math.random() * SPEED;
		float acceleration = (float)Math.random() * ACCELERATION;

		p.life = 1;
//		p.position = cone.getPosition().clone();
		p.speed = rot.rotate(new Vector3f(0, 0, speed));
		p.acceleration = rot.rotate(new Vector3f(0, 0, acceleration));
	}

	public void update(long timePassed)
	{
		for(int i = 0; i < particles.length; i++)
		{
			LivingParticle p = particles[i];
			//Update particle
			p.update(timePassed);
//			System.out.println(timePassed);
			//Re-generate particle ?
			if(p.getLife() <= 0)
				regenerateParticle(p);
		}
	}
}