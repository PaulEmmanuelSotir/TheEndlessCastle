package game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
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

		_backgroundShader = _assetsHndlr.get("MenuRaysShader");
		_timeLocaction = _backgroundShader.getUniformLocation("u_globalTime");
		_ratioLocaction = _backgroundShader.getUniformLocation("u_ratio");
		_backgroundSprite = new Sprite(new Texture(Gdx.files.internal("textures/defaultTexture.png")));	

		_menuMusic = _assetsHndlr.get(_MENU_MUSIC_NAME);
		if(_menuMusic != null)
		{
			_menuMusic.setLooping(true);
			_menuMusic.play();
		}
	}

	@Override
	protected void update()
	{
		// TODO: temporaire
		if(_time > 1.5)
		{
			_menuMusic.stop();
			_game.setScreen(new GameScreen(_game));
		}
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
		if(_menuMusic != null)
			_menuMusic.stop();
		// TODO: temporaire
		_backgroundSprite.getTexture().dispose();
	}

	// TODO: temporaire
	private Sprite _backgroundSprite;
	private ShaderProgram _backgroundShader;
	private int _timeLocaction;
	private int _ratioLocaction;

	private Music _menuMusic;

	private static final String _MENU_MUSIC_NAME = "MenuMusicHeroic";
}
