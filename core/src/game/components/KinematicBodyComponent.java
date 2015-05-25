package game.components;

import aurelienribon.bodyeditor.BodyEditorDAL;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import game.entities.Entity;

/**
 * Kinematic body component class
 */
public class KinematicBodyComponent extends BodyComponent
{
	public KinematicBodyComponent(String name, Entity owner, World box2DWorld, BodyEditorDAL bodyDAL, BodyDef bodyDef, FixtureDef fixtureDef, float scale) {
		super(name, owner, box2DWorld, bodyDAL, bodyDef, fixtureDef, scale);
	}
	
	public KinematicBodyComponent(String name, Entity owner, World box2DWorld, BodyEditorDAL bodyDAL, BodyDef bodyDef, FixtureDef fixtureDef) {
		this(name, owner, box2DWorld, bodyDAL, bodyDef, fixtureDef, 1f);
	}
}
