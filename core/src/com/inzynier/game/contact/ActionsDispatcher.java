package com.inzynier.game.contact;

import com.inzynier.game.gameplay.LevelController;
import java.util.ArrayList;
import java.util.List;

public class ActionsDispatcher {

    protected static List<ContactActionInterface> actions = new ArrayList<ContactActionInterface>();

    public static void addAction(ContactActionInterface action) {
        ActionsDispatcher.actions.add(action);
    }

    public static void dispatch(LevelController levelController) {
        for (ContactActionInterface action : ActionsDispatcher.actions) {
            action.doAction(levelController);
        }

        ActionsDispatcher.actions.clear();
    }
}
