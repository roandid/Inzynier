package com.inzynier.game.contact;

import java.util.ArrayList;
import java.util.List;

public class ActionsDispatcher {

    protected static List<ContactActionInterface> actions = new ArrayList<ContactActionInterface>();

    public static void addAction(ContactActionInterface action) {
        ActionsDispatcher.actions.add(action);
    }

    public static void dispatch() {
        for (ContactActionInterface action : ActionsDispatcher.actions) {
            action.doAction();
        }

        ActionsDispatcher.actions.clear();
    }
}
