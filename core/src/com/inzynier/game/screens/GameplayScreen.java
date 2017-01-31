package com.inzynier.game.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.inzynier.game.MyGame;
import com.inzynier.game.entities.Actor;
import com.inzynier.game.factory.ActorFactory;
import com.inzynier.game.gameplay.Level;

/**
 * Created by Krzysztof on 26.06.2016.
 */
public class GameplayScreen implements Screen {

    protected MyGame myGame;
    protected Actor player;
    protected Level level;

    public GameplayScreen(MyGame myGame) {

        // Box2d bez świata nie ładuje natywnych metod
        Box2D.init();

        this.myGame = myGame;
        this.player = ActorFactory.getActorFactory().createPlayer();
        this.level = new Level(this.player);
        this.level.init();
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float f) {
        this.level.run(f);
    }

    @Override
    public void resize(int i, int i1) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }

}
