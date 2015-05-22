package game.desktop;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import game.TheEndlessCastle;

public class DesktopLauncher
{
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.title = "The endless castle";
		//config.setFromDisplayMode(LwjglApplicationConfiguration.getDesktopDisplayMode());
		config.samples = 6;	
		config.addIcon("icon.medium.png", FileType.Internal);
		
		new LwjglApplication(new TheEndlessCastle(), config);
	}
}
