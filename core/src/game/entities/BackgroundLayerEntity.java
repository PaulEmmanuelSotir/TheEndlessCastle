package game.entities;

import game.World;
import game.components.SpriteComponent;
import game.dataAccessLayer.AssetsHandler;
import game.utils.Position;

/**
 * Infinite background layer for landscape with parallax effect
 */
public class BackgroundLayerEntity extends LayerEntity {

	/**
	 * Background layer entity constructor
	 * @param name
	 * @param bounds
	 * @param position
	 * @param assetsHndlr
	 * @param parallax
	 */
	public BackgroundLayerEntity(AssetsHandler assetsHndlr) {
		super(_BACKGROUND_LAYER_NAME, assetsHndlr, _BACKGROUND_PARALLLAX);
		_backSpriteComponent1 = AddLayerTexture(_BACKGROUND_TEXTURE_NAME);
		_backSpriteComponent2 = AddLayerTexture(_BACKGROUND_TEXTURE_NAME);
		_backgroundLandscapeCount = 1;
	}

	public void ScaleToHeight(float height)
	{
		_landscapeWidth = height*_backSpriteComponent1.GetWidth()/_backSpriteComponent1.GetHeight();
		_backSpriteComponent1.SetSize(_landscapeWidth, height);
		_backSpriteComponent2.SetSize(_landscapeWidth, height);
		_backSpriteComponent1.SetRelativePosition(new Position((_backgroundLandscapeCount-2)*_landscapeWidth, 0));
		_backSpriteComponent2.SetRelativePosition(new Position((_backgroundLandscapeCount-1)*_landscapeWidth, 0));
	}

	@Override
	public void update(World world)
	{
		super.update(world);
		if(world.GetCameraPosition().x > _position.x + _landscapeWidth/2f - world.METERS_TO_PIXELS/2f + Math.max(_backSpriteComponent1.GetRelativePosition().x, _backSpriteComponent2.GetRelativePosition().x))
		{
			if(_backSpriteComponent1.GetRelativePosition().x < _backSpriteComponent2.GetRelativePosition().x)
				_backSpriteComponent1.SetRelativePosition(new Position(_backgroundLandscapeCount*_landscapeWidth, 0));
			else
				_backSpriteComponent2.SetRelativePosition(new Position(_backgroundLandscapeCount*_landscapeWidth, 0));
			_backgroundLandscapeCount++;
		}
	}

	private SpriteComponent _backSpriteComponent1;
	private SpriteComponent _backSpriteComponent2;
	private long _backgroundLandscapeCount;
	private float _landscapeWidth;

	private static final float _BACKGROUND_PARALLLAX = 0.5f;
	private static final String _BACKGROUND_LAYER_NAME = "BackgroundLandscapeLayer";
	private static final String _BACKGROUND_TEXTURE_NAME = "BackgroundTexture";
}
