package game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import game.GameWorld;
import game.components.SpriteComponent;
import game.dataAccessLayer.AssetsHandler;
import game.utils.Position;

public class RotatingRaysEntity extends SpriteEntity
{
	public RotatingRaysEntity(String name, AssetsHandler assetsHndlr) {
		super(name, new Position(0, 0), assetsHndlr);
		SetSprite(_DEFAULT_TEXTURE_NAME);
		
		SetShader((ShaderProgram)_assetsHndlr.get(_ROTATING_RAYS_SHADER_NAME));
		ShaderProgram backgroundShader = GetShader();
		_timeLocaction = backgroundShader.getUniformLocation("u_globalTime");
		_ratioLocaction = backgroundShader.getUniformLocation("u_ratio");
	}
	
	@Override
	public void update(GameWorld world) {
		super.update(world);
		_ratio = world.getViewRatio();
		Position pos = world.GetCameraPosition();
		pos.x -= GameWorld.WORLD_VIEW_WIDTH/2f;
		pos.y -= _ratio*GameWorld.WORLD_VIEW_WIDTH/2f;
		this.setPosition(pos);
		_time = world.GetTime();
	}

	@Override
	public void draw(SpriteBatch spriteBatch, ModelBatch modelBatch, GameWorld world, ShaderProgram shader) {
		shader.setUniformf(_ratioLocaction, _ratio);
		shader.setUniformf(_timeLocaction, _time);
	}
	
	@Override
	public boolean IsUsingSpriteBatch() {
		return true;
	}
	
	public void resize(float WorldSize, float ratio)
	{
		((SpriteComponent)this._components.get(0)).SetSize(WorldSize, WorldSize*ratio);
	}
	
	private int _timeLocaction;
	private int _ratioLocaction;
	private float _time;
	private float _ratio;

	private static final String _DEFAULT_TEXTURE_NAME = "DefaultTexture";
	private static final String _ROTATING_RAYS_SHADER_NAME = "GameRaysShader";
}
