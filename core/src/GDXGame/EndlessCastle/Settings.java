package GDXGame.EndlessCastle;

public class Settings
{
	public static void load() {
		//TODO: implémenter ça
	}
	
	public static void save() {
		//TODO: implémenter ça
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
	}

	private int _volume;
}

enum Difficulty 
{
	easy,
	normal,
	hard,
	insane
}