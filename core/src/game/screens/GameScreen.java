package game.screens;

import com.badlogic.gdx.graphics.Texture;

import game.Button;
import game.Button.ButtonClickListener;
import game.RandomMusicPlaylist;
import game.TheEndlessCastle;
import game.GameWorld;

/**
 * Game screen class
 */
public class GameScreen extends Screen
{	
	public GameScreen(TheEndlessCastle game)
	{
		super(game);

		_world = new GameWorld(_assetsHndlr, _camera, new GameWorld.WorldListener() {
			// World events ...
		});
		
		_randomPlaylist = new RandomMusicPlaylist(_assetsHndlr);
		//_randomPlaylist.Start();
		// Menu button
		_menuButton = new Button(0.006f, (Texture)_assetsHndlr.get("PauseNormalTexture"), (Texture)_assetsHndlr.get("PauseOverTexture"), (Texture)_assetsHndlr.get("PausePressedTexture"), new ButtonClickListener() {
			@Override
			public void MouseRelease() {
				_randomPlaylist.Stop();
				_game.setScreen(new MenuScreen(_game));
			}
		});
	}

	@Override
	protected void update()
	{
		// Temporary continuous camera scrolling
		_camera.position.x = _time + _camera.viewportWidth/2f;
		
		//Update world
		_world.update(_time);
		
		// Update HUD
		_menuButton.update(_camera);
	}

	@Override
	protected void draw()
	{
		// Render world
		_world.render(_spriteBatch, _modelBatch);
		
		// Render HUD
		_spriteBatch.begin();
		_menuButton.render(_spriteBatch);
		_spriteBatch.end();
	}
	
	@Override
	public void resize(int width, int height)
	{
		super.resize(width, height);
		_world.setViewRatio(_ratio);
		
		_menuButton.SetPosition(1f, _camera.viewportHeight - 2.5f);
	}

	@Override
	public void dispose() {
		//_randomPlaylist.Stop();
	}

	private RandomMusicPlaylist _randomPlaylist;

	private GameWorld _world;
	private GameWorld.WorldListener _worldListener;
	
	private Button _menuButton;
}
