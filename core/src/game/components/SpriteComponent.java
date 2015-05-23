package game.components;

import game.GameWorld;
import game.entities.Entity;
import game.utils.Position;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Sprite component class
 */
public class SpriteComponent extends Component implements IRenderableComponent<SpriteBatch>, IUpdateableComponent {

	/**
	 * Creates a new sprite component with the given entity as owner
	 * @param name Sprite component name
	 * @param owner Sprite component owner entity
	 */
	public SpriteComponent(String name, Entity owner) {
		super(name, owner);
		_relativePosition = new Position(0, 0);
		_sprite = new Sprite();
	}

	/**
	 * Creates a new sprite component from a given texture
	 * @param name Sprite component name
	 * @param owner Sprite component owner entity
	 * @param texture Sprite component texture
	 */
	public SpriteComponent(String name, Entity owner, Texture texture) {
		super(name, owner);
		_relativePosition = new Position(0, 0);
		_sprite = new Sprite(texture);
	}

	public void SetTexture(Texture texture)
	{
		_sprite.setTexture(texture);
	}
	
	public void SetSize(float width, float height)
	{
		_sprite.setSize(width, height);
	}
	
	public void SetOrigin(Position origin)
	{
		_sprite.setOrigin(origin.x, origin.y);
	}
	
	public Sprite GetSprite()
	{
		return _sprite;
	}
	
	public float GetWidth()
	{
		return _sprite.getWidth();
	}

	public float GetHeight()
	{
		return _sprite.getHeight();
	}
	
	@Override
	public void render(SpriteBatch batch) {
		if(_isEnabled)
			_sprite.draw(batch);
	}

	@Override
	public void SetRelativePosition(Position pos)
	{
		if(pos != null)
			_relativePosition = pos;		
	}

	@Override
	public Position GetRelativePosition()
	{
		return _relativePosition;		
	}

	@Override
	public void update(GameWorld world) {
		// Update position
		Position OwnerPos = _owner.getPosition();
		_sprite.setPosition(_relativePosition.x + OwnerPos.x, _relativePosition.y + OwnerPos.y);
	}
	
	protected Sprite _sprite;
	protected Position _relativePosition;
}
