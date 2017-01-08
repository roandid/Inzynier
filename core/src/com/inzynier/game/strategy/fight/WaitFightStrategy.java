package com.inzynier.game.strategy.fight;

import com.badlogic.gdx.physics.box2d.World;
import com.inzynier.game.entities.FighterInterface;
import com.inzynier.game.strategy.FightStrategyInterface;

public class WaitFightStrategy implements FightStrategyInterface {

    protected float time;
    protected FightStrategyInterface next;
    protected float sum;

    public WaitFightStrategy(FightStrategyInterface next, float time) {
        this.next = next;
        this.time = time;
        this.sum = 0;
    }

    @Override
    public void fight(FighterInterface fighter, float dt, World world) {
        this.sum += dt;

        if (this.sum < this.time) {
            return;
        }

        this.next.fight(fighter, dt, world);
        this.sum = 0;
    }
}
