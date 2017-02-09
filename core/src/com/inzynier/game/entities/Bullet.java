package com.inzynier.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.inzynier.game.Constants;

/**
 * Może powinien zostać interfejs dla Bulletow ale prpoponuje o tym pomyśleć w
 * przypadku gdy będziemy robić też inne Bullety
 */
public class Bullet implements DrawableInterface {

    protected Body body;
    protected Vector2 force;
    protected Texture texture;
    protected short power;
    protected Actor owner;

    public Bullet(Body body, Vector2 force, Texture texture, Actor owner) {
        this.body = body;
        this.force = force;
        this.texture = texture;
        this.owner = owner;
    }

    @Override
    public void update(float dt, World world) {
        this.body.applyForceToCenter(this.force, true);
    }

    @Override
    public void draw(float dt, SpriteBatch sb) {
        Vector2 position = this.body.getPosition();
        sb.draw(this.texture, Constants.fromBox2d(position.x) - this.texture.getWidth() / 2 + 15, Constants.fromBox2d(position.y) - this.texture.getHeight() + 25);
    }

    public Actor getOwner() {
        return this.owner;
    }

    public Body getBody() {
        return this.body;
    }
}
