package game;

import com.badlogic.gdx.Preferences;

public class Settings
{
	public Settings(String settingsName) {
		_preferences = com.badlogic.gdx.Gdx.app.getPreferences(settingsName);
		
		_volume = _preferences.getInteger(VOL_SETTING_NAME, DEFAULT_VOLUME);
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
	
	public int getDifficulty() {
		return _volume;
	}

	public void setDifficulty(Difficulty difficulty) {
		_difficulty = difficulty;
		_preferences.putString(DIF_SETTING_NAME, _difficulty.name());
	}

	private Preferences _preferences;

	// Volume
	private int _volume;
	private static final int DEFAULT_VOLUME = 50;
	private static final String VOL_SETTING_NAME = "volume";
	
	// Difficulty
	private Difficulty _difficulty;
	private static final Difficulty DEFAULT_DIFF = Difficulty.normal;
	private static final String DIF_SETTING_NAME = "difficulty";
}

enum Difficulty 
{
	easy,
	normal,
	hard,
	insane
}