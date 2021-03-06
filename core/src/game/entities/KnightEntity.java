package game.entities;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController.AnimationDesc;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController.AnimationListener;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import game.GameWorld;
import game.Segment.SegmentDescriptor;
import game.components.AnimatedModelComponent;
import game.components.BodyComponent;
import game.components.PlayerComponent;
import game.components.PlayerComponent.MoveListener;
import game.utils.Position;

/**
 * Knight entity
 */
public class KnightEntity extends PhysicalEntity
{
	/**
	 * Player move listener
	 */
	public class PlayerMoveListener implements MoveListener
	{
		@Override
		public void MoveForward() {
			if((_jumpForeAnimation != _controller.current && _controller.current != _jumpBackAnimation) || _controller.current == null)
			{
				final SegmentDescriptor currentSeg =  _world.GetCurrentSegmentDescriptor(_position.x);
				
				_jumpForeAnimation = _controller.setAnimation(currentSeg.IsChangingHeight ? ( currentSeg.IsClimbing ? _KNIGHT_JUMPFOREUP_ANIMATION : _KNIGHT_JUMPFOREDOWN_ANIMATION) : _KNIGHT_JUMPFORE_ANIMATION, 1, _ANIMATIONS_SPEED, new AnimationListener() {
					@Override
					public void onEnd(AnimationDesc animation) {
						_breathNow = true;
						_turnedBack = false;
						ClearAnimations();
						
						// update player position
						if(currentSeg.IsChangingHeight)
						{
							if( currentSeg.IsClimbing)
							{
								_position.x +=  _JUMPUP_STEP;
								_position.y += _JUMPUP_STEP;
							}
							else
							{
								_position.x -= _JUMPDOWN_STEP;
								_position.y += _JUMPDOWN_STEP;
							}
						}
						else
							_position.x += _JUMP_STEP;
						_bodyCompo.SetRelativePosition(new Position(0f, 0f));
						
						if(_realMoveListener != null)
							_realMoveListener.MoveForward();
					}

					@Override
					public void onLoop(AnimationDesc animation) { }
				});
			}
		}

		@Override
		public void MoveBackward() {
			if((_jumpBackAnimation != _controller.current && _controller.current != _jumpForeAnimation) || _controller.current == null)
			{
				final SegmentDescriptor currentSeg =  _world.GetCurrentSegmentDescriptor(_position.x-0.5f);
				
				_jumpBackAnimation = _controller.setAnimation(currentSeg.IsChangingHeight ? ( currentSeg.IsClimbing ? _KNIGHT_JUMPBACKDOWN_ANIMATION : _KNIGHT_JUMPBACKUP_ANIMATION) : _KNIGHT_JUMPBACK_ANIMATION, 1, _ANIMATIONS_SPEED, new AnimationListener() {
					@Override
					public void onEnd(AnimationDesc animation) {
						_breathNow = true;
						_turnedBack = true;
						ClearAnimations();

						// update player position
						if(currentSeg.IsChangingHeight)
						{
							if(currentSeg.IsClimbing)
							{
								_position.x -=  _JUMPUP_STEP;
								_position.y -= _JUMPUP_STEP;
							}
							else
							{
								_position.x += _JUMPDOWN_STEP;
								_position.y -= _JUMPDOWN_STEP;
							}
						}
						else
							_position.x -= _JUMP_STEP;
						_bodyCompo.SetRelativePosition(new Position(0f, 0f));
						
						if(_realMoveListener != null)
							_realMoveListener.MoveBackward();
					}

					@Override
					public void onLoop(AnimationDesc animation) { }
				});
			}
		}

		@Override
		public void Jump() {
			if((_jumpAnimation != _controller.current  && _controller.current != _jumpForeAnimation && _controller.current != _jumpBackAnimation)|| _controller.current == null)
			{
				_jumpAnimation = _controller.setAnimation(_turnedBack ? _KNIGHT_JUMPB_ANIMATION : _KNIGHT_JUMPF_ANIMATION, 1, _ANIMATIONS_SPEED, new AnimationListener() {
					@Override
					public void onEnd(AnimationDesc animation) {
						_breathNow = true;
						_bodyCompo.SetRelativePosition(new Position(0f, 0f));
						ClearAnimations();
						if(_realMoveListener != null)
							_realMoveListener.Jump();
					}

					@Override
					public void onLoop(AnimationDesc animation) { }
				});
			}
		}

		@Override
		public void Crouch() {
			if((_crouchAnimation != _controller.current && _controller.current != _jumpForeAnimation && _controller.current != _jumpBackAnimation) || _controller.current == null)
			{
				ClearAnimations();
				_crouchAnimation = _controller.setAnimation(_turnedBack ? _KNIGHT_CROUCHBACK_ANIMATION : _KNIGHT_CROUCHFORE_ANIMATION, 1, _ANIMATIONS_SPEED, null);
				if(_realMoveListener != null)
					_realMoveListener.Crouch();
			}
		}

		@Override
		public void Uncrouch() {
			if(_crouchAnimation == _controller.current)
			{
				_uncrouchAnimation = _controller.setAnimation(_turnedBack ? _KNIGHT_UNCROUCHBACK_ANIMATION : _KNIGHT_UNCROUCHFORE_ANIMATION, 1, _ANIMATIONS_SPEED, new AnimationListener() {
					@Override
					public void onEnd(AnimationDesc animation) {
						ClearAnimations();
						_breathNow = true;
						if(_realMoveListener != null)
							_realMoveListener.Uncrouch();
					}

					@Override
					public void onLoop(AnimationDesc animation) { }
				});
			}
		}

		public final AnimationDesc GetCurrentAnimationDesc() {
			return _controller.current;
		}
		
		public boolean IsJumpingForward() {
			return _jumpForeAnimation == _controller.current;
		}
		
		public boolean IsJumpingBackward() {
			return _jumpBackAnimation == _controller.current;
		}
		
		public boolean IsJumping() {
			return _jumpAnimation == _controller.current;
		}
		
		public boolean IsCrouching() {
			return _crouchAnimation == _controller.current;
		}
		
		public boolean IsUncrouching() {
			return _uncrouchAnimation == _controller.current;
		}
		
		private void ClearAnimations() {
			_jumpForeAnimation = null;
			_jumpBackAnimation = null;
			_jumpAnimation = null;
			_crouchAnimation = null;
			_uncrouchAnimation = null;
		}
			
		private AnimationDesc _jumpForeAnimation;
		private AnimationDesc _jumpBackAnimation;
		private AnimationDesc _jumpAnimation;
		private AnimationDesc _crouchAnimation;
		private AnimationDesc _uncrouchAnimation;
	}

	public KnightEntity(String name, Position position, final GameWorld world) {
		super(name, position, world.GetAssetsHandler(), world.GetBodyDAL(), world.GetBox2DWorld());
		_world = world;

		// Animated model component
		AnimatedModelComponent modelCompo = new AnimatedModelComponent(_MODEL_COMPONENT_NAME, this, (Model)_assetsHndlr.get(_KNIGHT_MODEL_NAME), new ColorAttribute(ColorAttribute.AmbientLight, 1f, 1f, 1f, 1.0f));
		_controller = modelCompo.GetAnimationController();
		modelCompo.SetScale(_SCALE);
		addComponent(modelCompo);
		_controller.setAnimation(_KNIGHT_BREATHEFORE_ANIMATION, -1, 1f, null);
		_controller.animate(_KNIGHT_START_ANIMATION, 0f);
		
		// Player control component
		_playerCompo = new PlayerComponent(_PLAYER_COMPONENT_NAME, this);
		_world.GetInputMultiplexer().addProcessor(_playerCompo);
		_playerMoveListener = new PlayerMoveListener();
		_playerCompo.AddMoveListener(_playerMoveListener);
		addComponent(_playerCompo);
		
		// Knight box2D body
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(position.x, position.y);
		bodyDef.type = BodyType.KinematicBody;
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = 1;
		fixtureDef.friction = 0.5f;
		fixtureDef.restitution = 0.3f;
		_bodyCompo = new BodyComponent("knight_body", this, _box2DWorld, _bodyDAL, bodyDef, fixtureDef);
		_bodyCompo.setUserData(this);
		addComponent(_bodyCompo);
	}

	@Override
	public void update(final GameWorld world)
	{
		if(!_died)
		{
			if(_breathNow)
			{
				_controller.setAnimation(_turnedBack ? _KNIGHT_BREATHEBACK_ANIMATION : _KNIGHT_BREATHEFORE_ANIMATION,-1,1f,null);
				_breathNow = false;
			}
			
			// Move body according to model animations
			final AnimationDesc anim = _playerMoveListener.GetCurrentAnimationDesc();
			if(!_world.GetCurrentSegmentDescriptor(_position.x).IsChangingHeight)
			{
				if(_playerMoveListener.IsJumpingForward()) {
					float x = _JUMP_STEP*anim.time/anim.duration;
					_bodyCompo.SetRelativePosition(new Position(x, 4f/(_JUMP_STEP*_JUMP_STEP)*(_JUMP_HEIGHT-_KNIGHT_HEIGHT/2f)*x*x*(x-_JUMP_STEP)*(x-_JUMP_STEP)));
				}
				else if(_playerMoveListener.IsJumpingBackward()) {
					float x = -_JUMP_STEP*anim.time/anim.duration;
					_bodyCompo.SetRelativePosition(new Position(x, 4f/(_JUMP_STEP*_JUMP_STEP)*(_JUMP_HEIGHT-_KNIGHT_HEIGHT/2f)*x*x*(x+_JUMP_STEP)*(x+_JUMP_STEP)));
				}
				else if(_playerMoveListener.IsJumping()) {
					float t = 2f*anim.time/anim.duration;
					_bodyCompo.SetRelativePosition(new Position(0f, (_JUMP_HEIGHT)*t*t*(t-2f)*(t-2f)));
				}
				else if(_playerMoveListener.IsCrouching()) {
					float t = anim.time/anim.duration;
					_bodyCompo.SetRelativePosition(new Position(0f, (_JUMP_HEIGHT-_KNIGHT_HEIGHT/2f)*t*(t-2f)));
				}
				else if(_playerMoveListener.IsUncrouching()) {
					float t = anim.time/anim.duration;
					_bodyCompo.SetRelativePosition(new Position(0f, -(_JUMP_HEIGHT-_KNIGHT_HEIGHT/2f) * (t*(t-2f)+1f) ));
				}
			}
		}
		super.update(world);
	}

	@Override
	public boolean IsUsingSpriteBatch() {
		return false;
	}
	
	public void Die()
	{
		_controller.setAnimation(_turnedBack ? _KNIGHT_DIEBACK_ANIMATION : _KNIGHT_DIEFORE_ANIMATION);
		_playerCompo.RemoveMoveListener(_playerMoveListener);
		_breathNow = false;
		_died = true;
	}
	
	public void SetMoveListenner(MoveListener listener)
	{
		_realMoveListener = listener;
	}

	private final GameWorld _world;
	private final BodyComponent _bodyCompo;
	private final PlayerComponent _playerCompo;
	private final AnimationController _controller;
	private final PlayerMoveListener _playerMoveListener;
	private MoveListener _realMoveListener;
	private boolean _breathNow;
	private boolean _turnedBack;
	private boolean _died;

	private final String _PLAYER_COMPONENT_NAME = getName() + "_PlayerComponent";
	private final String _MODEL_COMPONENT_NAME = getName() + "_ModelComponent";
	private static final String _KNIGHT_MODEL_NAME = "KnightModel";
	private static final float _SCALE = 1.5f;
	public static final float _KNIGHT_HEIGHT = 1f*_SCALE;
	public static final float _JUMP_STEP = 2f*_SCALE;
	public static final float _JUMP_HEIGHT = 1.22f*_SCALE;
	public static final float _JUMPDOWN_STEP = -1f*_SCALE;
	public static final float _JUMPUP_STEP = 1f*_SCALE;
	
	// Knight model animation names
	private static final String _KNIGHT_JUMPF_ANIMATION = "Armature|JumpFore";
	private static final String _KNIGHT_JUMPB_ANIMATION = "Armature|JumpBack";
	private static final String _KNIGHT_JUMPFORE_ANIMATION = "Armature|JumpForeward";
	private static final String _KNIGHT_JUMPFOREUP_ANIMATION = "Armature|JumpUpForward";
	private static final String _KNIGHT_JUMPFOREDOWN_ANIMATION = "Armature|JumpDownForeward";
	private static final String _KNIGHT_JUMPBACK_ANIMATION = "Armature|JumpBackward";
	private static final String _KNIGHT_JUMPBACKUP_ANIMATION = "Armature|JumpUpBackward";
	private static final String _KNIGHT_JUMPBACKDOWN_ANIMATION = "Armature|JumpDownBackward";
	
	private static final String _KNIGHT_CROUCHBACK_ANIMATION =  "Armature|CrouchBackward";
	private static final String _KNIGHT_CROUCHFORE_ANIMATION =  "Armature|CrouchForeward";
	private static final String _KNIGHT_UNCROUCHBACK_ANIMATION =  "Armature|UncrouchBackward";
	private static final String _KNIGHT_UNCROUCHFORE_ANIMATION =  "Armature|UncrouchForward";
	
	//private static final String _KNIGHT_TURNBACK_ANIMATION = "Armature|TurnBack";
	//private static final String _KNIGHT_TURNFORE_ANIMATION = "Armature|TurnFore";
	
	//private static final String _KNIGHT_BREATHE_ANIMATION = "Armature|Breathe";
	private static final String _KNIGHT_BREATHEBACK_ANIMATION = "Armature|SwordBreathBackward";
	private static final String _KNIGHT_BREATHEFORE_ANIMATION = "Armature|SwordBreathForeward";
	
	private static final String _KNIGHT_START_ANIMATION = "Armature|StartDance";
	private static final String _KNIGHT_DIEFORE_ANIMATION = "Armature|DieForeward";
	private static final String _KNIGHT_DIEBACK_ANIMATION = "Armature|DieBackward";
	//private static final String _KNIGHT_HIGHSCORE_ANIMATION = "Armature|HighScore";
	//private static final String _KNIGHT_LOOSER_ANIMATION = "Armature|Looser";
	
	private static final float _ANIMATIONS_SPEED = 1.3f;
}
