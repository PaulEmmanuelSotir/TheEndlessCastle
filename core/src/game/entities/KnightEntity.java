package game.entities;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController.AnimationDesc;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController.AnimationListener;

import game.GameWorld;
import game.components.AnimatedModelComponent;
import game.components.PlayerComponent;
import game.components.PlayerComponent.MoveListener;
import game.dataAccessLayer.AssetsHandler;
import game.utils.Position;

public class KnightEntity extends Entity
{
	public class PlayerMoveListener implements MoveListener
	{
		@Override
		public void MoveForward() {
			if(_jumpForeAnimation != _controller.current || _controller.current == null)
			{
				_jumpForeAnimation = _controller.setAnimation(_KNIGHT_JUMPFORE_ANIMATION, 1, new AnimationListener() {
					@Override
					public void onEnd(AnimationDesc animation) {
						_breathForeNow = true;
						_position.x += _JUMP_STEP;
					}

					@Override
					public void onLoop(AnimationDesc animation) {
						
					}
				});
			}
		}

		@Override
		public void MoveBackward() {
			if(_jumpBackAnimation != _controller.current || _controller.current == null)
			{
				_jumpBackAnimation = _controller.setAnimation(_KNIGHT_JUMPBACK_ANIMATION, 1, new AnimationListener() {
					@Override
					public void onEnd(AnimationDesc animation) {
						_position.x -= _JUMP_STEP;
						_breathBackNow = true;
					}

					@Override
					public void onLoop(AnimationDesc animation) {
						
					}
				});
			}
		}
		
		private AnimationDesc _jumpForeAnimation;
		private AnimationDesc _jumpBackAnimation;
	}

	public KnightEntity(String name, Position position, AssetsHandler assetsHndlr, InputMultiplexer inPlexer) {
		super(name, position, assetsHndlr);

		// Animated model component
		AnimatedModelComponent modelCompo = new AnimatedModelComponent(_MODEL_COMPONENT_NAME, this, (Model)_assetsHndlr.get(_KNIGHT_MODEL_NAME), new ColorAttribute(ColorAttribute.AmbientLight, 1f, 1f, 1f, 1.0f));
		_controller = modelCompo.GetAnimationController();
		modelCompo.SetScale(2f);
		addComponent(modelCompo);
		_controller.setAnimation(_KNIGHT_BREATHE_ANIMATION, -1, 1f, null);
		_controller.animate(_KNIGHT_START_ANIMATION, 0f);
		
		// Player control component
		PlayerComponent playerCompo = new PlayerComponent(_PLAYER_COMPONENT_NAME, this);
		inPlexer.addProcessor(playerCompo);
		playerCompo.SetMoveListenner(new PlayerMoveListener());
		addComponent(playerCompo);
	}

	public void update(GameWorld world)
	{
		if(_breathForeNow)
		{
			_controller.setAnimation(_KNIGHT_BREATHE_ANIMATION,-1,1f,null);
			_breathForeNow = false;
		}
		if(_breathBackNow)
		{
			_controller.setAnimation(_KNIGHT_BREATHE_ANIMATION,-1,1f,null);
			_breathBackNow = false;
		}

		super.update(world);
	}

	@Override
	public boolean IsUsingSpriteBatch() {
		return false;
	}

	private AnimationController _controller;
	private PlayerMoveListener _playerMoveListener;
	private boolean _breathForeNow;
	private boolean _breathBackNow;

	private final String _PLAYER_COMPONENT_NAME = getName() + "_PlayerComponent";
	private final String _MODEL_COMPONENT_NAME = getName() + "_ModelComponent";
	private static final String _KNIGHT_MODEL_NAME = "KnightModel";
	
	// Knight model animation names
	private static final String _KNIGHT_JUMPFORE_ANIMATION = "Armature|JumpForward";
	private static final String _KNIGHT_JUMPFOREUP_ANIMATION = "Armature|JumpUpForeward";
	private static final String _KNIGHT_JUMPFOREDOWN_ANIMATION = "Armature|JumpDownForeward";
	
	private static final String _KNIGHT_JUMPBACK_ANIMATION = "Armature|JumpBackward";
	private static final String _KNIGHT_JUMPBACKUP_ANIMATION = "Armature|JumpUpBackward";
	private static final String _KNIGHT_JUMPBACKDOWN_ANIMATION = "Armature|JumpDownBackward";
	
	private static final String _KNIGHT_TURNBACK_ANIMATION = "Armature|turnBack";
	private static final String _KNIGHT_TURNFORE_ANIMATION = "Armature|turnFore";
	
	private static final String _KNIGHT_BREATHE_ANIMATION = "Armature|Breathe";//	private static final String _KNIGHT_BREATHEFORE_ANIMATION = "Armature|BreatheForeward";
	private static final String _KNIGHT_BREATHEBACK_ANIMATION = "Armature|BreatheBack";
	
	private static final String _KNIGHT_START_ANIMATION = "Armature|StartDance";
	private static final String _KNIGHT_DIE_ANIMATION = "Armature|Die";
	private static final String _KNIGHT_HIGHSCORE_ANIMATION = "Armature|HighScore";
	private static final String _KNIGHT_LOOSER_ANIMATION = "Armature|Looser";
	
	private static final float _JUMP_STEP = 2f;
}
