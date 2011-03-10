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

import java.io.Serializable;
import java.rmi.Remote;

/**
 * <H2></H2>
 * <HR>
 * Description goes here. If you see this message, please contact me and the description will be filled.<BR>
 * <BR>
 * @author J�r�me JOUVIE (Jouvieje)
 * @mail <A HREF="mailto:jerome.jouvie@gmail.com">jerome.jouvie@gmail.com</A>
 * @site <A HREF="http://jerome.jouvie.free.fr/">http://jerome.jouvie.free.fr/</A>
 * @project   Java OpenGl BaseCode
 * @homepage  <A HREF="http://jerome.jouvie.free.fr/OpenGl/BaseCode/BaseCode.php">http://jerome.jouvie.free.fr/OpenGl/BaseCode/BaseCode.php</A>
 * @copyright Copyright � 2004-2008 J�r�me JOUVIE (Jouvieje)
 */
public strictfp class Vector4f implements Cloneable, Serializable, Remote
{
	private static final long serialVersionUID = 0x010101;

	//Coordinates
	private float x = 0;
	private float y = 0;
	private float z = 0;
	private float w = 0;

	/**
	 * Constructor of the (0, 0, 0) Point
	 */
	public Vector4f(){}

	/**
	 * Constructor of the (x, y, z) Point
	 */
	public Vector4f(float x, float y, float z, float w)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	/**
	 * Constructor of the (x, y, z) Point
	 */
	public Vector4f(Vector3f vector, float w)
	{
		this(vector.getX(), vector.getY(), vector.getZ(), w);
	}

	public Vector4f clone()
	{
		return new Vector4f(x, y, z, w);
	}

	public boolean equals(Object obj)
	{
		if(super.equals(obj))
			return true;

		if(obj instanceof Vector4f)
		{
			Vector4f vector = (Vector4f)obj;
			if(vector.x == x && vector.y == y && vector.z == z && vector.w == w)
			{
				return true;
			}
		}
		return false;
	}

	public boolean isNullVector()
	{
		if(x == 0.0f && y == 0.0f && z == 0.0f)
			return true;
		return false;
	}

	public float getX()
	{
		return x;
	}
	public Vector4f setX(float x)
	{
		this.x = x;
		return this;
	}
	public Vector4f addX(float addX)
	{
		x += addX;
		return this;
	}
	public Vector4f multiplyX(float multX)
	{
		x *= multX;
		return this;
	}

	public float getY()
	{
		return y;
	}
	public Vector4f setY(float y)
	{
		this.y = y;
		return this;
	}
	public Vector4f addY(float addY)
	{
		y += addY;
		return this;
	}
	public Vector4f multiplyY(float multY)
	{
		y *= multY;
		return this;
	}

	public float getZ()
	{
		return z;
	}
	public Vector4f setZ(float z)
	{
		this.z = z;
		return this;
	}
	public Vector4f addZ(float addZ)
	{
		z += addZ;
		return this;
	}
	public Vector4f multiplyZ(float multZ)
	{
		z *= multZ;
		return this;
	}

	public float getW()
	{
		return w;
	}
	public Vector4f setW(float w)
	{
		this.w = w;
		return this;
	}
	public Vector4f addW(float addW)
	{
		w += addW;
		return this;
	}
	public Vector4f multiplyW(float multW)
	{
		w *= multW;
		return this;
	}

	/**
	 * @param v
	 * @return this
	 */
	public Vector4f add(Vector4f v)
	{
		x += v.x;
		y += v.y;
		z += v.z;
		w += v.w;
		return this;
	}
	/**
	 * @param v
	 * @return this
	 */
	public Vector4f subtract(Vector4f v)
	{
		x -= v.x;
		y -= v.y;
		z -= v.z;
		w -= v.w;
		return this;
	}
	/**
	 * @param v
	 * @return this
	 */
	public Vector4f multiply(Vector4f v)
	{
		x *= v.x;
		y *= v.y;
		z *= v.z;
		w *= v.w;
		return this;
	}
	/**
	 * @param f
	 * @return this
	 */
	public Vector4f multiply(float f)
	{
		x *= f;
		y *= f;
		z *= f;
		w *= f;
		return this;
	}
	/**
	 * @param v
	 * @return this
	 */
	public Vector4f divide(Vector4f v)
	{
		if(v.x != 0)
			x /= v.x;
		if(v.y != 0)
			y /= v.y;
		if(v.z != 0)
			z /= v.z;
		if(v.w != 0)
			w /= v.w;
		return this;
	}
	/**
	 * @param f
	 * @return this
	 */
	public Vector4f divide(float f)
	{
		if(f == 0) return this;
		x /= f;
		y /= f;
		z /= f;
		w /= f;
		return this;
	}

	/**
	 * @return the magnitude of this vector
	 */
	public float magnitude()
	{
		return (float)Math.sqrt(x*x+y*y+z*z+w*w);
	}

	/**
	 * Normalize this
	 * @return this
	 */
	public Vector4f normalize()
	{
		//Magnitude of the vector
		double l = Math.sqrt(x*x+y*y+z*z+w*w);
		if(l == 1 || l == 0) return this;

		x /= l;
		y /= l;
		z /= l;
		w /= l;
		return this;
	}

	/**
	 * @param next
	 * @param t range is [0, 1]
	 * @return (new) interpolated vector
	 */
	public Vector4f interpolate(Vector4f next, float t)
	{
		if(next == null || t == 0) return this.clone();
		if(t == 1) return next.clone();
		return VectorFunctions.multiply(this, 1-t).add(VectorFunctions.multiply(next, t));
	}

	/**
	 * Copy <code>src</code> in <code>this</code> vector.
	 * @param src source vector
	 */
	public void copyFrom(Vector4f src)
	{
		x = x;
		y = y;
		z = z;
		w = w;
	}
	/**
	 * Copy <code>this</code> in <code>dst</code> vector.
	 * @param dst destination vector
	 */
	public void copyIn(Vector4f dst)
	{
		dst.x = x;
		dst.y = y;
		dst.z = z;
		dst.w = w;
	}

	public static Vector3f toVector3f(Vector4f v)
	{
		return new Vector3f(v.x, v.y, v.z);
	}

	public String toString()
	{
		return getClass().getCanonicalName()+"=("+x+", "+y+", "+z+", "+w+")";
	}
}