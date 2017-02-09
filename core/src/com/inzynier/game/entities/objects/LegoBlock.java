package com.inzynier.game.entities.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.inzynier.game.Constants;
import com.inzynier.game.entities.DrawableInterface;

/**
 * Created by Krzysztof on 08.02.2017.
 */
public class LegoBlock extends TiledObject implements DrawableInterface {

    private Texture texture;
    private Body body;

    public LegoBlock(Body body) {
        this.texture = new Texture("klocek.png");
        this.body = body;
    }

    @Override
    public void update(float dt, World world) {

    }

    @Override
    public void draw(float dt, SpriteBatch sb) {
        Vector2 position = this.body.getTransform().getPosition();
        sb.draw(this.texture, Constants.fromBox2d(position.x) - 16, Constants.fromBox2d(position.y) - 16);
    }

    @Override
    public Body getBody() {
        return body;
    }
}
