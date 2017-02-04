package com.inzynier.game.strategy;

import com.badlogic.gdx.physics.box2d.World;
import com.inzynier.game.entities.Actor;

public interface StrategyInterface {

    public void action(Actor actor, float dt, World world);
}
