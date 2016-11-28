package com.inzynier.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.inzynier.game.screens.GameplayScreen;

public class MyGame extends Game {


    public final static String GAME_NAME = "Game";


    public final static int WIDTH = 512;
    public final static int HEIGHT = 384;
    public final static int SCALE = 2;
    private boolean paused;

    @Override
    public void create() {

        this.setScreen(new GameplayScreen(this));

        init();
    }

    private void init() {
      
    }




    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }





}
