package game.entities;

import game.components.SpriteComponent;
import game.dataAccessLayer.AssetsHandler;
import game.utils.Position;

import com.badlogic.gdx.graphics.Texture;

/**
 * Sprite entity class
 */
public class SpriteEntity extends Entity
{
	/**
	 * SpriteEntity constructor
	 * @param name Sprite entity name
	 * @param position Sprite entity position
	 * @param assetsHndlr Assets handler
	 */
	public SpriteEntity(String name, Position position, AssetsHandler assetsHndlr) {
		super(name, position, assetsHndlr);
	}

	/**
	 * SpriteEntity constructor with texture name
	 * @param name Sprite entity name
	 * @param position Sprite entity position
	 * @param assetsHndlr Assets handler
	 * @param textureName Sprite entity texture name
	 */
	public SpriteEntity(String name, Position position, AssetsHandler assetsHndlr, String textureName) {
		super(name, position, assetsHndlr);
		SetSprite(textureName);
	}

	@Override
	public boolean IsUsingSpriteBatch() {
		return true;
	}

	/**
	 * Set sprite from texture name
	 * @param textureName Sprite entity texture name
	 * @return Newly created Sprite component
	 */
	public SpriteComponent SetSprite(String textureName)
	{
		// TODO: remove the old one if any
		SpriteComponent NewComponent = new SpriteComponent(getName() + "_" + textureName, this, _assetsHndlr.<Texture>get(textureName));
		addComponent(NewComponent);
		return NewComponent;
	}
}
