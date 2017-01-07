package com.inzynier.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.inzynier.game.Constants;
import com.inzynier.game.strategy.FightStrategyInterface;
import com.inzynier.game.strategy.MoveStrategyInterface;
import com.inzynier.game.strategy.fight.PlayerRangedFightStrategy;
import com.inzynier.game.strategy.move.PlayerMoveStrategy;

public class Player implements DrawableInterface, MovableInterface, FighterInterface {

    protected Texture player;
    protected Texture bullet;
    protected Body body;
    protected BodyDef bodyDef;
    protected FixtureDef fixtureDef;
    protected MoveStrategyInterface moveStrategy;
    protected FightStrategyInterface fightStrategy;

    public Player() {
        this.player = new Texture("issac.png");
        this.bullet = new Texture("tear.png");
        this.bodyDef = new BodyDef();
        this.fixtureDef = new FixtureDef();
        this.moveStrategy = new PlayerMoveStrategy();
        this.fightStrategy = new PlayerRangedFightStrategy();

        this.createBodyDef();
        this.createFixtureDef();
    }

    public void createBody(World world) {
        this.body = world.createBody(this.bodyDef);
        this.body.createFixture(this.fixtureDef);
        this.body.setUserData(this);
    }

    public void setPosition(Vector2 position) {
        this.bodyDef.position.x = position.x;
        this.bodyDef.position.y = position.y;
    }

    @Override
    public void update(float dt, World world) {
        this.moveStrategy.move(this, dt, world);
        this.fightStrategy.fight(this, world);
    }

    @Override
    public void draw(SpriteBatch sb) {
        Vector2 position = this.body.getTransform().getPosition();

        sb.draw(this.player, Constants.fromBox2d(position.x) - this.player.getWidth() / 2 + 15, Constants.fromBox2d(position.y) - this.player.getHeight() / 2 + 35);
    }

    @Override
    public Body getBody() {
        return this.body;
    }

    @Override
    public float getRangedPower() {
        return 200;
    }

    @Override
    public Texture getRangedWeaponTexture() {
        return this.bullet;
    }

    @Override
    public Vector2 getPosition() {
        return this.body.getPosition();
    }

    /**
     * Do przeniesienia do faktorki, pewnie wrogowie beda mieli coś podobnego
     */
    protected void createBodyDef() {
        this.bodyDef.type = BodyType.DynamicBody;
        this.bodyDef.linearDamping = 20.5f;
    }

    /**
     * Do przeniesienia do faktorki, pewnie wrogowie beda mieli coś podobnego
     */
    protected void createFixtureDef() {
        PolygonShape shape = new PolygonShape();

        shape.setAsBox(Constants.toBox2d(14), Constants.toBox2d(12));
        this.fixtureDef.shape = shape;
        this.fixtureDef.friction = 0.5f;
        this.fixtureDef.filter.categoryBits = Constants.BIT_PLAYER;
        this.fixtureDef.filter.maskBits = Constants.BIT_WALL;
    }
}
