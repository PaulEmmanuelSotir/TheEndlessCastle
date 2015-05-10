package game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import game.screens.MenuScreen;

public class TheEndlessCastle extends Game
{
	@Override
	public void create() {
		_batch = new SpriteBatch();
		_assetMngr = new AssetManager();
		_settings = new Settings(_SETTINS_NAME);

		// Let shaders not use all available uniforms and attributes
		ShaderProgram.pedantic = false; 

		setScreen(new MenuScreen(this));
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1.0f, 0.5f, 0.125f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		super.render();
	} 

	@Override
	public void resize (int width, int height) {
		getScreen().resize(width, height);
	}

	@Override
	public void pause () {
		getScreen().pause();
	}

	@Override
	public void resume () {
		getScreen().resume();
	}

	@Override
	public void dispose () {
		getScreen().dispose();
		_batch.dispose();
		// TODO: savoir si il est mieux de saver les setting plus tôt
		_settings.save();
	}

	public SpriteBatch getBatch() {
		return _batch;
	}

	public AssetManager getAssetMngr() {
		return _assetMngr;
	}

	private AssetManager _assetMngr;
	private SpriteBatch _batch;
	private Settings _settings;

	private static final String _SETTINS_NAME = "TheEndlessCastleSettings";
}
