package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import game.dataAccessLayer.AssetsHandler;
import game.entities.Entity;
import game.utils.Position;

/**
 * World class containing all objects rendered in game
 */
public class World
{
	public interface WorldListener {
		// TODO: mettre les évenements du world ici...
	}

	public static final float PIXELS_TO_METERS = .3125f;
	public static final float METERS_TO_PIXELS = 32f;

	public World(WorldListener listener, AssetsHandler assetsHandler)
	{
		_listener = listener;
		_assetsHndlr = assetsHandler;

		_foregroundSprites = new ArrayList<Sprite>();
		_backgroundSprites = new ArrayList<Sprite>();
		_centerSprites = new ArrayList<Sprite>();

		_obstacles = new ArrayList<Entity>();
		_movingObstacles = new ArrayList<Entity>();

		//TODO: Charger le début du niveau déssiné à la main
	}

	public void update(float time, Position cameraPos)
	{
		_time = time;
		_cameraPos = cameraPos;
	}
	
	public void render(SpriteBatch batch)
	{
	}

	public float GetTime()
	{
		return _time;
	}

	public Position GetCameraPosition()
	{
		return _cameraPos;
	}

	/**
	 * Generates randomly a new part of the level.
	 */
	private void GenerateMoreWorld() {
		//TODO: implémenter ça
	}

	private WorldListener _listener;
	private AssetsHandler _assetsHndlr;

	private int _score;
	private long _distanceTraveled;
	private Difficulty _difficultyLevel;
	private float _time;
	private Position _cameraPos;

	private List<Sprite> _foregroundSprites;
	private List<Sprite> _backgroundSprites;
	private List<Sprite> _centerSprites;

	private List<Entity> _obstacles;
	private List<Entity> _movingObstacles;

	private static final Vector2 GRAVITY = new Vector2(0.0f, -9.81f);
}
