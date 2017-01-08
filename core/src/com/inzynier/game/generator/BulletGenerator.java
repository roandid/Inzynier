package com.inzynier.game.generator;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.inzynier.game.Constants;
import com.inzynier.game.entities.Bullet;
import com.inzynier.game.entities.FighterInterface;

public class BulletGenerator {

    protected static BulletGenerator instance;

    public static BulletGenerator getGenerator() {
        if (!(BulletGenerator.instance instanceof BulletGenerator)) {
            BulletGenerator.instance = new BulletGenerator();
        }

        return BulletGenerator.instance;
    }

    public void generate(FighterInterface fighter, World world, Vector2 force) {
        Body body = world.createBody(this.createBodyDef(fighter));
        body.createFixture(this.createFixtureDef(fighter));
        body.setUserData(new Bullet(body, force, fighter.getRangedWeaponTexture(), fighter));
    }

    protected BodyDef createBodyDef(FighterInterface fighter) {
        BodyDef bodyDef = new BodyDef();

        bodyDef.position.set(fighter.getPosition().x, fighter.getPosition().y);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.linearDamping = 3.5f;
        bodyDef.bullet = true;

        return bodyDef;
    }

    protected FixtureDef createFixtureDef(FighterInterface fighter) {
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        shape.setAsBox(Constants.toBox2d(fighter.getRangedWeaponTexture().getWidth()), Constants.toBox2d(fighter.getRangedWeaponTexture().getHeight()));
        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = Constants.BIT_PLAYER;
        fixtureDef.filter.maskBits = Constants.BIT_WALL;

        return fixtureDef;
    }
}
