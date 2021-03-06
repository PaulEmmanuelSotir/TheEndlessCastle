package game.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Position vector2 warper class
 */
public class Position extends Vector2
{
	public Position(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	public Position(Position pos)
	{
		this.x = pos.x;
		this.y = pos.y;
	}
	
	public Position(Vector3 pos)
	{
		this.x = pos.x;
		this.y = pos.y;
	}
	
	public static Position add(Position pos1, Position pos2)
	{
		return new Position(pos1.x + pos2.x, pos1.y + pos2.y);
	}
	
	private static final long serialVersionUID = 1L;
	
}
