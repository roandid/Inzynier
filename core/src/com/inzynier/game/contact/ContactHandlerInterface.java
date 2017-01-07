package com.inzynier.game.contact;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactListener;

public interface ContactHandlerInterface extends ContactListener {

    public boolean support(Contact contact);
}
