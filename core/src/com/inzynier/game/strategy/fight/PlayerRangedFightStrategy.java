package com.inzynier.game.strategy.fight;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.inzynier.game.entities.FighterInterface;
import com.inzynier.game.generator.BulletGenerator;
import com.inzynier.game.strategy.FightStrategyInterface;

/**
 * Trzeba zrobić faktorę do bulletów i ją tutaj wstrzyknąć
 */
public class PlayerRangedFightStrategy implements FightStrategyInterface {

    protected BulletGenerator bulletGenerator;

    public PlayerRangedFightStrategy() {
        this.bulletGenerator = BulletGenerator.getGenerator();
    }

    @Override
    public void fight(FighterInterface fighter, float dt, World world) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            this.bulletGenerator.generate(fighter, world, new Vector2(0, fighter.getRangedPower()));
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            this.bulletGenerator.generate(fighter, world, new Vector2(0, -fighter.getRangedPower()));
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            this.bulletGenerator.generate(fighter, world, new Vector2(-fighter.getRangedPower(), 0));
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            this.bulletGenerator.generate(fighter, world, new Vector2(fighter.getRangedPower(), 0));
        }
    }
}
