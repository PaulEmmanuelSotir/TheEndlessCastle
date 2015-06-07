package game.entities;

import aurelienribon.bodyeditor.BodyEditorDAL;

import com.badlogic.gdx.physics.box2d.World;

import game.Segment.SegmentTypeEnum;
import game.dataAccessLayer.AssetsHandler;
import game.utils.Position;

public class ProjectileEntity extends PhysicalEntity {

	public ProjectileEntity(String name, Position position, AssetsHandler assetsHndlr, BodyEditorDAL bodyDAL, World box2DWorld) {
		super(name, position, assetsHndlr, bodyDAL, box2DWorld);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean IsUsingSpriteBatch() {
		// TODO Auto-generated method stub
		return false;
	}

	public static enum ProjectileTypeEnum
	{
		climbing,
		falling,
		high,
		low
	}
	
	public static class ProjectileDescriptor
	{
		/**
		 * Segment descriptor constructor
		 * @param MaxWalkableHeight The maximum height of the body in percents of the sprite height (from 0f to 1f)
		 */
		public ProjectileDescriptor(String textureName, String bodyName, float MaxWalkableHeight, SegmentTypeEnum segmentType)
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
}
