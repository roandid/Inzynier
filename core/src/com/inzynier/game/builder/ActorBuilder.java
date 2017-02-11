package com.inzynier.game.builder;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.inzynier.game.Constants;
import com.inzynier.game.entities.Actor;
import com.inzynier.game.entities.AnimationsStorage;
import com.inzynier.game.strategy.DummyStrategy;
import com.inzynier.game.strategy.StrategyInterface;

public class ActorBuilder {

    protected boolean isPlayer;
    protected float health;
    protected float rangedPower;
    protected float contactPower;
    protected String atlasPath;
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
        this.fixtureDef.filter.categoryBits = Constants.BIT_PLAYER;

        return this;
    }

    public ActorBuilder setHealth(float health) {
        this.health = health;

        return this;
    }

    public ActorBuilder setRangedPower(float power) {
        this.rangedPower = power;

        return this;
    }

    public ActorBuilder setContactPower(float power) {
        this.contactPower = power;

        return this;
    }

    public ActorBuilder setAtlasPath(String path) {
        this.atlasPath = path;

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
            new AnimationsStorage(new TextureAtlas(this.atlasPath)),
            this.stragegy,
            this.bodyDef,
            this.fixtureDef,
            this.bulletTexture,
            this.rangedPower,
            this.contactPower
        );

        this.reset();

        return actor;
    }

    public ActorBuilder setSize(int x, int y) {
        ((PolygonShape) this.fixtureDef.shape).setAsBox(Constants.toBox2d(x), Constants.toBox2d(y));

        return this;
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
        fixtureDef.filter.categoryBits = Constants.BIT_ENEMY;
        fixtureDef.filter.maskBits = Constants.BIT_WALL_PLAYER | Constants.BIT_BULLET | Constants.BIT_LEGO | Constants.BIT_ENEMY | Constants.BIT_PLAYER;

        return fixtureDef;
    }

    private void reset() {
        this.isPlayer = false;
        this.health = 1000;
        this.rangedPower = 0;
        this.contactPower = 0;
        this.bodyDef = this.createDefaultBodyDef();
        this.fixtureDef = this.createDefaultFixtureDef();
        this.stragegy = new DummyStrategy();
    }
}
