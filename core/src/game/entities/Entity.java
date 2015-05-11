package game.entities;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import game.World;
import game.components.Component;
import game.utils.Position;

public abstract class Entity
{
	protected Entity(float x, float y, float width, float height) {
		_position = new Position(x, y);
		_bounds = new Rectangle(x - width / 2, y - height / 2, width, height);
		_components = new ArrayList<Component>();
	}

	public void render(SpriteBatch batch) {
		//TODO: implémenter ça
	}

	public void update(World world) {
		//TODO: implémenter ça
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

	public String toString() {
		return _id + "_pos=" + _position;
	}

	private String _id;
	private Position _position;
	private Rectangle _bounds;
	private List<Component> _components;
}
