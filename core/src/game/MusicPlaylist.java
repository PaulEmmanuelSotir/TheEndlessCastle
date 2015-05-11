package game;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;

public class MusicPlaylist {

	private String _musicsNames[];
	private Music _musics[];
	private Map<Music,String> _mp= new HashMap<Music, String>();
	private FinishedMusicListener _musicListener;
	
	
	public MusicPlaylist(){
		_musicsNames = new String[4];
		_musicsNames[0] = "CrunkKnight";
		_musicsNames[1] = "ExcitingTrailer";
		_musicsNames[2] = "HeroicAge";
		_musicsNames[3] = "Rapture";
		
		for(int i=0;i<_musics.length; i++){
			_musics[i]=  Gdx.audio.newMusic(Gdx.files.internal("musics/" + _musicsNames[i]+".mp3"));
		}
		
		for(int i=0;i<_musics.length;i++){
			_mp.put(_musics[i],_musicsNames[i]);
		}
		
		_musicListener = new FinishedMusicListener();
		
		PlayRandomMusic();
	}
	
	public class FinishedMusicListener implements Music.OnCompletionListener
	{
		@Override
		public void onCompletion(Music music)
		{
			PlayRandomMusic();	
		}
	}
	
	public void PlayRandomMusic(){
		int random = (int) Math.random() * (_musics.length+1);
		_musics[random].play();
		_musics[random].setOnCompletionListener(_musicListener);
	}
}
