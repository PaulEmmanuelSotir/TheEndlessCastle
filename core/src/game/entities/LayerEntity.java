package game.entities;

import java.util.ArrayList;
import java.util.List;

import game.World;
import game.components.SpriteComponent;
import game.dataAccessLayer.AssetsHandler;
import game.utils.Position;
import game.utils.Range;

import com.badlogic.gdx.graphics.Texture;

/**
 * Layer entity abstract class
 */
public class LayerEntity extends Entity
{
	/**
	 * Layer entity constructor
	 * @param name
	 * @param bounds
	 * @param position
	 * @param _assetsHndlr
	 */
	public LayerEntity(String name, AssetsHandler assetsHndlr) {
		super(name, new Position(0, 0), assetsHndlr);
	}
	
	/**
	 * Layer entity constructor with parallax
	 * @param name
	 * @param bounds
	 * @param position
	 * @param assetsHndlr
	 * @param parallax
	 */
	public LayerEntity(String name, AssetsHandler assetsHndlr, float parallax) {
		super(name, new Position(0, 0), assetsHndlr);
		SetParallax(parallax);
	}
	
	@Override
	public void update(World world) {
		super.update(world);
		
		// Apply parallax effect on x axis
		Position camPos = world.GetCameraPosition();
		_position.x = camPos.x*_parallax;
	}
	
	/**
	 * Set parallax
	 * @param parallax Value ranging from -1f to 1f representing parallax effect magnitude. (0f is no parallax)
	 */
	public void SetParallax(float parallax)
	{
		if(_parallaxRange.within(parallax))
			_parallax = parallax;
	}

	public List<SpriteComponent> AddLayerTextures(List<String> textureNames)
	{
		List<SpriteComponent> NewComponents = new ArrayList<SpriteComponent>(textureNames.size());
		
		for(String TexName : textureNames)
		{
			SpriteComponent component = new SpriteComponent(getName() + "LayerSprite#" + _layerSpriteCount++, this, _assetsHndlr.<Texture>get(TexName));
			addComponent(component);
			NewComponents.add(component);
		}
		
		return NewComponents;
	}

	public SpriteComponent AddLayerTexture(String textureName)
	{
		SpriteComponent component = new SpriteComponent(getName() + "LayerSprite#" + _layerSpriteCount++, this, _assetsHndlr.<Texture>get(textureName));
		addComponent(component);
		return component;
	}

	private long _layerSpriteCount;
	private float _parallax;
	private float _lastUpdateTime;
	private static final Range<Float> _parallaxRange = new Range<Float>(-1f, 1f);
}
