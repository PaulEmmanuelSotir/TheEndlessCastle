package game.dataAccessLayer;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.ObjectMap;

public class TypedAssetDescriptor<T> extends AssetDescriptor<T>
{
	public String AssetName;
	public AssetTypeEnum AssetType;
	public AssetLicense License;

	public TypedAssetDescriptor(String assetName, String fileName, AssetTypeEnum assetType) {
		this(assetName, fileName, assetType, null, null);
	}

	public TypedAssetDescriptor(String assetName, String fileName, AssetTypeEnum assetType, AssetLicense license) {
		this(assetName, fileName, assetType, null, license);
	}

	public TypedAssetDescriptor(String assetName, String fileName, AssetTypeEnum assetType, AssetLoaderParameters<T> params) {
		this(assetName, fileName, assetType, params, null);
	}

	public TypedAssetDescriptor(String assetName, String fileName, AssetTypeEnum assetType, AssetLoaderParameters<T> params, AssetLicense license) {
		// AssetDescriptor<T>.FileName is actually an internal path >.<
		super(_ASSETS_TYPES.get(assetType).directory + fileName, _ASSETS_TYPES.get(assetType).assetClass, params);
		AssetType = assetType;
		AssetName = assetName;
		License = license;
	}

	/**
	 * 'toString()' method can be used for game credits according to assets licenses.
	 */
	@Override
	public String toString () {

		if(License != null)
			if(License.CreditsText != null)
				return License.CreditsText + "  (" + License.LicenseShortName + ")";
		
		StringBuffer buffer = new StringBuffer();
		buffer.append(AssetName);
		buffer.append(", (");
		buffer.append(AssetType.name());
		buffer.append(") ");
		if(License != null)
		{
			buffer.append("\t License: ");
			buffer.append(License.LicenseSummary);
		}
		return buffer.toString();
	}
	
	public static <T> Class<T> getAssetClass(AssetTypeEnum type)
	{
		return _ASSETS_TYPES.get(type).assetClass;
	}

	/**
	 * Lists all available asset types with their respective directory
	 */
	protected final static ObjectMap<AssetTypeEnum, AssetType> _ASSETS_TYPES;
	static
	{
		_ASSETS_TYPES = new ObjectMap<AssetTypeEnum, AssetType>(5);
		_ASSETS_TYPES.put(AssetTypeEnum.music, new AssetType<Music>(Music.class, "musics/"));
		_ASSETS_TYPES.put(AssetTypeEnum.sound, new AssetType<Sound>(Sound.class, "sounds/"));
		_ASSETS_TYPES.put(AssetTypeEnum.texture, new AssetType<Texture>(Texture.class, "textures/"));
		_ASSETS_TYPES.put(AssetTypeEnum.shader, new AssetType<ShaderProgram>(ShaderProgram.class, "shaders/"));
		_ASSETS_TYPES.put(AssetTypeEnum.font, new AssetType<FreeTypeFontGenerator>(FreeTypeFontGenerator.class, "fonts/"));
		_ASSETS_TYPES.put(AssetTypeEnum.model, new AssetType<Model>(Model.class, "models/"));
	}

	protected static class AssetType<T>
	{
		AssetType(Class<T> assetClass, String directory)
		{
			this.assetClass = assetClass;
			this.directory = directory;
		}

		public String directory;
		public Class<T> assetClass;
	}

	public enum AssetTypeEnum
	{
		music,
		sound,
		shader,
		texture,
		font,
		model
	}
}
