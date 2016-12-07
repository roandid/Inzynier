/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inzynier.game;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.inzynier.game.entities.InterfaceBullet;
import java.util.List;

/**
 *
 * @author M.Koszowski
 */
public class MyContactListener implements ContactListener {

    List<InterfaceBullet> listBullet;

    public void setListBullet(List<InterfaceBullet> listBullet) {
        this.listBullet = listBullet;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();
        if ((b.getBody().getUserData().equals("bullet")) && (a.getBody().getUserData() == null)) {
            for (int i = 0; i < this.listBullet.size(); i++) {
                if (this.listBullet.get(i).getBody().equals(b.getBody())) {
                    this.listBullet.remove(i);
                }
            }
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
