package game.components;

import game.entities.Entity;

import com.badlogic.gdx.physics.box2d.Body;

/**
 * Body component abstract class
 */
public abstract class BodyComponent extends Component
{
	/**
	 * Body component constructor
	 * @param name Body component name
	 * @param owner Body component owner entity
	 */
	public BodyComponent(String name, Entity owner) {
		super(name, owner);
	}
	
	/**
	 * Body component constructor with shape information
	 * @param name Body component name
	 * @param owner Body component owner entity
	 * @param shape Body component shape
	 */
//	public BodyComponent(String name, Entity owner, Shape shape) {
//		super(name, owner);
//		_shape = Shape;
//	}
	
	public Body getBody() {
		return _body;
	}
	
	protected Body _body;
}
