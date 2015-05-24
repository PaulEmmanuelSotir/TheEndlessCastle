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

		// Random music
		_randomPlaylist = new RandomMusicPlaylist(_game.GetSettings().getVolume()/100f,_assetsHndlr);
		_randomPlaylist.Start();

		// Menu button
		_menuButton = new Button(0.006f, (Texture)_assetsHndlr.get("PauseNormalTexture"), (Texture)_assetsHndlr.get("PauseOverTexture"), (Texture)_assetsHndlr.get("PausePressedTexture"), new ButtonClickListener() {
			@Override
			public void MouseRelease() {
				_randomPlaylist.Stop();
				_game.setScreen(new MenuScreen(_game));
			}
		});

		// Sound button
		_soundButton = new Button(0.006f, (Texture)_assetsHndlr.get("Sound3NormalTexture"), (Texture)_assetsHndlr.get("Sound3OverTexture"), (Texture)_assetsHndlr.get("Sound3PressedTexture"), new ButtonClickListener() {
			@Override
			public void MouseRelease() {
				if(_game.GetSettings().getVolume() > 80)
				{
					_randomPlaylist.SetVolume(0.75f);
					_game.GetSettings().setVolume(75);
				}
				else if(_game.GetSettings().getVolume() > 50)
				{
					_randomPlaylist.SetVolume(0.25f);
					_game.GetSettings().setVolume(25);
				}
				else if(_game.GetSettings().getVolume() > 10)
				{
					_randomPlaylist.SetVolume(0);
					_game.GetSettings().setVolume(0);
				}
				else
				{
					_randomPlaylist.SetVolume(1);					
					_game.GetSettings().setVolume(100);
				}
				// TODO: change button textures
			}
		});

		// Pause button
		_pauseButton = new Button(0.006f, (Texture)_assetsHndlr.get("PauseNormalTexture"), (Texture)_assetsHndlr.get("PauseOverTexture"), (Texture)_assetsHndlr.get("PausePressedTexture"), new ButtonClickListener() {
			@Override
			public void MouseRelease() {
				// TODO: pause
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
		_soundButton.update(_camera);
		_pauseButton.update(_camera);
	}

	@Override
	protected void draw()
	{
		// Render world
		_world.render(_spriteBatch, _modelBatch);

		// Render HUD
		_spriteBatch.begin();
		_menuButton.render(_spriteBatch);
		_soundButton.render(_spriteBatch);
		_pauseButton.render(_spriteBatch);
		_spriteBatch.end();
	}

	@Override
	public void resize(int width, int height)
	{
		super.resize(width, height);
		_world.setViewRatio(_ratio);

		_menuButton.SetPosition(1f, _camera.viewportHeight - 2.5f);
		_pauseButton.SetPosition(_camera.viewportWidth - _pauseButton.getWidth() - 1f, _camera.viewportHeight - 2.5f);
		_soundButton.SetPosition(_camera.viewportWidth - _soundButton.getWidth() - 1f, _camera.viewportHeight - _pauseButton.getHeight() - 3f);
	}

	@Override
	public void dispose() {
		_randomPlaylist.Stop();
	}

	private RandomMusicPlaylist _randomPlaylist;

	private GameWorld _world;
	private GameWorld.WorldListener _worldListener;

	private Button _menuButton;
	private Button _soundButton;
	private Button _pauseButton;
}
