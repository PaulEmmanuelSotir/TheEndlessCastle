package game.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

import game.GameWorld;
import game.entities.Entity;
import game.utils.Position;

public class PlayerComponent extends Component implements InputProcessor, IUpdateableComponent
{
	public PlayerComponent(String name, Entity owner) {
		super(name, owner);
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void update(GameWorld world) {
		Position pos = _owner.getPosition();
		if(leftMove) {
			_owner.setPosition(new Position(pos.x - _STEP, pos.y));
			leftMove = false;
		}

		if(rightMove) {
			_owner.setPosition(new Position(pos.x + _STEP, pos.y));
			rightMove = false;
		}

	}
	
	@Override
	public boolean keyDown(int keycode) {
		switch (keycode)
		{
		case Keys.LEFT:
			leftMove=true;
			return true;
		case Keys.RIGHT:
			rightMove=true;
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

	private boolean leftMove;
	private boolean rightMove;	
	
	private static final float _STEP = 0.2f;
}
