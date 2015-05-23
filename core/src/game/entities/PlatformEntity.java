package game.entities;

import com.badlogic.gdx.graphics.Texture;

import game.components.SpriteComponent;
import game.components.StaticBodyComponent;
import game.dataAccessLayer.AssetsHandler;
import game.utils.Position;

/**
 * Platform entity class
 */
public class PlatformEntity extends Entity
{
	/**
	 * Platform entity constructor
	 * @param name Segment name
	 * @param position Segment position
	 * @param assetsHndlr Assets handler
	 */
	public PlatformEntity(String name, Position position, AssetsHandler assetsHndlr) {
		super(name, position, assetsHndlr);
	}
	
	/**
	 * Platform entity constructor with texture name and shape name
	 * @param name Segment name
	 * @param position Segment position
	 * @param assetsHndlr Assets handler
	 * @param textureName Platform texture name
	 * @param shapeName Platform body shape name
	 */
	public PlatformEntity(String name, Position position, AssetsHandler assetsHndlr, String textureName, String shapeName) {
		super(name, position, assetsHndlr);
	}

	@Override
	public boolean IsUsingSpriteBatch() {
		return true;
	}

	/**
	 * Set sprite from texture name
	 * @param textureName Platform texture name
	 * @return Newly created Sprite component
	 */
	public SpriteComponent SetSprite(String textureName)
	{
		SpriteComponent NewComponent = new SpriteComponent(getName() + "_" + textureName, this, _assetsHndlr.<Texture>get(textureName));
		addComponent(NewComponent);
		return NewComponent;
	}

	/**
	 * Set static body shape from shape name
	 * @param shapeName Platform body shape name
	 * @return Newly created static body component
	 */
//	public StaticBodyComponent SetShape(String shapeName)
//	{
//		StaticBodyComponent NewComponent = new StaticBodyComponent(getName() + "_" + shapeName, this, _assetsHndlr.<Shape>get(shapeName));
//		addComponent(NewComponent);
//		return NewComponent;
//	}
}
