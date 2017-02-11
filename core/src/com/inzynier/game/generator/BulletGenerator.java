package com.inzynier.game.generator;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.inzynier.game.Constants;
import com.inzynier.game.entities.Actor;
import com.inzynier.game.entities.Bullet;

public class BulletGenerator {

    protected static BulletGenerator instance;

    public static BulletGenerator getGenerator() {
        if (!(BulletGenerator.instance instanceof BulletGenerator)) {
            BulletGenerator.instance = new BulletGenerator();
        }

        return BulletGenerator.instance;
    }

    public void generate(Actor actor, World world, Vector2 force) {
        Body body = world.createBody(this.createBodyDef(actor));
        body.createFixture(this.createFixtureDef(actor));
        body.setUserData(new Bullet(body, force, actor.getRangedWeaponTexture(), actor));
    }

    protected BodyDef createBodyDef(Actor fighter) {
        BodyDef bodyDef = new BodyDef();

        bodyDef.position.set(fighter.getPosition().x, fighter.getPosition().y);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.linearDamping = 3.5f;
        bodyDef.bullet = true;

        return bodyDef;
    }

    protected FixtureDef createFixtureDef(Actor fighter) {
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        short masks = (short) (fighter.isPlayer() ? (Constants.BIT_LEGO | Constants.BIT_WALL_BULLET | Constants.BIT_ENEMY) : (Constants.BIT_LEGO | Constants.BIT_WALL_BULLET | Constants.BIT_PLAYER));

        shape.setAsBox(Constants.toBox2d(fighter.getRangedWeaponTexture().getWidth()), Constants.toBox2d(fighter.getRangedWeaponTexture().getHeight()));
        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = Constants.BIT_BULLET;
        fixtureDef.filter.maskBits = masks;

        return fixtureDef;
    }
}
