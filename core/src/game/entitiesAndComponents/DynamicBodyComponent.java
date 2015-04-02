package game.entitiesAndComponents;

import com.badlogic.gdx.math.Vector2;

public class DynamicBodyComponent extends Component
{
	public DynamicBodyComponent(Entity owner)
	{
		super(owner);
		
		_speed = new Vector2();
		_accel = new Vector2();
	}
	
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
