package game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
		_camera = new OrthographicCamera(32f, 32f*ratio);
		_camera.position.set(_camera.viewportWidth / 2f, _camera.viewportHeight / 2f, 0);
		_camera.update();
	}
	
	@Override
	public void render (float delta) {
		update(delta);
		draw();
	}
	
	@Override
	public void resize (int width, int height) {
		_camera.viewportWidth = 32f;
		_camera.viewportHeight = 32f * height/width;
		_camera.update();
	}
	
	@Override
	public void dispose() {
	}

	private void update(float deltaTime)
	{
		_camera.update();
		_batch.setProjectionMatrix(_camera.combined);

		_time += 1/60f;
		//TODO: update world
	}
	
	private void draw()
	{
		_batch.begin();
		//TODO: render world
		_batch.end();
	}
	
	private TheEndlessCastle _game;
	private SpriteBatch _batch;
	private OrthographicCamera _camera;
	private float _time;
	
	private World _world;
	private World.WorldListener _worldListener;
}
