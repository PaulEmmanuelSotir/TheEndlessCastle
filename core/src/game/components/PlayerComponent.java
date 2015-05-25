package game.components;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

import game.entities.Entity;

public class PlayerComponent extends Component implements InputProcessor
{
	public interface MoveListener extends EventListener
	{
		public void MoveForward();
		public void MoveBackward();
		public void Jump();
		public void Uncrouch();
		public void Crouch();
	}

	public PlayerComponent(String name, Entity owner) {
		super(name, owner);
		_MoveListeners = new ArrayList<MoveListener>();
	}

	public void AddMoveListener(MoveListener moveListener)
	{
		if(moveListener != null)
			_MoveListeners.add(moveListener);
	}

	public void RemoveMoveListener(MoveListener moveListener)
	{
		_MoveListeners.remove(moveListener);
	}

	@Override
	public boolean keyDown(int keycode) {
		if(_MoveListeners.size() > 0)
		{
			switch (keycode)
			{
			case Keys.LEFT:
				for(MoveListener listener : _MoveListeners)
					listener.MoveBackward();
				return true;
			case Keys.RIGHT:
				for(MoveListener listener : _MoveListeners)
					listener.MoveForward();
				return true;
			case Keys.UP:
				for(MoveListener listener : _MoveListeners)
					listener.Jump();
				return true;
			case Keys.DOWN:
				for(MoveListener listener : _MoveListeners)
					listener.Crouch();
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if(_MoveListeners.size() > 0)
		{
			switch (keycode)
			{
			case Keys.DOWN:
				for(MoveListener listener : _MoveListeners)
					listener.Uncrouch();
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}


	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}


	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}


	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}


	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}


	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	private List<MoveListener> _MoveListeners;
}
