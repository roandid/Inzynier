package com.inzynier.game.contact.handlers;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.inzynier.game.contact.ContactHandlerInterface;
import com.inzynier.game.entities.Actor;

public class PlayerEnemyContactHandler implements ContactHandlerInterface {

    @Override
    public boolean support(Contact contact) {
        Body a = contact.getFixtureA().getBody();
        Body b = contact.getFixtureB().getBody();

        if (a.getUserData() instanceof Actor && b.getUserData() instanceof Actor) {
            return ((Actor) a.getUserData()).isPlayer() || ((Actor) b.getUserData()).isPlayer();
        }

        return false;
    }

    @Override
    public void beginContact(Contact cntct) {
        Actor a = (Actor) cntct.getFixtureA().getBody().getUserData();
        Actor b = (Actor) cntct.getFixtureB().getBody().getUserData();

        if (a.isPlayer()) {
            a.hit(b.getContactPower());

            return;
        }

        b.hit(a.getContactPower());
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
