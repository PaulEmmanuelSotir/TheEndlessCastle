package GDXGame.EndlessCastle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * World class containing all objects rendered in game
 */
public class World
{
	public interface WorldListener {
		public void moveForward();
		public void moveBackward();
		// TODO: mettre les évenements du world ici...
	}
	
	public World(WorldListener listener)
	{
		_listener = listener;
		
		_playerKnight = new Knight();

		_foregroundSprites = new ArrayList<Sprite>();
		_backgroundSprites = new ArrayList<Sprite>();
		_centerSprites = new ArrayList<Sprite>();
		
		_obstacles = new ArrayList<GameObject>();
		_movingObstacles = new ArrayList<MovingGameObject>();
		
		//TODO: Charger le début du niveau déssiné à la main
	}
	
	/**
	 * Generates randomly a new part of the level.
	 */
	private void generateMoreWorld() {
		//TODO: implémenter ça
	}
	
	private WorldListener _listener;
	
	private int _score;
	private long _distanceTraveled;
	private Difficulty _difficultyLevel;

	private final Knight _playerKnight;
	
	private List<Sprite> _foregroundSprites;
	private List<Sprite> _backgroundSprites;
	private List<Sprite> _centerSprites;
	
	private List<GameObject> _obstacles;
	private List<MovingGameObject> _movingObstacles;
	
	private static final Vector2 GRAVITY = new Vector2(0.0f, -9.81f);
}
