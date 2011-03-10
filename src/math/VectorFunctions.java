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
public final strictfp class VectorFunctions
{
	public static float distance(Vector3f v1, Vector3f v2)
	{
		return magnitude(subtract(v2, v1));
	}
	public static float distance(Vector4f v1, Vector4f v2)
	{
		return magnitude(subtract(v2, v1));
	}
	public static float distance(Vector2f v1, Vector2f v2)
	{
		return magnitude(subtract(v2, v1));
	}
	public static float distance(Vector2i v1, Vector2i v2)
	{
		return magnitude(subtract(v2, v1));
	}

	/**
	 * This computes the magnitude of a normal.
	 * Magnitude of a vector = sqrt(x^2 + y^2 + z^2)
	 *
	 * Magnitude is also called the norm of a vector: ||v||
	 */
	public static float magnitude(Vector3f v)
	{
		return (float)Math.sqrt(v.getX()*v.getX() + v.getY()*v.getY() + v.getZ()*v.getZ());
	}
	public static float magnitude(Vector4f v)
	{
		return (float)Math.sqrt(v.getX()*v.getX() + v.getY()*v.getY() + v.getZ()*v.getZ() + v.getW()*v.getW());
	}
	public static float magnitude(Vector2f v)
	{
		return (float)Math.sqrt(v.getX()*v.getX() + v.getY()*v.getY());
	}
	public static float magnitude(Vector2i v)
	{
		return (float)Math.sqrt(v.getX()*v.getX() + v.getY()*v.getY());
	}

	/**
	 * Add two vectors v1+v2
	 */
	public static Vector3f add(Vector3f v1, Vector3f v2)
	{
		return new Vector3f(v1.getX()+v2.getX(), v1.getY()+v2.getY(), v1.getZ()+v2.getZ());
	}
	public static Vector4f add(Vector4f v1, Vector4f v2)
	{
		return new Vector4f(v1.getX()+v2.getX(), v1.getY()+v2.getY(), v1.getZ()+v2.getZ(), v1.getW()+v2.getW());
	}
	public static Vector2f add(Vector2f v1, Vector2f v2)
	{
		return new Vector2f(v1.getX()+v2.getX(), v1.getY()+v2.getY());
	}
	public static Vector2i add(Vector2i v1, Vector2i v2)
	{
		return new Vector2i(v1.getX()+v2.getX(), v1.getY()+v2.getY());
	}

	/**
	 * Substract two vectors v1-v2
	 */
	public static Vector3f subtract(Vector3f v1, Vector3f v2)
	{
		return new Vector3f(v1.getX()-v2.getX(), v1.getY()-v2.getY(), v1.getZ()-v2.getZ());
	}
	public static Vector4f subtract(Vector4f v1, Vector4f v2)
	{
		return new Vector4f(v1.getX()-v2.getX(), v1.getY()-v2.getY(), v1.getZ()-v2.getZ(), v1.getW()-v2.getW());
	}
	public static Vector2f subtract(Vector2f v1, Vector2f v2)
	{
		return new Vector2f(v1.getX()-v2.getX(), v1.getY()-v2.getY());
	}
	public static Vector2i subtract(Vector2i v1, Vector2i v2)
	{
		return new Vector2i(v1.getX()-v2.getX(), v1.getY()-v2.getY());
	}

	/**
	 * Multiply a vector by a single scalar (number) = (x*scalar, y*scalar, z*scalar)
	 */
	public static Vector3f multiply(Vector3f v1, float scalar)
	{
		return new Vector3f(v1.getX()*scalar, v1.getY()*scalar, v1.getZ()*scalar);
	}
	public static Vector4f multiply(Vector4f v1, float scalar)
	{
		return new Vector4f(v1.getX()*scalar, v1.getY()*scalar, v1.getZ()*scalar, v1.getW()*scalar);
	}
	public static Vector2f multiply(Vector2f v1, float scalar)
	{
		return new Vector2f(v1.getX()*scalar, v1.getY()*scalar);
	}
	public static Vector2i multiply(Vector2i v1, int scalar)
	{
		return new Vector2i(v1.getX()*scalar, v1.getY()*scalar);
	}

	public static Vector3f divide(Vector3f v1, float scalar)
	{
		return new Vector3f(v1.getX()/scalar, v1.getY()/scalar, v1.getZ()/scalar);
	}
	public static Vector4f divide(Vector4f v1, float scalar)
	{
		return new Vector4f(v1.getX()/scalar, v1.getY()/scalar, v1.getZ()/scalar, v1.getW()/scalar);
	}
	public static Vector2f divide(Vector2f v1, float scalar)
	{
		return new Vector2f(v1.getX()/scalar, v1.getY()/scalar);
	}
	public static Vector2i divide(Vector2i v1, int scalar)
	{
		return new Vector2i(v1.getX()/scalar, v1.getY()/scalar);
	}

	/**
	 * Dot product = x1*x2 + y1*y2 + z1*z2
	 */
	public static float dotProduct(Vector3f v1, Vector3f v2)
	{
		return v1.getX()*v2.getX() + v1.getY()*v2.getY() + v1.getZ()*v2.getZ();
	}
	public static float dotProduct(Vector4f v1, Vector4f v2)
	{
		return v1.getX()*v2.getX() + v1.getY()*v2.getY() + v1.getZ()*v2.getZ() + v1.getW()*v2.getW();
	}
	public static float dotProduct(Vector2f v1, Vector2f v2)
	{
		return v1.getX()*v2.getX() + v1.getY()*v2.getY();
	}
	public static int dotProduct(Vector2i v1, Vector2i v2)
	{
		return v1.getX()*v2.getX() + v1.getY()*v2.getY();
	}

	/**
	 * Calculate the cross product of two vector
	 */
	public static Vector3f crossProduct(Vector3f v1, Vector3f v2)
	{
		return new Vector3f(
				v1.getY()*v2.getZ() - v1.getZ()*v2.getY(),
				v1.getZ()*v2.getX() - v1.getX()*v2.getZ(),
				v1.getX()*v2.getY() - v1.getY()*v2.getX());
	}

	/**
	 * Normalize a vector.
	 * Magnitude of the resulting vector is 1.
	 */
	public static Vector3f normalize(Vector3f v)
	{
		//Magnitude of the vector
		double magnitude = 1/magnitude(v);
		if(magnitude == 1) return v.clone();

		return new Vector3f(
				(float)(v.getX() * magnitude),
				(float)(v.getY() * magnitude),
				(float)(v.getZ() * magnitude));
	}
	public static Vector4f normalize(Vector4f v)
	{
		//Magnitude of the vector
		double magnitude = 1/magnitude(v);
		if(magnitude == 1) return v.clone();

		return new Vector4f(
				(float)(v.getX() * magnitude),
				(float)(v.getY() * magnitude),
				(float)(v.getZ() * magnitude),
				(float)(v.getW() * magnitude));
	}
	public static Vector2f normalize(Vector2f v)
	{
		//Magnitude of the vector
		double magnitude = 1/magnitude(v);
		if(magnitude == 1) return v.clone();

		return new Vector2f(
				(float)(v.getX() * magnitude),
				(float)(v.getY() * magnitude));
	}
	public static Vector2f normalize(Vector2i v)
	{
		//Magnitude of the vector
		double magnitude = 1/magnitude(v);
		if(magnitude == 1) return new Vector2f(v.getX(), v.getY());

		return new Vector2f(
				(float)(v.getX() * magnitude),
				(float)(v.getY() * magnitude));
	}

	public static Vector3f magnitude(Vector3f v, double magnitude)
	{
		double scale = magnitude/magnitude(v);
		if(scale == 1) return v.clone();

		return new Vector3f(
				(float)(v.getX() * scale),
				(float)(v.getY() * scale),
				(float)(v.getZ() * scale));
	}
	public static Vector4f magnitude(Vector4f v, double magnitude)
	{
		double scale = magnitude / magnitude(v);
		if(scale == 1) return v.clone();

		return new Vector4f(
				(float)(v.getX() * scale),
				(float)(v.getY() * scale),
				(float)(v.getZ() * scale),
				(float)(v.getW() * scale));
	}
	public static Vector2f magnitude(Vector2f v, double magnitude)
	{
		double scale = magnitude / magnitude(v);
		if(scale == 1) return v.clone();

		return new Vector2f(
				(float)(v.getX() * scale),
				(float)(v.getY() * scale));
	}
	public static Vector2f magnitude(Vector2i v, double magnitude)
	{
		double scale = magnitude / magnitude(v);
		if(scale == 1) return new Vector2f(v.getX(), v.getY());

		return new Vector2f(
				(float)(v.getX() * scale),
				(float)(v.getY() * scale));
	}
}