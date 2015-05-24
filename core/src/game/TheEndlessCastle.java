package game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import game.dataAccessLayer.AssetsHandler;
import game.screens.LoadingScreen;

public class TheEndlessCastle extends Game
{
	@Override
	public void create() {
		_spriteBatch = new SpriteBatch();
		_modelBatch = new ModelBatch();
		_assetsHndlr = new AssetsHandler(_ASSETS_LIST_FILE_NAME);
		_settings = new Settings(_SETTINS_NAME);

		// Let shaders not use all available uniforms and attributes
		ShaderProgram.pedantic = false; 

		setScreen(new LoadingScreen(this));
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(MAIN_GAME_COLOR.r, MAIN_GAME_COLOR.g, MAIN_GAME_COLOR.b, MAIN_GAME_COLOR.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

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
		_spriteBatch.dispose();
		_modelBatch.dispose();
		// TODO: savoir si il est mieux de saver les setting plus tôt
		_settings.save();
	}

	public SpriteBatch getSpriteBatch() {
		return _spriteBatch;
	}
	
	public ModelBatch getModelBatch() {
		return _modelBatch;
	}

	public AssetsHandler getAssetsHandler() {
		return _assetsHndlr;
	}
	
	public Settings GetSettings()
	{
		return _settings;
	}

	public static final Color MAIN_GAME_COLOR = new Color(0.93333f, 0.921569f, 0.90980f, 1.0f);

	private AssetsHandler _assetsHndlr;
	private SpriteBatch _spriteBatch;
	private ModelBatch _modelBatch;
	private Settings _settings;
	
	private static final String _SETTINS_NAME = "TheEndlessCastleSettings";
	private static final String _ASSETS_LIST_FILE_NAME = "Resources.xml";
}
