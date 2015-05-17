package game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import game.TheEndlessCastle;
import game.World;
import game.utils.Position;

/**
 * Loading screen showing at the beginning of the game when AssetHandler loads assets
 */
public class LoadingScreen extends Screen
{
	public LoadingScreen(TheEndlessCastle game)
	{
		super(game);

		// We need to use raw path to assets and load it without assetsHandler as we are loading assets and reading ressources xml file
		_INSALogoSprite = new Sprite(new Texture(Gdx.files.internal(_INSA_LOGO_TEXTURE_PATH)));
		_openLogoSprite = new Sprite(new Texture(Gdx.files.internal(_OPEN_LOGO_TEXTURE_PATH)));
		_openLogoSprite.setSize(7, 7f *_openLogoSprite.getHeight()/_openLogoSprite.getWidth());
		_INSALogoSprite.setSize(7, 7f *_INSALogoSprite.getHeight()/_INSALogoSprite.getWidth());
		_INSALogoSprite.setPosition(World.METERS_TO_PIXELS/2f*_ratio, 1.5f);
		_openLogoSprite.setPosition(World.METERS_TO_PIXELS/2f*_ratio, 7f);
	}

	@Override
	protected void update() {
		// If we are done loading, we move to the menu screen.
		if(_assetsHndlr.update())
			_game.setScreen(new MenuScreen(_game));
	}

	@Override
	protected void draw() {
		_INSALogoSprite.draw(_batch);
		_openLogoSprite.draw(_batch);
	}

	Sprite _INSALogoSprite;
	Sprite _openLogoSprite;

	private final static String _INSA_LOGO_TEXTURE_PATH = "textures/LogoINSALyon.png";
	private final static String _OPEN_LOGO_TEXTURE_PATH = "textures/openSourceInitiative.png";
}
