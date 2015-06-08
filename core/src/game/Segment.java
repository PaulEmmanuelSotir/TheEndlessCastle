package game;

import game.dataAccessLayer.AssetsHandler;
import game.entities.Entity;
import game.entities.PlatformEntity;
import game.entities.ProjectileEntity;
import game.entities.ProjectileEntity.ProjectileTypeEnum;
import game.utils.Position;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pool;

import aurelienribon.bodyeditor.BodyEditorDAL;

/**
 * Segment class representing a part of the platform world
 */
public class Segment implements Disposable
{
	/**
	 * Segment constructor
	 */
	public Segment(Position pos, SegmentDescriptor segmentType, final Pool<ProjectileEntity> horizontalProjectilePool, final Pool<ProjectileEntity> fallingProjectilePool, final AssetsHandler assetsHndlr, final BodyEditorDAL bodyDAL, final World box2DWorld) {
		_assetsHndlr = assetsHndlr;
		_segmentEntities = new ArrayList<Entity>();
		_posX = pos.x;
		_segmentDescriptor = segmentType;
		_horizontalProjectilePool = horizontalProjectilePool;
		_fallingProjectilePool = fallingProjectilePool;

		_platformEntity = new PlatformEntity("Segment_PlatformEntity", pos, _SEGMENT_SCALE, segmentType.textureName, segmentType.bodyName, assetsHndlr, bodyDAL, box2DWorld);
		_segmentEntities.add(_platformEntity);

		_activeProjectiles = new ArrayList<ProjectileEntity>();
		_projectilesSources = new ArrayList<Position>();

		if(!_segmentDescriptor.IsChangingHeight)
		{
			int sourceCount = 4 + (int)Math.abs((Math.random()*_platformEntity.GetWidth()*_SEGMENT_SCALE - 5f)/4f);
			for(int pIdx = 0; pIdx < sourceCount ; pIdx += 3f*Math.random())
				_projectilesSources.add(new Position(pos.x + 4*(int)pIdx, GameWorld.WORLD_VIEW_WIDTH));
		}
	}

	/**
	 * Updates random projectile spawning
	 */
	public void updateProjectilesSpawn(final GameWorld world)
	{
		if(_nextSpawnTime <= world.GetTime() && _projectilesSources.size() > 0)
		{
			// Let's try to kill player with a random weapon from a random source
			ProjectileEntity newProjectile = _fallingProjectilePool.obtain();
			world.AddEntity(newProjectile);
			Position source = _projectilesSources.get((int)(Math.random()*_projectilesSources.size()));
			newProjectile.Init(new Position(source), 0f);
			_activeProjectiles.add(newProjectile);

			_nextSpawnTime = world.GetTime() + (float)(Math.random() + 0.1);
		}

		// Free inactive projectiles back to their pool
		for(int idx = 0; idx < _activeProjectiles.size(); idx++)
		{
			ProjectileEntity proj = _activeProjectiles.get(idx);
			if(!proj.IsAlive())
			{
				//world.GetBox2DWorld().destroyBody(proj.GetBody());
				proj.reset();
				world.RemoveEntity(proj);
				_activeProjectiles.remove(idx);
				if(proj.GetDescriptor().ProjectileType == ProjectileTypeEnum.falling)
					_fallingProjectilePool.free(proj);
				else
					_horizontalProjectilePool.free(proj);
			}
		}
	}

	public List<Entity> GetEntities()
	{
		return _segmentEntities;
	}


	public SegmentDescriptor GetSegmentDescriptor()
	{
		return _segmentDescriptor;
	}

	public void SetZIndex(int PlatformZIdx, int ObstaclesZIdx)
	{
		for(Entity e : _segmentEntities)
			e.setZIndex(ObstaclesZIdx);
		_platformEntity.setZIndex(PlatformZIdx);
	}

	public void SetXPosition(float posX)
	{
		_posX = posX;
		for(Entity e : _segmentEntities)
			e.setPosition(new Position(_posX, 0));
	}

	public float GetBeginningXPosition()
	{
		return _posX;
	}

	public float GetEndXPosition()
	{
		return _posX + _platformEntity.GetWidth()*_SEGMENT_SCALE;
	}

	/*public float GetBodyHeight()
	{
		return _segmentDescriptor.MaxHeight*_platformEntity.GetHeight()*_SEGMENT_SCALE;
	}*/


	public void SetShader(ShaderProgram shader)
	{
		for(Entity e : _segmentEntities)
			e.SetShader(shader);
	}

	private float _posX;

	@Override
	public void dispose() {
		if(_activeProjectiles != null)
		{
			// Free projectiles back to their pool
			for(int idx = 0; idx < _activeProjectiles.size(); idx++)
			{
				ProjectileEntity proj = _activeProjectiles.get(idx);
				if(proj.GetDescriptor().ProjectileType == ProjectileTypeEnum.falling)
					_fallingProjectilePool.free(proj);
				else
					_horizontalProjectilePool.free(proj);
			}
			_activeProjectiles.clear();
		}
	}

	private final SegmentDescriptor _segmentDescriptor;
	private final List<Entity> _segmentEntities;
	private final PlatformEntity _platformEntity;
	private final ArrayList<ProjectileEntity> _activeProjectiles;
	private final ArrayList<Position> _projectilesSources;
	private final Pool<ProjectileEntity> _fallingProjectilePool;
	private final Pool<ProjectileEntity> _horizontalProjectilePool;
	private final AssetsHandler _assetsHndlr;
	private float _nextSpawnTime = -1f;

	public final static List<SegmentDescriptor> _HIGH_SEGMENTS_TYPE_LIST;
	static {
		_HIGH_SEGMENTS_TYPE_LIST = new ArrayList<SegmentDescriptor>(6);
		_HIGH_SEGMENTS_TYPE_LIST.add(new SegmentDescriptor("SegmentCastle1Texture", "SegmentCastleBody", 0.84f, SegmentTypeEnum.high));
		_HIGH_SEGMENTS_TYPE_LIST.add(new SegmentDescriptor("SegmentCastle2Texture", "SegmentCastleBody", 0.84f, SegmentTypeEnum.high));
		_HIGH_SEGMENTS_TYPE_LIST.add(new SegmentDescriptor("SegmentCastle3Texture", "SegmentCastleBody", 0.84f, SegmentTypeEnum.high));
	}
	public final static List<SegmentDescriptor> _LOW_SEGMENTS_TYPE_LIST;
	static {
		_LOW_SEGMENTS_TYPE_LIST = new ArrayList<SegmentDescriptor>(6);
		_LOW_SEGMENTS_TYPE_LIST.add(new SegmentDescriptor("SegmentGround1Texture", "SegmentGroundBody", 0.94f, SegmentTypeEnum.low));
		_LOW_SEGMENTS_TYPE_LIST.add(new SegmentDescriptor("SegmentGround2Texture", "SegmentGroundBody", 0.94f, SegmentTypeEnum.low));
		_LOW_SEGMENTS_TYPE_LIST.add(new SegmentDescriptor("SegmentGround3Texture", "SegmentGroundBody", 0.94f, SegmentTypeEnum.low));
	}

	public final static SegmentDescriptor _FALLING_SEGMENT = new SegmentDescriptor("SegmentFallingStairsTexture", "SegmentFallingStairsBody", 0.94f, SegmentTypeEnum.falling);
	public final static SegmentDescriptor _CLIMBING_SEGMENT = new SegmentDescriptor("SegmentClimbingStairsTexture", "SegmentClimbingStairsBody", 0.94f, SegmentTypeEnum.climbing);

	public static enum SegmentTypeEnum
	{
		climbing,
		falling,
		high,
		low
	}

	public static class SegmentDescriptor
	{
		/**
		 * Segment descriptor constructor
		 * @param MaxWalkableHeight The maximum height of the body in percents of the sprite height (from 0f to 1f)
		 */
		public SegmentDescriptor(String textureName, String bodyName, float MaxWalkableHeight, SegmentTypeEnum segmentType)
		{
			this.textureName = textureName;
			this.bodyName = bodyName;
			MaxHeight = MaxWalkableHeight;
			if(segmentType == SegmentTypeEnum.climbing || segmentType == SegmentTypeEnum.falling)
			{
				this.IsChangingHeight = true;
				if(segmentType == SegmentTypeEnum.climbing)
					this.IsClimbing = true;
			}
			SegmentType = segmentType;
		}

		public String textureName;
		public String bodyName;

		public float MaxHeight;

		public SegmentTypeEnum SegmentType;
		public boolean IsClimbing;
		public boolean IsChangingHeight;
	}

	private static final float _SEGMENT_SCALE = 27/3366f;
}
