package com.inzynier.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.inzynier.game.strategy.StrategyInterface;
import static java.lang.Math.abs;

public class Actor implements DrawableInterface {

    private static float minimalVel = 0.1f;
    protected boolean isPlayer;
    protected float power;
    protected float health;
    protected AnimationsStorage animations;
    protected Texture bullet;
    protected Body body;
    protected BodyDef bodyDef;
    protected FixtureDef fixtureDef;
    protected StrategyInterface strategy;

    public Actor(
        boolean isPlayer,
        float health,
        AnimationsStorage animations,
        StrategyInterface strategy,
        BodyDef bodyDef,
        FixtureDef fixtureDef,
        Texture weapon,
        float power
    ) {
        this.isPlayer = isPlayer;
        this.health = health;
        this.animations = animations;
        this.strategy = strategy;
        this.bodyDef = bodyDef;
        this.fixtureDef = fixtureDef;
        this.bullet = weapon;
        this.power = power;
    }

    public Actor createBody(World world) {
        this.body = world.createBody(this.bodyDef);
        this.body.createFixture(this.fixtureDef);
        this.body.setUserData(this);

        return this;
    }

    public Actor setPosition(Vector2 position) {
        this.bodyDef.position.x = position.x;
        this.bodyDef.position.y = position.y;

        return this;
    }

    @Override
    public void update(float dt, World world) {
        this.strategy.action(this, dt, world);

        Vector2 vel = this.body.getLinearVelocity();

        if (abs(vel.x) < Actor.minimalVel && abs(vel.y) < Actor.minimalVel) {
            this.animations.stop();
        }
    }

    @Override
    public void draw(float dt, SpriteBatch sb) {
        Vector2 position = this.body.getTransform().getPosition();

        this.animations.play(dt, sb, position);
    }

    public AnimationsStorage getAnimationsStorage() {
        return this.animations;
    }

    public Body getBody() {
        return this.body;
    }

    public float getRangedPower() {
        return this.power;
    }

    public Texture getRangedWeaponTexture() {
        return this.bullet;
    }

    public Vector2 getPosition() {
        return new Vector2(this.body.getPosition().x, this.body.getPosition().y);
    }

    public float getHealth() {
        return this.health;
    }

    public Actor hit(float power) {
        this.health -= power;

        return this;
    }

    public boolean isDead() {
        return this.health <= 0.0;
    }

    public boolean isPlayer() {
        return this.isPlayer;
    }
}
