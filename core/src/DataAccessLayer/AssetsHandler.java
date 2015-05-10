package DataAccessLayer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ObjectMap;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.io.File;

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
		_assets = new ObjectMap<String, TypedAssetDescriptor>();

		_assetsManager.setLoader(Shader.class, new ShaderLoader(new InternalFileHandleResolver()));
		_assetsManager.setLoader(Font.class, new FontLoader(new InternalFileHandleResolver()));

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
	protected ObjectMap<String, TypedAssetDescriptor> _assets;
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
