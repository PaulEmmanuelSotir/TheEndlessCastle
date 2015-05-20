package game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import game.GameWorld;
import game.components.IRenderableComponent;
import game.components.SpriteComponent;
import game.dataAccessLayer.AssetsHandler;
import game.utils.Position;

public class RotatingRaysEntity extends SpriteEntity
{
	public RotatingRaysEntity(String name, AssetsHandler assetsHndlr) {
		super(name, new Position(0, 0), assetsHndlr);
		SpriteComponent compo = SetSprite(_RANDOM_TEXTURE_NAME);
		
		// Temporary
		SetShader(new ShaderProgram(Gdx.files.internal("shaders/vertex.glsl"), Gdx.files.internal("shaders/rotatingRays2.glsl")));
		ShaderProgram backgroundShader = GetShader();
		_timeLocaction = backgroundShader.getUniformLocation("u_globalTime");
		_ratioLocaction = backgroundShader.getUniformLocation("u_ratio");
	}
	
	@Override
	public void update(GameWorld world) {
		super.update(world);
		Position pos = world.GetCameraPosition();
		pos.x -= world.METERS_TO_PIXELS/2f;
		pos.y -= world.getCameraRatio()*world.METERS_TO_PIXELS/2f;
		this.setPosition(pos);
		_time = world.GetTime();
		_ratio = world.getCameraRatio();
	}

	@Override
	public void draw(SpriteBatch batch, GameWorld world, ShaderProgram shader) {
		shader.setUniformf(_ratioLocaction, _ratio);
		shader.setUniformf(_timeLocaction, _time);
	}
	
	public void resize(float WorldSize, float ratio)
	{
		((SpriteComponent)this._components.get(0)).SetSize(WorldSize, WorldSize*ratio);
	}
	
	private int _timeLocaction;
	private int _ratioLocaction;
	private float _time;
	private float _ratio;

	private static final String _RANDOM_TEXTURE_NAME = "DefaultTexture";
}
