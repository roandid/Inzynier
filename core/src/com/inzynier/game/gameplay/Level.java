package com.inzynier.game.gameplay;

import com.inzynier.game.entities.Actor;
import com.inzynier.game.gameplay.map.LayerGenerator;
import com.inzynier.game.gameplay.map.LayerGeneratorInterface;
import com.inzynier.game.gameplay.map.ObjectGenerator;
import com.inzynier.game.gameplay.map.ObjectGeneratorInterface;

public class Level {

    protected Actor player;
    protected LayerGeneratorInterface layerFactory;
    protected ObjectGeneratorInterface objectFactory;
    protected Room room;
    protected LevelController levelController;

    /**
     * Prawdopodobnie powinna być przekazana dodatkowo lista dostępnych stworków
     * na dany poziom, prefix do tekstur itd - możliwe że niektóre z tych rzeczy
     * powinniśmy przekazywać w init
     */
    public Level(Actor player, int sizeField, int amountRoom) {
        this.player = player;
        this.layerFactory = new LayerGenerator();
        this.objectFactory = new ObjectGenerator();
        this.levelController = new LevelController(
            sizeField,
            amountRoom,
            this.layerFactory,
            this.objectFactory, player
        );
    }

    public void init() {
        this.levelController.getBeginRoom();
    }

    public void run(float dt) {
        this.levelController.getCurrentRoom().run(dt);
    }

    public boolean isOver() {
        Room room = this.levelController.getCurrentRoom();
        return room.isClear() && room.getType() == Room.RoomType.BOSS_ROOM;
    }
}
