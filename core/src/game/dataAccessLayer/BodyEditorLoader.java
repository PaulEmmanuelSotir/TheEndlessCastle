package game.dataAccessLayer;

import aurelienribon.bodyeditor.BodyEditorDAL;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;

public class BodyEditorLoader extends SynchronousAssetLoader<BodyEditorDAL, BodyEditorLoader.BodyEditorDALParameters>
{
	public class BodyEditorDALParameters extends AssetLoaderParameters<BodyEditorDAL>
	{
		// Body Editor DAL parameters...
	}
	
	public BodyEditorLoader(FileHandleResolver resolver) {
		super(resolver);
	}

	@Override
	public BodyEditorDAL load(AssetManager assetManager, String fileName, FileHandle file, BodyEditorDALParameters parameter) {		
		return new BodyEditorDAL(file);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, BodyEditorDALParameters parameter) {
		return null;
	}
}
