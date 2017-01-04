package com.inzynier.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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

public class Player implements DrawableInterface {

    protected Texture player;
    protected Body body;
    protected BodyDef bodyDef;
    protected FixtureDef fixtureDef;

    public Player() {
        this.player = new Texture("issac.png");
        this.bodyDef = new BodyDef();
        this.fixtureDef = new FixtureDef();

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
        this.move();
        this.shoot(world);
    }

    @Override
    public void draw(SpriteBatch sb) {
        Vector2 position = this.body.getTransform().getPosition();

        sb.draw(this.player, Constants.fromBox2d(position.x) - this.player.getWidth() / 2, Constants.fromBox2d(position.y) - this.player.getHeight() / 2);
    }

    protected void move() {
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            this.body.applyForceToCenter(0, 400, true);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            this.body.applyForceToCenter(0, -400, true);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            this.body.applyForceToCenter(-400, 0, true);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            this.body.applyForceToCenter(400, 0, true);
        }
    }

    protected void shoot(World world) {
//        if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
//            this.bullet = new BasicBullet(this.player.getBody().getPosition().x, this.player.getBody().getPosition().y, InterfaceBullet.LEFT, 1000);
//            this.listPlayerBullets.add(this.bullet.generateBullet(this.world));
//        }
//        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
//            this.bullet = new BasicBullet(this.player.getBody().getPosition().x, this.player.getBody().getPosition().y, InterfaceBullet.UP, 1000);
//            this.listPlayerBullets.add(this.bullet.generateBullet(this.world));
//        }
//        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
//            this.bullet = new BasicBullet(this.player.getBody().getPosition().x, this.player.getBody().getPosition().y, InterfaceBullet.DOWN, 1000);
//            this.listPlayerBullets.add(this.bullet.generateBullet(this.world));
//        }
//        if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
//            this.bullet = new BasicBullet(this.player.getBody().getPosition().x, this.player.getBody().getPosition().y, InterfaceBullet.RIGHT, 1000);
//            this.listPlayerBullets.add(this.bullet.generateBullet(this.world));
//        }
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
