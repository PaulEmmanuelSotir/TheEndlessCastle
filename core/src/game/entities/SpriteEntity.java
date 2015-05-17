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
	 * @param name
	 * @param bounds
	 * @param position
	 * @param assetsHndlr
	 */
	public SpriteEntity(String name, Position position, AssetsHandler assetsHndlr) {
		super(name, position, assetsHndlr);
	}
	
	@Override
	public void update(World world) {
		super.update(world);
	}

	public SpriteComponent SetSprite(String textureName)
	{
		SpriteComponent NewComponent = new SpriteComponent(getName() + "_" + textureName, this, _assetsHndlr.<Texture>get(textureName));
		addComponent(NewComponent);
		return NewComponent;
	}
}
