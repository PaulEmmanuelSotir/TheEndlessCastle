package DataAccessLayer;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;

/**
 * Font asset loader
 */
public class FontLoader extends AsynchronousAssetLoader<Font, FontLoader.FontParameters> {

	/**
	 * @param resolver
	 */
	public FontLoader(FileHandleResolver resolver) {
		super(resolver);
		_font = new Font();
	}

	@Override
	public void loadAsync(AssetManager manager, String fileName, FileHandle file, FontParameters parameter) {
		// TODO: loadAsync a bitmap font after creating bitmaps dynamically

	}

	@Override
	public Font loadSync(AssetManager manager, String fileName, FileHandle file, FontParameters parameter) {
		// TODO: loadAsync a bitmap font after creating bitmaps dynamically
		return _font;
	}

	@Override
	public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, FontParameters parameter) {
		return null;
	}

	private Font _font;

	static public class FontParameters extends AssetLoaderParameters<Font>
	{
		// TODO: add or remove font parameters or extend bitmapfont parameters !!
	}

}
