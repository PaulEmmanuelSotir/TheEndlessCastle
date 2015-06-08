package game.entities;

import com.badlogic.gdx.physics.box2d.World;

import aurelienribon.bodyeditor.BodyEditorDAL;
import game.dataAccessLayer.AssetsHandler;
import game.utils.Position;

public abstract class PhysicalEntity extends Entity
{
	public PhysicalEntity(String name, Position position, final AssetsHandler assetsHndlr, final BodyEditorDAL bodyDAL, final World box2DWorld) {
		super(name, position, assetsHndlr);
		_bodyDAL = bodyDAL;
		_box2DWorld = box2DWorld;
	}

	public final BodyEditorDAL GetBodyEditorDAL()
	{
		return _bodyDAL;
	}
	
	public final World GetBox2DWorld()
	{
		return _box2DWorld;
	}

	protected final BodyEditorDAL _bodyDAL;
	protected final World _box2DWorld;
}
