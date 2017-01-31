package com.inzynier.game.strategy.move;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.physics.box2d.World;
import com.inzynier.game.entities.Actor;
import com.inzynier.game.strategy.StrategyInterface;

public class PlayerMoveStrategy implements StrategyInterface {

    @Override
    public void action(Actor actor, float dt, World world) {

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            actor.getBody().applyForceToCenter(0, 400, true);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            actor.getBody().applyForceToCenter(0, -400, true);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            actor.getBody().applyForceToCenter(-400, 0, true);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            actor.getBody().applyForceToCenter(400, 0, true);
        }
    }
}
