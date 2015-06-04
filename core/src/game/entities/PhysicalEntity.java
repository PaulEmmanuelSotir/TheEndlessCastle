package game.entities;

import com.badlogic.gdx.physics.box2d.World;

import aurelienribon.bodyeditor.BodyEditorDAL;
import game.dataAccessLayer.AssetsHandler;
import game.utils.Position;

public abstract class PhysicalEntity extends Entity
{
	public PhysicalEntity(String name, Position position, AssetsHandler assetsHndlr, BodyEditorDAL bodyDAL, World box2DWorld) {
		super(name, position, assetsHndlr);
		_bodyDAL = bodyDAL;
		_box2DWorld = box2DWorld;
	}

	@Override
	public boolean IsUsingSpriteBatch() {
		return false;
	}
	
	public BodyEditorDAL GetBodyEditorDAL()
	{
		return _bodyDAL;
	}
	
	public World GetBox2DWorld()
	{
		return _box2DWorld;
	}

	protected BodyEditorDAL _bodyDAL;
	protected World _box2DWorld;
}
