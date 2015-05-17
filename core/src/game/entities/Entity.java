package game.entities;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import game.World;
import game.components.Component;
import game.components.IRenderableComponent;
import game.components.IUpdateableComponent;
import game.dataAccessLayer.AssetsHandler;
import game.utils.Position;

public abstract class Entity
{
	/**
	 * Entity constructor
	 * @param name
	 * @param bounds
	 * @param position
	 * @param assetsHndlr
	 */
	protected Entity(String name, Position position, AssetsHandler assetsHndlr) {
		_assetsHndlr = assetsHndlr;
		_name = name;
		_position = position;
		_components = new ArrayList<Component>();
		_renderableComponents = new ArrayList<IRenderableComponent>();
		_updateableComponents = new ArrayList<IUpdateableComponent>();
	}

	/**
	 * Add given component to entity components
	 * @param component
	 */
	protected void addComponent(Component component)
	{
		if(component != null)
		{
			_components.add(component);
			if(component instanceof IRenderableComponent)
				_renderableComponents.add((IRenderableComponent)component);
			if(component instanceof IUpdateableComponent)
				_updateableComponents.add((IUpdateableComponent)component);
		}
	}
	
	protected void removeComponent(Component component)
	{
		if(component != null)
		{
			_components.remove(component);
			if(component instanceof IRenderableComponent)
				_renderableComponents.remove((IRenderableComponent)component);
			if(component instanceof IUpdateableComponent)
				_updateableComponents.remove((IUpdateableComponent)component);
		}
	}

	/**
	 * Renders entity's renderable components
	 * @param batch
	 */
	public void render(SpriteBatch batch) {
		for(IRenderableComponent compo : _renderableComponents)
			compo.render(batch);
	}

	/**
	 * Updates entity's updateable components
	 * @param world
	 */
	public void update(World world) {
		for(IUpdateableComponent compo : _updateableComponents)
			compo.update(world);
	}

	public Position getPosition() {
		return _position;
	}

	public void setPosition(Position position) {
		_position = position;
	}
	
	public String getName()
	{
		return _name;
	}
	
	public String toString() {
		return _name + "_pos=" + _position;
	}

	private String _name;
	protected AssetsHandler _assetsHndlr;
	protected Position _position;
	protected List<Component> _components;
	protected List<IRenderableComponent> _renderableComponents;
	protected List<IUpdateableComponent> _updateableComponents;
}
