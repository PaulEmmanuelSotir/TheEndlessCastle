package game.dataAccessLayer;

import com.badlogic.gdx.Preferences;

public class Settings
{
	public enum Difficulty 
	{
		easy,
		normal,
		hard,
		insane
	}
	
	public Settings(String settingsName) {
		_preferences = com.badlogic.gdx.Gdx.app.getPreferences(settingsName);
		
		_volume = _preferences.getInteger(VOL_SETTING_NAME, DEFAULT_VOLUME);
		_highScore = _preferences.getInteger(SCORE_SETTING_NAME, DEFAULT_SCORE);
		_isBloomShaderEnabled = _preferences.getBoolean(BLOOM_SETTING_NAME, DEFAULT_BLOOM);
		_difficulty = Difficulty.valueOf(_preferences.getString(DIF_SETTING_NAME, DEFAULT_DIFF.name()));
	}
		
	public void save() {
		_preferences.flush();
	}
	
	public int getVolume() {
		return _volume;
	}

	public void setVolume(int volume) {
		if(volume <= 100 && volume >= 0)
			_volume = volume;
		else if(volume < 0)
			_volume = 0;
		else
			_volume = 100;
		
		_preferences.putInteger(VOL_SETTING_NAME, _volume);
	}
	
	public int getHighScore()
	{
		return _highScore;
	}
	
	public void setHighScore(int score)
	{
		if(score > _highScore)
		{
			_highScore = score;
			_preferences.putInteger(SCORE_SETTING_NAME, _highScore);
		}
	}
	
	
	public int getDifficulty() {
		return _volume;
	}

	public void setDifficulty(Difficulty difficulty) {
		_difficulty = difficulty;
		_preferences.putString(DIF_SETTING_NAME, _difficulty.name());
	}
	
	public boolean IsBloomShaderEnabled() {
		return _isBloomShaderEnabled;
	}
	
	public void SetIsBloomShaderEnabled(boolean isEnabled) {
		_isBloomShaderEnabled = isEnabled;
		_preferences.putBoolean(BLOOM_SETTING_NAME, _isBloomShaderEnabled);
	}

	private Preferences _preferences;

	// Volume
	private int _volume;
	private static final int DEFAULT_VOLUME = 50;
	private static final String VOL_SETTING_NAME = "volume";
	
	// High score
	private int _highScore;
	private static final int DEFAULT_SCORE = 0;
	private static final String SCORE_SETTING_NAME = "highScore";
	
	// Bloom shader
	private boolean _isBloomShaderEnabled;
	private static final boolean DEFAULT_BLOOM = false;
	private static final String BLOOM_SETTING_NAME = "bloomEnabled";
	
	// Difficulty
	private Difficulty _difficulty;
	private static final Difficulty DEFAULT_DIFF = Difficulty.normal;
	private static final String DIF_SETTING_NAME = "difficulty";
}