package game.components;

import game.entities.Entity;
import aurelienribon.bodyeditor.BodyEditorDAL;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Body component abstract class
 */
public class BodyComponent extends Component
{
	private World _box2DWorld;
	private BodyEditorDAL _bodyDAL;

	/**
	 * Body component constructor
	 * @param name Body component name (must be the same name as corresponding JSON file Body name)
	 */
	public BodyComponent(String name, Entity owner, World box2DWorld, BodyEditorDAL bodyDAL, BodyDef bodyDef, FixtureDef fixtureDef) {
		super(name, owner);
		_box2DWorld = box2DWorld;
		_bodyDAL = bodyDAL;
	    _body = _box2DWorld.createBody(bodyDef);
	    _bodyDAL.attachFixture(_body, name, fixtureDef, 1f);
	}

	/**
	 * Body component constructor
	 * @param name Body component name (must be the same name as corresponding JSON file Body name)
	 */
	public BodyComponent(String name, Entity owner, World box2DWorld, BodyEditorDAL bodyDAL, BodyDef bodyDef, FixtureDef fixtureDef, float scale) {
		super(name, owner);
		_box2DWorld = box2DWorld;
		_bodyDAL = bodyDAL;
	    _body = _box2DWorld.createBody(bodyDef);
	    _bodyDAL.attachFixture(_body, name, fixtureDef, scale);
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
