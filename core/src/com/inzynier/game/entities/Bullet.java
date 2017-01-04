package com.inzynier.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Może powinien zostać interfejs dla Bulletow ale prpoponuje o tym pomyśleć w
 * przypadku gdy będziemy robić też inny Bullety
 */
public class Bullet implements DrawableInterface {

    protected Body body;
    protected Vector2 force;
    protected Texture texture;
    protected short power;

    /**
     * Pewnie jeszcze powinien być owner, sila i inne takie. Może zamiast
     * konstruktora zrobić gettery i settery?
     */
    public Bullet(Body body, Vector2 force, Texture texture) {
        this.body = body;
        this.force = force;
        this.texture = texture;
    }

    @Override
    public void update(float dt, World world) {
        this.body.applyForceToCenter(this.force, true);
    }

    @Override
    public void draw(SpriteBatch sb) {
        Vector2 position = this.body.getPosition();
        sb.draw(texture, position.x, position.y);
    }
}
