package com.inzynier.game.gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.inzynier.game.Constants;
import com.inzynier.game.MyContactListener;
import com.inzynier.game.MyGame;
import com.inzynier.game.contact.ActionsDispatcher;
import com.inzynier.game.entities.Doors;
import com.inzynier.game.entities.DrawableInterface;
import com.inzynier.game.entities.Actor;
import com.inzynier.game.entities.Position;
import com.inzynier.game.gameplay.map.LayerGeneratorInterface;

public class Room {

    public enum RoomType {
        BEGIN_ROOM, NORMAL_ROOM,
        BOSS_ROOM, TREASURE_ROOM, HIDDEN_ROOM
    }

    protected Actor player;
    protected Doors doors;
    protected TiledMap map;
    protected OrthogonalTiledMapRenderer renderer;
    protected World world;
    protected Box2DDebugRenderer b2dr;
    protected SpriteBatch spriteBatch;
    protected LayerGeneratorInterface layerFactory;
    protected OrthographicCamera camera;
    protected Texture leftWall, rightWall, upWall, downWall;
    protected RoomType type;

    public Room(String mapName, Actor player, Doors doors, LayerGeneratorInterface layerFactory, RoomType roomType) {

        this.player = player;
        this.doors = doors;
        this.layerFactory = layerFactory;
        this.type = roomType;

        this.map = new TmxMapLoader().load(mapName);
        this.renderer = new OrthogonalTiledMapRenderer(map);
        this.world = new World(new Vector2(0, 0), true);
        this.world.setContactListener(MyContactListener.getListener());
        this.b2dr = new Box2DDebugRenderer();
        this.spriteBatch = new SpriteBatch();
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, MyGame.WIDTH, MyGame.HEIGHT);
        this.leftWall = new Texture("walls/wall_left.png");
        this.rightWall = new Texture("walls/wall_right.png");
        this.upWall = new Texture("walls/wall_up.png");
        this.downWall = new Texture("walls/wall_down.png");
    }

    public void setLayerFactory(LayerGeneratorInterface layerFactory) {
        this.layerFactory = layerFactory;
    }

    public RoomType getType() {
        return type;
    }

    public void init() {
        this.spriteBatch.setProjectionMatrix(this.camera.combined);
        int tileSize = (Integer) map.getProperties().get("tilewidth");

        this.layerFactory.generateLayer(this.world, (TiledMapTileLayer) this.map.getLayers().get("ground"), Constants.BIT_GROUND, tileSize);
        this.layerFactory.generateLayer(this.world, (TiledMapTileLayer) this.map.getLayers().get("wall"), Constants.BIT_WALL, tileSize);
    }

    public void wakeUp(Position position) {
        // Ustawić gracza zgodnie z pozycja - w tym wypadku wyjątkowo na środku
        this.player.setPosition(new Vector2(Constants.toBox2d(MyGame.WIDTH) / 2, Constants.toBox2d(MyGame.HEIGHT) / 2));
        this.player.createBody(this.world);
    }

    public void sleep() {
        Array<Body> array = this.getBodies();

        for (int i = 0; i < array.size; i++) {
            Object object = array.get(i).getUserData();

            if (object instanceof Actor) {
                if (((Actor) object).isPlayer()) {
                    world.destroyBody(array.get(i));
                }

            }

        }
    }

    public void run(float dt) {

        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.renderer.setView(camera);
        this.renderer.render();

        ActionsDispatcher.dispatch();
        this.world.step(dt, 6, 2);
        Array<Body> array = this.getBodies();

        this.updateObjects(array, dt);

        this.camera.update();
        this.spriteBatch.begin();

        spriteBatch.draw(leftWall, 0, 64, 64, 320);
        spriteBatch.draw(rightWall, MyGame.WIDTH - 64, 64, 64, 320);
        spriteBatch.draw(upWall, 0, MyGame.HEIGHT - 64, MyGame.WIDTH, 64);
        spriteBatch.draw(downWall, 0, 0, MyGame.WIDTH, 64);
        this.renderObjects(array);
        this.spriteBatch.end();
        b2dr.render(world, camera.combined);
    }

    protected Array<Body> getBodies() {
        Array<Body> array = new Array();
        this.world.getBodies(array);

        return array;
    }

    protected void updateObjects(Array<Body> array, float dt) {

        for (int i = 0; i < array.size; i++) {
            Object object = array.get(i).getUserData();

            if (object instanceof DrawableInterface) {
                ((DrawableInterface) object).update(dt, this.world);
            }
        }
    }

    protected void renderObjects(Array<Body> array) {

        for (int i = 0; i < array.size; i++) {
            Object object = array.get(i).getUserData();

            if (object instanceof DrawableInterface) {
                ((DrawableInterface) object).draw(this.spriteBatch);
            }
        }
    }
}
