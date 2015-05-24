package game;

import java.util.ArrayList;
import java.util.List;

import game.dataAccessLayer.AssetsHandler;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Music.OnCompletionListener;

public class RandomMusicPlaylist
{
	public RandomMusicPlaylist(AssetsHandler assetsHndlr){
		this(100, assetsHndlr);
	}
	
	public RandomMusicPlaylist(float volume, AssetsHandler assetsHndlr){
		_assetsHndlr = assetsHndlr;
		_musics = new ArrayList<Music>();
		_volume = volume;
		for(String name : _GAME_MUSIC_NAMES)
			_musics.add((Music)_assetsHndlr.get(name));
		
		_musicListener = new OnCompletionListener() {
			@Override
			public void onCompletion(Music music) {
				if(music.isPlaying())
					music.stop();
			
				PlayRandomMusic();	
			}
		};
	}
	
	public void Start()
	{
		if(_playingMusic == null)
			PlayRandomMusic();
	}
	
	public void Stop()
	{
		if(_playingMusic != null)
		{
			_playingMusic.setOnCompletionListener(null);
			_playingMusic.stop();
			_playingMusic = null;
		}
	}
	
	public void SetVolume(float volume)
	{
		_volume = volume;
		if(_playingMusic != null)
			_playingMusic.setVolume(volume);
	}

	private void PlayRandomMusic() {
		if(_musics != null)
			if(_musics.size() > 0)
			{
				int random = (int) (Math.random() * _musics.size());
				_playingMusic = _musics.get(random);
				_playingMusic.setOnCompletionListener(_musicListener);
				_playingMusic.setVolume(_volume);
				_playingMusic.play();
			}
	}

	private List<Music> _musics;
	private OnCompletionListener _musicListener;
	private AssetsHandler _assetsHndlr;
	private Music _playingMusic;
	private float _volume;
	
	private static final List<String> _GAME_MUSIC_NAMES;
	static
	{
		_GAME_MUSIC_NAMES = new ArrayList<String>();
		_GAME_MUSIC_NAMES.add("GameMusicKnight");
		_GAME_MUSIC_NAMES.add("GameMusicTrailer");
		_GAME_MUSIC_NAMES.add("GameMusicRapture");
	}
}
