package game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.utils.Align;

import game.Button;
import game.Button.ButtonClickListener;
import game.VolumeButtonListener.VolumeSettingListener;
import game.RandomMusicPlaylist;
import game.TheEndlessCastle;
import game.GameWorld;
import game.VolumeButtonListener;

/**
 * Game screen class
 */
public class GameScreen extends Screen
{
	/**
	 *  World listener waiting for the player to die to show scores
	 */
	private class ScoreListener implements GameWorld.WorldListener
	{
		@Override
		public void PlayerDied(int Score, int DistanceTraveled) {
			// Score panel
			_ScorePanelSprite = new Sprite((Texture)_assetsHndlr.get(_SCORE_PANEL_TEXTURE_NAME));
			_ScorePanelSprite.setScale(0.017f);
			_ScorePanelSprite.setOrigin(0,0);
			
			// High score
			if(_game.GetSettings().getHighScore() < Score)
			{
				_game.GetSettings().setHighScore(Score);
				_highScore = true;
			}
			_score = Score;

			// Play again button
			/*_playAgainButton = new Button(0.015f, (Texture)_assetsHndlr.get("PlayAgainNormalTexture"), (Texture)_assetsHndlr.get("PlayAgainOverTexture"), (Texture)_assetsHndlr.get("PlayAgainPressedTexture"), new ButtonClickListener() {
				@Override
				public void MouseRelease() {
					_randomPlaylist.Stop();
					_game.setScreen(new GameScreen(_game));
					dispose();
				}
			});*/
			
			resize();
			_died = true;
		}

		public void drawScores() {
			_ScorePanelSprite.draw(_spriteBatch);
			float TextWidth = _ScorePanelSprite.getWidth()*_ScorePanelSprite.getScaleX() - 0.5f;
			_font.draw(_spriteBatch, (_highScore ? "NEW SCORE !!\nSCORE: " : ("BEST SCORE: " + _game.GetSettings().getHighScore() + "\nSCORE: ")) + _score, _highScorePosX -TextWidth/2f, _camera.viewportHeight/2f + 1f, TextWidth, Align.center, true);
			//_playAgainButton.render(_spriteBatch);
		}
		
		public void update()
		{
			_ScorePanelSprite.setPosition(_camera.position.x-_ScorePanelSprite.getWidth()*_ScorePanelSprite.getScaleX()/2f, _camera.position.y -_ScorePanelSprite.getHeight()*_ScorePanelSprite.getScaleY()/2f);
			_highScorePosX = _camera.position.x;
			//_playAgainButton.update(_camera);
		}
		
		public void resize() {
			//_playAgainButton.SetPosition(_camera.viewportWidth/2f - _playAgainButton.getWidth()/2f, _camera.viewportHeight/2f - 4f);
		}

		public boolean IsPlayerDied() {
			return _died;
		}

		private Sprite _ScorePanelSprite;
		//private Button _playAgainButton;
		private float _highScorePosX;
		private int _score;
		private boolean _highScore;
		private boolean _died;
		private static final String _SCORE_PANEL_TEXTURE_NAME = "ScorePanel";
	};

	public GameScreen(TheEndlessCastle game)
	{
		super(game);

		_worldListener = new ScoreListener();
		_world = new GameWorld(_assetsHndlr, _camera, _worldListener, _game.GetSettings().IsBloomShaderEnabled());

		// Random music
		_randomPlaylist = new RandomMusicPlaylist(_game.GetSettings().getVolume()/100f,_assetsHndlr);
		_randomPlaylist.Start();
		
		// Font
		FreeTypeFontGenerator generator = _assetsHndlr.get(_GAME_SCREEN_FONT_NAME);
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 128;
		parameter.genMipMaps = true;
		parameter.minFilter = TextureFilter.MipMapLinearLinear;
		parameter.magFilter = TextureFilter.Linear;
		parameter.color = TheEndlessCastle.MAIN_GAME_COLOR;
		_font = generator.generateFont(parameter);
		_font.getData().setScale(0.01f, 0.01f);

		// Menu button
		_menuButton = new Button(0.006f, (Texture)_assetsHndlr.get("MenuNormalTexture"), (Texture)_assetsHndlr.get("MenuOverTexture"), (Texture)_assetsHndlr.get("MenuPressedTexture"), new ButtonClickListener() {
			@Override
			public void MouseRelease() {
				_randomPlaylist.Stop();
				_game.setScreen(new MenuScreen(_game));
			}
		});

		// Sound button
		VolumeButtonListener _volumeListener = new VolumeButtonListener(_game.GetSettings(), new VolumeSettingListener() {			
			@Override
			public void SetVolume(float volume) {
				_randomPlaylist.SetVolume(volume);
			}
		});
		_sound100Button = new Button(0.006f, (Texture)_assetsHndlr.get("Sound3NormalTexture"), (Texture)_assetsHndlr.get("Sound3OverTexture"), (Texture)_assetsHndlr.get("Sound3PressedTexture"), _volumeListener);
		//_sound75Button = new Button(0.006f, (Texture)_assetsHndlr.get("Sound2NormalTexture"), (Texture)_assetsHndlr.get("Sound2OverTexture"), (Texture)_assetsHndlr.get("Sound2PressedTexture"), _volumeListener);
		//_sound25Button = new Button(0.006f, (Texture)_assetsHndlr.get("Sound1NormalTexture"), (Texture)_assetsHndlr.get("Sound1OverTexture"), (Texture)_assetsHndlr.get("Sound1PressedTexture"), _volumeListener);
		//_sound0Button = new Button(0.006f, (Texture)_assetsHndlr.get("Sound0NormalTexture"), (Texture)_assetsHndlr.get("Sound0OverTexture"), (Texture)_assetsHndlr.get("Sound0PressedTexture"), _volumeListener);
		//_volumeListener.SetButtons(_sound100Button, _sound75Button, _sound25Button, _sound0Button);
		//_volumeListener.SetButton(_sound100Button);
		
		// Pause button
		//_pauseButton = new Button(0.006f, (Texture)_assetsHndlr.get("PauseNormalTexture"), (Texture)_assetsHndlr.get("PauseOverTexture"), (Texture)_assetsHndlr.get("PausePressedTexture"), new ButtonClickListener() {
		//	@Override
		//	public void MouseRelease() {
				// TODO: pause
		//	}
		//});
	}

	@Override
	protected void update()
	{
		// Continuous camera scrolling
		if(_world.GetPlayerXPosition() > _camera.position.x + 3f || _speedUp)
		{
			_camera.position.x += 4f*Gdx.graphics.getDeltaTime();
			_speedUp = true;
			if(_world.GetPlayerXPosition() < _camera.position.x)
				_speedUp = false;
		}
		else
		{
			_camera.position.x += 2.8f*Gdx.graphics.getDeltaTime();
		}
		
		// Update Score display position
		_scorePosX = _camera.position.x;

		//Update world
		_world.update(_time);

		// Update HUD
		_menuButton.update(_camera);
		_sound100Button.update(_camera);
		//_sound75Button.update(_camera);
		//_sound25Button.update(_camera);
		//_sound0Button.update(_camera);
		//_pauseButton.update(_camera);
		
		// High score panel
		if(_worldListener.IsPlayerDied())
			_worldListener.update();
	}

	@Override
	protected void draw()
	{
		// Render world
		_world.render(_spriteBatch, _modelBatch);

		_spriteBatch.begin();
		// Render HUD
		_menuButton.render(_spriteBatch);
		_sound100Button.render(_spriteBatch);
		//_sound75Button.render(_spriteBatch);
		//_sound25Button.render(_spriteBatch);
		//_sound0Button.render(_spriteBatch);
		//_pauseButton.render(_spriteBatch);
		// Score
		_font.draw(_spriteBatch, "SCORE: " + _world.GetScore(), _scorePosX-1.5f, _camera.viewportHeight - 0.5f, 3f,  Align.center, false);
		// High score panel
		if(_worldListener.IsPlayerDied())
			_worldListener.drawScores();
		_spriteBatch.end();
	}

	@Override
	public void resize(int width, int height)
	{
		super.resize(width, height);
		_world.setViewRatio(_ratio);

		_menuButton.SetPosition(1f, _camera.viewportHeight - 2.5f);
		//_pauseButton.SetPosition(_camera.viewportWidth - _pauseButton.getWidth() - 1f, _camera.viewportHeight - 2.5f);
		_sound100Button.SetPosition(_camera.viewportWidth - _sound100Button.getWidth() - 1f, _camera.viewportHeight - 2.5f);
		//_sound75Button.SetPosition(_camera.viewportWidth - _sound75Button.getWidth() - 1f, _camera.viewportHeight - 3f - _pauseButton.getHeight());
		//_sound25Button.SetPosition(_camera.viewportWidth - _sound25Button.getWidth() - 1f, _camera.viewportHeight - 3f - _pauseButton.getHeight());
		//_sound0Button.SetPosition(_camera.viewportWidth - _sound0Button.getWidth() - 1f, _camera.viewportHeight - 3f - _pauseButton.getHeight());
		
		if(_worldListener.IsPlayerDied())
			_worldListener.resize();
	}

	@Override
	public void dispose() {
		_randomPlaylist.Stop();
		_world.dispose();
		_worldListener = null;
		_font.dispose();
	}

	private RandomMusicPlaylist _randomPlaylist;

	private GameWorld _world;
	private ScoreListener _worldListener;
	private boolean _speedUp;

	// Buttons
	private Button _menuButton;
	//private Button _pauseButton;
	private Button _sound100Button;
	//private Button _sound75Button;
	//private Button _sound25Button;
	//private Button _sound0Button;
	
	private BitmapFont _font;
	private float _scorePosX = _camera.viewportWidth/2f;
	private static final String _GAME_SCREEN_FONT_NAME = "XoloniumFont";
}
