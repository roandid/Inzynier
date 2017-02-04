package com.inzynier.game.strategy;

import com.badlogic.gdx.physics.box2d.World;
import com.inzynier.game.entities.Actor;

public class DoubleStrategy implements StrategyInterface {

    protected StrategyInterface first;
    protected StrategyInterface second;

    public DoubleStrategy(StrategyInterface first, StrategyInterface second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public void action(Actor actor, float dt, World world) {
        this.first.action(actor, dt, world);
        this.second.action(actor, dt, world);
    }
}
