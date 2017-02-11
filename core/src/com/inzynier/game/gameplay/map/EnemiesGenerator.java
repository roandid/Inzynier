package com.inzynier.game.gameplay.map;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.inzynier.game.Constants;
import com.inzynier.game.factory.ActorFactory;

public class EnemiesGenerator implements ObjectGeneratorInterface {

    @Override
    public void generateObject(World world, MapLayer layer, short bits, float width, float height) {
        for (MapObject obj : layer.getObjects()) {

            Float x = (Float) obj.getProperties().get("x");
            Float y = (Float) obj.getProperties().get("y");
            String type = (String) obj.getProperties().get("type");

            ActorFactory.getActorFactory().createFromString(type).setPosition(new Vector2(Constants.toBox2d(x), Constants.toBox2d(y))).createBody(world);
        }
    }
}
