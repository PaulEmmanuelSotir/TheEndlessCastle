package game.components;

import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.math.Vector3;

import game.GameWorld;
import game.entities.Entity;
import game.utils.Position;

public class ModelComponent extends Component implements IRenderableComponent<ModelBatch>, IUpdateableComponent
{
	public ModelComponent(String name, Entity owner) {
		super(name, owner);
		_scale = 1f;
		_axis = new Vector3(0f, 0f, 1f);
		_degrees = 0f;
		_environment = new Environment();
		_relativePosition = new Position(0, 0);
	}

	public ModelComponent(String name, Entity owner, Model model) {
		super(name, owner);
		_scale = 1f;
		_axis = new Vector3(0f, 0f, 1f);
		_degrees = 0f;
		_environment = new Environment();
		_relativePosition = new Position(0, 0);
		SetModel(model);
	}
	
	public ModelComponent(String name, Entity owner, Model model, ColorAttribute environmentLight) {
		super(name, owner);
		_scale = 1f;
		_axis = new Vector3(0f, 0f, 1f);
		_degrees = 0f;
		_environment = new Environment();
		_environment.set(environmentLight);
		_relativePosition = new Position(0, 0);
		SetModel(model);
	}
	
	@Override
	public void render(ModelBatch batch) {
		if(_isEnabled)
			batch.render(_modelInstance, _environment);
	}
	
	@Override
	public void SetRelativePosition(Position pos) {
		if(pos != null)
			_relativePosition = pos;
	}
	
	@Override
	public Position GetRelativePosition() {
		return _relativePosition;
	}

	@Override
	public void update(GameWorld world) {
		// Update position
		Position OwnerPos = _owner.getPosition();
		_modelInstance.transform.setToTranslation(new Vector3(Position.add(_relativePosition, OwnerPos),0));
		_modelInstance.transform.scale(_scale, _scale, _scale);
		_modelInstance.transform.rotate(_axis, _degrees);
	}
	
	public void SetScale(float scale) {
		if(scale > 0)
			_scale = scale;
	}
	
	public void SetModel(Model model)
	{
		if(model != null)
			_modelInstance = new ModelInstance(model);
	}
	
	public void SetRotation(Vector3 axis, float degrees)
	{
		_axis = axis;
		_degrees = degrees;
	}

	public ModelInstance GetModelInstance()
	{
		return _modelInstance;
	}
	
	public Environment getEnvironment() {
		return _environment;
	}

	public void setEnvironment(ColorAttribute environmentLight) {
		_environment.set(environmentLight);
	}
	
	protected Position _relativePosition;
	protected float _scale;
	protected Vector3 _axis;
	protected float _degrees;
	protected ModelInstance _modelInstance;
	protected Model _model;
	protected Environment _environment;
}
