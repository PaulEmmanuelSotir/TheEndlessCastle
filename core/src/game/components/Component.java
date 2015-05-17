package game.components;

import game.entities.Entity;

public abstract class Component
{
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
