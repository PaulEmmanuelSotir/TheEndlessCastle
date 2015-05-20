package game.components;

import game.utils.Position;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Renderable component interface
 */
public interface IRenderableComponent
{
	/**
	 * Renders component
	 * @param batch Sprite batch used to render component
	 */
	public void render(SpriteBatch batch);

	public void SetRelativePosition(Position pos);
	public Position GetRelativePosition();
}
