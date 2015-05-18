package game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.GL20;

import game.RandomMusicPlaylist;
import game.TheEndlessCastle;
import game.World;
import game.utils.Position;

/**
 * Game screen class
 */
public class GameScreen extends Screen
{
	public GameScreen(TheEndlessCastle game)
	{
		super(game);

		_worldListener = new World.WorldListener() {
			// TODO: implémenter les callcack du world ici...
		};
		_world = new World(_worldListener, _assetsHndlr);

		_randomPlaylist = new RandomMusicPlaylist(_assetsHndlr);
		_randomPlaylist.Start();
	}

	@Override
	protected void update()
	{
		// Temporary continuous camera scrolling
		_camera.position.x = _time + _camera.viewportWidth;
		_world.update(_time, new Position(_camera.position.x, _camera.position.y));
	}

	@Override
	protected void draw()
	{
		_world.render(_batch);
	}
	
	@Override
	public void resize(int width, int height)
	{
		super.resize(width, height);
		_world.setCameraRatio(_ratio);
	}

	@Override
	public void dispose() {
		_randomPlaylist.Stop();
	}

	private RandomMusicPlaylist _randomPlaylist;

	private World _world;
	private World.WorldListener _worldListener;
}
