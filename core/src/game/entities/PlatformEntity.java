package game.entities;

import aurelienribon.bodyeditor.BodyEditorDAL;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import game.components.BodyComponent;
import game.components.SpriteComponent;
import game.dataAccessLayer.AssetsHandler;
import game.utils.Position;

/**
 * Platform entity class
 */
public class PlatformEntity extends PhysicalEntity
{	
	/**
	 * Platform entity constructor with texture name and shape name
	 * @param name Segment name
	 * @param position Segment position
	 * @param assetsHndlr Assets handler
	 * @param textureName Platform texture name
	 * @param shapeName Platform body shape name
	 */
	public PlatformEntity(String name, Position position, float scale, String textureName, String bodyName, final AssetsHandler assetsHndlr, final BodyEditorDAL bodyDAL, final World box2DWorld) {
		super(name, position, assetsHndlr, bodyDAL, box2DWorld);
		SetSprite(textureName, scale);
		SetBody(bodyName);
	}

	@Override
	public boolean IsUsingSpriteBatch() {
		return true;
	}
	
	public float GetWidth()
	{
		return _width;
	}
	
	public float GetHeight()
	{
		return _height;
	}

	/**
	 * Set sprite from texture name
	 * @param textureName Platform texture name
	 * @return Newly created Sprite component
	 */
	private SpriteComponent SetSprite(String textureName, float scale)
	{
		_spriteComponent = new SpriteComponent(getName() + "_SpriteComponent", this, _assetsHndlr.<Texture>get(textureName));
		addComponent(_spriteComponent);
		_width = _spriteComponent.GetWidth();
		_height = _spriteComponent.GetHeight();
		_spriteComponent.SetScale(scale);
		return _spriteComponent;
	}
	
	private BodyComponent SetBody(String name)
	{
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(_position.x, _position.y);
		bodyDef.type = BodyType.StaticBody;
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = 1;
		fixtureDef.friction = 0.5f;
		fixtureDef.restitution = 0f;
		BodyComponent bodyCompo = new BodyComponent(name, this, _box2DWorld, _bodyDAL, bodyDef, fixtureDef );
		addComponent(bodyCompo);
		return bodyCompo;
	}
	
	private float _width;
	private float _height;
	private SpriteComponent _spriteComponent;
}
