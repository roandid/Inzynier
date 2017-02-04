package com.inzynier.game.gameplay.map;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.physics.box2d.World;


/**
 * Created by Krzysztof on 11.01.2017.
 */
public interface ObjectGeneratorInterface {
    public void generateObject(World world, MapLayer layer, short bits, float width, float height);
}
