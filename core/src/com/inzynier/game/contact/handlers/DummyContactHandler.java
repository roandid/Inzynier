package com.inzynier.game.contact.handlers;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.inzynier.game.contact.ContactHandlerInterface;

/**
 * Po to żeby nie wywalać exceptiona, w przyszłosci pomoże znaleźć nie
 * zaimplementowane handlery
 */
public class DummyContactHandler implements ContactHandlerInterface {

    @Override
    public boolean support(Contact contact) {
        return true;
    }

    @Override
    public void beginContact(Contact cntct) {
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
