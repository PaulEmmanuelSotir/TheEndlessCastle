package game;

import game.dataAccessLayer.AssetsHandler;
import game.entities.Entity;
import game.entities.PlatformEntity;
import game.utils.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Segment class representing a part of the platform world
 */
public class Segment
{
	/**
	 * Segment constructor
	 * @param assetsHndlr Assets handler
	 */
	public Segment(String name, Position pos, AssetsHandler assetsHndlr) {
		_name = name;
		_assetsHndlr = assetsHndlr;
		_segmentEntities = new ArrayList<Entity>();
		
		_platformEntity = new PlatformEntity(_name + "_PlatformEntity", pos, assetsHndlr);
		_segmentEntities.add(_platformEntity);
	}
	
	public List<Entity> GetEntities()
	{
		return _segmentEntities;
	}

	private String _name;
	private List<Entity> _segmentEntities;
	private PlatformEntity _platformEntity;
	private AssetsHandler _assetsHndlr;
}
