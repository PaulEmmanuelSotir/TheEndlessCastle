package game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import game.screens.GameScreen;

public class TheEndlessCastle extends Game
{
	@Override
	public void create() {
		_batch = new SpriteBatch();
		_settings = new Settings("TheEndlessCastleSettings");

		setScreen(new GameScreen(this));
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1.0f, 0.5f, 0.125f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		super.render();
	} 

	@Override
	public void resize (int width, int height) {
		//TODO: implémenter ça
	}

	@Override
	public void pause () {
		//TODO: implémenter ça
	}

	@Override
	public void resume () {
		//TODO: implémenter ça
	}

	@Override
	public void dispose () {
		// TODO: savoir si il est mieux de saver les setting plus tôt
		_settings.save();
	}

	public SpriteBatch getBatch() {
		return _batch;
	}

	private SpriteBatch _batch;
	private Settings _settings;
}
