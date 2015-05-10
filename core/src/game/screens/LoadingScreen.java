package game.screens;

import game.TheEndlessCastle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Loading screen showing at the beginning of the game when AssetHandler loads assets
 */
public class LoadingScreen extends ScreenAdapter
{
	public LoadingScreen(TheEndlessCastle game)
	{
		_game = game;
		_batch = game.getBatch();

		_ratio = (float)Gdx.graphics.getHeight() / Gdx.graphics.getWidth();
		_camera = new OrthographicCamera(32f, 32f*_ratio);
		_camera.position.set(_camera.viewportWidth / 2f, _camera.viewportHeight / 2f, 0);
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

		// Render
		_batch.begin();
		// TODO: render loading screen
		_batch.end();
	}

	@Override
	public void resize(int width, int height) {
		_ratio = (float)height/width;
		_camera.viewportWidth = 32f;
		_camera.viewportHeight = 32f * _ratio;
		_camera.position.set(_camera.viewportWidth / 2f, _camera.viewportHeight / 2f, 0);
		_camera.update();
	}

	@Override
	public void dispose() {
		// TODO: dispose disposables or die !
	}

	private TheEndlessCastle _game;
	private SpriteBatch _batch;
	private OrthographicCamera _camera;

	private float _time;
	private float _ratio;
}
