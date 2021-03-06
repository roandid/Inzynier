package com.inzynier.game.strategy.fight;

import com.badlogic.gdx.physics.box2d.World;
import com.inzynier.game.entities.Actor;
import com.inzynier.game.factory.ActorFactory;
import com.inzynier.game.strategy.StrategyInterface;

public class SpawnSpecialBrocolliStrategy implements StrategyInterface {

    @Override
    public void action(Actor actor, float dt, World world) {
        Actor walker = ActorFactory.getActorFactory().createSpecialBrocolli();
        walker.setPosition(actor.getPosition());
        walker.createBody(world);
    }
}
