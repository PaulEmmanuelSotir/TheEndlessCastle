package game.screens;

import game.TheEndlessCastle;
import game.World;
import game.dataAccessLayer.AssetsHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Screen implements com.badlogic.gdx.Screen
{
	public Screen(TheEndlessCastle game)
	{
		_game = game;
		_batch = game.getBatch();
		_assetsHndlr = game.getAssetsHandler();

		_ratio = (float)Gdx.graphics.getHeight() / Gdx.graphics.getWidth();
		_camera = new OrthographicCamera(32f, 32f*_ratio);
		_camera.position.set(_camera.viewportWidth / 2f, _camera.viewportHeight / 2f, 3f);
		_camera.update();
	}


	@Override
	public void render(float delta) {
		// Update time
		if(_time > 0.8f * Float.MAX_VALUE)
			_time = 0f;
		_time += delta;
		
		// Update camera
		_camera.update();
		_batch.setProjectionMatrix(_camera.combined);

		update();

		// Render
		_batch.begin();
		draw();
		_batch.end();
	}
	
	protected abstract void update();
	protected abstract void draw();

	@Override
	public void resize(int width, int height) {
		_ratio = (float)height/width;
		_camera.viewportWidth = World.METERS_TO_PIXELS;
		_camera.viewportHeight = World.METERS_TO_PIXELS * _ratio;
		_camera.position.set(_camera.viewportWidth / 2f, _camera.viewportHeight / 2f, 0);
		_camera.update();
	}

	@Override
	public void dispose() {
		// TODO: dispose disposables or die !
	}
	
	@Override
	public void show() {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
		
	}

	protected TheEndlessCastle _game;
	protected SpriteBatch _batch;
	protected AssetsHandler _assetsHndlr;
	protected OrthographicCamera _camera;

	protected float _time;
	protected float _ratio;
}
