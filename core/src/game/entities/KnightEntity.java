package game.entities;

import game.components.PlayerComponent;
import game.dataAccessLayer.AssetsHandler;
import game.utils.Position;

public class KnightEntity extends Entity
{
	public KnightEntity(String name, Position position, AssetsHandler assetsHndlr) {
		super(name, position, assetsHndlr);
	}
	
}
