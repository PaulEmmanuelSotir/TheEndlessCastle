package game.entitiesAndComponents;

import com.badlogic.gdx.physics.box2d.Body;

public abstract class BodyComponent extends Component
{
	public BodyComponent(Entity owner) {
		super(owner);
	}
	
	public Body getBody() {
		return _body;
	}
	
	protected Body _body;
}
