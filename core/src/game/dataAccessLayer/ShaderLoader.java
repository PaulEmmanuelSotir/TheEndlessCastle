package game.dataAccessLayer;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AssetLoader;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

/**
 * Shader asset loader
 */
public class ShaderLoader extends SynchronousAssetLoader<Shader, ShaderLoader.ShaderParameter> {

	/**
	 * Shader loader constructor
	 * @param resolver
	 */
	public ShaderLoader(FileHandleResolver resolver) {
		super(resolver);

		// TODO: determiner l'utilité de ce test (pooling ??) (vu dans un exemple de AssetLoader)
		if(_shader == null)
			_shader = new Shader();
	}

	/** Loads the asset.
	 * @param manager
	 * @param fileName
	 * @param file the resolved file to load
	 * @param parameter */
	@Override
	public Shader load(AssetManager assetManager, String fileName, FileHandle file, ShaderParameter parameter) {
		//TODO: load and compile shader
		return _shader;
	}

	@Override
	public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, ShaderLoader.ShaderParameter parameter) {
		return null;
	}

	protected Shader _shader;

	public static class ShaderParameter extends AssetLoaderParameters<Shader> {
		// TODO: add or remove shader parameters
		public boolean TimeUniformNeeded = true;
		public boolean RatioUniformNeeded = true;
	}
}
