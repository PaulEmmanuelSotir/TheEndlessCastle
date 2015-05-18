package game.entities;

import game.components.PlayerComponent;
import game.dataAccessLayer.AssetsHandler;
import game.utils.Position;

public class KnightEntity extends Entity
{
	
	protected KnightEntity(String name, Position position, AssetsHandler assetsHndlr) {
		super(name, position, assetsHndlr);
		addComponent(new PlayerComponent(_PLAYERCOMPONENTNAME, this));
	}
	
	private final String _PLAYERCOMPONENTNAME = getName() + "_PlayerComponent";
}
