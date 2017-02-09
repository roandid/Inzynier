package com.inzynier.game;

import com.badlogic.gdx.Game;
import com.inzynier.game.screens.MenuScreen;

public class MyGame extends Game {

    public final static String GAME_NAME = "Inzynier Krzysztof";

    public final static int WIDTH = 1280;
    public final static int HEIGHT = 896;
    public final static int SCALE = 1;
    private boolean paused;

    @Override
    public void create() {
        this.setScreen(new MenuScreen(this));
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }
}
