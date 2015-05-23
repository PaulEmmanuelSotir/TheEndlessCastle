package game.screens;

import java.util.ArrayList;
import java.util.List;

import game.TheEndlessCastle;
import game.dataAccessLayer.AssetsHandler;
import game.dataAccessLayer.TypedAssetDescriptor;
import game.dataAccessLayer.TypedAssetDescriptor.AssetTypeEnum;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Array;

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
		parameter.color = _game.MAIN_GAME_COLOR;
		_font = generator.generateFont(parameter);
		_font.setScale(0.01f);
		
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
			_creditsMusic.setLooping(true);
			_creditsMusic.play();
		}
	}

	@Override
	protected void update() {
		_camera.position.y = -2*_time + _camera.viewportHeight/2f;
		_lavaSprite.setCenter(_camera.position.x , _camera.position.y);
		
		if(-_camera.position.y > _assetsDescriptors.size()*15f + 5f)
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
			_font.drawWrapped(_spriteBatch, _assetsDescriptors.get(idx).toString(), _camera.viewportWidth/2f - sprite.getWidth()*sprite.getScaleX()/2f + 1.5f, -idx*15f + 2f, sprite.getWidth()*sprite.getScaleX() - 3f, HAlignment.CENTER);
		}
		
		_spriteBatch.end();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		_lavaSprite.setSize(_camera.viewportWidth, _camera.viewportHeight);
	}
	
	private ArrayList<TypedAssetDescriptor> _assetsDescriptors;

	// Stone sprites
	private List<Sprite> _stoneSprites;
	private static final String _CREDITS_STONE_TEXTURE_NAME = "CreditsStoneTexture";
	
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

	// Music
	private Music _creditsMusic;
	private static final String _CREDITS_MUSIC_NAME = "MenuMusicHeroic";
}
