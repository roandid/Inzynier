package com.inzynier.game.contact.handlers;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.inzynier.game.contact.ActionsDispatcher;
import com.inzynier.game.contact.ContactHandlerInterface;
import com.inzynier.game.contact.actions.ChangeRoomAction;
import com.inzynier.game.entities.Actor;
import com.inzynier.game.entities.Door;

public class DoorActorContactHandler implements ContactHandlerInterface {

    @Override
    public boolean support(Contact contact) {
        Body a = contact.getFixtureA().getBody();
        Body b = contact.getFixtureB().getBody();

        if (a.getUserData() instanceof Actor && b.getUserData() instanceof Door) {
            return ((Actor) a.getUserData()).isPlayer();
        }

        if (a.getUserData() instanceof Door && b.getUserData() instanceof Actor) {
            return ((Actor) b.getUserData()).isPlayer();
        }

        return false;
    }

    @Override
    public void beginContact(Contact cntct) {
        Door door = this.getDoors(cntct.getFixtureA().getBody(), cntct.getFixtureB().getBody());

        if (!door.isOpen()) {
            return;
        }

        ActionsDispatcher.addAction(new ChangeRoomAction(door.getPosition()));
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

    protected Door getDoors(Body a, Body b) {
        if (a.getUserData() instanceof Door) {
            return (Door) a.getUserData();
        }

        return (Door) b.getUserData();
    }
}
