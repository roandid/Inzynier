package com.inzynier.game.strategy.move;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.physics.box2d.World;
import com.inzynier.game.entities.Actor;
import com.inzynier.game.entities.AnimationsStorage;
import com.inzynier.game.strategy.StrategyInterface;

public class PlayerMoveStrategy implements StrategyInterface {

    @Override
    public void action(Actor actor, float dt, World world) {

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            actor.getAnimationsStorage().changeAnimation(AnimationsStorage.Action.WALK_UP);
            actor.getBody().applyForceToCenter(0, 800, true);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            actor.getAnimationsStorage().changeAnimation(AnimationsStorage.Action.WALK_DOWN);
            actor.getBody().applyForceToCenter(0, -800, true);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            actor.getAnimationsStorage().changeAnimation(AnimationsStorage.Action.WALK_LEFT);
            actor.getBody().applyForceToCenter(-800, 0, true);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            actor.getAnimationsStorage().changeAnimation(AnimationsStorage.Action.WALK_RIGHT);
            actor.getBody().applyForceToCenter(800, 0, true);
        }
    }
}
