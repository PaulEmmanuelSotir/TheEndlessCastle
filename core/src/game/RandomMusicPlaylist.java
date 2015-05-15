package game;

import game.dataAccessLayer.AssetsHandler;
import game.dataAccessLayer.TypedAssetDescriptor.AssetTypeEnum;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Array;

public class RandomMusicPlaylist
{
	private Array<Music> _musics;
	private FinishedMusicListener _musicListener;
	private AssetsHandler _assetsHndlr;


	public RandomMusicPlaylist(AssetsHandler assetsHndlr){
		_assetsHndlr = assetsHndlr;
		_musics = _assetsHndlr.get(AssetTypeEnum.music);
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
			if(_musics.size > 0)
			{
				int random = (int) (Math.random() * _musics.size);
				_playingMusic = _musics.get(random);
				_playingMusic.setOnCompletionListener(_musicListener);
				_playingMusic.play();
			}
	}
	
	private Music _playingMusic;
}
