package com.inzynier.game.gameplay.map;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.physics.box2d.World;

public interface LayerFactoryInterface {

    public void createLayer(World world, TiledMapTileLayer layer, short bits, int tileSize);
}
