package game.components;

import game.entities.Entity;

import com.badlogic.gdx.physics.box2d.Body;

public abstract class BodyComponent extends Component
{
	public BodyComponent(String name, Entity owner) {
		super(name, owner);
	}
	
	public Body getBody() {
		return _body;
	}
	
	protected Body _body;
}
