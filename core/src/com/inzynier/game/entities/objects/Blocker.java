package com.inzynier.game.entities.objects;

import com.badlogic.gdx.physics.box2d.Body;
import com.inzynier.game.entities.objects.TiledObject;

/**
 * Created by Krzysztof on 11.01.2017.
 */
public class Blocker extends TiledObject {
    private Body body;

    public Blocker(Body body) {
        this.body = body;
    }
    public Body getBody(){return body;}
}
