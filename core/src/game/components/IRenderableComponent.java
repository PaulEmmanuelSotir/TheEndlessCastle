package game.components;

import game.utils.Position;

/**
 * Renderable component interface
 * T must be a ModelBatch or a Batch
 */
public interface IRenderableComponent<T>
{
	/**
	 * Renders component
	 * @param batch batch used to render component
	 */
	public void render(T batch);

	public void SetRelativePosition(Position pos);
	public Position GetRelativePosition();
}
