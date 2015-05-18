package game.entities;

import game.World;
import game.components.SpriteComponent;
import game.dataAccessLayer.AssetsHandler;
import game.utils.Position;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

/**
 * Sprite Entity
 */
public class SpriteEntity extends Entity
{
	/**
	 * SpriteEntity constructor
	 * @param name
	 * @param bounds
	 * @param position
	 * @param assetsHndlr
	 */
	public SpriteEntity(String name, Position position, AssetsHandler assetsHndlr) {
		super(name, position, assetsHndlr);
	}
	
	/**
	 * SpriteEntity constructor with texture name
	 * @param name
	 * @param position
	 * @param assetsHndlr
	 * @param textureName
	 */
	public SpriteEntity(String name, Position position, AssetsHandler assetsHndlr, String textureName) {
		super(name, position, assetsHndlr);
		SetSprite(textureName);
	}

	/**
	 * Set sprite from texture name
	 * @param textureName
	 * @return
	 */
	public SpriteComponent SetSprite(String textureName)
	{
		SpriteComponent NewComponent = new SpriteComponent(getName() + "_" + textureName, this, _assetsHndlr.<Texture>get(textureName));
		addComponent(NewComponent);
		return NewComponent;
	}
}
