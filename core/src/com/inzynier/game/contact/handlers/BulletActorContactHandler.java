package com.inzynier.game.contact.handlers;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.inzynier.game.contact.ActionsDispatcher;
import com.inzynier.game.contact.ContactHandlerInterface;
import com.inzynier.game.contact.actions.DestroyAction;
import com.inzynier.game.entities.Actor;
import com.inzynier.game.entities.Bullet;

public class BulletActorContactHandler implements ContactHandlerInterface {

    @Override
    public boolean support(Contact contact) {
        Body a = contact.getFixtureA().getBody();
        Body b = contact.getFixtureB().getBody();

        if (a.getUserData() instanceof Actor && b.getUserData() instanceof Bullet) {
            return true;
        }

        if (a.getUserData() instanceof Bullet && b.getUserData() instanceof Actor) {
            return true;
        }

        return false;
    }

    @Override
    public void beginContact(Contact cntct) {
        Body a = cntct.getFixtureA().getBody();
        Body b = cntct.getFixtureB().getBody();

        if (a.getUserData() instanceof Actor && b.getUserData() instanceof Bullet) {
            this.handle((Actor) a.getUserData(), (Bullet) b.getUserData());

            return;
        }

        this.handle((Actor) b.getUserData(), (Bullet) a.getUserData());
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

    protected void handle(Actor actor, Bullet bullet) {
        if (bullet.getOwner() == actor) {
            return;
        }

        actor.hit(bullet.getOwner().getRangedPower());
        ActionsDispatcher.addAction(new DestroyAction(bullet.getBody()));

        if (actor.isDead()) {
            ActionsDispatcher.addAction(new DestroyAction(actor.getBody()));
        }
    }
}
