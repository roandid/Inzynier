package com.inzynier.game.gameplay;

import java.util.Random;

public class MapStorage {

    final private static String[] NORMAL_ROOMS = new String[]{
        "maps/normal_1.tmx",
        "maps/normal_2.tmx",
        "maps/normal_3.tmx",
        "maps/normal_4.tmx",
        "maps/normal_5.tmx"
    };
    final private static String[] BOSS_ROOMS = new String[]{
        "maps/boss_1.tmx",
        "maps/boss_2.tmx",
        "maps/boss_3.tmx"
    };
    final private static Random GENERATOR = new Random();

    public static String getRandomNormalRoom() {

        return NORMAL_ROOMS[GENERATOR.nextInt(NORMAL_ROOMS.length)];
    }

    public static String getRandomBossMap() {

        return BOSS_ROOMS[GENERATOR.nextInt(BOSS_ROOMS.length)];
    }

    public static String getStartMap() {
        return "maps/empty.tmx";
    }
}
