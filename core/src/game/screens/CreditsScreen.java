package game.screens;

import java.util.ArrayList;
import java.util.List;

import game.Button;
import game.TheEndlessCastle;
import game.Button.ButtonClickListener;
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
		//_font.setScale(0.01f, 0.01f);

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
		_soundButton = new Button(0.006f, (Texture)_assetsHndlr.get("Sound3NormalTexture"), (Texture)_assetsHndlr.get("Sound3OverTexture"), (Texture)_assetsHndlr.get("Sound3PressedTexture"), new ButtonClickListener() {
			@Override
			public void MouseRelease() {
				if(_game.GetSettings().getVolume() > 80)
				{
					_creditsMusic.setVolume(0.75f);
					_game.GetSettings().setVolume(75);
				}
				else if(_game.GetSettings().getVolume() > 50)
				{
					_creditsMusic.setVolume(0.25f);
					_game.GetSettings().setVolume(25);
				}
				else if(_game.GetSettings().getVolume() > 10)
				{
					_creditsMusic.setVolume(0);
					_game.GetSettings().setVolume(0);
				}
				else
				{
					_creditsMusic.setVolume(1);		
					_game.GetSettings().setVolume(100);
				}
				// TODO: change button textures
			}
		});
		
		// Menu button
		_menuButton = new Button(0.006f, (Texture)_assetsHndlr.get("PauseNormalTexture"), (Texture)_assetsHndlr.get("PauseOverTexture"), (Texture)_assetsHndlr.get("PausePressedTexture"), new ButtonClickListener() {
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
		_soundButton.update(_camera);
		
		// End of the credits
		if(-_camera.position.y > _assetsDescriptors.size()*_STEP + 5f)
		{
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
		//	_font.drawWrapped(_spriteBatch, _assetsDescriptors.get(idx).toString(), _camera.viewportWidth/2f - sprite.getWidth()*sprite.getScaleX()/2f + 1.5f, -idx*_STEP + 2f, sprite.getWidth()*sprite.getScaleX() - 3f, Align.center);
		}

		// Render buttons
		_menuButton.render(_spriteBatch);
		_soundButton.render(_spriteBatch);

		_spriteBatch.end();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		
		// Resize lava sprite
		_lavaSprite.setSize(_camera.viewportWidth, _camera.viewportHeight);
		
		// Update buttons position
		_menuButton.SetPosition(1f, _camera.viewportHeight - 2.5f);
		_soundButton.SetPosition(_camera.viewportWidth - _soundButton.getWidth() - 1f, _camera.viewportHeight - 2.5f);
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
	private Button _soundButton;
	private Button _menuButton;
	
	// Music
	private Music _creditsMusic;
	private static final String _CREDITS_MUSIC_NAME = "MenuMusicHeroic";
}
