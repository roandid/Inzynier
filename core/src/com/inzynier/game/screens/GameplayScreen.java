package com.inzynier.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.inzynier.game.MyGame;
import com.inzynier.game.entities.Actor;
import com.inzynier.game.factory.ActorFactory;
import com.inzynier.game.gameplay.Level;

public class GameplayScreen implements Screen {

    protected MyGame myGame;
    protected Actor player;
    protected Level level;

    public GameplayScreen(MyGame myGame) {

        // Box2d bez świata nie ładuje natywnych metod
        Box2D.init();

        this.myGame = myGame;
        this.player = ActorFactory.getActorFactory().createPlayer();

        this.initNewLevel();
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float f) {
        this.level.run(f);

        if (this.level.isOver()) {
            this.myGame.setScreen(new TheEndScreen(this.myGame));
        }

        if (this.player.isDead()) {
            this.myGame.setScreen(new GameOverScreen(this.myGame));
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            this.initNewLevel();
        }
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

    private void initNewLevel() {

        this.level = new Level(this.player, 6, 10);
        this.level.init();
    }
}
