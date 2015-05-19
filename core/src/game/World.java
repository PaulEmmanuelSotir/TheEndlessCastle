package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;

import game.dataAccessLayer.AssetsHandler;
import game.entities.BackgroundLayerEntity;
import game.entities.Entity;
import game.entities.RotatingRaysEntity;
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
		_ratio = 1f;
		_listener = listener;
		_assetsHndlr = assetsHandler;
		_entities = new ArrayList<Entity>();

		//_foregroundSprites = new ArrayList<Sprite>();
		//_backgroundSprites = new ArrayList<Sprite>();
		//_centerSprites = new ArrayList<Sprite>();

		//_obstacles = new ArrayList<Entity>();
		//_movingObstacles = new ArrayList<Entity>();

		_rotatingRaysSpriteEntity = new RotatingRaysEntity("RotatingRaysSpriteEntity", _assetsHndlr);
		_rotatingRaysSpriteEntity.setZIndex(0);
		_entities.add(_rotatingRaysSpriteEntity);
		
		_backgroundLayer = new BackgroundLayerEntity(_assetsHndlr);
		_backgroundLayer.setZIndex(1);
		_entities.add(_backgroundLayer);
		
		// Sort entities by their Zindex so that we draw them in the right order
		Collections.sort(_entities, new Comparator<Entity>() {
			@Override
			public int compare(Entity e1, Entity e2) {
				return (e2.getZIndex() > e1.getZIndex()) ? -1 : 1;
			}
		});
	}

	public void update(float time, Position cameraPos)
	{
		_time = time;
		_cameraPos = cameraPos;

		for(Entity e : _entities)
			e.update(this);
	}
	
	public void render(SpriteBatch batch)
	{
		for(Entity e : _entities)
			e.render(batch, this);
	}
	
	public void setCameraRatio(float ratio)
	{
		_ratio = ratio;
		_backgroundLayer.ScaleToHeight(32f*_ratio);
		_rotatingRaysSpriteEntity.resize(METERS_TO_PIXELS, _ratio);
	}
	
	public float getCameraRatio()
	{
		return _ratio;
	}

	public float GetTime()
	{
		return _time;
	}

	public Position GetCameraPosition()
	{
		return _cameraPos;
	}
	
	public ShaderProgram GetCurrentShader()
	{
		return _currentShader;
	}
	
	public void SetCurrentShader(ShaderProgram shader)
	{
		_currentShader = shader;
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
	private float _ratio;
	
	private ShaderProgram _currentShader;
	
	private RotatingRaysEntity _rotatingRaysSpriteEntity;

	//private List<Sprite> _foregroundSprites;
	//private List<Sprite> _backgroundSprites;
	//private List<Sprite> _centerSprites;
	private BackgroundLayerEntity _backgroundLayer;

	//private List<Entity> _obstacles;
	//private List<Entity> _movingObstacles;
	
	private ArrayList<Entity> _entities;

	private static final Vector2 _GRAVITY = new Vector2(0.0f, -9.81f);
}
