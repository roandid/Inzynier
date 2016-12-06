package com.inzynier.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.inzynier.game.Animation;
import com.inzynier.game.Constants;

//wspolna klasa dla animowanych obiekt�w
public class B2DSprite {

    protected Body body;
    protected Animation animation;
    protected float width;
    protected float height;

    public B2DSprite(Body body) {
        this.body = body;
        animation = new Animation();
    }

    public void setAnimation(TextureRegion[] region, float delay) {
        animation.setFrames(region, delay);
        width = region[0].getRegionWidth();
        height = region[0].getRegionHeight();
    }

    public void update(float dt) {
        animation.update(dt);

    }

    public void render(SpriteBatch sb) {

        sb.begin();
        sb.draw(animation.getFrame(), Constants.fromBox2d(body.getPosition().x) - width / 2, Constants.fromBox2d(body.getPosition().y) - height / 2 + 20);
        sb.end();
    }

    public Body getBody() {
        return body;
    }

    public Vector2 getPosition() {
        return body.getPosition();
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

}
