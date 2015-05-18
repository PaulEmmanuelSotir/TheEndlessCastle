package game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import game.World;
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
		_backgroundShader = new ShaderProgram(Gdx.files.internal("shaders/vertex.glsl"), Gdx.files.internal("shaders/rotatingRays2.glsl"));
		_timeLocaction = _backgroundShader.getUniformLocation("u_globalTime");
		_ratioLocaction = _backgroundShader.getUniformLocation("u_ratio");
	}
	
	@Override
	public void update(World world) {
		super.update(world);
		Position pos = world.GetCameraPosition();
		pos.x -= world.METERS_TO_PIXELS/2f;
		pos.y -= world.getCameraRatio()*world.METERS_TO_PIXELS/2f;
		this.setPosition(pos);
		_time = world.GetTime();
		_ratio = world.getCameraRatio();
	}
	
	public void resize(float WorldSize, float ratio)
	{
		((SpriteComponent)this._components.get(0)).SetSize(WorldSize, WorldSize*ratio);
	}

	@Override
	public void render(SpriteBatch batch) {
		batch.setShader(_backgroundShader);
		_backgroundShader.setUniformf(_ratioLocaction, _ratio);
		_backgroundShader.setUniformf(_timeLocaction, _time);
		super.render(batch);
		batch.setShader(null);
	}
	
	private ShaderProgram _backgroundShader;
	private int _timeLocaction;
	private int _ratioLocaction;
	private float _time;
	private float _ratio;

	private static final String _RANDOM_TEXTURE_NAME = "DefaultTexture";
}
