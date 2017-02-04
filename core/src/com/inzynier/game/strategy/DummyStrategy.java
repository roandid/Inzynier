package com.inzynier.game.strategy;

import com.badlogic.gdx.physics.box2d.World;
import com.inzynier.game.entities.Actor;

public class DummyStrategy implements StrategyInterface {

    @Override
    public void action(Actor actor, float dt, World world) {
        // Dummy strategy, just don't do anything
    }
}
