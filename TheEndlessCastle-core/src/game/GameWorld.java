package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

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
		// World events ...
	}
	
	public GameWorld(AssetsHandler assetsHandler, Camera camera, WorldListener listener)
	{
		_ratio = 1f;
		_listener = listener;
		_assetsHndlr = assetsHandler;
		_camera = camera;

		// Box 2D initialization
		_box2DWorld = new World(_GRAVITY, true);
		if(_DEBUG_RENDERING_ENABLED)
			_debugRenderer = new Box2DDebugRenderer();
		
		// Input multiplexer
		_inputMultiplexer = new InputMultiplexer();
		Gdx.input.setInputProcessor(_inputMultiplexer);

		// Entities initialization
		_entities = new ArrayList<Entity>();

		_rotatingRaysSpriteEntity = new RotatingRaysEntity("RotatingRaysSpriteEntity", _assetsHndlr);
		_rotatingRaysSpriteEntity.setZIndex(0);
		_entities.add(_rotatingRaysSpriteEntity);

		_backgroundLayerEntity = new BackgroundLayerEntity(_assetsHndlr);
		_backgroundLayerEntity.setZIndex(1);
		_entities.add(_backgroundLayerEntity);

		_knightEntity = new KnightEntity("KnightEntity", new Position(20, 0), _assetsHndlr, _inputMultiplexer);
		_knightEntity.setZIndex(10);
		_entities.add(_knightEntity);

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
		_box2DWorld.step(1/300f, 6, 2);

		for(Entity e : _entities)
			e.update(this);
	}

	public void render(SpriteBatch spriteBatch, ModelBatch modelBatch)
	{
		if(!_DEBUG_RENDERING_ENABLED)
		{
			spriteBatch.begin();
			for(Entity e : _entities)
				e.render(spriteBatch, modelBatch, this);
			endBatch(spriteBatch, modelBatch);
		}
		else
			_debugRenderer.render(_box2DWorld, _camera.combined);
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
	
	public void SetCurrentShader(ShaderProgram currentShader)
	{
		_currentShader = currentShader;
	}
	
	public InputMultiplexer GetInputMultiplexer()
	{
		return _inputMultiplexer;
	}

	public boolean IsCurrentModelBatch()
	{
		return _isModelBatch;
	}
	
	public boolean IsCurrentSpriteBatch()
	{
		return !_isModelBatch;
	}
	
	public void SetCurrentBatchToModel(SpriteBatch spriteBatch, ModelBatch modelBatch)
	{
		if(!_isModelBatch)
		{
			if(spriteBatch.isDrawing())
				spriteBatch.end();
			modelBatch.begin(_camera);
			_isModelBatch = true;
		}
	}

	public void SetCurrentBatchToSprite(SpriteBatch spriteBatch, ModelBatch modelBatch)
	{
		if(_isModelBatch)
		{
			modelBatch.end();
			if(!spriteBatch.isDrawing())
				spriteBatch.begin();
			_isModelBatch = false;
		}
	}
	
	private void endBatch(SpriteBatch spriteBatch, ModelBatch modelBatch)
	{
		if(_isModelBatch)
			modelBatch.end();
		else if(spriteBatch.isDrawing())
			spriteBatch.end();
	}
	
	/**
	 * Generates randomly a new part of the level by choosing a Segment next the current one
	 */
	private void GenerateMoreWorld() {
		//TODO: impl�menter �a
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
	private boolean _isModelBatch;

	private World _box2DWorld;
	private Box2DDebugRenderer _debugRenderer;
	
	private InputMultiplexer _inputMultiplexer;

	private ArrayList<Entity> _entities;
	private RotatingRaysEntity _rotatingRaysSpriteEntity;
	private KnightEntity _knightEntity;
	private BackgroundLayerEntity _backgroundLayerEntity;

	private static final boolean _DEBUG_RENDERING_ENABLED = false;
	private static final Vector2 _GRAVITY = new Vector2(0.0f, -9.81f);
}