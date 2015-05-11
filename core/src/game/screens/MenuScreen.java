package game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import game.TheEndlessCastle;

/**
 * Menu screen class
 */
public class MenuScreen  extends Screen
{

	public MenuScreen(TheEndlessCastle game)
	{
		super(game);

		// TODO: temporaire
		// TODO: verifier _testShader.getLog() et _testShader.isCompiled();
		_backgroundShader = new ShaderProgram(Gdx.files.internal("shaders/vertex.glsl"), Gdx.files.internal("shaders/rotatingRays.glsl"));
		_timeLocaction = _backgroundShader.getUniformLocation("u_globalTime");
		_ratioLocaction = _backgroundShader.getUniformLocation("u_ratio");
		_backgroundSprite = new Sprite(new Texture(Gdx.files.internal("textures/defaultTexture.png")));	
	}

	@Override
	protected void update()
	{
		// TODO: temporaire
		if(_time > 1.5)
			_game.setScreen(new GameScreen(_game));
	}

	@Override
	protected void draw()
	{
		// TODO: temporaire
		_backgroundShader.setUniformf(_ratioLocaction, _ratio);
		_backgroundShader.setUniformf(_timeLocaction, _time);
		_batch.setShader(_backgroundShader);
		_backgroundSprite.draw(_batch);
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		_backgroundSprite.setSize(32f, 32f*_ratio);
	}

	@Override
	public void dispose() {
		// TODO: temporaire
		_backgroundSprite.getTexture().dispose();
	}

	// TODO: temporaire
	private Sprite _backgroundSprite;
	private ShaderProgram _backgroundShader;
	private int _timeLocaction;
	private int _ratioLocaction;
}
