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
		_openLogoSprite = new Sprite(OpenTexture);
		_openLogoSprite.setSize(7, 7f *_openLogoSprite.getHeight()/_openLogoSprite.getWidth());
		_INSALogoSprite.setSize(7, 7f *_INSALogoSprite.getHeight()/_INSALogoSprite.getWidth());
		_INSALogoSprite.setCenter(_camera.viewportWidth/2f, 2.5f);
		_openLogoSprite.setCenter(_camera.viewportWidth/2f, 11f);
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
		_openLogoSprite.draw(_spriteBatch);
		_spriteBatch.end();
	}

	Sprite _INSALogoSprite;
	Sprite _openLogoSprite;

	private final static String _INSA_LOGO_TEXTURE_PATH = "textures/LogoINSALyon.png";
	private final static String _OPEN_LOGO_TEXTURE_PATH = "textures/openSourceInitiative.png";
}
