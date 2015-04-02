package game.entitiesAndComponents;

public abstract class Component
{
	public Component(Entity owner) {
		_owner = owner;
	}

	public void setOwner(Entity owner) {
	    _owner = owner;
	}
	
	public Entity getOwner() {
		return _owner;
	}
	
	public void SetEnabled(boolean enabled) {
	    _isEnabled = enabled;
	}
	
	public boolean isEnabled() {
	    return _isEnabled;
	}
	
	protected Entity _owner;
	protected boolean _isEnabled;
}
