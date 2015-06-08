package game.components;

import game.GameWorld;
import game.entities.Entity;
import game.utils.Position;
import aurelienribon.bodyeditor.BodyEditorDAL;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Body component abstract class
 */
public class BodyComponent extends Component implements IUpdateableComponent
{
	private World _box2DWorld;
	private BodyEditorDAL _bodyDAL;

	/**
	 * Body component constructor
	 * @param name Body component name (must be the same name as corresponding JSON file Body name)
	 */
	public BodyComponent(String name, Entity owner, World box2DWorld, BodyEditorDAL bodyDAL, BodyDef bodyDef, FixtureDef fixtureDef) {
		this(name, owner, box2DWorld, bodyDAL, bodyDef, fixtureDef, 1f);
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
		_bodyType = _body.getType();
		_bodyDAL.attachFixture(_body, name, fixtureDef, scale);
		_relativePosition = new Position(0, 0);
	}

	@Override
	public void update(GameWorld world) {
		// Update position
		if(_bodyType == BodyType.DynamicBody)
		{
			_owner.setPosition(new Position(_body.getPosition().x, _body.getPosition().y));
	        // We need to convert our angle from radians to degrees
			_owner.setRotation(MathUtils.radiansToDegrees * _body.getAngle());
		}
		else
		{
			Position OwnerPos = _owner.getPosition();
			_body.setTransform(_relativePosition.x + OwnerPos.x, _relativePosition.y + OwnerPos.y, 0);
		}
	}

	public void SetRelativePosition(Position pos)
	{
		if(pos != null)
			_relativePosition = pos;		
	}

	public Position GetRelativePosition()
	{
		return _relativePosition;		
	}
	
	public Body getBody() {
		return _body;
	}

	protected Body _body;
	protected BodyType _bodyType;
	protected Position _relativePosition;
}
