package GDXGame.EndlessCastle.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.GL20;
import GDXGame.EndlessCastle.TheEndlessCastle;
import GDXGame.EndlessCastle.World;

public class GameScreen extends ScreenAdapter
{
	public GameScreen(TheEndlessCastle game)
	{
		_game = game;
		_batch = game.getBatch();
		
		_worldListener = new World.WorldListener() {
			@Override
			public void moveForward() {
				//TODO: implémenter ça (play sounds, ...)
			}
			
			@Override
			public void moveBackward() {
				//TODO: implémenter ça (play sounds, ...)
			}
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
		Gdx.gl.glClearColor(1, 1, 0.5f, 0.125f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
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
