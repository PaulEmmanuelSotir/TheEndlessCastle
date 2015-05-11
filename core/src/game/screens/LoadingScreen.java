package game.screens;

import game.TheEndlessCastle;
import game.dataAccessLayer.AssetsHandler;

import com.badlogic.gdx.Gdx;

/**
 * Loading screen showing at the beginning of the game when AssetHandler loads assets
 */
public class LoadingScreen extends Screen
{
	public LoadingScreen(TheEndlessCastle game)
	{
		super(game);
	}

	@Override
	protected void update() {
		// If we are done loading, we move to the menu screen.
		if(_assetsHndlr.update())
			_game.setScreen(new MenuScreen(_game));
	}

	@Override
	protected void draw() {
		// TODO: draw a loading view
	}
}
