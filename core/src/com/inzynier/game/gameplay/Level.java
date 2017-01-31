package com.inzynier.game.gameplay;

import com.inzynier.game.entities.Doors;
import com.inzynier.game.entities.Player;
import com.inzynier.game.entities.Position;
import com.inzynier.game.gameplay.map.LayerGenerator;
import com.inzynier.game.gameplay.map.LayerGeneratorInterface;

public class Level {

    protected Player player;
    protected LayerGeneratorInterface layerFactory;
    protected Room room;
    protected LevelController levelController;

    /**
     * Prawdopodobnie powinna być przekazana dodatkowo lista dostępnych stworków
     * na dany poziom, prefix do tekstur itd - możliwe że niektóre z tych rzeczy
     * powinniśmy przekazywać w init
     */
    public Level(Player player, int sizeField, int amountRoom) {
        this.player = player;
        this.layerFactory = new LayerGenerator();
        //Tworzy się generator a w nim tablica z pokojami 
        this.levelController = new LevelController(sizeField, amountRoom, "maps/tester.tmx", this.layerFactory, player);
    }

    public void init() {
        // Rozmieszczenie wszystkich pomieszczeń itd
        //Pobranie obecnego pokoju
        this.room = this.levelController.getCurrentRoom();
        
        //jakaś magia
        this.room.init();
        this.room.wakeUp(Position.DOWN);

        //Potrzebny jest słuchacz gdzie wlezie postać i rakcja generatora na to
        //czyli przemieszczenie obecnego pokoju i ponowne go pobranie 
//        this.generator.moveEast();
        
    }

    public void run(float dt) {
        this.room.run(dt);
    }
}
