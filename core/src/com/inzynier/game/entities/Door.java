package com.inzynier.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.inzynier.game.Constants;

public class Door implements DrawableInterface {

    public enum Position {
        NORTH, SOUTH, EAST, WEST
    }

    protected boolean isOpen;
    protected Texture open;
    protected Texture close;
    protected Body body;
    protected Position doorPosition;

    public Door(Body body, Texture open, Texture close, Position doorPosition) {
        this.body = body;
        this.open = open;
        this.close = close;
        this.doorPosition = doorPosition;
    }

    @Override
    public void update(float dt, World world) {
        if (this.isOpen) {
            return;
        }

        Array<Body> array = new Array<Body>();
        world.getBodies(array);

        for (Body body : array) {
            if (body.getUserData() instanceof Actor) {
                Actor actor = (Actor) body.getUserData();

                if (!actor.isPlayer()) {
                    return;
                }
            }
        }

        this.isOpen = true;
    }

    @Override
    public void draw(float dt, SpriteBatch sb) {
        Vector2 position = this.body.getTransform().getPosition();

        Texture texture = this.isOpen ? this.open : this.close;

        sb.draw(
            texture,
            Constants.fromBox2d(position.x) - texture.getWidth() * 0.25f,
            Constants.fromBox2d(position.y) - texture.getHeight() * 0.1255f
        );
    }

    public Position getPosition() {
        return this.doorPosition;
    }

    public boolean isOpen() {
        return this.isOpen;
    }
}
