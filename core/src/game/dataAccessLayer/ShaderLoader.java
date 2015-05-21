package game.dataAccessLayer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AssetLoader;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Array;

/**
 * Shader asset loader
 */
public class ShaderLoader extends SynchronousAssetLoader<ShaderProgram, ShaderLoader.ShaderParameter> {

	/**
	 * Shader loader constructor
	 * @param resolver
	 */
	public ShaderLoader(FileHandleResolver resolver) {
		super(resolver);
	}

	/** Loads the asset.
	 * @param manager
	 * @param fileName
	 * @param file the resolved file to load
	 * @param parameter */
	@Override
	public ShaderProgram load(AssetManager assetManager, String fileName, FileHandle file, ShaderParameter parameter) {
		// TODO: verifier _testShader.getLog() et _testShader.isCompiled();
		if(parameter != null)
			_shader = new ShaderProgram(Gdx.files.internal(parameter.VertexShaderPath), Gdx.files.internal(fileName));
		else
			_shader = new ShaderProgram(Gdx.files.internal(parameter.VertexShaderPath).readString(), DefaultShader.getDefaultVertexShader());
		
		return _shader;
	}

	@Override
	public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, ShaderLoader.ShaderParameter parameter) {
		return null;
	}

	protected ShaderProgram _shader;

	public static class ShaderParameter extends AssetLoaderParameters<ShaderProgram> {
		public String VertexShaderPath;
	}
}
