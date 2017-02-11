package com.inzynier.game.contact.actions;

import com.badlogic.gdx.physics.box2d.Body;
import com.inzynier.game.contact.ContactActionInterface;
import com.inzynier.game.gameplay.LevelController;

public class DestroyAction implements ContactActionInterface {

    protected Body body;

    public DestroyAction(Body body) {
        this.body = body;
    }

    @Override
    public void doAction(LevelController levelController) {
        if (this.body == null) {
            return;
        }

        if (!this.body.isActive()) {
            return;
        }

        this.body.getWorld().destroyBody(this.body);
    }
}
