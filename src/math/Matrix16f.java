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

//import java.io.Serializable;
//import java.rmi.Remote;

//import org.jouvieje.camera.PositionableEx;

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
public strictfp class Matrix16f implements Cloneable//, Serializable, Remote, PositionableEx
{
	private static final long serialVersionUID = 0x010101;

	/**
	 * Create the identity matrix.
	 * @return identity matrix
	 */
	public static Matrix16f identity()
	{
		Matrix16f m = new Matrix16f();
		m.m[ 0] = 1;
		m.m[ 5] = 1;
		m.m[10] = 1;
		m.m[15] = 1;
		return m;
	}
	/**
	 * @param angle in radians
	 */
	public static Matrix16f rotationAroundX(float angle)
	{
	    float cos = (float)Math.cos(angle);
	    float sin = (float)Math.sin(angle);

		Matrix16f m = new Matrix16f();
		m.m[0] = 1; m.m[4] =    0; m.m[ 8] =   0; m.m[12] = 0;
		m.m[1] = 0; m.m[5] =  cos; m.m[ 9] = sin; m.m[13] = 0;
		m.m[2] = 0; m.m[6] = -sin; m.m[10] = cos; m.m[14] = 0;
		m.m[3] = 0; m.m[7] =    0; m.m[11] =   0; m.m[15] = 1;
		return m;
	}
	/**
	 * @param angle in radians
	 */
	public static Matrix16f rotationAroundY(float angle)
	{
	    float cos = (float)Math.cos(angle);
	    float sin = (float)Math.sin(angle);

		Matrix16f m = new Matrix16f();
		m.m[0] =  cos; m.m[4] = 0; m.m[ 8] =  sin; m.m[12] = 0;
		m.m[1] =    0; m.m[5] = 1; m.m[ 9] =    0; m.m[13] = 0;
		m.m[2] = -sin; m.m[6] = 0; m.m[10] =  cos; m.m[14] = 0;
		m.m[3] =    0; m.m[7] = 0; m.m[11] =    0; m.m[15] = 1;
		return m;
	}
	/**
	 * @param angle in radians
	 */
	public static Matrix16f rotationAroundZ(float angle)
	{
	    float cos = (float)Math.cos(angle);
	    float sin = (float)Math.sin(angle);

		Matrix16f m = new Matrix16f();
		m.m[0] =  cos; m.m[4] = sin; m.m[ 8] = 0; m.m[12] = 0;
		m.m[1] = -sin; m.m[5] = cos; m.m[ 9] = 0; m.m[13] = 0;
		m.m[2] =    0; m.m[6] =   0; m.m[10] = 1; m.m[14] = 0;
		m.m[3] =    0; m.m[7] =   0; m.m[11] = 0; m.m[15] = 1;
		return m;
	}

	/**
	 * @param angle in radians
	 */
	public static Matrix16f scaleMatrix(Vector3f scale)
	{
		float x = scale.getX();
		float y = scale.getY();
		float z = scale.getZ();

		Matrix16f m = new Matrix16f();
		m.m[0] = x; m.m[4] = 0; m.m[ 8] = 0; m.m[12] = 0;
		m.m[1] = 0; m.m[5] = y; m.m[ 9] = 0; m.m[13] = 0;
		m.m[2] = 0; m.m[6] = 0; m.m[10] = z; m.m[14] = 0;
		m.m[3] = 0; m.m[7] = 0; m.m[11] = 0; m.m[15] = 1;
		return m;
	}

	/**
	 * @param angle in radians
	 */
	public static Matrix16f translation(Vector3f pos)
	{
		Matrix16f m = identity();
		m.m[12] = pos.getX();
		m.m[13] = pos.getY();
		m.m[14] = pos.getZ();
		return m;
	}

	public static Matrix16f lookAt(Vector3f eye, Vector3f ref, Vector3f up)
	{
		Vector3f forward = ref.clone().subtract(eye);
		Vector3f left = VectorFunctions.crossProduct(up, forward);
		return lookAt(left, up, forward, eye);
	}
	public static Matrix16f lookAt(Vector3f left, Vector3f up, Vector3f forward, Vector3f position)
	{
		Matrix16f m = new Matrix16f();
		m.m[0] =    -left.getX(); m.m[4] =    -left.getY(); m.m[ 8] =    -left.getZ(); m.m[12] = 0;
		m.m[1] =       up.getX(); m.m[5] =       up.getY(); m.m[ 9] =       up.getZ(); m.m[13] = 0;
		m.m[2] = -forward.getX(); m.m[6] = -forward.getY(); m.m[10] = -forward.getZ(); m.m[14] = 0;
		m.m[3] = 0;               m.m[7] = 0;               m.m[11] = 0;               m.m[15] = 1;
		m.translate(new Vector3f(-position.getX(), -position.getY(), -position.getZ()));
		return m;
	}

	public final float[] m; //TODO Hide ?

	public Matrix16f()
	{
		m = new float[16];
	}

	/**
	 * @param m a float[16]
	 * @throws RuntimeException, when array is null or length is not 16
	 */
	public Matrix16f(float[] m) throws RuntimeException
	{
		if(m == null || m.length != 16)
			throw new RuntimeException();
		this.m = m;
	}

	/**
	 * Clone this matrix.
	 * @return a clone of this matrix
	 */
	public Matrix16f clone()
	{
		Matrix16f clone = new Matrix16f();
		System.arraycopy(m, 0, clone.m, 0, m.length);
//		for(int i = 0; i < m.length; i++) clone.m[i] = m[i];
		return clone;
	}

	public Matrix16f copyTo(Matrix16f to)
	{
		System.arraycopy(this.m, 0, to.m, 0, m.length);
		return this;
	}
	public Matrix16f copyFrom(Matrix16f from)
	{
		System.arraycopy(from.m, 0, this.m, 0, this.m.length);
		return this;
	}

	public void print()
	{
		System.out.printf("%.4f\t%.4f\t%.4f\t%.4f\n%.4f\t%.4f\t%.4f\t%.4f\n%.4f\t%.4f\t%.4f\t%.4f\n%.4f\t%.4f\t%.4f\t%.4f\n",
				m[0], m[4], m[ 8], m[12],
				m[1], m[5], m[ 9], m[13],
				m[2], m[6], m[10], m[14],
				m[3], m[7], m[11], m[15]);
	}

	/**
	 * Translate this matrix.
	 * @param translate translation vector.
	 * @return this
	 */
	public Matrix16f translate(Vector3f translate)
	{
		Vector3f v = transform(translate);
		m[12] += v.getX();
		m[13] += v.getY();
		m[14] += v.getZ();
		return this;
	}

	/**
	 * Rotate this matrix
	 * @param q
	 * @return this
	 */
	public Matrix16f rotate(Quaternion4f q)
	{
		return multiply3x3(q.createMatrix());
	}

	/**
	 * Scale this matrix.<BR>
	 * Scale column i of <code>this</code> by the i<sub>th</sub> component of <code>scale</code>.
	 * @param scale scale vector
	 * @return this
	 */
	public Matrix16f scale(Vector3f scale)
	{
		m[0] *= scale.getX(); m[4] *= scale.getY(); m[ 8] *= scale.getZ();
		m[1] *= scale.getX(); m[5] *= scale.getY(); m[ 9] *= scale.getZ();
		m[2] *= scale.getX(); m[6] *= scale.getY(); m[10] *= scale.getZ();
		m[3] *= scale.getX(); m[7] *= scale.getY(); m[11] *= scale.getZ();
		return this;
	}

	/**
	 * Scale this matrix.<BR>
	 * Scale column i of <code>this</code> by <code>scale</code>.
	 * @param scale scale scalar
	 * @return this
	 */
	public Matrix16f scale(float scale)
	{
		m[0] *= scale; m[4] *= scale; m[ 8] *= scale;
		m[1] *= scale; m[5] *= scale; m[ 9] *= scale;
		m[2] *= scale; m[6] *= scale; m[10] *= scale;
		m[3] *= scale; m[7] *= scale; m[11] *= scale;
		return this;
	}

	/**
	 * Multiply this matrix.<BR>
	 * Formula : <code>this</code>[i][j] = row i of <code>this</code> * column j of <code>matrix</code>.<BR>
	 * Where <code>this</code>[i][j] is the element at row i and column j.
	 * @param matrix
	 * @return this
	 */
	public Matrix16f multiply(Matrix16f matrix)
	{
		//FIXME Can probably be optimized

		Matrix16f clone = this.clone();
		for(int j = 0; j < 4; j++)		//Loop over matrix columns
		{
			for(int i = 0; i < 4; i++)	//Loop over matrix rows
			{
				//This could be done with a for loop, but it is slower i think ...
//				int jj = 4*j;
//				m[jj+i] = 0.0f;
//				for(int k = 0; k < 4; k++)
//					m[i+jj] += clone.m[i+4*k]*matrix.m[k+jj];

				int jj = 4*j;
				m[i+jj] =   clone.m[i   ] * matrix.m[  jj] +
							clone.m[i+ 4] * matrix.m[1+jj] +
							clone.m[i+ 8] * matrix.m[2+jj] +
							clone.m[i+12] * matrix.m[3+jj];
			}
		}
		clone = null;
		return this;
	}

	public Matrix16f multiply3x3(Matrix16f matrix)
	{
		//FIXME Can probably be optimized

		Matrix16f clone = this.clone();
		for(int j = 0; j < 3; j++)		//Loop over matrix columns
		{
			for(int i = 0; i < 3; i++)	//Loop over matrix rows
			{
				//This could be done with a for loop, but it is slower i think ...
//				int jj = 4*j;
//				m[jj+i] = 0.0f;
//				for(int k = 0; k < 3; k++)
//					m[i+jj] += clone.m[i+4*k]*matrix.m[k+jj];

				int jj = 4*j;
				m[i+jj] =   clone.m[i   ] * matrix.m[  jj] +
							clone.m[i+ 4] * matrix.m[1+jj] +
							clone.m[i+ 8] * matrix.m[2+jj];
			}
		}
		clone = null;
		return this;
	}

	/**
	 * Rotate and translate vector.
	 * @param v Vector to rotate and translate
	 */
	public Vector3f multiply(Vector3f v)
	{
		Vector3f result = transform(v);
		result.addX(m[12]);
		result.addY(m[13]);
		result.addZ(m[14]);
		return result;
	}

	/**
	 * Rotate vector.
	 * @param v Vector to rotate
	 */
	public Vector3f transform(Vector3f v)
	{
		float x = m[0]*v.getX() + m[4]*v.getY() + m[ 8]*v.getZ();
		float y = m[1]*v.getX() + m[5]*v.getY() + m[ 9]*v.getZ();
		float z = m[2]*v.getX() + m[6]*v.getY() + m[10]*v.getZ();
		return new Vector3f(x, y, z);
	}

	/**
	 * Rotate a vector by the transformed matrix.
	 * @param v Vector to rotate
	 */
	public Vector3f transformTransposed(Vector3f v)
	{
		float x = m[0]*v.getX() + m[1]*v.getY() + m[ 2]*v.getZ();
		float y = m[4]*v.getX() + m[5]*v.getY() + m[ 6]*v.getZ();
		float z = m[8]*v.getX() + m[9]*v.getY() + m[10]*v.getZ();
		return new Vector3f(x, y, z);
	}

	/**
	 * 3x3 transpose this matrix.<BR>
	 * Formula : <code>this</code>[i][j] = <code>this</code>[j][i]<BR>.
	 * @return this transposed
	 */
	public Matrix16f transpose3x3()
	{
		for(int j = 0; j < 3; j++)			//Loop over matrix columns
		{
			for(int i = j+1; i < 3; i++)	//Loop over matrix rows
			{
				final int jj = 4*j, ii = 4*i;
				final float swap = m[i+jj];
				m[i+jj] = m[ii+j];
				m[ii+j] = swap;
			}
		}
		return this;
	}

	/**
	 * Transpose this matrix.<BR>
	 * Formula : <code>this</code>[i][j] = <code>this</code>[j][i]<BR>.
	 * @return this transposed
	 */
	public Matrix16f transpose()
	{
		for(int j = 0; j < 4; j++)			//Loop over matrix columns
		{
			for(int i = j+1; i < 4; i++)	//Loop over matrix rows
			{
				final int jj = 4*j, ii = 4*i;
				final float swap = m[i+jj];
				m[i+jj] = m[ii+j];
				m[ii+j] = swap;
			}
		}
		return this;
	}

	/**
	 * Matrix determinant.
	 * @author     Gabor Simko (tsg@coder.hu)
	 * @date       08.10.2001
	 * @copyright (c) Gabor Simko, All Rights Reserved.
	 * @return matrix determinant
	 */
	public float determinant()
	{
		return    (m[0] * m[5] - m[1] * m[4]) * (m[10] * m[15] - m[11] * m[14])
				- (m[0] * m[6] - m[2] * m[4]) * (m[ 9] * m[15] - m[11] * m[13])
				+ (m[0] * m[7] - m[3] * m[4]) * (m[ 9] * m[14] - m[10] * m[13])
				+ (m[1] * m[6] - m[2] * m[5]) * (m[ 8] * m[15] - m[11] * m[12])
				- (m[1] * m[7] - m[3] * m[5]) * (m[ 8] * m[14] - m[10] * m[12])
				+ (m[2] * m[7] - m[3] * m[6]) * (m[ 8] * m[13] - m[ 9] * m[12]);
	}

	/**
	 * Invert this matrix
	 * @return this inverted
	 */
	public Matrix16f inverseRotation()
	{
		/*
		 * In case of rotation matrix or identity, transposed matrix
		 * is the inverse ie
		 *  M*M-1 = M*MT
		 *
		 * A rotation matrix have always a determinant of 1
		 */
		transpose();
		Vector3f tr = transform(new Vector3f(m[12], m[13], m[14]));
		m[12] = -tr.getX();
		m[13] = -tr.getY();
		m[14] = -tr.getZ();
		return this;
	}

	/**
	 * Matrix inverse.
	 * @author     Gabor Simko (tsg@coder.hu)
	 * @date       08.10.2001
	 * @copyright (c) Gabor Simko, All Rights Reserved.
	 * @return inverse of this matrix
	 */
	public Matrix16f inverse()
	{
		float det = determinant();
		if(det == 0)
			throw new RuntimeException("Can't calculate the inverse matrix, determinant is null.");

		Matrix16f inv = new Matrix16f();
		inv.m[ 0] = (m[ 5] * (m[10] * m[15] - m[11] * m[14]) + m[ 6] * (m[11] * m[13] - m[ 9] * m[15]) + m[ 7] * (m[ 9] * m[14] - m[10] * m[13])) / det;
		inv.m[ 1] = (m[ 9] * (m[ 2] * m[15] - m[ 3] * m[14]) + m[10] * (m[ 3] * m[13] - m[ 1] * m[15]) + m[11] * (m[ 1] * m[14] - m[ 2] * m[13])) / det;
		inv.m[ 2] = (m[13] * (m[ 2] * m[ 7] - m[ 3] * m[ 6]) + m[14] * (m[ 3] * m[ 5] - m[ 1] * m[ 7]) + m[15] * (m[ 1] * m[ 6] - m[ 2] * m[ 5])) / det;
		inv.m[ 3] = (m[ 1] * (m[ 7] * m[10] - m[ 6] * m[11]) + m[ 2] * (m[ 5] * m[11] - m[ 7] * m[ 9]) + m[ 3] * (m[ 6] * m[ 9] - m[ 5] * m[10])) / det;

		inv.m[ 4] = (m[ 6] * (m[ 8] * m[15] - m[11] * m[12]) + m[ 7] * (m[10] * m[12] - m[ 8] * m[14]) + m[ 4] * (m[11] * m[14] - m[10] * m[15])) / det;
		inv.m[ 5] = (m[10] * (m[ 0] * m[15] - m[ 3] * m[12]) + m[11] * (m[ 2] * m[12] - m[ 0] * m[14]) + m[ 8] * (m[ 3] * m[14] - m[ 2] * m[15])) / det;
		inv.m[ 6] = (m[14] * (m[ 0] * m[ 7] - m[ 3] * m[ 4]) + m[15] * (m[ 2] * m[ 4] - m[ 0] * m[ 6]) + m[12] * (m[ 3] * m[ 6] - m[ 2] * m[ 7])) / det;
		inv.m[ 7] = (m[ 2] * (m[ 7] * m[ 8] - m[ 4] * m[11]) + m[ 3] * (m[ 4] * m[10] - m[ 6] * m[ 8]) + m[ 0] * (m[ 6] * m[11] - m[ 7] * m[10])) / det;

		inv.m[ 8] = (m[ 7] * (m[ 8] * m[13] - m[ 9] * m[12]) + m[ 4] * (m[ 9] * m[15] - m[11] * m[13]) + m[ 5] * (m[11] * m[12] - m[ 8] * m[15])) / det;
		inv.m[ 9] = (m[11] * (m[ 0] * m[13] - m[ 1] * m[12]) + m[ 8] * (m[ 1] * m[15] - m[ 3] * m[13]) + m[ 9] * (m[ 3] * m[12] - m[ 0] * m[15])) / det;
		inv.m[10] = (m[15] * (m[ 0] * m[ 5] - m[ 1] * m[ 4]) + m[12] * (m[ 1] * m[ 7] - m[ 3] * m[ 5]) + m[13] * (m[ 3] * m[ 4] - m[ 0] * m[ 7])) / det;
		inv.m[11] = (m[ 3] * (m[ 5] * m[ 8] - m[ 4] * m[ 9]) + m[ 0] * (m[ 7] * m[ 9] - m[ 5] * m[11]) + m[ 1] * (m[ 4] * m[11] - m[ 7] * m[ 8])) / det;

		inv.m[12] = (m[ 4] * (m[10] * m[13] - m[ 9] * m[14]) + m[ 5] * (m[ 8] * m[14] - m[10] * m[12]) + m[ 6] * (m[ 9] * m[12] - m[ 8] * m[13])) / det;
		inv.m[13] = (m[ 8] * (m[ 2] * m[13] - m[ 1] * m[14]) + m[ 9] * (m[ 0] * m[14] - m[ 2] * m[12]) + m[10] * (m[ 1] * m[12] - m[ 0] * m[13])) / det;
		inv.m[14] = (m[12] * (m[ 2] * m[ 5] - m[ 1] * m[ 6]) + m[13] * (m[ 0] * m[ 6] - m[ 2] * m[ 4]) + m[14] * (m[ 1] * m[ 4] - m[ 0] * m[ 5])) / det;
		inv.m[15] = (m[ 0] * (m[ 5] * m[10] - m[ 6] * m[ 9]) + m[ 1] * (m[ 6] * m[ 8] - m[ 4] * m[10]) + m[ 2] * (m[ 4] * m[ 9] - m[ 5] * m[ 8])) / det;
		return inv;
	}

	/*Positionable interface*/

	public Matrix16f getMatrix()
	{
		return this;
	}
	public Vector3f getLeft()
	{
		return transformTransposed(new Vector3f(-1, 0, 0));
	}
	public Vector3f getUp()
	{
		return transformTransposed(new Vector3f(0, 1, 0));
	}
	public Vector3f getForward()
	{
		return transformTransposed(new Vector3f(0, 0, -1));
	}
	public Vector3f getPosition()
	{
		return transformTransposed(new Vector3f(-m[12], -m[13], -m[14]));
	}

	/**/

	public Quaternion4f getQuaternion()
	{
		Matrix16f m = clone();
		//Don't need translation information
		m.m[12] = 0;
		m.m[13] = 0;
		m.m[14] = 0;
//		Vector3f x = normalize(new Vector3f(m.m[0], m.m[4], m.m[ 8]));
//		Vector3f y = normalize(new Vector3f(m.m[1], m.m[5], m.m[ 9]));
//		Vector3f z = normalize(new Vector3f(m.m[2], m.m[6], m.m[10]));
//		m.m[0] = x.getX(); m.m[4] = y.getX(); m.m[ 8] = z.getX();
//		m.m[1] = x.getY(); m.m[5] = y.getY(); m.m[ 9] = z.getY();
//		m.m[2] = x.getZ(); m.m[6] = y.getZ(); m.m[10] = z.getZ();
		return Quaternion4f.fromMatrix(m);
	}
}