package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

import aurelienribon.bodyeditor.BodyEditorDAL;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;

import game.Segment.SegmentDescriptor;
import game.Segment.SegmentTypeEnum;
import game.components.PlayerComponent.MoveListener;
import game.dataAccessLayer.AssetsHandler;
import game.dataAccessLayer.Settings.Difficulty;
import game.entities.BackgroundLayerEntity;
import game.entities.Entity;
import game.entities.KnightEntity;
import game.entities.RotatingRaysEntity;
import game.utils.Position;

/**
 * GameWorld class containing all objects rendered in game and their corresponding Box2D bodies
 */
public class GameWorld implements Disposable
{	
	public static final float WORLD_VIEW_WIDTH = 32f;

	public interface WorldListener {
		public void PlayerDied(int Score, int DistanceTraveled);
	}

	public GameWorld(AssetsHandler assetsHandler, Camera camera, WorldListener listener, boolean isBloomShaderEnabled) {
		_ratio = 1f;
		_listener = listener;
		_assetsHndlr = assetsHandler;
		_camera = camera;
		_isBloomShaderEnabled = isBloomShaderEnabled;

		// Box 2D initialization
		_box2DWorld = new World(_GRAVITY, true);
		if(_DEBUG_RENDERING_ENABLED)
			_debugRenderer = new Box2DDebugRenderer();
		_bodyDAL = _assetsHndlr.get(_BODIES_DAL_NAME);
		
		// Input multiplexer
		_inputMultiplexer = new InputMultiplexer();
		Gdx.input.setInputProcessor(_inputMultiplexer);

		// Bloom shader
		if(_isBloomShaderEnabled) {
		_bloomShader = _assetsHndlr.get(_BLOOM_SAHDER_NAME);
		_ratioLocation = _bloomShader.getUniformLocation(_RATIO_UNIFORM_NAME);
		}
		
		// Entities initialization
		_entities = new ArrayList<Entity>();
		// Rotating rays
		_rotatingRaysSpriteEntity = new RotatingRaysEntity("RotatingRaysSpriteEntity", _assetsHndlr);
		_rotatingRaysSpriteEntity.setZIndex(0);
		_entities.add(_rotatingRaysSpriteEntity);
		// Background
		_backgroundLayerEntity = new BackgroundLayerEntity(_assetsHndlr);
		_backgroundLayerEntity.setZIndex(1);
		//if(_isBloomShaderEnabled)
		//	_backgroundLayerEntity.SetShader(_bloomShader);
		_entities.add(_backgroundLayerEntity);
		// Segments
		_semgentsFIFO = new LinkedList<Segment>();
		for(int n = 0; n < _SEGMENTS_NUMBER; n++)
			AddSegment();
		Segment FirstSeg = _semgentsFIFO.peek();
		// Knight
		_knightEntity = new KnightEntity("KnightEntity", new Position(18f, 8f), this);
		_knightEntity.setZIndex(10);
		//if(_isBloomShaderEnabled)
		//	_knightEntity.SetShader(_bloomShader);
		_knightEntity.SetMoveListenner(new MoveListener() {
			@Override
			public void MoveForward() {
				_score++;
				_distanceTraveled += KnightEntity._JUMP_STEP;
			}

			@Override
			public void MoveBackward() {
				if(_score >= 2)
					_score -= 2;
				else
					_score = 0;
				_distanceTraveled -= KnightEntity._JUMP_STEP;
			}

			@Override
			public void Jump() { }
			@Override
			public void Uncrouch() { }
			@Override
			public void Crouch() { }
		});
		_entities.add(_knightEntity);

		// Sort entities by their Zindex so that we draw them in the right order
		_entitiesZindexComparator = new Comparator<Entity>() {
			@Override
			public int compare(Entity e1, Entity e2) {
				return (e2.getZIndex() > e1.getZIndex()) ? -1 : 1;
			}
		};
		Collections.sort(_entities, _entitiesZindexComparator);
	}

	public void update(float time) {
		_time = time;
		_box2DWorld.step(1/300f, 6, 2);

		for(Entity e : _entities)
			e.update(this);

		// Add a new random segment if the player/camera is near the end of the last segment
		if(_camera.position.x + _camera.viewportWidth + 10f > _lastSegment.GetEndXPosition())
		{
			AddSegment();
			Collections.sort(_entities, _entitiesZindexComparator); // update Z indexes
		}

		// If player can't follow the camera, let's kill him !
		if(_camera.position.x -  _camera.viewportWidth/2f >  _knightEntity.getPosition().x)
		{
			_knightEntity.Die();
			_listener.PlayerDied(_score, _distanceTraveled);
		}
	}

	public void render(SpriteBatch spriteBatch, ModelBatch modelBatch) {
		if(_CLASSIC_RENDERING_ENABLED)
		{
			spriteBatch.begin();
			//_bloomShader.setUniformf(_ratioLocation, _ratio);
			//SetCurrentShader(_bloomShader);
			for(Entity e : _entities)
				e.render(spriteBatch, modelBatch, this);
			endBatch(spriteBatch, modelBatch);
		}
		
		if(_DEBUG_RENDERING_ENABLED)
			_debugRenderer.render(_box2DWorld, _camera.combined);
	}

	public void setViewRatio(float ratio) {
		_ratio = ratio;
		_backgroundLayerEntity.ScaleToHeight(32f*_ratio);
		_rotatingRaysSpriteEntity.resize(WORLD_VIEW_WIDTH, _ratio);
	}

	public float getViewRatio() {
		return _ratio;
	}

	public float GetTime() {
		return _time;
	}

	public float GetDeltaTime() {
		return Gdx.graphics.getDeltaTime(); 
	}

	public float GetPlayerXPosition() {
		return _knightEntity.getPosition().x;
	}

	public Position GetCameraPosition() {
		return new Position(_camera.position);
	}

	public ShaderProgram GetCurrentShader() {
		return _currentShader;
	}

	public void SetCurrentShader(ShaderProgram currentShader) {
		_currentShader = currentShader;
	}

	/**
	 *  Gets the current segment , i.e. the segment where the player is.
	 */
	public final SegmentDescriptor GetCurrentSegmentDescriptor(float playerPosX) {
		for(Segment seg : _semgentsFIFO)
			if(seg.GetEndXPosition() > playerPosX && seg.GetBeginningXPosition() <= playerPosX)
				return seg.GetSegmentDescriptor();
		return null;
	}

	public InputMultiplexer GetInputMultiplexer() {
		return _inputMultiplexer;
	}

	public int GetScore()
	{
		return _score;
	}

	public AssetsHandler GetAssetsHandler() {
		return _assetsHndlr;
	}

	public BodyEditorDAL GetBodyDAL() {
		return _bodyDAL;
	}

	public World GetBox2DWorld() {
		return _box2DWorld;
	}

	public boolean IsCurrentModelBatch() {
		return _isModelBatch;
	}

	public boolean IsCurrentSpriteBatch() {
		return !_isModelBatch;
	}

	public void SetCurrentBatchToModel(SpriteBatch spriteBatch, ModelBatch modelBatch) {
		if(!_isModelBatch)
		{
			if(spriteBatch.isDrawing())
				spriteBatch.end();
			modelBatch.begin(_camera);
			_isModelBatch = true;
		}
	}

	public void SetCurrentBatchToSprite(SpriteBatch spriteBatch, ModelBatch modelBatch) {
		if(_isModelBatch)
		{
			modelBatch.end();
			if(!spriteBatch.isDrawing())
				spriteBatch.begin();
			_isModelBatch = false;
		}
	}

	private void endBatch(SpriteBatch spriteBatch, ModelBatch modelBatch) {
		if(_isModelBatch)
			modelBatch.end();
		else if(spriteBatch.isDrawing())
			spriteBatch.end();
	}

	/**
	 * Generates randomly a new part of the level by choosing a Segment next the current one
	 */
	private void AddSegment() {
		if(_semgentsFIFO.size() >= _SEGMENTS_NUMBER)
		{
			Segment OldSegment = _semgentsFIFO.poll();
			_entities.removeAll(OldSegment.GetEntities());
		}

		// Determines the new segment type
		SegmentTypeEnum NewSegmentType = SegmentTypeEnum.high;
		SegmentDescriptor NewSegmentDesc = Segment._HIGH_SEGMENTS_TYPE_LIST.get(0);
		if(_lastSegment != null)
		{
			switch(_lastSegment.GetSegmentDescriptor().SegmentType)
			{
			case climbing:
				NewSegmentType = SegmentTypeEnum.high;
				break;
			case falling:
				NewSegmentType = SegmentTypeEnum.low;
				break;
			case high:
				if(Math.random() > 0.25)
				{
					NewSegmentType = SegmentTypeEnum.falling;
					NewSegmentDesc = Segment._FALLING_SEGMENT;
				}
				else
					NewSegmentType = SegmentTypeEnum.high;
				break;
			case low:
				if(Math.random() > 0.25)
				{
					NewSegmentType = SegmentTypeEnum.climbing;
					NewSegmentDesc = Segment._CLIMBING_SEGMENT;				
				}
				else
					NewSegmentType = SegmentTypeEnum.low;
				break;
			}

			if(NewSegmentType == SegmentTypeEnum.low)
			{
				int rand = (int)(Math.random()*Segment._LOW_SEGMENTS_TYPE_LIST.size());
				NewSegmentDesc = Segment._LOW_SEGMENTS_TYPE_LIST.get(rand);
			}
			else if(NewSegmentType == SegmentTypeEnum.high)
			{
				int rand = (int)(Math.random()*Segment._HIGH_SEGMENTS_TYPE_LIST.size());
				NewSegmentDesc = Segment._HIGH_SEGMENTS_TYPE_LIST.get(rand);
			}
			Segment NewSegment = new Segment(new Position(_lastSegment.GetEndXPosition(), -0.08f), NewSegmentDesc, _assetsHndlr, _bodyDAL, _box2DWorld);
			_lastSegment = NewSegment;
		}
		else
			_lastSegment = new Segment(new Position(0, -0.08f), NewSegmentDesc, _assetsHndlr, _bodyDAL, _box2DWorld);

		if(_isBloomShaderEnabled)
			_lastSegment.SetShader(_bloomShader);
		_lastSegment.SetZIndex(5, 6);
		_entities.addAll(_lastSegment.GetEntities());

		_semgentsFIFO.add(_lastSegment);
	}

	@Override
	public void dispose() {
		_inputMultiplexer.clear();
		_listener = null;
		_assetsHndlr = null;
		_currentShader = null;
		_box2DWorld.dispose();
		_box2DWorld = null;
		if(_debugRenderer != null)
			_debugRenderer.dispose();
		_debugRenderer = null;
	}

	private WorldListener _listener;
	private AssetsHandler _assetsHndlr;
	private Camera _camera;
	private BodyEditorDAL _bodyDAL;
	private ShaderProgram _bloomShader;
	private boolean _isBloomShaderEnabled;
	private int _ratioLocation;

	private int _score;
	private int _distanceTraveled;
	private Difficulty _difficultyLevel;
	private float _time;
	private float _ratio;
	private ShaderProgram _currentShader;
	private boolean _isModelBatch;
	private Segment _lastSegment;

	private World _box2DWorld;
	private Box2DDebugRenderer _debugRenderer;

	private InputMultiplexer _inputMultiplexer;

	private Comparator<Entity> _entitiesZindexComparator;
	private ArrayList<Entity> _entities;
	private RotatingRaysEntity _rotatingRaysSpriteEntity;
	private KnightEntity _knightEntity;
	private BackgroundLayerEntity _backgroundLayerEntity;
	private Queue<Segment> _semgentsFIFO;

	private static final int _SEGMENTS_NUMBER = 5;
	private static final String _BODIES_DAL_NAME = "BodiesDAL";
	private static final boolean _DEBUG_RENDERING_ENABLED = true;
	private static final boolean _CLASSIC_RENDERING_ENABLED = false;
	private static final Vector2 _GRAVITY = new Vector2(0.0f, -9.81f);
	private static final String _RATIO_UNIFORM_NAME = "u_ratio";
	private static final String _BLOOM_SAHDER_NAME = "BloomShader";
}
