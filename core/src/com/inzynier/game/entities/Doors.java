package com.inzynier.game.entities;

public class Doors {

    protected boolean north;
    protected boolean south;
    protected boolean east;
    protected boolean west;

    public Doors(boolean north, boolean south, boolean east, boolean west) {
        this.north = north;
        this.south = south;
        this.east = east;
        this.west = west;
    }

    public boolean isOnNorth() {
        return this.north;
    }

    public boolean isOnSouth() {
        return this.south;
    }

    public boolean isOnEast() {
        return this.east;
    }

    public boolean isOnWest() {
        return this.west;
    }
}
