package util;

import math.Vector3f;

public class Camera {

	Vector3f mRotation;
	Vector3f position;
	public Camera(Vector3f position, Vector3f rotation)
	{
		mRotation = rotation;
		this.position = position;
	}
	public Vector3f getRotation() {
		// TODO Auto-generated method stub
		return mRotation;
	}
	/**
	 * @return the position
	 */
	public Vector3f getPosition()
	{
		return this.position;
	}
	/**
	 * @param position the position to set
	 */
	public void setPosition(Vector3f position)
	{
		this.position = position;
	}
}
