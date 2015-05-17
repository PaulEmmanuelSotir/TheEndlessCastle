package game.components;

import game.entities.Entity;
import game.utils.Position;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Renderable Sprite component
 */
public class SpriteComponent extends Component implements IRenderableComponent {

	/**
	 * Creates a new sprite component with the given entity as owner
	 * @param owner
	 */
	public SpriteComponent(Entity owner) {
		super(owner);
		_sprite = new Sprite();
	}

	/**
	 * Creates a new sprite component from a given texture
	 * @param owner
	 * @param texture
	 */
	public SpriteComponent(Entity owner, Texture texture) {
		super(owner);
		_sprite = new Sprite(texture);
	}

	public void SetTexture(Texture texture)
	{
		_sprite.setTexture(texture);
	}

	public void SetRelativePosition(Position pos)
	{
		_relativePosition = pos;
		Position OwnerPos = _owner.getPosition();
		
		_sprite.setPosition(_relativePosition.x + OwnerPos.x, _relativePosition.y + OwnerPos.y);
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

	protected Sprite _sprite;
	protected Position _relativePosition;
}
