package game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.GL20;

import game.TheEndlessCastle;
import game.World;

/**
 * Game screen class
 */
public class GameScreen extends Screen
{
	public GameScreen(TheEndlessCastle game)
	{
		super(game);
		
		_worldListener = new World.WorldListener() {
			// TODO: implémenter les callcack du world ici...
		};
		_world = new World(_worldListener);

	}

	@Override
	protected void update()
	{
		_world.update(_time);
	}

	@Override
	protected void draw()
	{

		//TODO: render world
	}

	@Override
	public void dispose() {
	}


	private World _world;
	private World.WorldListener _worldListener;
}
