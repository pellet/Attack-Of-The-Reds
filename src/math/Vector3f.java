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
public strictfp class Vector3f implements Cloneable, Serializable, Remote
{
	private static final long serialVersionUID = 0x010101;

	//Coordinates
	private float x = 0;
	private float y = 0;
	private float z = 0;

	/**
	 * Constructor of the (0, 0, 0) Point
	 */
	public Vector3f()
	{
		this(0, 0, 0);
	}

	/**
	 * Constructor of the (x, y, z) Point
	 */
	public Vector3f(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector3f clone()
	{
		return new Vector3f(x, y, z);
	}

	public boolean equals(Object obj)
	{
		if(super.equals(obj))
			return true;

		if(obj instanceof Vector3f)
		{
			Vector3f vector = (Vector3f)obj;
			if(vector.x == x && vector.y == y && vector.z == z)
				return true;
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
	public Vector3f setX(float x)
	{
		this.x = x;
		return this;
	}
	public Vector3f addX(float addX)
	{
		x += addX;
		return this;
	}
	public Vector3f multiplyX(float multX)
	{
		x *= multX;
		return this;
	}

	public float getY()
	{
		return y;
	}
	public Vector3f setY(float y)
	{
		this.y = y;
		return this;
	}
	public Vector3f addY(float addY)
	{
		y += addY;
		return this;
	}
	public Vector3f multiplyY(float multY)
	{
		y *= multY;
		return this;
	}

	public float getZ()
	{
		return z;
	}
	public Vector3f setZ(float z)
	{
		this.z = z;
		return this;
	}
	public Vector3f addZ(float addZ)
	{
		z += addZ;
		return this;
	}
	public Vector3f multiplyZ(float multZ)
	{
		z *= multZ;
		return this;
	}

	/**
	 * @param v
	 * @return this
	 */
	public Vector3f add(Vector3f v)
	{
		x += v.x;
		y += v.y;
		z += v.z;
		return this;
	}
	/**
	 * @param v
	 * @return this
	 */
	public Vector3f subtract(Vector3f v)
	{
		x -= v.x;
		y -= v.y;
		z -= v.z;
		return this;
	}
	/**
	 * @param v
	 * @return this
	 */
	public Vector3f multiply(Vector3f v)
	{
		x *= v.x;
		y *= v.y;
		z *= v.z;
		return this;
	}
	/**
	 * @param f
	 * @return this
	 */
	public Vector3f multiply(float f)
	{
		x *= f;
		y *= f;
		z *= f;
		return this;
	}
	/**
	 * @param v
	 * @return this
	 */
	public Vector3f divide(Vector3f v)
	{
		if(v.x != 0)
			x /= v.x;
		if(v.y != 0)
			y /= v.y;
		if(v.z != 0)
			z /= v.z;
		return this;
	}
	/**
	 * @param f
	 * @return this
	 */
	public Vector3f divide(float f)
	{
		if(f == 0) return this;
		x /= f;
		y /= f;
		z /= f;
		return this;
	}

	/**
	 * @return the magnitude of this vector
	 */
	public float magnitude()
	{
		return (float)Math.sqrt(x*x+y*y+z*z);
	}

	/**
	 * Normalize this
	 * @return this
	 */
	public Vector3f normalize()
	{
		//Magnitude of the vector
		double l = Math.sqrt(x*x+y*y+z*z);
		if(l == 1 || l == 0) return this;

		x /= l;
		y /= l;
		z /= l;
		return this;
	}

	/**
	 * @param next
	 * @param t range is [0, 1]
	 * @return (new) interpolated vector
	 */
	public Vector3f interpolate(Vector3f next, float t)
	{
		if(next == null || t == 0) return this.clone();
		if(t == 1) return next.clone();
		return VectorFunctions.multiply(this, 1-t).add(VectorFunctions.multiply(next, t));
	}

	/**
	 * Copy <code>src</code> in <code>this</code> vector.
	 * @param src source vector
	 */
	public void copyFrom(Vector3f src)
	{
		x = x;
		y = y;
		z = z;
	}
	/**
	 * Copy <code>this</code> in <code>dst</code> vector.
	 * @param dst destination vector
	 */
	public void copyIn(Vector3f dst)
	{
		dst.x = x;
		dst.y = y;
		dst.z = z;
	}

	public String toString()
	{
		return getClass().getCanonicalName()+"=("+x+", "+y+", "+z+")";
	}
}