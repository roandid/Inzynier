package com.inzynier.game.gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.inzynier.game.Constants;
import com.inzynier.game.MyContactListener;
import com.inzynier.game.MyGame;
import com.inzynier.game.contact.ActionsDispatcher;
import com.inzynier.game.contact.actions.DestroyAction;
import com.inzynier.game.entities.objects.Blocker;
import com.inzynier.game.entities.Doors;
import com.inzynier.game.entities.DrawableInterface;
import com.inzynier.game.entities.Actor;
import com.inzynier.game.entities.Door;
import com.inzynier.game.entities.Position;
import com.inzynier.game.factory.ActorFactory;
import com.inzynier.game.gameplay.map.LayerGeneratorInterface;
import java.util.Comparator;
import com.inzynier.game.gameplay.map.ObjectGeneratorInterface;

import java.util.ArrayList;

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
    protected RoomType type;
    protected Texture leftWall, rightWall, upWall, downWall, ground;
    protected ObjectGeneratorInterface objectFactory;
    protected ArrayList<Blocker> listOfBlockers;
    protected LevelController levelController;

    //debug
    BitmapFont font = new BitmapFont();

    public Room(String mapName, Actor player, Doors doors, LayerGeneratorInterface layerFactory,
        ObjectGeneratorInterface objectFactory, RoomType roomType, LevelController levelController) {

        this.player = player;
        this.doors = doors;
        this.layerFactory = layerFactory;
        this.type = roomType;
        this.levelController = levelController;

        this.objectFactory = objectFactory;

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

        this.ground = new Texture("walls/ground.png");
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
        this.layerFactory.generateLayer(this.world, (TiledMapTileLayer) this.map.getLayers().get("player_wall"), Constants.BIT_WALL_PLAYER, tileSize);
        this.layerFactory.generateLayer(this.world, (TiledMapTileLayer) this.map.getLayers().get("bullet_wall"), Constants.BIT_WALL_BULLET, tileSize);

        this.objectFactory.generateObject(this.world, map.getLayers().get("block_up"), Constants.BIT_BLOCKER, 32, 32);
        this.objectFactory.generateObject(this.world, map.getLayers().get("block_down"), Constants.BIT_BLOCKER, 32, 32);
        this.objectFactory.generateObject(this.world, map.getLayers().get("block_right"), Constants.BIT_BLOCKER, 32, 32);
        this.objectFactory.generateObject(this.world, map.getLayers().get("block_left"), Constants.BIT_BLOCKER, 32, 32);
        this.objectFactory.generateObject(this.world, map.getLayers().get("lego"), Constants.BIT_LEGO, 32, 32);
        this.createDoors();

        ActorFactory.getActorFactory().createBroccoli().setPosition(new Vector2(10, 10)).createBody(this.world);

    }

    public void wakeUp(Position position) {

        switch (position) {
            case LEFT:
                this.player.setPosition(new Vector2(Constants.toBox2d(150), Constants.toBox2d(MyGame.HEIGHT) / 2));
                break;
            case RIGHT:
                this.player.setPosition(new Vector2(Constants.toBox2d(1000), Constants.toBox2d(MyGame.HEIGHT) / 2));
                break;
            case UP:
                this.player.setPosition(new Vector2(Constants.toBox2d(MyGame.WIDTH) / 2, Constants.toBox2d(150)));
                break;
            case DOWN:
                this.player.setPosition(new Vector2(Constants.toBox2d(MyGame.WIDTH) / 2, Constants.toBox2d(700)));
                break;
            case CENTER:
                this.player.setPosition(new Vector2(Constants.toBox2d(MyGame.WIDTH) / 2, Constants.toBox2d(MyGame.HEIGHT) / 2));
                break;
        }

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

        ActionsDispatcher.dispatch(this.levelController);
        this.world.step(dt, 6, 2);
        Array<Body> array = this.getBodies();

        this.updateObjects(array, dt);

        this.camera.update();
        this.spriteBatch.begin();

        spriteBatch.draw(leftWall, 0, 128, 128, 640);
        spriteBatch.draw(rightWall, MyGame.WIDTH - 128, 128, 128, 640);
        spriteBatch.draw(upWall, 0, MyGame.HEIGHT - 128, MyGame.WIDTH, 128);
        spriteBatch.draw(downWall, 0, 0, MyGame.WIDTH, 128);
        spriteBatch.draw(ground, 128, 128, MyGame.WIDTH - 256, 640);

        font.draw(spriteBatch, "X:" + player.getPosition().x + " Y:" + player.getPosition().y,
            20, MyGame.HEIGHT - 20);

        this.renderObjects(dt, array);
        this.spriteBatch.end();
        b2dr.render(world, camera.combined);

        //przy okreslonym warunku zniszczyc okreslone blokery
        Array<Body> blockers = getBlockers();

        for (Body body : blockers) {
            ActionsDispatcher.addAction(new DestroyAction(body));
        }

    }

    private Array<Body> getBlockers() {
        Array<Body> array = new Array<Body>();
        Array<Body> resultArray = new Array<Body>();
        this.world.getBodies(array);
        for (int i = 0; i < array.size; i++) {
            Body body = array.get(i);
            Object object = body.getUserData();
            if (object != null) {
                if (object instanceof Blocker) {
                    resultArray.add(body);
                }
            }
        }
        return resultArray;
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

    protected void renderObjects(float dt, Array<Body> array) {

        array.sort(new Comparator<Body>() {
            @Override
            public int compare(Body o1, Body o2) {
                return o1.getPosition().y == o2.getPosition().y ? 0 : (o1.getPosition().y > o2.getPosition().y ? -1 : 1);
            }
        });

        for (int i = 0; i < array.size; i++) {
            Object object = array.get(i).getUserData();

            if (object instanceof DrawableInterface) {
                ((DrawableInterface) object).draw(dt, this.spriteBatch);
            }
        }
    }

    protected void createDoors() {

        if (this.doors.isOnWest()) {
            Body body = this.world.createBody(this.createBodyDef(10, 44));
            PolygonShape shape = new PolygonShape();
            shape.setAsBox(0.1f, 3);
            body.createFixture(this.createFixtureDef(shape, Constants.BIT_WALL_PLAYER));

            Door door = new Door(body, new Texture("walls/leftright_o.png"), new Texture("walls/leftright.png"), Door.Position.WEST);
            body.setUserData(door);
        }

        if (this.doors.isOnEast()) {
            Body body = this.world.createBody(this.createBodyDef(112, 44));
            PolygonShape shape = new PolygonShape();
            shape.setAsBox(0.1f, 3);
            body.createFixture(this.createFixtureDef(shape, Constants.BIT_WALL_PLAYER));

            Door door = new Door(body, new Texture("walls/leftright_o.png"), new Texture("walls/leftright.png"), Door.Position.EAST);
            body.setUserData(door);
        }

        if (this.doors.isOnNorth()) {
            Body body = this.world.createBody(this.createBodyDef(65, 73.5f));
            PolygonShape shape = new PolygonShape();
            shape.setAsBox(3, 0.1f);
            body.createFixture(this.createFixtureDef(shape, Constants.BIT_WALL_PLAYER));

            Door door = new Door(body, new Texture("walls/topdown_o.png"), new Texture("walls/topdown.png"), Door.Position.NORTH);
            body.setUserData(door);
        }

        if (this.doors.isOnSouth()) {
            Body body = this.world.createBody(this.createBodyDef(65, 10));
            PolygonShape shape = new PolygonShape();
            shape.setAsBox(3, 0.1f);
            body.createFixture(this.createFixtureDef(shape, Constants.BIT_WALL_PLAYER));

            Door door = new Door(body, new Texture("walls/topdown_o.png"), new Texture("walls/topdown.png"), Door.Position.SOUTH);
            body.setUserData(door);
        }
    }

    protected BodyDef createBodyDef(float col, float row) {
        BodyDef bodyDef = new BodyDef();

        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(col, row);

        return bodyDef;
    }

    protected FixtureDef createFixtureDef(Shape cShape, short bits) {
        FixtureDef fixtureDef = new FixtureDef();

        fixtureDef.friction = 0.8f;
        fixtureDef.shape = cShape;
        fixtureDef.filter.categoryBits = bits;
        fixtureDef.filter.maskBits = Constants.BIT_PLAYER | Constants.BIT_BULLET | Constants.BIT_ENEMY;
        fixtureDef.isSensor = false;

        return fixtureDef;
    }
}
