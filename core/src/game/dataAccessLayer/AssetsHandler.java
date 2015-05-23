package game.dataAccessLayer;

import game.dataAccessLayer.ShaderLoader.ShaderParameter;
import game.dataAccessLayer.TypedAssetDescriptor.AssetTypeEnum;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.UBJsonReader;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class AssetsHandler implements Disposable
{
	/**
	 * AssetsHandler constructor
	 * @param AssetsListFileName The name of the XML file listing all assets to be loaded by assets handler.
	 */
	public AssetsHandler(String AssetsListFileName) {
		Load(AssetsListFileName);
	}

	/**
	 * Loads all listed assets in given XML file.
	 * @param AssetsListFileName The name of the XML file listing all assets to be loaded by assets handler.
	 */
	public void Load(String AssetsListFileName)
	{
		_assetsManager = new AssetManager();
		_assetsManager.setLoader(ShaderProgram.class, new ShaderLoader(new InternalFileHandleResolver()));
		_assetsManager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(new InternalFileHandleResolver()));
		
		_assets = new HashMap<String, TypedAssetDescriptor>();
		//_assetsByType = new EnumMap<TypedAssetDescriptor.AssetTypeEnum, ArrayList<TypedAssetDescriptor>>(TypedAssetDescriptor.AssetTypeEnum.class);
		//for(TypedAssetDescriptor.AssetTypeEnum type : TypedAssetDescriptor.AssetTypeEnum.values())
		//	_assetsByType.put(type, new ArrayList<TypedAssetDescriptor>());

		// XML assets list parsing (TODO: do it in background thread?)
		try
		{
			DocumentBuilder DBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document AssetsDoc = DBuilder.parse(Gdx.files.internal(AssetsListFileName).path());
			AssetsDoc.getDocumentElement().normalize();

			// Logo
			// TODO: take resolution into account
			NodeList IconNodes =  AssetsDoc.getElementsByTagName(_ICON_TAG_NAME);
			if(IconNodes.getLength() > 0)
				_iconPath = IconNodes.item(0).getTextContent();
			
			// Licenses
			NodeList LicenseNodes =  AssetsDoc.getElementsByTagName(_LICENSE_TAG_NAME);
			_licenses = new ObjectMap<String, String>(LicenseNodes.getLength());
			for(int idx = 0; idx < LicenseNodes.getLength(); idx++)
			{
				Element LicenseNode = (Element)LicenseNodes.item(idx);
				_licenses.put(LicenseNode.getAttribute(_NAME_ATTRIBUTE), LicenseNode.getTextContent());
			}

			// Assets
			NodeList AssetsOwnerNodes = AssetsDoc.getElementsByTagName(_ASSETS_TAG_NAME);
			if(AssetsOwnerNodes.getLength() > 0)
			{
				NodeList AssetsNodes = AssetsOwnerNodes.item(0).getChildNodes();

				// Load assets and store their description
				for (int i = 0; i < AssetsNodes.getLength(); i++) {
					Node AssetNode = AssetsNodes.item(i);
					if(AssetNode.getNodeType() == Node.ELEMENT_NODE) {
						Element AssetElement = (Element)AssetNode;
						TypedAssetDescriptor assetDescriptor;
						AssetTypeEnum type = TypedAssetDescriptor.AssetTypeEnum.valueOf(AssetElement.getTagName());
						
						AssetLicense AssetLicense;
						String LicenseName = AssetElement.getAttribute(_LICENSE_ATTRIBUTE_NAME);
						String Credits = AssetElement.getAttribute(_CREDITS_TEXT_ATTRIBUTE_NAME);
						if(Credits != null && Credits != "")
							AssetLicense = new AssetLicense(LicenseName, _licenses.get(LicenseName), Credits);
						else
							AssetLicense = new AssetLicense(LicenseName, _licenses.get(LicenseName));
						
						switch(type)
						{
						case texture:
							// Loads texture so that it is filtered correctly
							TextureParameter TextureParam = new TextureParameter();
							TextureParam.minFilter = TextureFilter.MipMapLinearLinear;
							TextureParam.magFilter = TextureFilter.Linear;
							TextureParam.genMipMaps = true;
							assetDescriptor = new TypedAssetDescriptor(AssetElement.getAttribute(_NAME_ATTRIBUTE), AssetElement.getTextContent(), type, TextureParam, AssetLicense);
							break;
						case shader:
							ShaderParameter shaderParam = new ShaderParameter();
							shaderParam.VertexShaderPath = TypedAssetDescriptor._ASSETS_TYPES.get(AssetTypeEnum.shader).directory + AssetElement.getAttribute(_SHADER_VERTEX_ATTRIBUTE_NAME);
							assetDescriptor = new TypedAssetDescriptor(AssetElement.getAttribute(_NAME_ATTRIBUTE), AssetElement.getTextContent(), type, shaderParam, AssetLicense);
							break;
						default:
							assetDescriptor = new TypedAssetDescriptor(AssetElement.getAttribute(_NAME_ATTRIBUTE), AssetElement.getTextContent(), type, AssetLicense);
						}

						_assetsManager.load(assetDescriptor);
						_assets.put(assetDescriptor.AssetName, assetDescriptor);
						//_assetsByType.get(assetDescriptor.AssetType).add(assetDescriptor);
					}				
				}
			}
			else
			{
				// TODO: show error message
			}
		}
		catch (Exception e){
			e.printStackTrace();
			// TODO: show error message
		}
	}

	public AssetManager GetAssetsManager()
	{
		return _assetsManager;
	}

	// TODO: être sûr qu'il faut synchroniser ici
	public synchronized String GetAssetPath(String name)
	{
		return _assets.get(name).fileName;
	}
	
	public ArrayList<TypedAssetDescriptor> GetAssetsDescriptors()
	{
		return new ArrayList<TypedAssetDescriptor>(_assets.values());
	}

	public String GetIconPath(resolution res)
	{
		return _iconPath;
	}

	public FileType GetFilesPathType()
	{
		return FileType.Internal;
	}

	/**
	 * Updates the AssetManager, keeping it loading any assets in the preload queue. (AssetManager.update warper)
	 */
	public Boolean update()
	{
		return _assetsManager.update();
	}

	/**
	 * Unload specified asset (AssetManager.unload warper)
	 */
	public void unload(String name)
	{
		_assetsManager.unload(GetAssetPath(name));
	}

	/**
	 * Return the asset corresponding to the given name (AssetManager.get warper)
	 */
	public <T> T get(String name)
	{
		return _assetsManager.get(GetAssetPath(name));
	}

	public <T> Array<T> get(TypedAssetDescriptor.AssetTypeEnum assetsType)
	{
		Array<T> RsltAssets = new Array<T>();
		_assetsManager.getAll(TypedAssetDescriptor.<T>getAssetClass(assetsType), RsltAssets);
		return RsltAssets;
	}
	
	/**
	 * Clears stored assets. (You will need to call 'Load' before to use assetsHandler again)
	 */
	public void clear()
	{
		_assets.clear();
		_assets = null;
		_iconPath = "";
		_assetsManager.clear();
		_assetsManager.dispose();
		_assetsManager = null;
	}

	@Override
	public void dispose() {
		clear();
	}

	protected AssetManager _assetsManager;
	protected ObjectMap<String, String> _licenses;
	protected Map<String, TypedAssetDescriptor> _assets;
	//protected EnumMap<TypedAssetDescriptor.AssetTypeEnum, ArrayList<TypedAssetDescriptor>> _assetsByType;
	protected String _iconPath;

	protected final static String _NAME_ATTRIBUTE = "Name";
	protected final static String _CREDITS_TEXT_ATTRIBUTE_NAME = "CreditsText";
	protected final static String _LICENSE_ATTRIBUTE_NAME = "License";
	protected final static String _LICENSE_TAG_NAME = "License";
	protected final static String _ASSETS_TAG_NAME = "Assets";
	protected final static String _ICON_TAG_NAME = "icon";
	protected final static String _SHADER_VERTEX_ATTRIBUTE_NAME = "VertexShaderFileName";
	
	public enum resolution
	{
		low,
		medium,
		high
	}
}
