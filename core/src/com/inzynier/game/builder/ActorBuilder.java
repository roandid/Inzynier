package com.inzynier.game.builder;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.inzynier.game.Constants;
import com.inzynier.game.entities.Actor;
import com.inzynier.game.strategy.DummyStrategy;
import com.inzynier.game.strategy.StrategyInterface;

public class ActorBuilder {

    protected boolean isPlayer;
    protected float health;
    protected float power;
    protected Texture mainTexture;
    protected Texture bulletTexture;
    protected BodyDef bodyDef;
    protected FixtureDef fixtureDef;
    protected StrategyInterface stragegy;

    private ActorBuilder() {
        this.reset();
    }

    public static ActorBuilder getActorBuilder() {
        return new ActorBuilder();
    }

    public ActorBuilder setIsPlayer(boolean isPlayer) {
        this.isPlayer = isPlayer;

        return this;
    }

    public ActorBuilder setHealth(float health) {
        this.health = health;

        return this;
    }

    public ActorBuilder setPower(float power) {
        this.power = power;

        return this;
    }

    public ActorBuilder setMainTexture(Texture mainTexture) {
        this.mainTexture = mainTexture;

        return this;
    }

    public ActorBuilder setBulletTexture(Texture bulletTexture) {
        this.bulletTexture = bulletTexture;

        return this;
    }

    public ActorBuilder setBodyDef(BodyDef bodyDef) {
        this.bodyDef = bodyDef;

        return this;
    }

    public ActorBuilder setFixtureDef(FixtureDef fixtureDef) {
        this.fixtureDef = fixtureDef;

        return this;
    }

    public ActorBuilder setStragegy(StrategyInterface stragegy) {
        this.stragegy = stragegy;

        return this;
    }

    public Actor build() {
        Actor actor = new Actor(
                this.isPlayer,
                this.health,
                this.mainTexture,
                this.stragegy,
                this.bodyDef,
                this.fixtureDef,
                this.bulletTexture,
                this.power
        );

        this.reset();

        return actor;
    }

    private BodyDef createDefaultBodyDef() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.linearDamping = 20.5f;

        return bodyDef;
    }

    private FixtureDef createDefaultFixtureDef() {
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        shape.setAsBox(Constants.toBox2d(14), Constants.toBox2d(12));
        fixtureDef.shape = shape;
        fixtureDef.friction = 0.5f;
        fixtureDef.filter.categoryBits = Constants.BIT_PLAYER;
        fixtureDef.filter.maskBits = Constants.BIT_WALL;

        return fixtureDef;
    }

    private void reset() {
        this.isPlayer = false;
        this.health = 1000;
        this.power = 0;
        this.bodyDef = this.createDefaultBodyDef();
        this.fixtureDef = this.createDefaultFixtureDef();
        this.stragegy = new DummyStrategy();
    }
}
