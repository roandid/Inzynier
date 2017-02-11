package com.inzynier.game.gameplay.map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.inzynier.game.Constants;
import com.inzynier.game.entities.Door;
import com.inzynier.game.entities.Doors;

public class DoorsGenerator {

    public void generate(Doors doors, World world) {
        if (doors.isOnWest()) {
            this.addDoor(
                world,
                new Vector2(0.1f, 3),
                new Vector2(10, 44),
                new Texture("walls/left_open.png"),
                new Texture("walls/left_closed.png"),
                Door.Position.WEST
            );
        }

        if (doors.isOnEast()) {
            this.addDoor(
                world,
                new Vector2(0.1f, 3),
                new Vector2(112, 44),
                new Texture("walls/right_open.png"),
                new Texture("walls/right_closed.png"),
                Door.Position.EAST
            );
        }

        if (doors.isOnNorth()) {
            this.addDoor(
                world,
                new Vector2(3, 0.1f),
                new Vector2(65, 73.5f),
                new Texture("walls/top_open.png"),
                new Texture("walls/top_closed.png"),
                Door.Position.NORTH
            );
        }

        if (doors.isOnSouth()) {
            this.addDoor(
                world,
                new Vector2(3, 0.1f),
                new Vector2(65, 10),
                new Texture("walls/bottom_open.png"),
                new Texture("walls/bottom_closed.png"),
                Door.Position.SOUTH
            );
        }
    }

    protected void addDoor(World world, Vector2 size, Vector2 pos, Texture open, Texture closed, Door.Position position) {
        Body body = world.createBody(this.createBodyDef(pos.x, pos.y));
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(size.x, size.y);
        body.createFixture(this.createFixtureDef(shape, Constants.BIT_WALL_PLAYER));

        Door door = new Door(body, open, closed, position);
        body.setUserData(door);
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
