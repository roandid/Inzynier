package com.inzynier.game.strategy.fight;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.inzynier.game.entities.FighterInterface;
import com.inzynier.game.generator.BulletGenerator;
import com.inzynier.game.strategy.FightStrategyInterface;

public class QuadraShootStrategy implements FightStrategyInterface {

    protected BulletGenerator bulletGenerator;

    public QuadraShootStrategy() {
        this.bulletGenerator = BulletGenerator.getGenerator();
    }

    @Override
    public void fight(FighterInterface fighter, float dt, World world) {

        this.bulletGenerator.generate(fighter, world, new Vector2(0, fighter.getRangedPower()));
        this.bulletGenerator.generate(fighter, world, new Vector2(0, -fighter.getRangedPower()));
        this.bulletGenerator.generate(fighter, world, new Vector2(-fighter.getRangedPower(), 0));
        this.bulletGenerator.generate(fighter, world, new Vector2(fighter.getRangedPower(), 0));
    }
}
