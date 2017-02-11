package com.inzynier.game;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.inzynier.game.contact.ContactHandlerInterface;
import com.inzynier.game.contact.handlers.BulletActorContactHandler;
import com.inzynier.game.contact.handlers.BulletContactHandler;
import com.inzynier.game.contact.handlers.DoorActorContactHandler;
import com.inzynier.game.contact.handlers.DummyContactHandler;
import com.inzynier.game.contact.handlers.PlayerEnemyContactHandler;
import java.util.ArrayList;
import java.util.List;

public class MyContactListener implements ContactListener {

    protected List<ContactHandlerInterface> handlers;
    protected List<Body> toDestroy;
    protected static MyContactListener instance;

    public static MyContactListener getListener() {
        if (MyContactListener.instance instanceof MyContactListener) {
            return MyContactListener.instance;
        }

        List<ContactHandlerInterface> handlers = new ArrayList<ContactHandlerInterface>();
        handlers.add(new BulletActorContactHandler());
        handlers.add(new BulletContactHandler());
        handlers.add(new PlayerEnemyContactHandler());
        handlers.add(new DoorActorContactHandler());
        handlers.add(new DummyContactHandler());

        MyContactListener.instance = new MyContactListener(handlers);

        return MyContactListener.instance;
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

    protected MyContactListener(List<ContactHandlerInterface> handlers) {
        this.handlers = handlers;
    }

    protected ContactHandlerInterface getHandler(Contact contact) {
        for (int i = 0; i < this.handlers.size(); ++i) {
            if (handlers.get(i).support(contact)) {
                return this.handlers.get(i);
            }
        }

        throw new RuntimeException("Handler for contact not found.");
    }
}
