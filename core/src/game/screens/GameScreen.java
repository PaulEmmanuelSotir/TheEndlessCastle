package game.screens;

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
	}

	@Override
	protected void update()
	{
		// Temporary continuous camera scrolling
		_camera.position.x = _time + _camera.viewportWidth/2f;
		
		_world.update(_time);
	}

	@Override
	protected void draw()
	{
		_world.render(_spriteBatch, _modelBatch);
	}
	
	@Override
	public void resize(int width, int height)
	{
		super.resize(width, height);
		_world.setViewRatio(_ratio);
	}

	@Override
	public void dispose() {
		//_randomPlaylist.Stop();
	}

	private RandomMusicPlaylist _randomPlaylist;

	private GameWorld _world;
	private GameWorld.WorldListener _worldListener;
}
