package com.inzynier.game.gameplay.map;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.inzynier.game.Constants;

public class LayerFactory implements LayerFactoryInterface {

    @Override
    public void createLayer(World world, TiledMapTileLayer layer, short bits, int tileSize) {
        float ntileSize = Constants.toBox2d(tileSize);
        ChainShape cShape = this.createChainShape(ntileSize);
        FixtureDef fixtureDef = this.createFixtureDef(cShape, bits);
        BodyDef bodyDef;

        for (int row = 0; row < layer.getHeight(); row++) {
            for (int col = 0; col < layer.getWidth(); col++) {

                Cell cell = layer.getCell(col, row);

                if (cell == null) {
                    continue;
                }
                if (cell.getTile() == null) {
                    continue;
                }

                bodyDef = this.createBodyDef(col, row, ntileSize);

                world.createBody(bodyDef).createFixture(fixtureDef);
            }
        }
    }

    protected Vector2[] createShapeVector(float size) {

        Vector2[] vector = new Vector2[5];
        vector[0] = new Vector2(-size / 2, -size / 2);
        vector[1] = new Vector2(-size / 2, size / 2);
        vector[2] = new Vector2(size / 2, size / 2);
        vector[3] = new Vector2(size / 2, -size / 2);
        vector[4] = new Vector2(-size / 2, -size / 2);

        return vector;
    }

    protected ChainShape createChainShape(float size) {
        ChainShape cShape = new ChainShape();
        cShape.createChain(this.createShapeVector(size));

        return cShape;
    }

    protected BodyDef createBodyDef(int col, int row, float size) {
        BodyDef bodyDef = new BodyDef();

        bodyDef.type = BodyType.StaticBody;
        bodyDef.position.set((col + 0.5f) * size, (row + 0.5f) * size);

        return bodyDef;
    }

    protected FixtureDef createFixtureDef(ChainShape cShape, short bits) {
        FixtureDef fixtureDef = new FixtureDef();

        fixtureDef.friction = 0.8f;
        fixtureDef.shape = cShape;
        fixtureDef.filter.categoryBits = bits;
        fixtureDef.filter.maskBits = Constants.BIT_PLAYER;
        fixtureDef.isSensor = false;

        return fixtureDef;
    }
}
