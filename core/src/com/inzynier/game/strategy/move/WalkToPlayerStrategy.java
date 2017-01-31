package com.inzynier.game.strategy.move;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.inzynier.game.entities.Actor;
import com.inzynier.game.strategy.StrategyInterface;

public class WalkToPlayerStrategy implements StrategyInterface {

    protected float speed;

    public WalkToPlayerStrategy(float speed) {
        this.speed = speed;
    }

    @Override
    public void action(Actor actor, float dt, World world) {

        try {
            Vector2 ap = new Vector2(actor.getBody().getPosition());
            Vector2 mp = new Vector2(this.getPlayer(world).getBody().getPosition());

            Vector2 delta = mp.sub(ap).nor();

            actor.getBody().applyForceToCenter(delta.x * this.speed, delta.y * this.speed, true);
        } catch (RuntimeException ex) {
            return;
        }
    }

    protected Actor getPlayer(World world) {
        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);

        for (Body body : bodies) {
            if (body.getUserData() instanceof Actor && ((Actor) body.getUserData()).isPlayer()) {

                return (Actor) body.getUserData();
            }
        }

        throw new RuntimeException("Player is dead");
    }
}
