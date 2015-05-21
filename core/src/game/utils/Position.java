package game.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

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

	//TODO: add getters and setters for x and y coordinates in meters + toString in metters
	
}
