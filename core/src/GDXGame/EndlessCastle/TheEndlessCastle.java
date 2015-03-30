package GDXGame.EndlessCastle;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import GDXGame.EndlessCastle.Screens.GameScreen;

public class TheEndlessCastle extends Game
{
	@Override
	public void create() {
		_batch = new SpriteBatch();

		setScreen(new GameScreen(this));
	}

	@Override
	public void render() {
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
		//TODO: implémenter ça
	}

	public SpriteBatch getBatch() {
		return _batch;
	}

	private SpriteBatch _batch;
}
