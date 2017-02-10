package com.inzynier.game.contact.actions;

import com.inzynier.game.contact.ContactActionInterface;
import com.inzynier.game.entities.Door.Position;
import com.inzynier.game.gameplay.LevelController;

public class ChangeRoomAction implements ContactActionInterface {

    protected Position position;

    public ChangeRoomAction(Position position) {
        this.position = position;
    }

    @Override
    public void doAction(LevelController levelController) {
        switch (this.position) {
            case NORTH:
                levelController.moveSouth();
                break;
            case SOUTH:
                levelController.moveNorth();
                break;
            case EAST:
                levelController.moveEast();
                break;
            case WEST:
                levelController.moveWest();
                break;
        }
    }
}
