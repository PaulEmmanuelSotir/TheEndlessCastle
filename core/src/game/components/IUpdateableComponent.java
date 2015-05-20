package game.components;

import game.GameWorld;

/**
 * Updateable component interface
 */
public interface IUpdateableComponent
{
	/**
	 * Updates component
	 * @param world GameWorld to which the component belongs to
	 */
	public void update(GameWorld world);
}
