package game.components;

import game.entities.Entity;

import com.badlogic.gdx.math.Vector2;

/**
 * Dynamic body component class
 */
public class DynamicBodyComponent extends BodyComponent
{
	/**
	 * Dynamic body component constructor with shape information
	 * @param name Dynamic body component name
	 * @param owner Dynamic body component owner entity
	 */
	public DynamicBodyComponent(String name, Entity owner)
	{
		super(name, owner);
		
		_speed = new Vector2();
		_accel = new Vector2();
	}

	/**
	 * Dynamic body component constructor with shape information
	 * @param name Dynamic body component name
	 * @param owner Dynamic body component owner entity
	 * @param shape Dynamic body component shape
	 */
//	public DynamicBodyComponent(String name, Entity owner, Shape shape) {
//		super(name, owner, shape);
//	}
	
	public Vector2 getSpeed() {
		return _speed;
	}

	public void setSpeed(Vector2 speed) {
		_speed = speed;
	}
	
	public Vector2 getAccel() {
		return _accel;
	}

	public void setAccel(Vector2 accel) {
		_accel = accel;
	}
	
	private Vector2 _speed;
	private Vector2 _accel;
}
