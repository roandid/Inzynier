package com.inzynier.game.strategy.fight;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.inzynier.game.entities.Actor;
import com.inzynier.game.generator.BulletGenerator;
import com.inzynier.game.strategy.StrategyInterface;

/**
 * Trzeba zrobić faktorę do bulletów i ją tutaj wstrzyknąć
 */
public class PlayerRangedFightStrategy implements StrategyInterface {

    protected BulletGenerator bulletGenerator;

    public PlayerRangedFightStrategy() {
        this.bulletGenerator = BulletGenerator.getGenerator();
    }

    @Override
    public void action(Actor actor, float dt, World world) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            this.bulletGenerator.generate(actor, world, new Vector2(0, actor.getRangedPower()));
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            this.bulletGenerator.generate(actor, world, new Vector2(0, -actor.getRangedPower()));
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            this.bulletGenerator.generate(actor, world, new Vector2(-actor.getRangedPower(), 0));
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            this.bulletGenerator.generate(actor, world, new Vector2(actor.getRangedPower(), 0));
        }
    }
}
