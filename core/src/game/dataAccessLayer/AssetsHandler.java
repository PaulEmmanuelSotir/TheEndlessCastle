package game.dataAccessLayer;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ObjectMap;

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
		_assetsManager.setLoader(Shader.class, new ShaderLoader(new InternalFileHandleResolver()));
		_assetsManager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(new InternalFileHandleResolver()));
		
		_assets = new HashMap<String, TypedAssetDescriptor>();
		//_assetsByType = new EnumMap<TypedAssetDescriptor.AssetTypeEnum, ArrayList<TypedAssetDescriptor>>(TypedAssetDescriptor.AssetTypeEnum.class);
		//for(TypedAssetDescriptor.AssetTypeEnum type : TypedAssetDescriptor.AssetTypeEnum.values())
		//	_assetsByType.put(type, new ArrayList<TypedAssetDescriptor>());

		// XML assets list parsing (TODO: do it in background thread?)
		try
		{
			DocumentBuilder DBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document AssetsDoc = DBuilder.parse(Gdx.files.internal(AssetsListFileName).file());
			AssetsDoc.getDocumentElement().normalize();

			// TODO: take resolution into account
			NodeList IconNodes =  AssetsDoc.getElementsByTagName(_ICON_TAG_NAME);
			if(IconNodes.getLength() > 0)
				_iconPath = IconNodes.item(0).getTextContent();

			NodeList AssetsOwnerNodes = AssetsDoc.getElementsByTagName(_ASSETS_TAG_NAME);
			if(AssetsOwnerNodes.getLength() > 0)
			{
				NodeList AssetsNodes = AssetsOwnerNodes.item(0).getChildNodes();

				// Load assets and store their description
				// TODO: store licensing information
				for (int i = 0; i < AssetsNodes.getLength(); i++) {
					Node AssetNode = AssetsNodes.item(i);
					if(AssetNode.getNodeType() == Node.ELEMENT_NODE) {
						Element AssetElement = (Element)AssetNode;
						TypedAssetDescriptor assetDescriptor = new TypedAssetDescriptor(AssetElement.getAttribute(_NAME_ATTRIBUTE), AssetElement.getTextContent(), TypedAssetDescriptor.AssetTypeEnum.valueOf(AssetElement.getTagName()));
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
	protected Map<String, TypedAssetDescriptor> _assets;
	//protected EnumMap<TypedAssetDescriptor.AssetTypeEnum, ArrayList<TypedAssetDescriptor>> _assetsByType;
	protected String _iconPath;

	protected final static String _NAME_ATTRIBUTE = "Name";
	protected final static String _ASSETS_TAG_NAME = "Assets";
	protected final static String _ICON_TAG_NAME = "icon";

	public enum resolution
	{
		low,
		medium,
		high
	}
}
