package game.entities;

import game.World;
import game.components.SpriteComponent;
import game.dataAccessLayer.AssetsHandler;
import game.utils.Position;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Rectangle;

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

	/**
	 * Set sprite from texture name
	 * @param textureName Sprite entity texture name
	 * @return Newly created Sprite component
	 */
	public SpriteComponent SetSprite(String textureName)
	{
		SpriteComponent NewComponent = new SpriteComponent(getName() + "_" + textureName, this, _assetsHndlr.<Texture>get(textureName));
		addComponent(NewComponent);
		return NewComponent;
	}
}
