package com.inzynier.game.contact.handlers;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.inzynier.game.contact.ActionsDispatcher;
import com.inzynier.game.contact.ContactHandlerInterface;
import com.inzynier.game.contact.actions.DestroyAction;
import com.inzynier.game.entities.Bullet;

public class BulletContactHandler implements ContactHandlerInterface {

    @Override
    public boolean support(Contact contact) {
        Body a = contact.getFixtureA().getBody();
        Body b = contact.getFixtureB().getBody();

        if (a.getUserData() instanceof Bullet && null == b.getUserData()) {
            return true;
        }

        if (b.getUserData() instanceof Bullet && null == a.getUserData()) {
            return true;
        }

        if (a.getUserData() instanceof Bullet && b.getUserData() instanceof Bullet) {
            return true;
        }

        return false;
    }

    @Override
    public void beginContact(Contact cntct) {
        Body a = cntct.getFixtureA().getBody();
        Body b = cntct.getFixtureB().getBody();

        if (a.getUserData() instanceof Bullet) {
            ActionsDispatcher.addAction(new DestroyAction(a));
        }

        if (b.getUserData() instanceof Bullet) {
            ActionsDispatcher.addAction(new DestroyAction(b));
        }
    }

    @Override
    public void endContact(Contact cntct) {
    }

    @Override
    public void preSolve(Contact cntct, Manifold mnfld) {
    }

    @Override
    public void postSolve(Contact cntct, ContactImpulse ci) {
    }
}
