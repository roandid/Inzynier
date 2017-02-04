package com.inzynier.game.strategy.fight;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.inzynier.game.entities.Actor;
import com.inzynier.game.generator.BulletGenerator;
import com.inzynier.game.strategy.StrategyInterface;

public class QuadraShootStrategy implements StrategyInterface {

    protected BulletGenerator bulletGenerator;

    public QuadraShootStrategy() {
        this.bulletGenerator = BulletGenerator.getGenerator();
    }

    @Override
    public void action(Actor actor, float dt, World world) {

        this.bulletGenerator.generate(actor, world, new Vector2(0, actor.getRangedPower()));
        this.bulletGenerator.generate(actor, world, new Vector2(0, -actor.getRangedPower()));
        this.bulletGenerator.generate(actor, world, new Vector2(-actor.getRangedPower(), 0));
        this.bulletGenerator.generate(actor, world, new Vector2(actor.getRangedPower(), 0));
    }
}
