package game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import game.Button;
import game.TheEndlessCastle;
import game.Button.ButtonClickListener;

/**
 * Menu screen class
 */
public class MenuScreen  extends Screen
{
	public MenuScreen(TheEndlessCastle game)
	{
		super(game);

		// Music
		_menuMusic = _assetsHndlr.get(_MENU_MUSIC_NAME);
		if(_menuMusic != null)
		{
			_menuMusic.setVolume(game.GetSettings().getVolume());
			_menuMusic.setLooping(true);
			_menuMusic.play();
		}
		
		//Background
		_backgroudSprite = new Sprite((Texture)_assetsHndlr.get(_BACKGROUND_TEXTURE_NAME));
		
		// Rotating rays
		_raysShader = _assetsHndlr.get(_ROTATING_RAYS_SHADER_NAME );
		_timeLocaction = _raysShader.getUniformLocation("u_globalTime");
		_ratioLocaction = _raysShader.getUniformLocation("u_ratio");
		_raysSprite = new Sprite((Texture)_assetsHndlr.get(_DEFAULT_TEXTURE_NAME));	

		// Sound button
		_soundButton = new Button(0.006f, (Texture)_assetsHndlr.get("Sound3NormalTexture"), (Texture)_assetsHndlr.get("Sound3OverTexture"), (Texture)_assetsHndlr.get("Sound3PressedTexture"), new ButtonClickListener() {
			@Override
			public void MouseRelease() {
				if(_game.GetSettings().getVolume() > 80)
				{
					_menuMusic.setVolume(0.75f);
					_game.GetSettings().setVolume(75);
				}
				else if(_game.GetSettings().getVolume() > 50)
				{
					_menuMusic.setVolume(0.25f);
					_game.GetSettings().setVolume(25);
				}
				else if(_game.GetSettings().getVolume() > 10)
				{
					_menuMusic.setVolume(0);
					_game.GetSettings().setVolume(0);
				}
				else
				{
					_menuMusic.setVolume(1);		
					_game.GetSettings().setVolume(100);
				}
				// TODO: change button textures
			}
		});

		// Play button
		_playButton = new Button(0.02f, (Texture)_assetsHndlr.get("PlayNormalTexture"), (Texture)_assetsHndlr.get("PlayOverTexture"), (Texture)_assetsHndlr.get("PlayPressedTexture"), new ButtonClickListener() {
			@Override
			public void MouseRelease() {
				_menuMusic.stop();
				_game.setScreen(new GameScreen(_game));
			}
		});
		
		// Credits button
		_creditsButton = new Button(0.02f, (Texture)_assetsHndlr.get("CreditsNormalTexture"), (Texture)_assetsHndlr.get("CreditsOverTexture"), (Texture)_assetsHndlr.get("CreditsPressedTexture"), new ButtonClickListener() {
			@Override
			public void MouseRelease() {
				_menuMusic.stop();
				_game.setScreen(new CreditsScreen(_game));
			}
		});
	}

	@Override
	protected void update()
	{
		_soundButton.update(_camera);
		_playButton.update(_camera);
		_creditsButton.update(_camera);
	}

	@Override
	protected void draw()
	{
		_spriteBatch.begin();
		
		// Rotating rays
		_spriteBatch.setShader(_raysShader);
		_raysShader.setUniformf(_ratioLocaction, _ratio);
		_raysShader.setUniformf(_timeLocaction, _time);
		_raysSprite.draw(_spriteBatch);
		_spriteBatch.setShader(null);
		
		// Background
		_backgroudSprite.draw(_spriteBatch);
		
		// Buttons
		_soundButton.render(_spriteBatch);
		_playButton.render(_spriteBatch);
		_creditsButton.render(_spriteBatch);
		_spriteBatch.end();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		
		// Resize rotating rays sprite
		_raysSprite.setSize(_camera.viewportWidth, _camera.viewportHeight);
		
		// Resize background
		float backSpriteRatio = _backgroudSprite.getWidth()/_backgroudSprite.getHeight();
		_backgroudSprite.setSize(_camera.viewportHeight*backSpriteRatio, _camera.viewportHeight);
		_backgroudSprite.setCenter(_camera.viewportWidth/2f, _camera.viewportHeight/2f);

		// Update buttons position
		_soundButton.SetPosition(_camera.viewportWidth - _soundButton.getWidth() - 1f, _camera.viewportHeight - 2.5f);
		_creditsButton.SetPosition(_camera.viewportWidth/2f - 11.08f*_ratio, _camera.viewportHeight/2f - 4f);
		_playButton.SetPosition(_camera.viewportWidth/2f - 11.08f*_ratio, _camera.viewportHeight/2f + _creditsButton.getHeight()-1.5f);
	}

	@Override
	public void dispose() {
		if(_menuMusic != null)
			_menuMusic.stop();
	}
	
	// Background
	private Sprite _backgroudSprite;

	// Rotating rays
	private ShaderProgram _raysShader;
	private int _timeLocaction;
	private int _ratioLocaction;
	private Sprite _raysSprite;
	
	// Buttons
	private Button _playButton;
	private Button _soundButton;
	private Button _creditsButton;

	private Music _menuMusic;

	private static final String _MENU_MUSIC_NAME = "MenuMusicHeroic";
	private static final String _ROTATING_RAYS_SHADER_NAME = "MenuRaysShader";
	private static final String _DEFAULT_TEXTURE_NAME = "DefaultTexture";
	private static final String _BACKGROUND_TEXTURE_NAME = "MenuBackgroundTexture";
}
