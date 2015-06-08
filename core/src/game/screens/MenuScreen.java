package game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import game.Button;
import game.TheEndlessCastle;
import game.Button.ButtonClickListener;
import game.VolumeButtonListener;
import game.VolumeButtonListener.VolumeSettingListener;

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
		VolumeButtonListener _volumeListener = new VolumeButtonListener(_game.GetSettings(), new VolumeSettingListener() {			
			@Override
			public void SetVolume(float volume) {
				_menuMusic.setVolume(volume);
			}
		});
		_sound100Button = new Button(0.006f, (Texture)_assetsHndlr.get("Sound3NormalTexture"), (Texture)_assetsHndlr.get("Sound3OverTexture"), (Texture)_assetsHndlr.get("Sound3PressedTexture"), _volumeListener);
		//_sound75Button = new Button(0.006f, (Texture)_assetsHndlr.get("Sound2NormalTexture"), (Texture)_assetsHndlr.get("Sound2OverTexture"), (Texture)_assetsHndlr.get("Sound2PressedTexture"), _volumeListener);
		//_sound25Button = new Button(0.006f, (Texture)_assetsHndlr.get("Sound1NormalTexture"), (Texture)_assetsHndlr.get("Sound1OverTexture"), (Texture)_assetsHndlr.get("Sound1PressedTexture"), _volumeListener);
		//_sound0Button = new Button(0.006f, (Texture)_assetsHndlr.get("Sound0NormalTexture"), (Texture)_assetsHndlr.get("Sound0OverTexture"), (Texture)_assetsHndlr.get("Sound0PressedTexture"), _volumeListener);
		//_volumeListener.SetButtons(_sound100Button, _sound75Button, _sound25Button, _sound0Button);
		//_volumeListener.SetButton(_sound100Button);
		
		// Play button
		_playButton = new Button(0.015f, (Texture)_assetsHndlr.get("PlayNormalTexture"), (Texture)_assetsHndlr.get("PlayOverTexture"), (Texture)_assetsHndlr.get("PlayPressedTexture"), new ButtonClickListener() {
			@Override
			public void MouseRelease() {
				_menuMusic.stop();
				_game.setScreen(new GameScreen(_game));
			}
		});

		// Credits button
		_creditsButton = new Button(0.015f, (Texture)_assetsHndlr.get("CreditsNormalTexture"), (Texture)_assetsHndlr.get("CreditsOverTexture"), (Texture)_assetsHndlr.get("CreditsPressedTexture"), new ButtonClickListener() {
			@Override
			public void MouseRelease() {
				_menuMusic.stop();
				_game.setScreen(new CreditsScreen(_game));
			}
		});

		// Quit button
		_quitButton = new Button(0.015f, (Texture)_assetsHndlr.get("QuitNormalTexture"), (Texture)_assetsHndlr.get("QuitOverTexture"), (Texture)_assetsHndlr.get("QuitPressedTexture"), new ButtonClickListener() {
			@Override
			public void MouseRelease() {
				Gdx.app.exit();
			}
		});

	}

	@Override
	protected void update()
	{
		_sound100Button.update(_camera);
		//_sound75Button.update(_camera);
		//_sound25Button.update(_camera);
		//_sound0Button.update(_camera);
		_playButton.update(_camera);
		_creditsButton.update(_camera);
		_quitButton.update(_camera);
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
		_sound100Button.render(_spriteBatch);
		//_sound75Button.render(_spriteBatch);
		//_sound25Button.render(_spriteBatch);
		//_sound0Button.render(_spriteBatch);
		_playButton.render(_spriteBatch);
		_creditsButton.render(_spriteBatch);
		_quitButton.render(_spriteBatch);
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
		_sound100Button.SetPosition(_camera.viewportWidth - _sound100Button.getWidth() - 1f, _camera.viewportHeight - 2.5f);;
		//_sound75Button.SetPosition(_camera.viewportWidth - _sound75Button.getWidth() - 1f, _camera.viewportHeight - 2.5f);;
		//_sound25Button.SetPosition(_camera.viewportWidth - _sound25Button.getWidth() - 1f, _camera.viewportHeight - 2.5f);;
		//_sound0Button.SetPosition(_camera.viewportWidth - _sound0Button.getWidth() - 1f, _camera.viewportHeight - 2.5f);;
		_creditsButton.SetPosition(_camera.viewportWidth/2f - 11.08f*_ratio, _camera.viewportHeight/2f - 0.7f);
		_playButton.SetPosition(_camera.viewportWidth/2f - 11.08f*_ratio, _camera.viewportHeight/2f + _creditsButton.getHeight());
		_quitButton.SetPosition(_camera.viewportWidth/2f - 11.08f*_ratio, _camera.viewportHeight/2f - _playButton.getHeight() - 1.4f);
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
	private Button _sound100Button;
	//private Button _sound75Button;
	//private Button _sound25Button;
	//private Button _sound0Button;
	private Button _creditsButton;
	private Button _quitButton;

	private Music _menuMusic;

	private static final String _MENU_MUSIC_NAME = "MenuMusicHeroic";
	private static final String _ROTATING_RAYS_SHADER_NAME = "MenuRaysShader";
	private static final String _DEFAULT_TEXTURE_NAME = "DefaultTexture";
	private static final String _BACKGROUND_TEXTURE_NAME = "MenuBackgroundTexture";
}
