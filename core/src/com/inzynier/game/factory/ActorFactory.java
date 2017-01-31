package com.inzynier.game.factory;

import com.badlogic.gdx.graphics.Texture;
import com.inzynier.game.builder.ActorBuilder;
import com.inzynier.game.entities.Actor;
import com.inzynier.game.strategy.DoubleStrategy;
import com.inzynier.game.strategy.WaitStrategy;
import com.inzynier.game.strategy.fight.PlayerRangedFightStrategy;
import com.inzynier.game.strategy.fight.QuadraShootStrategy;
import com.inzynier.game.strategy.fight.SpawnWalkerStrategy;
import com.inzynier.game.strategy.move.PlayerMoveStrategy;
import com.inzynier.game.strategy.move.RandomWalkStrategy;
import com.inzynier.game.strategy.move.WalkToPlayerStrategy;

public class ActorFactory {

    public static ActorFactory getActorFactory() {
        return new ActorFactory();
    }

    public Actor createPlayer() {
        return ActorBuilder.getActorBuilder()
            .setIsPlayer(true)
            .setMainTexture(new Texture("issac.png"))
            .setBulletTexture(new Texture("tear.png"))
            .setStragegy(new DoubleStrategy(new PlayerMoveStrategy(), new PlayerRangedFightStrategy()))
            .setPower(100)
            .build();
    }

    public Actor createSpawner() {
        return ActorBuilder.getActorBuilder()
            .setPower(100)
            .setMainTexture(new Texture("knight.png"))
            .setBulletTexture(new Texture("tear.png"))
            .setStragegy(
                new DoubleStrategy(
                    new RandomWalkStrategy(3, 1),
                    new WaitStrategy(new SpawnWalkerStrategy(), 3.5f)
                )
            )
            .build();
    }

    public Actor createRandomWalker() {
        return ActorBuilder.getActorBuilder()
            .setPower(100)
            .setMainTexture(new Texture("knight.png"))
            .setBulletTexture(new Texture("tear.png"))
            .setStragegy(
                new DoubleStrategy(
                    new RandomWalkStrategy(1, 1),
                    new WaitStrategy(new QuadraShootStrategy(), 5f)
                )
            )
            .build();
    }

    public Actor createPlayerFollower() {
        return ActorBuilder.getActorBuilder()
            .setPower(100)
            .setMainTexture(new Texture("knight.png"))
            .setBulletTexture(new Texture("tear.png"))
            .setStragegy(
                new DoubleStrategy(
                    new WalkToPlayerStrategy(150.0f),
                    new WaitStrategy(new QuadraShootStrategy(), 5f)
                )
            )
            .build();
    }
}
