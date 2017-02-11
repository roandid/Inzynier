package com.inzynier.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.inzynier.game.MyGame;

public class TheEndScreen implements Screen {

    protected MyGame game;
    protected SpriteBatch spriteBatch;
    protected Texture backgroundTexture;
    protected Texture theend;

    public TheEndScreen(MyGame game) {
        this.game = game;
        this.spriteBatch = new SpriteBatch();
        this.backgroundTexture = new Texture("menu_screen.png");
        this.theend = new Texture("theend.png");
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float f) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.spriteBatch.begin();
        this.spriteBatch.draw(this.backgroundTexture, 0, 0);
        this.spriteBatch.draw(this.theend, 140, 450);
        this.spriteBatch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            this.game.setScreen(new MenuScreen(this.game));
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
}
