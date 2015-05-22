package game.entities;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import game.GameWorld;
import game.components.Component;
import game.components.IRenderableComponent;
import game.components.IUpdateableComponent;
import game.components.ModelComponent;
import game.components.SpriteComponent;
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
	public Entity(String name, Position position, AssetsHandler assetsHndlr) {
		_assetsHndlr = assetsHndlr;
		_name = name;
		_position = position;
		_components = new ArrayList<Component>();
		_renderableSpriteComponents = new ArrayList<SpriteComponent>();
		_renderableModelComponents = new ArrayList<ModelComponent>();
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
			if(component instanceof IUpdateableComponent)
				_updateableComponents.add((IUpdateableComponent)component);
			
			if(component instanceof SpriteComponent)
				_renderableSpriteComponents.add((SpriteComponent)component);
			else if(component instanceof ModelComponent)
				_renderableModelComponents.add((ModelComponent)component);
		}
	}
	
	protected void removeComponent(Component component)
	{
		if(component != null)
		{
			_components.remove(component);
			if(component instanceof IUpdateableComponent)
				_updateableComponents.remove((IUpdateableComponent)component);
			
			if(component instanceof SpriteComponent)
				_renderableSpriteComponents.remove((SpriteComponent)component);
			else if(component instanceof ModelComponent)
				_renderableModelComponents.remove((ModelComponent)component);
		}
	}
	
	public abstract boolean IsUsingSpriteBatch();

	/**
	 * Renders entity's renderable components
	 * @param batch
	 */
	public void render(SpriteBatch spriteBatch, ModelBatch modelBatch, GameWorld world) {
		if(IsUsingSpriteBatch())
		{
			world.SetCurrentBatchToSprite(spriteBatch, modelBatch);
			BeginSpriteShader(spriteBatch, world);
		}
		else
			world.SetCurrentBatchToModel(spriteBatch, modelBatch);
		
		draw(spriteBatch, modelBatch, world, _shader);
		
		for(SpriteComponent compo : _renderableSpriteComponents)
			compo.render(spriteBatch);
		for(ModelComponent compo : _renderableModelComponents)
			compo.render(modelBatch);
	}

	/**
	 * Draws entity.
	 * Shader is already set when this method is called.
	 * Override this if, for example, you want to supply uniforms to the shader
	 * @param batch
	 * @param world
	 * @param shader
	 */
	protected void draw(SpriteBatch spriteBatch, ModelBatch modelBatch, GameWorld world, ShaderProgram shader) {
		
	}

	/**
	 * Sets the sprite batch shader and verify if the shader is already set before doing dumb things (unlike batch)
	 * @param batch
	 * @param world
	 */
	private void BeginSpriteShader(SpriteBatch batch, GameWorld world)
	{
		if(world.GetCurrentShader() != _shader)
		{
			world.SetCurrentShader(_shader);
			batch.setShader(_shader);
		}
	}
	
	public void SetShader(ShaderProgram shader)
	{
		_shader = shader;
	}
	
	public ShaderProgram GetShader()
	{
		return _shader;
	}

	/**
	 * Updates entity's updateable components
	 * @param world
	 */
	public void update(GameWorld world) {
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
	
	public int getZIndex()
	{
		return _zIndex;
	}

	public void setZIndex(int zIndex)
	{
		_zIndex = zIndex;
	}
	
	public String toString() {
		return _name + "_pos=" + _position;
	}

	private ShaderProgram _shader;
	private String _name;
	protected int _zIndex;
	protected AssetsHandler _assetsHndlr;
	protected Position _position;
	protected List<Component> _components;
	protected List<SpriteComponent> _renderableSpriteComponents;
	protected List<ModelComponent> _renderableModelComponents;
	protected List<IUpdateableComponent> _updateableComponents;
}
