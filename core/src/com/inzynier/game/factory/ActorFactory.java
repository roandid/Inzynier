package com.inzynier.game.factory;

import com.badlogic.gdx.graphics.Texture;
import com.inzynier.game.builder.ActorBuilder;
import com.inzynier.game.entities.Actor;
import com.inzynier.game.strategy.move.PlayerMoveStrategy;

public class ActorFactory {

    public static ActorFactory getActorFactory() {
        return new ActorFactory();
    }

    public Actor createPlayer() {
        return ActorBuilder.getActorBuilder()
                .setIsPlayer(true)
                .setMainTexture(new Texture("issac.png"))
                .setBulletTexture(new Texture("tear.png"))
                .setStragegy(new PlayerMoveStrategy())
                .setPower(100)
                .build();
    }
}
