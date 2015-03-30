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
	
	public SpriteBatch getBatch() {
		return _batch;
	}

	private SpriteBatch _batch;
}
