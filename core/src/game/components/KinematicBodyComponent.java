package game.components;

import game.entities.Entity;

/**
 * Kinematic body component class
 */
public class KinematicBodyComponent extends BodyComponent
{
	public KinematicBodyComponent(String name, Entity owner) {
		super(name, owner);
	}
	
	/**
	 * Kinematic body component constructor with shape information
	 * @param name Kinematic body component name
	 * @param owner Kinematic body component owner entity
	 * @param shape Kinematic body component shape
	 */
//	public KinematicBodyComponent(String name, Entity owner, Shape shape) {
//		super(name, owner, shape);
//	}
	
}
