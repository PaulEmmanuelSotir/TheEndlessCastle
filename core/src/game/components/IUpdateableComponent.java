package game.components;

import game.World;

/**
 * Updateable component interface
 */
public interface IUpdateableComponent
{
	/**
	 * Updates component
	 * @param world World to which the component belongs to
	 */
	public void update(World world);
}
