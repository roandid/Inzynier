package com.inzynier.game.strategy.move;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.inzynier.game.entities.Actor;
import com.inzynier.game.entities.AnimationsStorage;
import com.inzynier.game.strategy.StrategyInterface;
import static java.lang.Math.abs;

public class AnimatedWalkStrategy implements StrategyInterface {

    protected StrategyInterface next;

    public AnimatedWalkStrategy(StrategyInterface next) {
        this.next = next;
    }

    @Override
    public void action(Actor actor, float dt, World world) {
        next.action(actor, dt, world);

        AnimationsStorage storage = actor.getAnimationsStorage();
        Vector2 vel = actor.getBody().getLinearVelocity();

        if (vel.x < 0 && vel.y <= 0) {
            if (lIsBigger(vel.x, vel.y)) {
                storage.changeAnimation(AnimationsStorage.Action.WALK_LEFT);
            } else {
                storage.changeAnimation(AnimationsStorage.Action.WALK_UP);
            }
        }

        if (vel.x < 0 && vel.y >= 0) {
            if (lIsBigger(vel.x, vel.y)) {
                storage.changeAnimation(AnimationsStorage.Action.WALK_LEFT);
            } else {
                storage.changeAnimation(AnimationsStorage.Action.WALK_DOWN);
            }
        }

        if (vel.x > 0 && vel.y <= 0) {
            if (lIsBigger(vel.x, vel.y)) {
                storage.changeAnimation(AnimationsStorage.Action.WALK_RIGHT);
            } else {
                storage.changeAnimation(AnimationsStorage.Action.WALK_UP);
            }
        }

        if (vel.x > 0 && vel.y >= 0) {
            if (lIsBigger(vel.x, vel.y)) {
                storage.changeAnimation(AnimationsStorage.Action.WALK_RIGHT);
            } else {
                storage.changeAnimation(AnimationsStorage.Action.WALK_DOWN);
            }
        }
    }

    protected boolean lIsBigger(float l, float r) {
        return abs(l) > abs(r);
    }
}
