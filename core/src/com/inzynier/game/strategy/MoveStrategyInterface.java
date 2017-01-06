package com.inzynier.game.strategy;

import com.badlogic.gdx.physics.box2d.World;
import com.inzynier.game.entities.MovableInterface;

public interface MoveStrategyInterface {

    public void move(MovableInterface character, float dt, World world);
}
