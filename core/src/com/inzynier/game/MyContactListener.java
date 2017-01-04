package com.inzynier.game;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.inzynier.game.contact.ContactHandlerInterface;

public class MyContactListener implements ContactListener {

    protected ContactHandlerInterface[] handlers;

    public MyContactListener(ContactHandlerInterface[] handlers) {
        this.handlers = handlers;
    }

    @Override
    public void beginContact(Contact contact) {

        this.getHandler(contact).beginContact(contact);
    }

    @Override
    public void endContact(Contact cntct) {

        this.getHandler(cntct).endContact(cntct);
    }

    @Override
    public void preSolve(Contact cntct, Manifold mnfld) {

        this.getHandler(cntct).preSolve(cntct, mnfld);
    }

    @Override
    public void postSolve(Contact cntct, ContactImpulse ci) {

        this.getHandler(cntct).postSolve(cntct, ci);
    }

    protected ContactHandlerInterface getHandler(Contact contact) {
        for (int i = 0; i < this.handlers.length; ++i) {
            if (handlers[i].support(contact)) {
                return this.handlers[i];
            }
        }

        throw new RuntimeException("Handler for contact not found.");
    }
}
