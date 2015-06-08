package game;

import java.util.EventListener;

import game.Button.ButtonClickListener;
import game.dataAccessLayer.Settings;

public class VolumeButtonListener extends ButtonClickListener
{
	public interface VolumeSettingListener extends EventListener {
		public void SetVolume(float volume);
	}

	public VolumeButtonListener(Settings settings, VolumeSettingListener listener) {
		_settings = settings;
		_listener = listener;
	}

/*	public void SetButtons(Button sound100Button, Button sound75Button, Button sound25Button, Button sound0Button)
	{
		_sound100Button = sound100Button;
		_sound75Button = sound75Button;
		_sound25Button = sound25Button;
		_sound0Button = sound0Button;
		
		// Temporary
		if(_settings.getVolume() > 80)
		{
			_sound100Button.Disable();
			_sound75Button.Enable();
			_sound25Button.Disable();
			_sound0Button.Disable();
		}
		else if(_settings.getVolume() > 50)
		{
			_sound100Button.Disable();
			_sound75Button.Disable();
			_sound25Button.Enable();
			_sound0Button.Disable();
		}
		else if(_settings.getVolume() > 10)
		{
			_sound100Button.Disable();
			_sound75Button.Disable();
			_sound25Button.Disable();
			_sound0Button.Enable();
		}
		else
		{
			_sound100Button.Enable();
			_sound75Button.Disable();
			_sound25Button.Disable();
			_sound0Button.Disable();
		}
	}*/
	
	//public void SetButton(Button sound100Button)
	//{
	//	_sound100Button = sound100Button;
	//}
	
	@Override
	public void MouseRelease() {
		if(_settings.getVolume() > 80)
		{
			/*_sound100Button.Disable();
			_sound75Button.Enable();
			_sound25Button.Disable();
			_sound0Button.Disable();*/
			if(_listener != null)
				_listener.SetVolume(0.75f);
			_settings.setVolume(75);

		}
		else if(_settings.getVolume() > 50)
		{
			/*_sound100Button.Disable();
			_sound75Button.Disable();
			_sound25Button.Enable();
			_sound0Button.Disable();*/
			if(_listener != null)
				_listener.SetVolume(0.25f);
			_settings.setVolume(25);
		}
		else if(_settings.getVolume() > 10)
		{
			/*_sound100Button.Disable();
			_sound75Button.Disable();
			_sound25Button.Disable();
			_sound0Button.Enable();*/
			if(_listener != null)
				_listener.SetVolume(0.00f);
			_settings.setVolume(0);
		}
		else
		{
			/*_sound100Button.Enable();
			_sound75Button.Disable();
			_sound25Button.Disable();
			_sound0Button.Disable();*/
			if(_listener != null)
				_listener.SetVolume(1.00f);
			_settings.setVolume(100);
		}
	}

//	private Button _sound100Button;
//	private Button _sound75Button;
//	private Button _sound25Button;
//	private Button _sound0Button;

	private Settings _settings;
	private VolumeSettingListener _listener;
}
