package com.inzynier.game.strategy.move;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.physics.box2d.World;
import com.inzynier.game.entities.MovableInterface;
import com.inzynier.game.strategy.MoveStrategyInterface;

public class PlayerMoveStrategy implements MoveStrategyInterface {

    @Override
    public void move(MovableInterface character, float dt, World world) {

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            character.getBody().applyForceToCenter(0, 400, true);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            character.getBody().applyForceToCenter(0, -400, true);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            character.getBody().applyForceToCenter(-400, 0, true);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            character.getBody().applyForceToCenter(400, 0, true);
        }
    }
}
