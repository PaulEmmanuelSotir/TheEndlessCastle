package game;

import game.dataAccessLayer.AssetsHandler;
import game.entities.Entity;
import game.entities.PlatformEntity;
import game.utils.Pair;
import game.utils.Position;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ObjectMap;

import aurelienribon.bodyeditor.BodyEditorDAL;

/**
 * Segment class representing a part of the platform world
 */
public class Segment
{

	/**
	 * Segment constructor
	 */
	public Segment(Position pos, SegmentDescriptor segmentType, AssetsHandler assetsHndlr, BodyEditorDAL bodyDAL, World box2DWorld) {
		_assetsHndlr = assetsHndlr;
		_segmentEntities = new ArrayList<Entity>();
		_posX = pos.x;
		_segmentDescriptor = segmentType;
		
		_platformEntity = new PlatformEntity("Segment_PlatformEntity", pos, _SEGMENT_SCALE, segmentType.textureName, segmentType.bodyName, assetsHndlr, bodyDAL, box2DWorld);
		_segmentEntities.add(_platformEntity);
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
	
	public float GetBodyHeight()
	{
		return _segmentDescriptor.MaxHeight*_platformEntity.GetHeight()*_SEGMENT_SCALE;
	}
	
	public void SetShader(ShaderProgram shader)
	{
		for(Entity e : _segmentEntities)
			e.SetShader(shader);
	}

	private float _posX;
	private SegmentDescriptor _segmentDescriptor;
	private List<Entity> _segmentEntities;
	private PlatformEntity _platformEntity;
	private AssetsHandler _assetsHndlr;
	
	
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
