package game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.GL20;
import game.TheEndlessCastle;
import game.World;

public class GameScreen extends ScreenAdapter
{
	public GameScreen(TheEndlessCastle game)
	{
		_game = game;
		_batch = game.getBatch();
		
		_worldListener = new World.WorldListener() {
			// TODO: implémenter les callcack du world ici...
		};
		
		_world = new World(_worldListener);
	}
	
	@Override
	public void render (float delta) {
		update(delta);
		draw();
	}

	private void update(float deltaTime)
	{
		//TODO: implémenter ça
	}
	
	private void draw()
	{		
		//TODO: implémenter ça
		
		_batch.begin();
		// ...
		_batch.end();
	}
	
	private TheEndlessCastle _game;
	private SpriteBatch _batch;
	
	private World _world;
	private World.WorldListener _worldListener;
}
