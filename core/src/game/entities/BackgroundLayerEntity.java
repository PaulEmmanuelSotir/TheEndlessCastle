package game.entities;

import game.GameWorld;
import game.components.SpriteComponent;
import game.dataAccessLayer.AssetsHandler;
import game.utils.Position;

/**
 * Infinite background layer for landscape with parallax effect
 */
public class BackgroundLayerEntity extends LayerEntity {

	/**
	 * Background layer entity constructor
	 */
	public BackgroundLayerEntity(AssetsHandler assetsHndlr) {
		super(_BACKGROUND_LAYER_NAME, assetsHndlr, _BACKGROUND_PARALLLAX);
		_backSpriteComponent1 = AddLayerTexture(_BACKGROUND_TEXTURE_NAME);
		_backSpriteComponent2 = AddLayerTexture(_BACKGROUND_TEXTURE_NAME);
		_backgroundLandscapeCount = 1;
	}

	public void ScaleToHeight(float height)
	{
		_landscapeWidth = _backSpriteComponent1.ScaleToHeight(height);
		_backSpriteComponent2.ScaleToHeight(height);
		_backSpriteComponent1.SetRelativePosition(new Position((_backgroundLandscapeCount-2)*_landscapeWidth, 0));
		_backSpriteComponent2.SetRelativePosition(new Position((_backgroundLandscapeCount-1)*_landscapeWidth, 0));
	}

	@Override
	public void update(GameWorld world)
	{
		super.update(world);
		if(world.GetCameraPosition().x > _position.x + _landscapeWidth/2f - GameWorld.WORLD_VIEW_WIDTH/2f + Math.max(_backSpriteComponent1.GetRelativePosition().x, _backSpriteComponent2.GetRelativePosition().x))
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
