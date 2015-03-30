package GDXGame.EndlessCastle;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;

public abstract class GameObject
{
	protected GameObject (float x, float y, float width, float height) {
		_position = new Vector2(x, y);
		_bounds = new Rectangle(x - width / 2, y - height / 2, width, height);
	}
	
	public Vector2 getPosition() {
		return _position;
	}

	public void setPosition(Vector2 position) {
		_position = position;
	}
	
	public Rectangle getBounds() {
		return _bounds;
	}

	public void setBounds(Rectangle bounds) {
		_bounds = bounds;
	}

	private Vector2 _position;
	private Rectangle _bounds;
}
