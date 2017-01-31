package com.inzynier.game;

public class Constants {

    public static final int SCALE = 10;

    public static final short VIEWPORT_WIDTH = 6;
    public static final short VIEWPORT_HEIGHT = 3;

    //do test�w
    public static final short PPM = 100;

    //u�ywane do filtrowania kolizji
    public static final short BIT_PLAYER = 2;
    public static final short BIT_GROUND = 4;
    public static final short BIT_WALL = 8;
    public static final short BIT_BULLET = 16;
    public static final short BIT_ENEMY = 32;

    public static float toBox2d(float x) {
        return x / Constants.SCALE;
    }

    public static float fromBox2d(float x) {
        return x * Constants.SCALE;
    }
}
