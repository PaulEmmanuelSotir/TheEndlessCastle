package game.components;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

import game.World;
import game.entities.Entity;
import game.utils.Position;

public class PlayerComponent extends Component implements InputProcessor, IUpdateableComponent
{
	boolean leftMove;
	boolean rightMove;

	public PlayerComponent(String name, Entity owner) {
		super(name, owner);
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

	@Override
	public void update(World world) {
		Position pos = _owner.getPosition();
		if(leftMove){
			_owner.setPosition(new Position(pos.x - 0.2f, pos.y));
			leftMove=false;
		}

		if(rightMove){
			_owner.setPosition(new Position(pos.x + 0.2f, pos.y));
			rightMove=false;
		}

	}


}
