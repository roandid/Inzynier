package com.inzynier.game.factory;

import com.badlogic.gdx.graphics.Texture;
import com.inzynier.game.builder.ActorBuilder;
import com.inzynier.game.entities.Actor;
import com.inzynier.game.strategy.DoubleStrategy;
import com.inzynier.game.strategy.WaitStrategy;
import com.inzynier.game.strategy.fight.PlayerRangedFightStrategy;
import com.inzynier.game.strategy.fight.QuadraShootStrategy;
import com.inzynier.game.strategy.fight.SpawnSpecialBrocolliStrategy;
import com.inzynier.game.strategy.move.AnimatedWalkStrategy;
import com.inzynier.game.strategy.move.PlayerMoveStrategy;
import com.inzynier.game.strategy.move.RandomWalkStrategy;
import com.inzynier.game.strategy.move.WalkToPlayerStrategy;

public class ActorFactory {

    private enum Enemy {
        RANDOM_WALKER,
        PLAYER_FOLLOWER,
        BROCOLLI,
        SPECIAL_BROCOLLI,
        BOSS
    }

    public static ActorFactory getActorFactory() {
        return new ActorFactory();
    }

    public Actor createFromString(String name) {
        Enemy enemy = Enemy.valueOf(name.toUpperCase());
        switch (enemy) {
            case RANDOM_WALKER:
                return this.createRandomWalker();
            case PLAYER_FOLLOWER:
                return this.createPlayerFollower();
            case BROCOLLI:
                return this.createBroccoli();
            case SPECIAL_BROCOLLI:
                return this.createSpecialBrocolli();
            case BOSS:
                return this.createBoss();
        }

        return this.createBroccoli();
    }

    public Actor createPlayer() {
        return ActorBuilder.getActorBuilder()
            .setIsPlayer(true)
            .setAtlasPath("postac.txt")
            .setBulletTexture(new Texture("kredka_b.png"))
            .setStragegy(new DoubleStrategy(new PlayerMoveStrategy(), new PlayerRangedFightStrategy()))
            .setRangedPower(100)
            .setSize(22, 30)
            .build();
    }

    public Actor createBroccoli() {
        return ActorBuilder.getActorBuilder()
            .setAtlasPath("brokul.txt")
            .setHealth(200)
            .setContactPower(500)
            .setBulletTexture(new Texture("drop_g.png"))
            .setStragegy(
                new AnimatedWalkStrategy(new WalkToPlayerStrategy(400))
            )
            .build();
    }

    public Actor createRandomWalker() {
        return ActorBuilder.getActorBuilder()
            .setRangedPower(100)
            .setContactPower(100)
            .setAtlasPath("zielony.txt")
            .setBulletTexture(new Texture("kredka_g.png"))
            .setStragegy(
                new DoubleStrategy(
                    new RandomWalkStrategy(2, 1),
                    new WaitStrategy(new QuadraShootStrategy(), 1.5f)
                )
            )
            .build();
    }

    public Actor createPlayerFollower() {
        return ActorBuilder.getActorBuilder()
            .setRangedPower(100)
            .setContactPower(300)
            .setAtlasPath("czerwony.txt")
            .setBulletTexture(new Texture("kredka_r.png"))
            .setStragegy(
                new DoubleStrategy(
                    new AnimatedWalkStrategy(new WalkToPlayerStrategy(150.0f)),
                    new WaitStrategy(new QuadraShootStrategy(), 2.5f)
                )
            )
            .build();
    }

    public Actor createSpecialBrocolli() {
        return ActorBuilder.getActorBuilder()
            .setAtlasPath("brokul.txt")
            .setHealth(300)
            .setRangedPower(500)
            .setBulletTexture(new Texture("drop_g.png"))
            .setStragegy(
                new DoubleStrategy(
                    new AnimatedWalkStrategy(new WalkToPlayerStrategy(350.0f)),
                    new WaitStrategy(new QuadraShootStrategy(), 2.5f)
                )
            )
            .build();
    }

    public Actor createBoss() {
        return ActorBuilder.getActorBuilder()
            .setRangedPower(300)
            .setContactPower(900)
            .setAtlasPath("boss.txt")
            .setBulletTexture(new Texture("drop_r.png"))
            .setStragegy(
                new DoubleStrategy(
                    new WalkToPlayerStrategy(600),
                    new DoubleStrategy(
                        new WaitStrategy(new QuadraShootStrategy(), 1.5f),
                        new WaitStrategy(new SpawnSpecialBrocolliStrategy(), 5f)
                    )
                )
            )
            .build();
    }
}
