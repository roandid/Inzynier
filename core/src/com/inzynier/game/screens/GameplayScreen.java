package com.inzynier.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import com.inzynier.game.entities.Player;
import com.inzynier.game.Constants;
import com.inzynier.game.MyContactListener;
import com.inzynier.game.MyGame;
import com.inzynier.game.entities.BasicBullet;
import com.inzynier.game.entities.InterfaceBullet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Krzysztof on 26.06.2016.
 */
public class GameplayScreen implements Screen {

    protected MyGame game;

    private OrthographicCamera camera;

    protected SpriteBatch spriteBatch;

    private MyContactListener contactListener;
    
    private World world;
    private Box2DDebugRenderer b2dr;

    private Player player;
    private InterfaceBullet bullet = null;
    private List<InterfaceBullet> listPlayerBullets = new ArrayList<InterfaceBullet>();

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private Integer tileMapWidth;
    private Integer tileMapHeight;
    private Integer tileSize;

    private Texture leftWall, rightWall, upWall, downWall;

    public GameplayScreen(MyGame game) {
        this.game = game;
        createCamera();
        spriteBatch = new SpriteBatch();
        init();
    }

    private void init() {

        world = new World(new Vector2(0, 0), true);
        this.contactListener = new MyContactListener();
        this.world.setContactListener(this.contactListener);

        // debug
        b2dr = new Box2DDebugRenderer();

        createPlayer();

        createTiles();

        createWalls();
    }

    private void createWalls() {

        leftWall = new Texture("walls/wall_left.png");
        rightWall = new Texture("walls/wall_right.png");
        upWall = new Texture("walls/wall_up.png");
        downWall = new Texture("walls/wall_down.png");
    }

    private void createTiles() {

        map = new TmxMapLoader().load("maps/tester.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        tileMapWidth = (Integer) map.getProperties().get("width");
        tileMapHeight = (Integer) map.getProperties().get("height");
        tileSize = (Integer) map.getProperties().get("tilewidth");
        TiledMapTileLayer layer;

        layer = (TiledMapTileLayer) map.getLayers().get("wall");
        createLayer(layer, Constants.BIT_WALL, "wall");

        layer = (TiledMapTileLayer) map.getLayers().get("ground");
        createLayer(layer, Constants.BIT_GROUND, "ground");

    }

    private void createLayer(TiledMapTileLayer layer, short bits, String name) {
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        float ntileSize = Constants.toBox2d(tileSize);

        for (int row = 0; row < layer.getHeight(); row++) {
            for (int col = 0; col < layer.getWidth(); col++) {

                Cell cell = layer.getCell(col, row);

                if (cell == null) {
                    continue;
                }
                if (cell.getTile() == null) {
                    continue;
                }

                bodyDef.type = BodyType.StaticBody;
                bodyDef.position.set((col + 0.5f) * ntileSize, (row + 0.5f) * ntileSize);

                ChainShape cShape = new ChainShape();
                Vector2[] vector = new Vector2[5];
                vector[0] = new Vector2(-ntileSize / 2, -ntileSize / 2);
                vector[1] = new Vector2(-ntileSize / 2, ntileSize / 2);
                vector[2] = new Vector2(ntileSize / 2, ntileSize / 2);
                vector[3] = new Vector2(ntileSize / 2, -ntileSize / 2);
                vector[4] = new Vector2(-ntileSize / 2, -ntileSize / 2);
                cShape.createChain(vector);

                fixtureDef.friction = 0.8f;
                fixtureDef.shape = cShape;
                fixtureDef.filter.categoryBits = bits;
                fixtureDef.filter.maskBits = Constants.BIT_PLAYER;
                fixtureDef.isSensor = false;
                world.createBody(bodyDef).createFixture(fixtureDef);
            }
        }
    }

    private void createPlayer() {
        BodyDef bodyDef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();

        bodyDef.position.set(Constants.toBox2d(MyGame.WIDTH) / 2, Constants.toBox2d(MyGame.HEIGHT) / 2);
        bodyDef.type = BodyType.DynamicBody;
        bodyDef.linearDamping = 20.5f;

        Body body = world.createBody(bodyDef);

        shape.setAsBox(Constants.toBox2d(14), Constants.toBox2d(12));
        fixtureDef.shape = shape;
        fixtureDef.friction = 0.5f;
        fixtureDef.filter.categoryBits = Constants.BIT_PLAYER;
        fixtureDef.filter.maskBits = Constants.BIT_WALL;
        body.createFixture(fixtureDef);

        player = new Player(body);
        player.getBody().setUserData("player");

    }

    private void createCamera() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, MyGame.WIDTH, MyGame.HEIGHT);
        camera.update();
    }

    @Override
    public void render(float delta) {
        clearScreen();
        camera.update();
        spriteBatch.setProjectionMatrix(camera.combined);

        world.step(delta, 6, 2);

        player.update(delta);

        for (int i = 0; i < this.listPlayerBullets.size(); i++) {
            this.listPlayerBullets.get(i).update();
        }

        renderer.setView(camera);
        renderer.render();

        spriteBatch.begin();
        spriteBatch.draw(leftWall, 0, 64, 64, 320);
        spriteBatch.draw(rightWall, MyGame.WIDTH - 64, 64, 64, 320);
        spriteBatch.draw(upWall, 0, MyGame.HEIGHT - 64, MyGame.WIDTH, 64);
        spriteBatch.draw(downWall, 0, 0, MyGame.WIDTH, 64);
        spriteBatch.end();

        player.render(spriteBatch);
        for (int i = 0; i < this.listPlayerBullets.size(); i++) {
            this.listPlayerBullets.get(i).render(spriteBatch);
        }
        b2dr.render(world, camera.combined);

        if (isPlayerOutOfMap()) {
            game.setScreen(new GameplayScreen(game));
        }

        // test
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            player.getBody().applyForceToCenter(0, 400, true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            player.getBody().applyForceToCenter(0, -400, true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            player.getBody().applyForceToCenter(-400, 0, true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            player.getBody().applyForceToCenter(400, 0, true);
        }

        //Generowanie nowych pocisków przez klikanie na przyciski w,a,s,d
        if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            this.bullet = new BasicBullet(this.player.getBody().getPosition().x, this.player.getBody().getPosition().y, InterfaceBullet.LEFT, 250, new Texture("issac.png"));
            this.listPlayerBullets.add(this.bullet.generateBullet(this.world));
            this.contactListener.setListBullet(this.listPlayerBullets);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            this.bullet = new BasicBullet(this.player.getBody().getPosition().x, this.player.getBody().getPosition().y, InterfaceBullet.UP, 250, new Texture("issac.png"));
            this.listPlayerBullets.add(this.bullet.generateBullet(this.world));
            this.contactListener.setListBullet(this.listPlayerBullets);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            this.bullet = new BasicBullet(this.player.getBody().getPosition().x, this.player.getBody().getPosition().y, InterfaceBullet.DOWN, 250, new Texture("issac.png"));
            this.listPlayerBullets.add(this.bullet.generateBullet(this.world));
            this.contactListener.setListBullet(this.listPlayerBullets);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            this.bullet = new BasicBullet(this.player.getBody().getPosition().x, this.player.getBody().getPosition().y, InterfaceBullet.RIGHT, 250, new Texture("issac.png"));
            this.listPlayerBullets.add(this.bullet.generateBullet(this.world));
            this.contactListener.setListBullet(this.listPlayerBullets);
        }

    }

    private boolean isPlayerOutOfMap() {
        float x = player.getPosition().x;
        float y = player.getPosition().y;
        if (x < 0 || x > MyGame.WIDTH) {
            return true;
        } else if (y < 0 || y > MyGame.HEIGHT) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void show() {

    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void resume() {
        game.setPaused(false);
    }

    @Override
    public void pause() {
        game.setPaused(true);
    }

    @Override
    public void dispose() {
        game.dispose();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void hide() {

    }
}
