package game.components;

import game.entities.Entity;

/**
 * Component abstract class
 */
public abstract class Component
{
	/**
	 * Component constructor
	 * @param name Component name
	 * @param owner Component owner entity
	 */
	public Component(String name, Entity owner) {
		_name = name;
		_owner = owner;
		_isEnabled = true;
	}

	public void setOwner(Entity owner) {
	    _owner = owner;
	}
	
	public Entity getOwner() {
		return _owner;
	}
	
	public String getName() {
		return _name;
	}
	
	public void SetEnabled(boolean enabled) {
	    _isEnabled = enabled;
	}
	
	public boolean isEnabled() {
	    return _isEnabled;
	}
	
	protected String _name;
	protected Entity _owner;
	protected boolean _isEnabled;
}
