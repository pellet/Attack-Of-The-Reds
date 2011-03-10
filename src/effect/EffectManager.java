/**
 * 
 */
package effect;

import java.util.ArrayList;
import javax.media.opengl.GL;

import math.Quaternion4f;
import math.Vector3f;
import math.VectorFunctions;
import util.Camera;
import util.H_DC;


/**
 * @author pettit
 *
 */
public class EffectManager
{
	public static final int MAX_EFFECT_TIME = 150;
	
	public enum Effect
	{
		SPARK_UPWARDS,
		BLUE_FLAME,
		EXPLODE,
		SPARK,
		
		ALL_EFFECTS,
	}
	
	protected boolean activeEffects[];
	
	public EffectManager()
	{
//		effectList = new 
	}
	
	protected ArrayList <ParticleEffect> mEffectList;
	
	public boolean create(Effect e, Vector3f position)
	{
//		int index;
		if(mEffectList==null)
		{
			activeEffects = new boolean[Effect.ALL_EFFECTS.ordinal()];
			mEffectList = new ArrayList<ParticleEffect>();
		}
		activeEffects[e.ordinal()] = true;
		switch(e)
		{
			case SPARK_UPWARDS:
			{
				Cone fireCone = new Cone(
						new Vector3f(0.0f, 0.05f, 0.0f),
						Quaternion4f.fromAxisAngle(1, 0, 0, - (float)Math.PI / 2),
						15
				);
				FireEmitter fireEmitter = new FireEmitter(100, fireCone, 2.0f, 0.5f);
				ParticleEffect pe = new ParticleEffect(position, fireEmitter);
				
				mEffectList.add(pe);
			}return true;
			
			case BLUE_FLAME:
			{
				//Spot
				float spotAngle = 35.0f;
				Cone spotCone = new Cone(
						new Vector3f(2.5f*10, 3.5f*10, 0),
						Quaternion4f.fromAxisAngle(1, 0, 0, (float)Math.toRadians(spotAngle)),
						5
				);
				ParticleConeEmitter spotEmitter = new ParticleConeEmitter(100, spotCone);
				ParticleEffect pe = new ParticleEffect(position, spotEmitter);
				
				mEffectList.add(pe);
			}return true;
			
			case EXPLODE:
			{
				Cone fireCone = new Cone(
						new Vector3f(0.0f, 0.0f, 0.0f),
						Quaternion4f.fromAxisAngle(0, 0, 1, - (float)Math.PI / 2),
						15*10
				);
				FireEmitter fireEmitter = new FireEmitter(100, fireCone, 2.0f*90, 0.5f*2);
				ParticleEffect pe = new ParticleEffect(position, fireEmitter);
				
				mEffectList.add(pe);
			}return true;
			

			case SPARK:
			{
				Cone fireCone = new Cone(
						new Vector3f(0.0f, 0.05f, 0.0f),
						Quaternion4f.fromAxisAngle(1, 0, 0, - (float)Math.PI / 2),
						15
				);
				FireEmitter fireEmitter = new FireEmitter(25, fireCone, 2.0f, 0.5f);
				ParticleEffect pe = new ParticleEffect(position, fireEmitter);
				
				mEffectList.add(pe);
			}return true;
		}
		return false;
	}
	
	/**
	 * @param splodes
	 * @param i
	 * @param j
	 */
	public void create(Effect e, int i, int j)
	{
		// TODO Auto-generated method stub
		create(e, new Vector3f(i,j,0));
	}
	
	public boolean clear()
	{
		//TODO: Clear effect
		if(mEffectList!=null)
			mEffectList.clear();
		mEffectList = null;
		return true;
	}

	/**
	 * @param currentTime
	 */
	public void update()
	{
		// TODO Auto-generated method stub
		if(mEffectList!=null)
		{
			int i=0;
			ArrayList<Integer> shouldRemove = new ArrayList<Integer>();
			for(ParticleEffect e : mEffectList )
			{
				e.update();
				if(e.getTimePassed()>MAX_EFFECT_TIME)
				{
					shouldRemove.add(new Integer(i));
				}
			}
			for(Integer index: shouldRemove)
			{
				mEffectList.remove(index.intValue());
			}
		}
	}
	
	
	private final float black[] = {0.0f, 0.0f, 0.0f, 1.0f};
//	private final float white[] = {1.0f, 1.0f, 1.0f, 1.0f};
	private final float softWhite[] = {0.1f, 0.1f, 0.1f, 1.0f};
	private final float grey[] = {0.5f, 0.5f, 0.5f, 1.0f};
	private final float blue[] = {0.0f, 0.0f, 1.0f, 1.0f};
	private final float fullFire[] = {1.0f, 0.5f, 0.1f, 1.0f};
	//define different sprite names for different actions
//	private static final String[] FIRE_SPRITE = new String[] {"Effect12.png"};

	@SuppressWarnings("unchecked")
	public void drawParticles(H_DC dc)
	{
		GL gl = dc.getGL();
		
		if(mEffectList!=null)
		{
			//Make a copy of the effect list so effects can be added seperate to this draw loop.
			
			ArrayList <ParticleEffect> effectList = (ArrayList<ParticleEffect>) mEffectList.clone();
			
			for( ParticleEffect effect : effectList )
			{
				ParticleSystem<? extends Particle> emitter = effect.getParticleEmitter();
				Vector3f origin = effect.getOrigin();
				gl.glPushMatrix();
				{
					gl.glTranslated(-origin.getX(), -origin.getY(), 0);
					
					if(emitter instanceof FireEmitter)
					{	
						/* FIRE EMITTER */
						//Fire particles emits light (emission) and are not affected by the other lights (no ambient/diffuse/specular)
						gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_AMBIENT, black,0);
						gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_DIFFUSE, softWhite,0);
						gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_SPECULAR, black,0);
						gl.glMaterialf(GL.GL_FRONT_AND_BACK, GL.GL_SHININESS, 0);
				
						gl.glDisable(GL.GL_TEXTURE_2D);
						gl.glBlendFunc(GL.GL_ONE, GL.GL_ONE);
						FireEmitter fireEmitter = (FireEmitter) emitter;
						for(int z = 0; z < 3; z++)
						{
							for(int x = 0; x < 2; x++)
							{
								gl.glPushMatrix();
								gl.glTranslated(0.5f + 4.0f * x, 0, 1 + 4 * z);
								for(LivingParticle p : fireEmitter.getParticles())
								{
									//Particles emits light
									gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_EMISSION,
											new float[]{p.getLife()*fullFire[0], p.getLife()*fullFire[1], p.getLife()*fullFire[2], 1.0f}
											,0);
									p.render(dc);
								}
								gl.glPopMatrix();
							}
						}
						gl.glEnable(GL.GL_TEXTURE_2D);
						
					    gl.glDisable(GL.GL_DEPTH_TEST);
					    
					}else if(emitter instanceof ParticleConeEmitter)
					{
						ParticleConeEmitter spotEmitter = (ParticleConeEmitter) emitter;
						/* SPOT EMITTER */
						//Material properties
						gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_AMBIENT, grey,0);
						gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_DIFFUSE, black,0);
						gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_EMISSION, black,0);
						gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_SPECULAR, blue,0);
						gl.glMaterialf(GL.GL_FRONT_AND_BACK, GL.GL_SHININESS, 0);

						gl.glPointSize(3.0f);
						gl.glBegin(GL.GL_POINTS);
						
						Camera camera = dc.getCamera();
						
							Vector3f camPos = camera.getPosition();
							for(int i = 0; i < spotEmitter.getParticles().length; i++)
							{
								Vector3f pos = spotEmitter.getParticles()[i].getPosition();
								gl.glVertex3f(pos.getX(), pos.getY(), pos.getZ());

								//Points normal toward the viewer : particles in the cone will have full specular light up
								Vector3f normal = VectorFunctions.normalize(pos.clone().subtract(camPos));
								gl.glNormal3f(normal.getX(), normal.getY(), normal.getZ());
							}
						gl.glEnd();
						gl.glPointSize(1.0f);
					}
				}gl.glPopMatrix();
			
			}
		}
	}

	/**
	 * @param Active effect
	 * @return
	 */
	public boolean effectActive(Effect e)
	{
		// TODO Auto-generated method stub
		if(activeEffects!=null)
		{
			if(activeEffects[e.ordinal()])
				return true;
			else
				return false;
		}else
			return false;
	}
}
