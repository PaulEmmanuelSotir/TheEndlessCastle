package game.entities;

import java.util.ArrayList;
import java.util.List;

import aurelienribon.bodyeditor.BodyEditorDAL;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Pool.Poolable;

import game.Segment.SegmentDescriptor;
import game.Segment.SegmentTypeEnum;
import game.components.BodyComponent;
import game.components.SpriteComponent;
import game.dataAccessLayer.AssetsHandler;
import game.utils.Position;

/**
 * Projectile entity
 */
public class ProjectileEntity extends PhysicalEntity implements Poolable
{
	public ProjectileEntity(String name, final ProjectileDescriptor desc, final AssetsHandler assetsHndlr, final BodyEditorDAL bodyDAL, final World box2DWorld) {
		super(name, new Position(0, 0), assetsHndlr, bodyDAL, box2DWorld);
		_descriptor = desc;
		_alive = false;
		SetSprite(desc.TextureName, desc.Scale);
		SetBody(desc.BodyName);
	}

	/**
	 * Initialize projectile state
	 */
	public void Init(Position position, float angle)
	{
		_position = position;
		_angle = angle;
		_bodyCompo.getBody().setActive(true);
		_bodyCompo.getBody().setTransform(position, (float)Math.toRadians(angle));
		_alive = true;
	}
	
	/**
	 * Reset projectile state so that it can be reused in a pool of projectiles, but here we keep ProjectileDescriptor :)
	 */
	@Override
	public void reset() {
		_alive = false;
		_bodyCompo.getBody().setActive(false);
		_position.set(0, 0);
	}
	
	public boolean IsAlive() {
		return _alive;
	}
	
	public ProjectileDescriptor GetDescriptor() {
		return _descriptor;
	}
	
	@Override
	public boolean IsUsingSpriteBatch() {
		return true;
	}
	
	/**
	 * Set sprite from texture name
	 * @param textureName Platform texture name
	 * @return Newly created Sprite component
	 */
	private SpriteComponent SetSprite(String textureName, float scale)
	{
		_spriteComponent = new SpriteComponent(getName() + "_SpriteComponent", this, _assetsHndlr.<Texture>get(textureName));
		addComponent(_spriteComponent);
		_spriteComponent.SetScale(scale);
		return _spriteComponent;
	}
	
	private BodyComponent SetBody(String name)
	{
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(_position.x, _position.y);
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.fixedRotation = _descriptor.FixedRotation;
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = _descriptor.Density;
		fixtureDef.friction = 0.7f;
		fixtureDef.restitution = 0f;
		_bodyCompo = new BodyComponent(name, this, _box2DWorld, _bodyDAL, bodyDef, fixtureDef);
		_bodyCompo.getBody().setActive(false);
		addComponent(_bodyCompo);
		return _bodyCompo;
	}	
	
	private final ProjectileDescriptor _descriptor;
	private SpriteComponent _spriteComponent;
	private BodyComponent _bodyCompo;
	private boolean _alive;

	/**
	 * Available projectiles
	 */
	public final static List<ProjectileDescriptor> _FALLING_PROJECTILES_TYPE_LIST;
	static {
		_FALLING_PROJECTILES_TYPE_LIST = new ArrayList<ProjectileDescriptor>(3);
		_FALLING_PROJECTILES_TYPE_LIST.add(new ProjectileDescriptor("ArrowVObstacleTexture", "arrowV_body", 0.002f, ProjectileTypeEnum.falling, 0.9f, true));
		_FALLING_PROJECTILES_TYPE_LIST.add(new ProjectileDescriptor("ArrowVObstacleTexture", "arrowV_body", 0.002f, ProjectileTypeEnum.falling, 0.9f, true));
		_FALLING_PROJECTILES_TYPE_LIST.add(new ProjectileDescriptor("ArrowVObstacleTexture", "arrowV_body", 0.002f, ProjectileTypeEnum.falling, 0.9f, true));
		_FALLING_PROJECTILES_TYPE_LIST.add(new ProjectileDescriptor("StoneObstacleTexture", "stone_body", 0.003f, ProjectileTypeEnum.falling, 2f, false));
		_FALLING_PROJECTILES_TYPE_LIST.add(new ProjectileDescriptor("StoneObstacleTexture", "stone_body", 0.003f, ProjectileTypeEnum.falling, 2f, false));
		_FALLING_PROJECTILES_TYPE_LIST.add(new ProjectileDescriptor("PieuObstacleTexture", "pieu_body", 0.0075f, ProjectileTypeEnum.falling, 0.7f, true));
	}
	public final static List<ProjectileDescriptor> _HORIZONTAL_PROJECTILES_TYPE_LIST;
	static {
		_HORIZONTAL_PROJECTILES_TYPE_LIST = new ArrayList<ProjectileDescriptor>(2);
		_HORIZONTAL_PROJECTILES_TYPE_LIST.add(new ProjectileDescriptor("ArrowHObstacleTexture", "arrowH_body", 0.001f, ProjectileTypeEnum.highHorinzontal, 0.9f, true));
		_HORIZONTAL_PROJECTILES_TYPE_LIST.add(new ProjectileDescriptor("ArrowHObstacleTexture", "arrowH_body", 0.001f, ProjectileTypeEnum.lowHorinzontal, 0.9f, true));
	}
	
	/**
	 * Projectile direction type enumeration
	 */
	public static enum ProjectileTypeEnum
	{
		falling,
		highHorinzontal,
		lowHorinzontal
	}
	
	public static class ProjectileDescriptor
	{
		/**
		 * Projectile descriptor constructor
		 */
		public ProjectileDescriptor(String textureName, String bodyName, float textureScale, ProjectileTypeEnum projectileType, float density, boolean fixedRotation)
		{
			TextureName = textureName;
			BodyName = bodyName;
			ProjectileType = projectileType;
			Scale = textureScale;
			FixedRotation = fixedRotation;
			Density = density;
		}

		public String TextureName;
		public String BodyName;		
		public float Scale;
		public float Density;
		public ProjectileTypeEnum ProjectileType;
		public boolean FixedRotation;
	}
}
