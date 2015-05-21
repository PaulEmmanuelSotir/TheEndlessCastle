package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;

import game.dataAccessLayer.AssetsHandler;
import game.entities.BackgroundLayerEntity;
import game.entities.Entity;
import game.entities.KnightEntity;
import game.entities.RotatingRaysEntity;
import game.utils.Position;

/**
 * GameWorld class containing all objects rendered in game and their corresponding Box2D bodies
 */
public class GameWorld
{
	public static final float WORLD_VIEW_WIDTH = 32f;

	public interface WorldListener {
		// TODO: mettre les évenements du world ici...
	}

	public GameWorld(WorldListener listener, AssetsHandler assetsHandler, Camera camera)
	{
		_ratio = 1f;
		_listener = listener;
		_assetsHndlr = assetsHandler;
		_camera = camera;


		// Entities initialization
		_entities = new ArrayList<Entity>();

		_rotatingRaysSpriteEntity = new RotatingRaysEntity("RotatingRaysSpriteEntity", _assetsHndlr);
		_rotatingRaysSpriteEntity.setZIndex(0);
		_entities.add(_rotatingRaysSpriteEntity);

		_backgroundLayerEntity = new BackgroundLayerEntity(_assetsHndlr);
		_backgroundLayerEntity.setZIndex(1);
		_entities.add(_backgroundLayerEntity);

		_knightEntity = new KnightEntity("KnightEntity", new Position(0, 0), _assetsHndlr);

		// Sort entities by their Zindex so that we draw them in the right order
		Collections.sort(_entities, new Comparator<Entity>() {
			@Override
			public int compare(Entity e1, Entity e2) {
				return (e2.getZIndex() > e1.getZIndex()) ? -1 : 1;
			}
		});
	}

	public void update(float time)
	{
		_time = time;

		for(Entity e : _entities)
			e.update(this);
	}

	public void render(SpriteBatch batch)
	{
		for(Entity e : _entities)
			e.render(batch, this);
	}

	public void setViewRatio(float ratio)
	{
		_ratio = ratio;
		_backgroundLayerEntity.ScaleToHeight(32f*_ratio);
		_rotatingRaysSpriteEntity.resize(WORLD_VIEW_WIDTH, _ratio);
	}

	public float getViewRatio()
	{
		return _ratio;
	}

	public float GetTime()
	{
		return _time;
	}

	public float GetDeltaTime()
	{
		return Gdx.graphics.getDeltaTime(); 
	}

	public Position GetCameraPosition()
	{
		return new Position(_camera.position);
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
	 * Generates randomly a new part of the level by choosing a Segment next the current one
	 */
	private void GenerateMoreWorld() {
		//TODO: implémenter ça
	}

	private WorldListener _listener;
	private AssetsHandler _assetsHndlr;
	private Camera _camera;

	private int _score;
	private long _distanceTraveled;
	private Difficulty _difficultyLevel;
	private float _time;
	private float _ratio;
	private ShaderProgram _currentShader;

	private ArrayList<Entity> _entities;
	private RotatingRaysEntity _rotatingRaysSpriteEntity;
	private KnightEntity _knightEntity;
	private BackgroundLayerEntity _backgroundLayerEntity;

	private static final Vector2 _GRAVITY = new Vector2(0.0f, -9.81f);
}
