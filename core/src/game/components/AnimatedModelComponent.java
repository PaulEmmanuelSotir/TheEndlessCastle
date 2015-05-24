package game.components;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;

import game.GameWorld;
import game.entities.Entity;

public class AnimatedModelComponent extends ModelComponent {

	public AnimatedModelComponent(String name, Entity owner) {
		super(name, owner);
	}
	
	public AnimatedModelComponent(String name, Entity owner, Model model) {
		super(name, owner, model);
	}

	public AnimatedModelComponent(String name, Entity owner, Model model, ColorAttribute environmentLight) {
		super(name, owner, model, environmentLight);
	}
	
	@Override
	public void SetModel(Model model)
	{
		super.SetModel(model);
		_controller = new AnimationController(_modelInstance);
	}
	
	public AnimationController GetAnimationController()
	{
		return _controller;
	}

	protected AnimationController _controller;

	@Override
	public void update(GameWorld world) {
		super.update(world);
		_controller.update(world.GetDeltaTime());
	}
}
