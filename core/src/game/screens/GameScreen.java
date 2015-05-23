package game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

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

		_worldListener = new GameWorld.WorldListener() {
			// TODO: implémenter les callcack du world ici...
		};
		_world = new GameWorld(_worldListener, _assetsHndlr, _camera);

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
