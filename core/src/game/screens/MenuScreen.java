package game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import game.TheEndlessCastle;

/**
 * Menu screen class
 */
public class MenuScreen extends ScreenAdapter
{

	public MenuScreen(TheEndlessCastle game)
	{
		_game = game;
		_batch = game.getBatch();
		
		float ratio = (float)Gdx.graphics.getHeight() / Gdx.graphics.getWidth();
		_camera = new OrthographicCamera(32f, 32f*ratio);
		_camera.position.set(_camera.viewportWidth / 2f, _camera.viewportHeight / 2f, 0);
		_camera.update();
		
		// TODO: temporaire
		// TODO: verifier _testShader.getLog() et _testShader.isCompiled();
		_backgroundShader = new ShaderProgram(Gdx.files.internal("shaders/vertex.glsl"), Gdx.files.internal("shaders/rotatingRays.glsl"));
		_timeLocaction  = _backgroundShader.getUniformLocation("u_globalTime");
		_backgroundSprite = new Sprite(new Texture(Gdx.files.internal("textures/defaultTexture.png")));
		_backgroundSprite.setSize(32f, 32f*ratio);		
	}
	
	@Override
	public void render(float delta) {
		// Update time and camera
		if(_time > 0.8f * Float.MAX_VALUE)
			_time = 0f;
		_time += delta;

		// TODO: temporaire
		if(_time > 0.5)
			_game.setScreen(new GameScreen(_game));
		
		_camera.update();
		_batch.setProjectionMatrix(_camera.combined);
		
		// Render
		_batch.begin();
		// TODO: temporaire
		_backgroundShader.setUniformf(_timeLocaction, _time);
		_batch.setShader(_backgroundShader);
		_backgroundSprite.draw(_batch);
		_batch.end();
	}
	
	@Override
	public void resize(int width, int height) {
		_camera.viewportWidth = 32f;
		_camera.viewportHeight = 32f * height/width;
		_camera.update();
	}
	
	@Override
	public void dispose() {
		// TODO: temporaire
		_backgroundSprite.getTexture().dispose();
	}

	private TheEndlessCastle _game;
	private SpriteBatch _batch;
	private OrthographicCamera _camera;
	
	// TODO: temporaire
	private Sprite _backgroundSprite;
	private ShaderProgram _backgroundShader;	
	private int _timeLocaction;
	
	private float _time;
}
