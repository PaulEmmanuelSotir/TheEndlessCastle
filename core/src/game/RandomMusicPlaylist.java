package game;

import java.util.ArrayList;
import java.util.List;

import game.dataAccessLayer.AssetsHandler;
import game.dataAccessLayer.TypedAssetDescriptor.AssetTypeEnum;

import com.badlogic.gdx.audio.Music;

public class RandomMusicPlaylist
{
	public RandomMusicPlaylist(AssetsHandler assetsHndlr){
		_assetsHndlr = assetsHndlr;
		_musics = new ArrayList<Music>();
		for(String name : _GAME_MUSIC_NAMES)
			_musics.add((Music)_assetsHndlr.get(name));
		
		_musicListener = new FinishedMusicListener();
	}
	
	public void Start()
	{
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

	private class FinishedMusicListener implements Music.OnCompletionListener
	{
		@Override
		public void onCompletion(Music music)
		{
			if(music.isPlaying())
			{
				music.stop();
			}
			
			PlayRandomMusic();	
		}
	}

	private void PlayRandomMusic() {
		if(_musics != null)
			if(_musics.size() > 0)
			{
				int random = (int) (Math.random() * _musics.size());
				_playingMusic = _musics.get(random);
				_playingMusic.setOnCompletionListener(_musicListener);
				_playingMusic.play();
			}
	}

	private List<Music> _musics;
	private FinishedMusicListener _musicListener;
	private AssetsHandler _assetsHndlr;
	private Music _playingMusic;
	
	private static final List<String> _GAME_MUSIC_NAMES;
	static
	{
		_GAME_MUSIC_NAMES = new ArrayList<String>();
		_GAME_MUSIC_NAMES.add("GameMusicKnight");
		_GAME_MUSIC_NAMES.add("GameMusicTrailer");
		_GAME_MUSIC_NAMES.add("GameMusicRapture");
	}
}
