package game.components;

import game.entities.Entity;
import aurelienribon.bodyeditor.BodyEditorDAL;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Dynamic body component class
 */
public class DynamicBodyComponent extends BodyComponent
{
	/**
	 * Dynamic body component constructor with shape information
	 * @param name Dynamic body component name (must be the same name as corresponding JSON file Body name)
	 */
	public DynamicBodyComponent(String name, Entity owner, World box2DWorld, BodyEditorDAL bodyDAL, BodyDef bodyDef, FixtureDef fixtureDef, float scale)
	{
		super(name, owner, box2DWorld, bodyDAL, bodyDef, fixtureDef, scale);
		
		_speed = new Vector2();
		_accel = new Vector2();
	}

	/**
	 * Dynamic body component constructor with shape information
	 * @param name Dynamic body component name (must be the same name as corresponding JSON file Body name)
	 */
	public DynamicBodyComponent(String name, Entity owner, World box2DWorld, BodyEditorDAL bodyDAL, BodyDef bodyDef, FixtureDef fixtureDef)
	{
		this(name, owner, box2DWorld, bodyDAL, bodyDef, fixtureDef, 1f);
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
