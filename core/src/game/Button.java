package game;

import game.utils.Position;

import java.util.ArrayList;
import java.util.EventListener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Sprite based button class
 */
public class Button
{
	public interface ButtonListener extends EventListener
	{
		public void MouseOver();
		public void MouseClick();
		public void MouseRelease();
		public void MouseExit();
	}
	
	public static abstract class ButtonClickListener implements ButtonListener
	{
		public void MouseOver() { }
		public void MouseClick() { }
		public void MouseExit() { }
	}
	
	public Button(Texture normalTexture, Texture overTexture, Texture pressedTexture, ButtonListener buttonListener)
	{
		this(0f, 0f, 1f, normalTexture, overTexture, pressedTexture, buttonListener);
	}
	
	public Button(float x, float y, Texture normalTexture, Texture overTexture, Texture pressedTexture, ButtonListener buttonListener)
	{
		this(x, y, 1f, normalTexture, overTexture, pressedTexture, buttonListener);
	}
	
	public Button(float textureScale, Texture normalTexture, Texture overTexture, Texture pressedTexture, ButtonListener buttonListener)
	{
		this(0f, 0f, textureScale, normalTexture, overTexture, pressedTexture, buttonListener);
	}
	
	public Button(float x, float y, float textureScale, Texture normalTexture, Texture overTexture, Texture pressedTexture, ButtonListener buttonListener)
	{		
		_x = x;
		_y = y;
		_bounds = new Rectangle();

		// Button normal Sprite
		_buttonNormalSprite = new Sprite(normalTexture);
		_buttonNormalSpriteEnabled = true;

		// Button over Sprite
		_buttonOverSprite = new Sprite(overTexture);
		_buttonOverSpriteEnabled = false;

		// Button pressed Sprite
		_buttonPressedSprite = new Sprite(pressedTexture);
		_buttonPressedSpriteEnabled = false;
		
		SetSpritesScale(textureScale);

		// Set initial bounds and sprites position assuming initial camera position is (viewportWidth/2f, camera.viewportHeight/2f) (center)
		_buttonNormalSprite.setPosition(_x, _y);
		_buttonOverSprite.setPosition(_x, _y);
		_buttonPressedSprite.setPosition(_x, _y);
		_bounds = new Rectangle(_x, _y, _buttonNormalSprite.getWidth()*_buttonNormalSprite.getScaleX(), _buttonNormalSprite.getHeight()*_buttonNormalSprite.getScaleY());

		// Button listener
		_buttonListeners = new ArrayList<ButtonListener>();
		_buttonListeners.add(new ButtonListener() {
			@Override
			public void MouseRelease() {
				_buttonNormalSpriteEnabled = true;
				_buttonOverSpriteEnabled = false;
				_buttonPressedSpriteEnabled = false;
			}
			
			@Override
			public void MouseOver() {
				_buttonNormalSpriteEnabled = false;
				_buttonOverSpriteEnabled = true;
				_buttonPressedSpriteEnabled = false;
			}
			
			@Override
			public void MouseClick() {
				_buttonNormalSpriteEnabled = false;
				_buttonOverSpriteEnabled = false;
				_buttonPressedSpriteEnabled = true;
			}
	
			@Override
			public void MouseExit() { MouseRelease(); }
		});
		addButtonListener(buttonListener);
	}
	
	public void addButtonListener(ButtonListener listener) {
		if(listener != null)
			_buttonListeners.add(listener);
	}
	
	public void render(SpriteBatch batch)
	{
		if(_buttonNormalSpriteEnabled)
			_buttonNormalSprite.draw(batch);
		else if(_buttonOverSpriteEnabled)
			_buttonOverSprite.draw(batch);
		else if(_buttonPressedSpriteEnabled)
			_buttonPressedSprite.draw(batch);
	}
	
	public void update(Camera camera) {
		// Correct sprites position modifications due to camera moves
		_buttonNormalSprite.setPosition(camera.position.x-camera.viewportWidth/2f + _x, camera.position.y-camera.viewportHeight/2f + _y);
		_buttonOverSprite.setPosition(camera.position.x-camera.viewportWidth/2f + _x, camera.position.y-camera.viewportHeight/2f + _y);
		_buttonPressedSprite.setPosition(camera.position.x-camera.viewportWidth/2f + _x, camera.position.y-camera.viewportHeight/2f + _y);
		
		// Raise appropriate events depending on mouse action
		if(_buttonListeners.size() > 0)
		{
			// Get mouse position in world coords and get button bounding rectangle
			Vector2 MousePos = new Position(camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)));
			_bounds = new Rectangle(_x + camera.position.x-camera.viewportWidth/2f, _y + camera.position.y-camera.viewportHeight/2f, _buttonNormalSprite.getWidth()*_buttonNormalSprite.getScaleX(), _buttonNormalSprite.getHeight()*_buttonNormalSprite.getScaleY());
			
			if(_bounds.contains(MousePos.x, MousePos.y))
			{
				if(!Gdx.input.isButtonPressed(Buttons.LEFT))
				{
					if(!_buttonOver)
					{
						_buttonOver = true;
						for(ButtonListener listener : _buttonListeners)
							listener.MouseOver();
					}
					
					if(_buttonPressed)
					{
						_buttonPressed = false;
						for(ButtonListener listener : _buttonListeners)
							listener.MouseRelease();
					}
				}
				else
				{
					if(!_buttonPressed)
					{
						_buttonPressed = true;
						for(ButtonListener listener : _buttonListeners)
							listener.MouseClick();
					}
					if(!_buttonOver)
					{
						_buttonOver = true;
						for(ButtonListener listener : _buttonListeners)
							listener.MouseOver();
					}
				}
			}
			else
			{
				if(_buttonPressed)
				{
					_buttonPressed = false;
					if(_buttonPressed)
						for(ButtonListener listener : _buttonListeners)
							listener.MouseRelease();
				}
				if(_buttonOver)
				{
					_buttonOver = false;
					for(ButtonListener listener : _buttonListeners)
						listener.MouseExit();
				}
			}
		}
	}
	
	public void SetSpritesScale(float scale)
	{
		_buttonNormalSprite.setScale(scale);
		_buttonNormalSprite.setOrigin(0,0);
		_buttonNormalSprite.setCenter(scale*_buttonNormalSprite.getWidth()/2f, scale*_buttonNormalSprite.getHeight()/2f);
		
		_buttonOverSprite.setScale(scale);
		_buttonOverSprite.setOrigin(0,0);
		_buttonOverSprite.setCenter(scale*_buttonOverSprite.getWidth()/2f, scale*_buttonOverSprite.getHeight()/2f);
		
		_buttonPressedSprite.setScale(scale);
		_buttonPressedSprite.setOrigin(0,0);
		_buttonPressedSprite.setCenter(scale*_buttonPressedSprite.getWidth()/2f, scale*_buttonPressedSprite.getHeight()/2f);
	}
	
	public void SetPosition(float x, float y)
	{
		_x = x;
		_y = y;
	}
	
	public float getWidth()
	{
		return _bounds.width;
	}
	
	public float getHeight()
	{
		return _bounds.height;
	}

	private ArrayList<ButtonListener> _buttonListeners;
	
	private boolean _buttonPressed;
	private boolean _buttonOver;
	
	private boolean _buttonPressedSpriteEnabled;
	private boolean _buttonOverSpriteEnabled;
	private boolean _buttonNormalSpriteEnabled;

	private Sprite _buttonNormalSprite;
	private Sprite _buttonOverSprite;
	private Sprite _buttonPressedSprite;

	private Rectangle _bounds;
	private float _x;
	private float _y;
}
