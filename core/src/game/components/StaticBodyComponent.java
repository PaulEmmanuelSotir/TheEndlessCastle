package game.components;

import game.entities.Entity;

/**
 * Static body component class
 */
public class StaticBodyComponent extends BodyComponent
{
	/**
	 * Static body component constructor
	 * @param name Static body component name
	 * @param owner Static body component owner entity
	 */
	public StaticBodyComponent(String name, Entity owner) {
		super(name, owner);
	}

	/**
	 * Static body component constructor with shape information
	 * @param name Static body component name
	 * @param owner Static body component owner entity
	 * @param shape Static body component shape
	 */
//	public StaticBodyComponent(String name, Entity owner, Shape shape) {
//		super(name, owner, shape);
//	}
}
