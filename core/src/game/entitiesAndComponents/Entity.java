package game.entitiesAndComponents;

import game.utils.Position;
import com.badlogic.gdx.math.Rectangle;

public abstract class Entity
{
	protected Entity(float x, float y, float width, float height) {
		_position = new Position(x, y);
		_bounds = new Rectangle(x - width / 2, y - height / 2, width, height);
	}
	
	public Position getPosition() {
		return _position;
	}

	public void setPosition(Position position) {
		_position = position;
	}
	
	public Rectangle getBounds() {
		return _bounds;
	}

	public void setBounds(Rectangle bounds) {
		_bounds = bounds;
	}

	private Position _position;
	private Rectangle _bounds;
}
