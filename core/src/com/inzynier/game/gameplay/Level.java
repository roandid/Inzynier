package com.inzynier.game.gameplay;

import com.inzynier.game.entities.Doors;
import com.inzynier.game.entities.Player;
import com.inzynier.game.entities.Position;
import com.inzynier.game.gameplay.map.LayerFactory;
import com.inzynier.game.gameplay.map.LayerFactoryInterface;

public class Level {

    protected Player player;
    protected LayerFactoryInterface layerFactory;
    protected Room room;

    /**
     * Prawdopodobnie powinna być przekazana dodatkowo lista dostępnych stworków
     * na dany poziom, prefix do tekstur itd - możliwe że niektóre z tych rzeczy
     * powinniśmy przekazywać w init
     */
    public Level(Player player) {
        this.player = player;
        this.layerFactory = new LayerFactory();
    }

    public void init() {
        // Rozmieszczenie wszystkich pomieszczeń itd
        this.room = new Room("maps/tester.tmx", this.player, new Doors(false, false, false, false), this.layerFactory);
        this.room.init();
        this.room.wakeUp(Position.DOWN);
    }

    public void run(float dt) {
        this.room.run(dt);
    }
}
