package com.inzynier.game.strategy;

import com.badlogic.gdx.physics.box2d.World;
import com.inzynier.game.entities.Actor;

public class WaitStrategy implements StrategyInterface {

    protected float time;
    protected float sum;
    protected StrategyInterface next;

    public WaitStrategy(StrategyInterface next, float time) {
        this.next = next;
        this.time = time;
        this.sum = 0;
    }

    @Override
    public void action(Actor actor, float dt, World world) {
        this.sum += dt;

        if (this.sum < this.time) {
            return;
        }

        this.next.action(actor, dt, world);
        this.sum = 0;
    }
}
