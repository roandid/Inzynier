package com.inzynier.game.gameplay.map;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.inzynier.game.Constants;
import com.inzynier.game.entities.objects.Blocker;
import com.inzynier.game.entities.objects.TiledObject;

/**
 * Created by Krzysztof on 11.01.2017.
 */
public class ObjectGenerator implements ObjectGeneratorInterface {

    @Override
    public void generateObject(World world, MapLayer layer, short bits, float width, float height) {

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();

        for (MapObject obj : layer.getObjects()) {

            bdef.type = BodyDef.BodyType.StaticBody;
            Float x = (Float) obj.getProperties().get("x");
            Float y = (Float) obj.getProperties().get("y");

            bdef.position.set(Constants.toBox2d(x), Constants.toBox2d(y));

            PolygonShape shape = new PolygonShape();

            shape.setAsBox(Constants.toBox2d(width), Constants.toBox2d(height));

            fdef.shape = shape;
            fdef.isSensor = false;
            fdef.filter.categoryBits = bits;
            fdef.filter.maskBits = Constants.BIT_PLAYER;

            Body body = world.createBody(bdef);
            body.createFixture(fdef);

            TiledObject tiledObject = makeObject(body, bits);
            body.setUserData(tiledObject);
        }

    }

    private TiledObject makeObject(Body body, short bits) {
        if(bits == Constants.BIT_BLOCKER) return new Blocker(body);
        else return null;
    }
}
