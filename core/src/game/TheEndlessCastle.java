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
		//TODO: impl�menter �a
	}

	@Override
	public void pause () {
		//TODO: impl�menter �a
	}

	@Override
	public void resume () {
		//TODO: impl�menter �a
	}

	@Override
	public void dispose () {
		//TODO: impl�menter �a
	}

	public SpriteBatch getBatch() {
		return _batch;
	}

	private SpriteBatch _batch;
}
