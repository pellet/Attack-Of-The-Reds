/**
 * 				OpenGl BaseCode
 *
 * Copyright � 2004-2008 J�r�me JOUVIE (Jouvieje)
 *
 * @version BaseCode v2.2.0 Beta Build 13-01-2008
 * @author J�r�me JOUVIE (Jouvieje)
 *
 * WANT TO CONTACT ME ?
 * E-mail   : jerome.jouvie@gmail.com
 * Web site : http://jerome.jouvie.free.fr/
 *
 * BASECODE HOMEPAGE
 * http://jerome.jouvie.free.fr/OpenGl/BaseCode/BaseCode.php
 *
 * THIRD PARTY
 * BaseCode contains Third parties works.
 *
 * TgaTextureLoader class from the package org.jouvieje.opengl.texture was created by Ron Sullivan :
 *     TgaTextureLoader    (I've modify the original source to use this class with Jogl and Gl4java)
 *
 * org.jouvieje.memory.Memory & org.jouvieje.memory.SizeOfPrimitive based on Sun Microsystems work (from JOGL project).
 *
 * GNU GENERAL PUBLIC LICENSE (GPL)
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of
 * the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package math;

import static java.lang.Math.cos;
import static java.lang.Math.acos;
import static java.lang.Math.sin;
import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.sqrt;

//import java.io.Serializable;
//import java.rmi.Remote;

/**
 * What is a quaternion ?<BR>
 * ----------------------<BR>
 * A quaternion is the generalization of complex number.<BR>
 *  complex number c = a + i.b<BR>
 *  quaternon      q = a + i.b + j.c + k.d<BR>
 * <BR>
 * A quaternion can be considered as a scalar number (a) and a vector (b,c,d).<BR>
 * @author J�r�me JOUVIE (Jouvieje)
 * @mail <A HREF="mailto:jerome.jouvie@gmail.com">jerome.jouvie@gmail.com</A>
 * @site <A HREF="http://jerome.jouvie.free.fr/">http://jerome.jouvie.free.fr/</A>
 * @project   Java OpenGl BaseCode
 * @homepage  <A HREF="http://jerome.jouvie.free.fr/OpenGl/BaseCode/BaseCode.php">http://jerome.jouvie.free.fr/OpenGl/BaseCode/BaseCode.php</A>
 * @copyright Copyright � 2004-2008 J�r�me JOUVIE (Jouvieje)
 */
public strictfp class Quaternion4f implements Cloneable//, Serializable//, Remote
{
	private static final long serialVersionUID = 0x010101;

	private float x, y, z, w;

	/**
	 * @param pitch rotation around x, in radians
	 * @param yaw rotation around y, in radians
	 * @param roll rotation around z, in radians
	 * @return
	 */
	public static Quaternion4f fromEulerAngles(float pitch, float yaw, float roll)
	{
		//May be easier, but slower
//		return fromAxisAngle(1, 0, 0, pitch).
//			multiply(fromAxisAngle(0, 1, 0, yaw)).
//			multiply(fromAxisAngle(0, 0, 1, roll));

		pitch *= 0.5f;
		yaw *= 0.5f;
		roll *= 0.5f;

		float cPitch = (float)Math.cos(pitch);
		float cYaw   = (float)Math.cos(yaw);
		float cRoll  = (float)Math.cos(roll);

		float sPitch = (float)Math.sin(pitch);
		float sYaw   = (float)Math.sin(yaw);
		float sRoll  = (float)Math.sin(roll);

		float cYawRoll = cYaw * cRoll;
		float sYawRoll = sYaw * sRoll;

		return new Quaternion4f(
				sPitch*cYawRoll   - cPitch*sYawRoll,
				cPitch*sYaw*cRoll + sPitch*cYaw*sRoll,
				cPitch*cYaw*sRoll - sPitch*sYaw*cRoll,
				cPitch*cYawRoll   + sPitch*sYawRoll
		)/*.normalize()*/;
	}

	/**
	 * @param e1 , also called cosinus director in x direction
	 * @param e2 , also called cosinus director in y direction
	 * @param e3 , also called cosinus director in z direction
	 * @param alpha rotation angle in radians
	 * @return
	 */
	public static Quaternion4f fromAxisAngle(float e1, float e2, float e3, float alpha)
	{
		//Normalize axis vector
		float f = (float)sqrt(e1*e1+e2*e2+e3*e3);
		if(f == 0) return new Quaternion4f(0, 0, 0, 1);

		//Correct angle if needed (make sure it is in a valid range)
		float alphaOverTwo = alpha / 2;

		//Calculate cos/sin one time
		float w = (float)cos(alphaOverTwo);
		float scale = (float)sin(alphaOverTwo);
		scale /= f;

		return new Quaternion4f(
				e1 * scale,
				e2 * scale,
				e3 * scale,
				w);
	}

	public static Quaternion4f fromMatrix(Matrix16f m)
	{
		float x, y, z, w;

		float trace = m.m[0] + m.m[5] + m.m[10] + 1;
		if(trace > 0)
		{
			float s = (float)sqrt(trace);
			w = 0.5f * s;
			s = 0.5f / s;
			x = (m.m[6] - m.m[9]) * s;
			y = (m.m[8] - m.m[2]) * s;
			z = (m.m[1] - m.m[4]) * s;
		}
		else
		{
			float max = max(max(m.m[0], m.m[5]), m.m[10]);
			if(max == m.m[0])
			{
				float s = (float)sqrt(1 + m.m[0] - m.m[5] - m.m[10]);
				x = 0.5f * s;
				s = 0.5f / s;
				y = (m.m[1] + m.m[4]) * s;
				z = (m.m[2] + m.m[8]) * s;
				w = (m.m[6] - m.m[9]) * s;
			}
			else if(max == m.m[5])
			{
				float s = (float)sqrt(1 + m.m[5] - m.m[0] - m.m[10]);
				y = 0.5f * s;
				s = 0.5f / s;
				x = (m.m[1] + m.m[4]) * s;
				z = (m.m[6] + m.m[9]) * s;
				w = (m.m[8] - m.m[2]) * s;
			}
			else //if(max == m.m[10])
			{
				float s = (float)sqrt(1 + m.m[10] - m.m[0] - m.m[5]);
				z = 0.5f * s;
				s = 0.5f / s;
				x = (m.m[2] + m.m[8]) * s;
				y = (m.m[6] + m.m[9]) * s;
				w = (m.m[1] - m.m[4]) * s;
			}
		}
		return new Quaternion4f(x, y, z, w);
	}

	public Quaternion4f()
	{
		this(0, 0, 0, 1);
	}
	public Quaternion4f(float x, float y, float z, float w)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

    public Quaternion4f clone()
    {
    	return new Quaternion4f(x, y, z, w);
    }
    public void copyFrom(Quaternion4f from)
    {
    	this.x = from.x;
    	this.y = from.y;
    	this.z = from.z;
    	this.w = from.w;
    }
	public String toString()
	{
		return "[Quaternion] "+x+", "+y+", "+z+", "+w;
	}

    /**
     *
     */
	public Matrix16f createMatrix()
	{
		float[] m = new float[16];

		m[ 0] = 1 - 2*y*y - 2*z*z;
		m[ 1] =     2*x*y + 2*z*w;
		m[ 2] =     2*x*z - 2*y*w;
		m[ 3] = 0;

		m[ 4] =     2*x*y - 2*z*w;
		m[ 5] = 1 - 2*x*x - 2*z*z;
		m[ 6] =     2*y*z + 2*x*w;
		m[ 7] = 0;

		m[ 8] =     2*x*z + 2*y*w;
		m[ 9] =     2*y*z - 2*x*w;
		m[10] = 1 - 2*x*x - 2*y*y;
		m[11] = 0;

		m[12] = 0;
		m[13] = 0;
		m[14] = 0;
		m[15] = 1;

		return new Matrix16f(m);
	}

	/**
	 * @return Euler angles in radians
	 */
	public Vector3f createEulerAngles()
	{
		float cosY = (float)Math.cos(y);
		if(Math.abs(cosY) > 0.001f)
		{
			float oneOverCosY = 1.0f / cosY;

			//No gimball lock
			float xx = (float)Math.atan2(2 * (w*x - y*z) * oneOverCosY, (1.0 - 2.0 * (x*x + y*y)) * oneOverCosY );
			float yy = (float)Math.asin (2 * (x*z + w*y) );
			float zz = (float)Math.atan2(2 * (w*z - x*y) * oneOverCosY, (1 - 2 * (y*y + z*z)) * oneOverCosY );
			return new Vector3f(xx, yy, zz);
		}
		else
		{
			//Gimball lock
			float xx = 0.0f;
			float yy = (float)Math.asin (2.0 * (x*z + w*y));
			float zz = (float)Math.atan2(2.0 * (x*y + w*z), 1 - 2*(x*x + z*z) );
			return new Vector3f(xx, yy, zz);
		}

//		float phi  = (float)Math.atan2(2 * (w*x+y*z), (1-2*(x*x+y*y)) );
//		float teta = (float)Math.asin (2 * (w*y-z*x));
//		float psi  = (float)Math.atan2(2 * (w*z+x*y), (1-2*(y*y+z*z)));
//		float phi  = (float)Math.atan2(2 * (w*x-y*z), (1-2*(x*x+y*y)) );
//		float teta = (float)Math.asin (2 * (w*y+z*x));
//		float psi  = (float)Math.atan2(2 * (w*z-x*y), (1-2*(y*y+z*z)));
//		return new Vector3f(phi, teta, psi);
	}

	/**
	 * @return Axis (x,y,z) & Angle(w) (angle in radians)
	 */
	public Vector4f createAxisAngles()
	{
		float l = (float)sqrt(x*x+y*y+z*z);

		float e1 = 0, e2 = 0, e3 = 0;
		if(l != 0)
		{
			e1 = x/l;
			e2 = y/l;
			e3 = z/l;
		}

		float alpha = (float)(2*acos(w));
		return new Vector4f(e1, e2, e3, alpha);
	}

	/**
	 *
	 * @param q
	 * @param t
	 * @return the interpolated quaternion
	 */
	public Quaternion4f slerp(Quaternion4f q, float t)
	{
		if(q == null) return this.clone();

		//Do a linear interpolation between two quaternions
		float l = this.x*q.x + this.y*q.y + this.z*q.z + this.w*q.w;
		Quaternion4f slerp = null;
		if(l < 0)
		{
			l = -l;
			slerp = new Quaternion4f(-q.x, -q.y, -q.z, -q.w);
		}
		else
		{
			slerp = new Quaternion4f(q.x, q.y, q.z, q.w);
		}

		//If the quaternions are really close, do a simple linear interpolation
		float scale0 = 1 - t;
		float scale1 = t;
		//Otherwise SLERP
//		if((1 - l) > 0.0001f)
//		{
//			float acosL  = acos(l);
//			float sinAcosL = sin(acosL);
//			scale0 = sin(scale0 * acosL) / sinAcosL;
//			scale1 = sin(scale1 * acosL) / sinAcosL;
//		}
		if((1 - l) > 0.0001f)
		{
			if(l > 1.0f) l /= l;
			float acosL    = (float)acos(l);
			float sinAcosL = (float)sin(acosL);
			if(Math.abs(sinAcosL) > 0.0001f)
			{
				scale0 = (float)sin(scale0 * acosL) / sinAcosL;
				scale1 = (float)sin(scale1 * acosL) / sinAcosL;
			}
		}

		//Calculate interpolated quaternion
		slerp.x = scale0 * this.x + scale1 * slerp.x;
		slerp.y = scale0 * this.y + scale1 * slerp.y;
		slerp.z = scale0 * this.z + scale1 * slerp.z;
		slerp.w = scale0 * this.w + scale1 * slerp.w;
		return slerp;
	}

	public Quaternion4f slerp2(Quaternion4f q, float t)
	{
		if(q == null) return this.clone();

		float l = this.x * q.x + this.y * q.y + this.z * q.z + this.w * q.w;

		Quaternion4f slerp = null;
		float scale0 = 1-t;
		float scale1 = t;
		if(1 + l > 0.001f)
		{
			slerp = new Quaternion4f(q.x, q.y, q.z, q.w);

			if(abs(l) > 1.0f) l /= abs(l);
			float acosL    = (float)acos(l);
			float sinAcosL = (float)sin(acosL);
			if(abs(sinAcosL) > 0.001f)
			{
				scale0 = (float)sin(scale0 * acosL) / sinAcosL;
				scale1 = (float)sin(scale1 * acosL) / sinAcosL;
			}
		}
		else
		{
			slerp = new Quaternion4f(-this.y, this.x, -this.w, this.z);

			scale0 = (float)sin(scale0 * Math.PI / 2.0);
			scale1 = (float)sin(scale1 * Math.PI / 2.0);
		}

		//Calculate interpolated quaternion
		slerp.x = scale0 * this.x + scale1 * slerp.x;
		slerp.y = scale0 * this.y + scale1 * slerp.y;
		slerp.z = scale0 * this.z + scale1 * slerp.z;
		slerp.w = scale0 * this.w + scale1 * slerp.w;
		return slerp;
	}

	/**
	 * Multiply this quaternion by q (this*q)
	 * @param q
	 * @return this
	 */
	public Quaternion4f multiply(Quaternion4f q)
	{
		float x =   this.x * q.w + this.y * q.z - this.z * q.y + this.w * q.x;
		float y = - this.x * q.z + this.y * q.w + this.z * q.x + this.w * q.y;
		float z =   this.x * q.y - this.y * q.x + this.z * q.w + this.w * q.z;
		float w = - this.x * q.x - this.y * q.y - this.z * q.z + this.w * q.w;

		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;

		return this;
	}
	/**
	 * Calculate the product q*v<BR>
	 * This is a quaternion*quaternion product, vector v is considered
	 * as a the quaternion (v.x, v.y, v.z, 0)
	 * @param v
	 * @return
	 */
	public Quaternion4f multiply(Vector3f v)
	{
		float x =   this.w * v.getX() - this.z * v.getY() + this.y * v.getZ();
		float y =   this.z * v.getX() + this.w * v.getY() - this.x * v.getZ();
		float z = - this.y * v.getX() + this.x * v.getY() + this.w * v.getZ();
		float w = - this.x * v.getX() - this.y * v.getY() - this.z * v.getZ();

		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;

		return this;
	}

	/**
	 * Rotation q*v*q^-1 and return the x, y, z components<BR>
	 * Same result than <code>this.createMatrix().multiply(v)</code>.
	 * @param v
	 * @return
	 */
	public Vector3f rotate(Vector3f v)
	{
		Quaternion4f inv = this.clone().conjugate().normalize();
//		Quaternion4d inv = this.clone().invert();
		inv = this.clone().multiply(v).multiply(inv);
		return new Vector3f(inv.x, inv.y, inv.z);
	}

	/**
	 * Invert this quaternion (q^-1, with qq^-1=1).
	 * @return this
	 */
	public Quaternion4f invert()
	{
		//Conjugate / (norme * norme)
		float f = (x * x + y * y + z * z + w * w);
		x /= -f;
		y /= -f;
		z /= -f;
		w /=  f;
		return this;
	}

	/**
	 *
	 * @param q
	 * @return this
	 */
	public Quaternion4f multiplyInvert(Quaternion4f q)
	{
		return invert().multiply(q);
	}

	public float length()
	{
		return (float)sqrt(x*x + y*y + z*z + w*w);
	}

	/**
	 * @return this
	 */
	public Quaternion4f normalize()
	{
		float length = length();
		if(length > 0)
		{
			x /= length;
			y /= length;
			z /= length;
			w /= length;
		}
		return this;
	}

	/**
	 * Conjugate the quaternion.<BR>
	 * This is equivalent to transpose the corresponding matrix.
	 * @return this
	 */
	public Quaternion4f conjugate()
	{
		x = -x;
		y = -y;
		z = -z;
		return this;
	}

	/**
	 * @return this
	 */
	public Quaternion4f scale(float scale)
	{
		x *= scale;
		y *= scale;
		z *= scale;
		w *= scale;
		return this;
	}

	//Getters
	public float getX() { return x; }
	public float getY() { return y; }
	public float getZ() { return z; }
	public float getW() { return w; }
}