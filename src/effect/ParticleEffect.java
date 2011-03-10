/**
 * 
 */
package effect;

import math.Vector3f;

/**
 * @author pettit
 *
 */
public class ParticleEffect
{
	protected Vector3f origin;
	protected ParticleSystem<? extends Particle> particleEmitter;

	protected long timePassed;
	protected boolean remove;

	/**
	 * @param origin
	 * @param particleEmitter
	 */
	public ParticleEffect(Vector3f origin,
			ParticleSystem<?> particleEmitter)
	{
		super();
		this.origin = origin;
		this.particleEmitter = particleEmitter;
		this.timePassed = 0;
		this.remove = false;
	}
	
	/**
	 * @return the origin
	 */
	public Vector3f getOrigin()
	{
		return this.origin;
	}
	
	/**
	 * @param origin the origin to set
	 */
	public void setOrigin(Vector3f origin)
	{
		this.origin = origin;
	}
	
	/**
	 * @return the particleEmitter
	 */
	public ParticleSystem<? extends Particle> getParticleEmitter()
	{
		return this.particleEmitter;
	}
	
	/**
	 * @param particleEmitter the particleEmitter to set
	 */
	public void setParticleEmitter(ParticleSystem<LivingParticle> particleEmitter)
	{
		this.particleEmitter = particleEmitter;
	}

	/**
	 * 
	 */
	public void update()
	{
		// TODO Auto-generated method stub
		particleEmitter.update(timePassed);
		timePassed++;
	}

	/**
	 * @return the timePassed
	 */
	public long getTimePassed()
	{
		return this.timePassed;
	}

	/**
	 * 
	 */
	public void markForRemoval()
	{
		// TODO Auto-generated method stub
		remove = true;
	}
	
	public boolean shouldRemove()
	{
		return remove;
	}
}
