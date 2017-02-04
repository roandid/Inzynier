package com.inzynier.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.inzynier.game.Constants;
import com.inzynier.game.strategy.StrategyInterface;

public class Actor implements DrawableInterface {

    protected boolean isPlayer;
    protected float power;
    protected float health;
    protected Texture player;
    protected Texture bullet;
    protected Body body;
    protected BodyDef bodyDef;
    protected FixtureDef fixtureDef;
    protected StrategyInterface strategy;

    public Actor(
            boolean isPlayer,
            float health,
            Texture character,
            StrategyInterface strategy,
            BodyDef bodyDef,
            FixtureDef fixtureDef,
            Texture weapon,
            float power
    ) {
        this(isPlayer, health, character, strategy, bodyDef, fixtureDef);
        this.bullet = weapon;
        this.power = power;
    }

    public Actor(
            boolean isPlayer,
            float health,
            Texture character,
            StrategyInterface strategy,
            BodyDef bodyDef,
            FixtureDef fixtureDef
    ) {
        this.isPlayer = isPlayer;
        this.health = health;
        this.player = character;
        this.strategy = strategy;
        this.bodyDef = bodyDef;
        this.fixtureDef = fixtureDef;

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
    }

    @Override
    public void draw(SpriteBatch sb) {
        Vector2 position = this.body.getTransform().getPosition();

        sb.draw(
                this.player,
                Constants.fromBox2d(position.x) - this.player.getWidth() / 2 + 15,
                Constants.fromBox2d(position.y) - this.player.getHeight() / 2 + 35
        );
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
