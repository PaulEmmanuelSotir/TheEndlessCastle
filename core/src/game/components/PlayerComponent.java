package game.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

import java.util.EventListener;

import game.entities.Entity;

public class PlayerComponent extends Component implements InputProcessor
{
	public interface MoveListener extends EventListener
	{
		public void MoveForward();
		public void MoveBackward();
	}
	
	public PlayerComponent(String name, Entity owner) {
		super(name, owner);
		Gdx.input.setInputProcessor(this);
	}

	public void SetMoveListenner(MoveListener moveListener)
	{
		_MoveListener = moveListener;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		switch (keycode)
		{
		case Keys.LEFT:
			if(_MoveListener != null)
				_MoveListener.MoveBackward();
			return true;
		case Keys.RIGHT:
			if(_MoveListener != null)
				_MoveListener.MoveForward();
			return true;
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
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
	
	private MoveListener _MoveListener;
}
