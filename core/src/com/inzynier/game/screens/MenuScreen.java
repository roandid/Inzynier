package com.inzynier.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.inzynier.game.MyGame;

public class MenuScreen implements Screen {

    boolean activePlay;

    protected MyGame game;
    protected SpriteBatch spriteBatch;
    protected Texture backgroundTexture;
    protected Texture logo;
    protected Texture start;
    protected Texture exit;
    protected Texture startActive;
    protected Texture exitActive;

    public MenuScreen(MyGame game) {
        this.game = game;
        this.spriteBatch = new SpriteBatch();
        this.backgroundTexture = new Texture("menu_screen.png");
        this.logo = new Texture("logo.png");
        this.start = new Texture("start.png");
        this.exit = new Texture("exit.png");
        this.startActive = new Texture("start_active.png");
        this.exitActive = new Texture("exit_active.png");
        this.activePlay = true;
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
        this.spriteBatch.draw(this.logo, 250, 620);
        this.spriteBatch.draw(this.activePlay ? this.startActive : this.start, 475, 400);
        this.spriteBatch.draw(this.activePlay ? this.exit : this.exitActive, 555, 300);
        this.spriteBatch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)
            || Gdx.input.isKeyJustPressed(Input.Keys.DOWN)
            || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            this.activePlay = !this.activePlay;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {

            if (this.activePlay) {
                this.game.setScreen(new GameplayScreen(this.game));
                return;
            }

            Gdx.app.exit();
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
