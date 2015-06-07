package game.screens;

import java.util.ArrayList;
import java.util.List;

import game.Button;
import game.TheEndlessCastle;
import game.VolumeButtonListener;
import game.Button.ButtonClickListener;
import game.VolumeButtonListener.VolumeSettingListener;
import game.dataAccessLayer.TypedAssetDescriptor;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Align;

/**
 * Credits screen
 */
public class CreditsScreen extends Screen
{
	public CreditsScreen(TheEndlessCastle game)
	{
		super(game);

		// Font
		FreeTypeFontGenerator generator = _assetsHndlr.get(_CREDITS_FONT_NAME);
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 64;
		parameter.genMipMaps = true;
		parameter.minFilter = TextureFilter.MipMapLinearLinear;
		parameter.magFilter = TextureFilter.Linear;
		parameter.color = TheEndlessCastle.MAIN_GAME_COLOR;
		_font = generator.generateFont(parameter);
		_font.getData().setScale(0.01f, 0.01f);

		// Lava background
		_lavaShader = _assetsHndlr.get(_LAVA_SHADER_NAME);
		_timeLocaction  = _lavaShader.getUniformLocation(_TIME_UNIFORM_NAME);
		_ratioLocaction  = _lavaShader.getUniformLocation(_RATIO_UNIFORM_NAME);
		_lavaSprite = new Sprite((Texture)_assetsHndlr.get(_RANDOM_NOISE_TEXTURE));
		_lavaSprite.getTexture().setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
		_lavaSprite.getTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Linear);

		// Sprites and Assets descriptors
		_assetsDescriptors = _assetsHndlr.GetAssetsDescriptors();
		_stoneSprites = new ArrayList<Sprite>(_assetsDescriptors.size());
		Texture StoneTexture = _assetsHndlr.get(_CREDITS_STONE_TEXTURE_NAME);
		for(int idx = 0; idx < _assetsDescriptors.size(); idx++)
		{
			Sprite StoneSprite = new Sprite(StoneTexture);
			StoneSprite.setScale(0.01f);
			StoneSprite.setCenter(_camera.viewportWidth/2f, -idx*15f);
			_stoneSprites.add(StoneSprite);
		}

		//Music
		_creditsMusic = _assetsHndlr.get(_CREDITS_MUSIC_NAME);
		if(_creditsMusic != null)
		{
			_creditsMusic.setVolume(game.GetSettings().getVolume());
			_creditsMusic.setLooping(true);
			_creditsMusic.play();
		}

		// Sound button
		VolumeButtonListener _volumeListener = new VolumeButtonListener(_game.GetSettings(), new VolumeSettingListener() {			
			@Override
			public void SetVolume(float volume) {
				_creditsMusic.setVolume(volume);
			}
		});
		_sound100Button = new Button(0.006f, (Texture)_assetsHndlr.get("Sound3NormalTexture"), (Texture)_assetsHndlr.get("Sound3OverTexture"), (Texture)_assetsHndlr.get("Sound3PressedTexture"), _volumeListener);
		//_sound75Button = new Button(0.006f, (Texture)_assetsHndlr.get("Sound2NormalTexture"), (Texture)_assetsHndlr.get("Sound2OverTexture"), (Texture)_assetsHndlr.get("Sound2PressedTexture"), _volumeListener);
		//_sound25Button = new Button(0.006f, (Texture)_assetsHndlr.get("Sound1NormalTexture"), (Texture)_assetsHndlr.get("Sound1OverTexture"), (Texture)_assetsHndlr.get("Sound1PressedTexture"), _volumeListener);
		//_sound0Button = new Button(0.006f, (Texture)_assetsHndlr.get("Sound0NormalTexture"), (Texture)_assetsHndlr.get("Sound0OverTexture"), (Texture)_assetsHndlr.get("Sound0PressedTexture"), _volumeListener);
		//_volumeListener.SetButtons(_sound100Button, _sound75Button, _sound25Button, _sound0Button);
		_volumeListener.SetButton(_sound100Button);
		
		
		// Menu button
		_menuButton = new Button(0.006f, (Texture)_assetsHndlr.get("MenuNormalTexture"), (Texture)_assetsHndlr.get("MenuOverTexture"), (Texture)_assetsHndlr.get("MenuPressedTexture"), new ButtonClickListener() {
			@Override
			public void MouseRelease() {
				_creditsMusic.stop();
				_game.setScreen(new MenuScreen(_game));
			}
		});
	}

	@Override
	protected void update() {
		// Update camera position with a time function which permits a focus on credits stones
		_camera.position.y = -4f*(_time + (float)Math.pow(Math.sin((_time-10f)*4f*Math.PI/_STEP), 2)) + _camera.viewportHeight/2f;
		
		// Center lava background
		_lavaSprite.setCenter(_camera.position.x , _camera.position.y);
		
		// Update buttons
		_menuButton.update(_camera);
		_sound100Button.update(_camera);
	//	_sound75Button.update(_camera);
	//	_sound25Button.update(_camera);
	//	_sound0Button.update(_camera);
		
		// End of the credits
		if(-_camera.position.y > _assetsDescriptors.size()*_STEP + 5f)
		{
			_creditsMusic.stop();
			_game.setScreen(new MenuScreen(_game));
		}
	}

	@Override
	protected void draw() {
		_spriteBatch.begin();

		// Draw background lava
		_spriteBatch.setShader(_lavaShader);
		_lavaShader.setUniformf(_timeLocaction, _time);
		_lavaShader.setUniformf(_ratioLocaction, _ratio);
		_lavaSprite.draw(_spriteBatch);

		// Draw Credits stones
		_spriteBatch.setShader(null);
		for(int idx = 0; idx < _assetsDescriptors.size(); idx++)
		{
			Sprite sprite = _stoneSprites.get(idx);
			sprite.draw(_spriteBatch);
			_font.draw(_spriteBatch, _assetsDescriptors.get(idx).toString(),
					_camera.viewportWidth/2f - sprite.getWidth()*sprite.getScaleX()/2f + 1.5f, -idx*_STEP + 2f,
					sprite.getWidth()*sprite.getScaleX() - 3f, Align.center, true);
		}

		// Render buttons
		_menuButton.render(_spriteBatch);
		_sound100Button.render(_spriteBatch);
		//_sound75Button.render(_spriteBatch);
		//_sound25Button.render(_spriteBatch);
		//_sound0Button.render(_spriteBatch);

		_spriteBatch.end();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		
		// Resize lava sprite
		_lavaSprite.setSize(_camera.viewportWidth, _camera.viewportHeight);
		
		// Update buttons position
		_menuButton.SetPosition(1f, _camera.viewportHeight - 2.5f);
		_sound100Button.SetPosition(_camera.viewportWidth - _sound100Button.getWidth() - 1f, _camera.viewportHeight - 2.5f);
		//_sound75Button.SetPosition(_camera.viewportWidth - _sound75Button.getWidth() - 1f, _camera.viewportHeight - 2.5f);
		//_sound25Button.SetPosition(_camera.viewportWidth - _sound25Button.getWidth() - 1f, _camera.viewportHeight - 2.5f);
		//_sound0Button.SetPosition(_camera.viewportWidth - _sound0Button.getWidth() - 1f, _camera.viewportHeight - 2.5f);
	}

	@Override
	public void dispose()
	{
		_font.dispose();
	}

	@SuppressWarnings("rawtypes")
	private ArrayList<TypedAssetDescriptor> _assetsDescriptors;

	// Stone sprites
	private List<Sprite> _stoneSprites;
	private static final String _CREDITS_STONE_TEXTURE_NAME = "CreditsStoneTexture";
	private static final float _STEP = 15f;

	// Credits font
	private BitmapFont _font;
	private static final String _CREDITS_FONT_NAME = "XoloniumFont";

	// Lava background
	private Sprite _lavaSprite;
	private ShaderProgram _lavaShader;
	private int _timeLocaction;
	private int _ratioLocaction;
	private static final String _LAVA_SHADER_NAME = "Lava2Shader";
	private static final String _TIME_UNIFORM_NAME = "u_globalTime";
	private static final String _RATIO_UNIFORM_NAME = "u_ratio";
	private static final String _RANDOM_NOISE_TEXTURE = "RandomNoiseTexture";

	// Buttons
	private Button _sound100Button;
	//private Button _sound75Button;
	//private Button _sound25Button;
	//private Button _sound0Button;
	private Button _menuButton;
	
	// Music
	private Music _creditsMusic;
	private static final String _CREDITS_MUSIC_NAME = "CreditsMusic";
}
