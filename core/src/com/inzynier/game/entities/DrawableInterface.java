package com.inzynier.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;

public interface DrawableInterface {

    public void update(float dt, World world);

    public void draw(float dt, SpriteBatch sb);
}
