package game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;

import game.TheEndlessCastle;

/**
 * Loading screen showing at the beginning of the game when AssetHandler loads assets
 */
public class LoadingScreen extends Screen
{
	public LoadingScreen(TheEndlessCastle game)
	{
		super(game);

		// We need to use raw path to assets and load it without assetsHandler as we are loading assets and reading ressources xml file
		Texture INSATexture = new Texture(Gdx.files.internal(_INSA_LOGO_TEXTURE_PATH), true);
		INSATexture.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.Linear);
		Texture OpenTexture = new Texture(Gdx.files.internal(_OPEN_LOGO_TEXTURE_PATH), true);
		OpenTexture.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.Linear);
		_INSALogoSprite = new Sprite(INSATexture);
		_mainLogoSprite = new Sprite(OpenTexture);
		_mainLogoSprite.setSize(9, 9f *_mainLogoSprite.getHeight()/_mainLogoSprite.getWidth());
		_INSALogoSprite.setSize(4, 4f *_INSALogoSprite.getHeight()/_INSALogoSprite.getWidth());
		_INSALogoSprite.setCenter(_camera.viewportWidth/2f, 1f);
		_mainLogoSprite.setCenter(_camera.viewportWidth/2f+1f, 9.5f);
	}

	@Override
	protected void update() {
		// If we are done loading, we move to the menu screen.
		if(_assetsHndlr.update())
			_game.setScreen(new MenuScreen(_game));
	}

	@Override
	protected void draw() {
		_spriteBatch.begin();
		_INSALogoSprite.draw(_spriteBatch);
		_mainLogoSprite.draw(_spriteBatch);
		_spriteBatch.end();
	}
	
	@Override
	public void dispose()
	{
		_INSALogoSprite.getTexture().dispose();
		_mainLogoSprite.getTexture().dispose();
	}

	Sprite _INSALogoSprite;
	Sprite _mainLogoSprite;

	private final static String _INSA_LOGO_TEXTURE_PATH = "textures/LogoINSALyon.png";
	private final static String _OPEN_LOGO_TEXTURE_PATH = "textures/MainLogo.png";
}
