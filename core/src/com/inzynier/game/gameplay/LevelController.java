package com.inzynier.game.gameplay;

import com.inzynier.game.entities.Doors;
import com.inzynier.game.entities.Actor;
import com.inzynier.game.gameplay.map.LayerGeneratorInterface;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author mkoszowski
 */
public class LevelController {

    private final int FREE_SPACE = 90;
    private final int BEGIN_ROOM = 10;
    private final int NORMAL_ROOM = 20;
    private final int BOSS_ROOM = 30;
    private final int TREASURE_ROOM = 40;
    private final int HIDDEN_ROOM = 50;

    private int sizeField;
    private int amountRoom;
    private int currentAmountRoom;
    private String map;
    private Actor player;
    private int[][] numberRooms;
    private int[][] numberRoomsNoAmount;
    private Room[][] rooms;
    private ArrayList<Doors> listDoors;
    private LayerGeneratorInterface layerFactor;
    private int currentXPos = 0;
    private int currentYPos = 0;

    //<editor-fold defaultstate="collapsed" desc="Konstruktor">
    public LevelController(int sizeField, int amountRoom, String map, LayerGeneratorInterface layerFactory, Actor player) {
        this.currentAmountRoom = 0;
        this.sizeField = sizeField;
        this.amountRoom = amountRoom;
        this.map = map;
        this.layerFactor = layerFactory;
        this.listDoors = new ArrayList<Doors>();
        this.player = player;
        this.init();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="generate - generuje mapę liczbowa -> normalna">
    private void init() {
        this.rooms = new Room[this.sizeField][this.sizeField];
        this.generateNumberMap();
        this.generateRealMap();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getCurrentRoom - zwraca obecn pokój">
    public Room getCurrentRoom() {
        return this.rooms[this.currentXPos][this.currentYPos];
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="move - poruszanie się po tablicy">
    public void moveNorth() {
        this.currentXPos++;
    }

    public void moveSouth() {
        this.currentXPos--;
    }

    public void moveEast() {
        this.currentYPos++;
    }

    public void moveWest() {
        this.currentYPos--;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="generateNumberMap - towrzy mapę składającą się z liczb">
    private void generateNumberMap() {
        this.numberRooms = new int[this.sizeField][this.sizeField];
        this.numberRoomsNoAmount = new int[this.sizeField][this.sizeField];
        this.fillList();
        this.insertStartRoom();
        this.addRoom();
        this.addRoomAmout();
        this.addSpecialRoom();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="generateRealMap - generuje mapę składającą się z obiektów pokoi">
    private void generateRealMap() {
        int counterDoorList = 0;
        for (int i = 0; i < this.sizeField; i++) {
            for (int j = 0; j < this.sizeField; j++) {
                if ((this.numberRoomsNoAmount[i][j] == this.FREE_SPACE) || (this.numberRoomsNoAmount[i][j] == this.HIDDEN_ROOM)) {
                    this.rooms[i][j] = null;
                } else if (this.numberRoomsNoAmount[i][j] == this.NORMAL_ROOM) {
                    this.rooms[i][j] = new Room(this.map, this.player, this.listDoors.get(counterDoorList++), this.layerFactor, Room.RoomType.NORMAL_ROOM);
                } else if (this.numberRoomsNoAmount[i][j] == this.BOSS_ROOM) {
                    this.rooms[i][j] = new Room(this.map, this.player, this.listDoors.get(counterDoorList++), this.layerFactor, Room.RoomType.BOSS_ROOM);
                } else if (this.numberRoomsNoAmount[i][j] == this.TREASURE_ROOM) {
                    this.rooms[i][j] = new Room(this.map, this.player, this.listDoors.get(counterDoorList++), this.layerFactor, Room.RoomType.TREASURE_ROOM);
                } else if ((this.numberRoomsNoAmount[i][j] == this.BEGIN_ROOM)) {
                    this.rooms[i][j] = new Room(this.map, this.player, this.listDoors.get(counterDoorList++), this.layerFactor, Room.RoomType.BEGIN_ROOM);
                }
            }
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="fillList - uzupełnia planszę pustymi polami">
    private void fillList() {
        for (int i = 0; i < this.sizeField; i++) {
            for (int j = 0; j < this.sizeField; j++) {
                this.numberRooms[i][j] = this.FREE_SPACE;
            }
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="insertStartRoom - wstawia pokój na poczkątek">
    private void insertStartRoom() {
        int startI = this.sizeField / 2;
        int startJ = this.sizeField / 2;
        this.numberRooms[startI][startJ] = this.BEGIN_ROOM;
        this.currentAmountRoom = 1;
        this.analizeSmallField(startI, startJ, this.numberRooms);
        this.currentXPos = this.sizeField / 2;
        this.currentYPos = this.sizeField / 2;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="analizeSmallField - na podstawie środka ustawia pokoje">
    private void analizeSmallField(int centerI, int centerJ, int[][] fieldArray) {

        //<editor-fold defaultstate="collapsed" desc="Tworzy małe pole które może zostać poddane analize">
        int i = 0;
        int j = 0;
        int smallFieldSizeI = 3;
        int smallFieldSizeJ = 3;
        int cenI;
        int cenJ;
        boolean isTop = true;
        boolean isBot = true;
        boolean isLeft = true;
        boolean isRight = true;
        if ((centerI - 1) < 0) {
            smallFieldSizeI--;
            isTop = false;
        }
        if ((centerI + 1) >= this.sizeField) {
            smallFieldSizeI--;
            isBot = false;
        }
        if ((centerJ - 1) < 0) {
            smallFieldSizeJ--;
            isLeft = false;
        }
        if ((centerJ + 1) >= this.sizeField) {
            smallFieldSizeJ--;
            isRight = false;
        }
        int[][] smallField = new int[smallFieldSizeI][smallFieldSizeJ];
        //<editor-fold defaultstate="collapsed" desc="Przepisuje dane do małego pola">
        //góra
        if (isTop) {
            if (isLeft) {
                smallField[i][j++] = fieldArray[centerI - 1][centerJ - 1];
            }
            smallField[i][j++] = fieldArray[centerI - 1][centerJ];
            if (isRight) {
                smallField[i][j++] = fieldArray[centerI - 1][centerJ + 1];
            }
            i++;
        }
        //środek
        j = 0;
        if (isLeft) {
            smallField[i][j++] = fieldArray[centerI][centerJ - 1];
        }
        cenI = i;
        cenJ = j;
        smallField[i][j++] = fieldArray[centerI][centerJ];
        if (isRight) {
            smallField[i][j++] = fieldArray[centerI][centerJ + 1];
        }
        //doł
        i++;
        j = 0;
        if (isBot) {
            if (isLeft) {
                smallField[i][j++] = fieldArray[centerI + 1][centerJ - 1];
            }
            smallField[i][j++] = fieldArray[centerI + 1][centerJ];
            if (isRight) {
                smallField[i][j++] = fieldArray[centerI + 1][centerJ + 1];
            }
        }
        //</editor-fold>
        //</editor-fold>

        this.analizeSmalField(smallField, smallFieldSizeI, smallFieldSizeJ, cenI, cenJ);
        this.checkCollides(smallField, smallFieldSizeI, smallFieldSizeJ);

        //<editor-fold defaultstate="collapsed" desc="wpisanie wygenerowanego pola do głownej mapy">
        i = 0;
        j = 0;
        if (isTop) {
            if (isLeft) {
                fieldArray[centerI - 1][centerJ - 1] = smallField[i][j++];
            }
            fieldArray[centerI - 1][centerJ] = smallField[i][j++];
            if (isRight) {
                fieldArray[centerI - 1][centerJ + 1] = smallField[i][j++];
            }
            i++;
        }
        //środek
        j = 0;
        if (isLeft) {
            fieldArray[centerI][centerJ - 1] = smallField[i][j++];
        }
        fieldArray[centerI][centerJ] = smallField[i][j++];
        if (isRight) {
            fieldArray[centerI][centerJ + 1] = smallField[i][j++];
        }
        //doł
        i++;
        j = 0;
        if (isBot) {
            if (isLeft) {
                fieldArray[centerI + 1][centerJ - 1] = smallField[i][j++];
            }
            fieldArray[centerI + 1][centerJ] = smallField[i][j++];
            if (isRight) {
                fieldArray[centerI + 1][centerJ + 1] = smallField[i][j++];
            }
        }
        //</editor-fold>
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="checkCollides - sprawdza czy pokoje nie stykają się">
    private void checkCollides(int[][] smallField, int sizeI, int sizeJ) {
        for (int i = 0; i < sizeI; i++) {
            for (int j = 0; j < sizeJ; j++) {
                if (smallField[i][j] == this.NORMAL_ROOM) {
                    try {
                        if (smallField[i + 1][j + 1] == this.NORMAL_ROOM) {
                            if (smallField[i][j + 1] == this.FREE_SPACE) {
                                smallField[i][j + 1] = this.HIDDEN_ROOM;
                            }
                            if (smallField[i + 1][j] == this.FREE_SPACE) {
                                smallField[i + 1][j] = this.HIDDEN_ROOM;
                            }
                        }
                    } catch (ArrayIndexOutOfBoundsException ex) {
                        //Exception programing!
                    }
                    try {
                        if (smallField[i + 1][j - 1] == this.NORMAL_ROOM) {
                            if (smallField[i][j - 1] == this.FREE_SPACE) {
                                smallField[i][j - 1] = this.HIDDEN_ROOM;
                            }
                            if (smallField[i + 1][j] == this.FREE_SPACE) {
                                smallField[i + 1][j] = this.HIDDEN_ROOM;
                            }
                        }
                    } catch (ArrayIndexOutOfBoundsException ex) {
                        //Exception programing!
                    }
                }
            }
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="analizeSmalField - usupełnia odpowiednio pole">
    private void analizeSmalField(int[][] smallField, int sizeI, int sizeJ, int centerI, int centerJ) {
        int[][] temp = new int[sizeI][sizeJ];
        this.copyArray(temp, smallField, sizeI, sizeJ);
        Random gen = new Random();
        int generatedRoom = 0;
        int notGeneratedCounter = 0;
        while (generatedRoom == 0) {
            this.copyArray(smallField, temp, sizeI, sizeJ);
            for (int i = 0; i < sizeI; i++) {
                for (int j = 0; j < sizeJ; j++) {
                    if ((i == centerI) || (j == centerJ)) {
                        if (smallField[i][j] == this.FREE_SPACE) {
                            int rand = gen.nextInt(2);
                            if (rand == 0) {
                                if (this.currentAmountRoom < this.amountRoom) {
                                    smallField[i][j] = this.NORMAL_ROOM;
                                    this.currentAmountRoom++;
                                }
                                generatedRoom++;
                            } else {
                                smallField[i][j] = this.HIDDEN_ROOM;
                            }
                        }
                    }
                }
            }
            notGeneratedCounter++;
            if (notGeneratedCounter == 60) {
                generatedRoom++;
            }
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="copyArray - przepisuje tablice do innej">
    private void copyArray(int[][] to, int[][] from, int sizeI, int sizeJ) {
        for (int i = 0; i < sizeI; i++) {
            for (int j = 0; j < sizeJ; j++) {
                to[i][j] = from[i][j];
            }
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="addRoom - dodaje pokoje do stworzonej planszy">
    private void addRoom() {
        Random gen = new Random();
        int num = 0;
        int iterationAmount = 0;
        List<Integer> listI = new ArrayList<Integer>();
        List<Integer> listJ = new ArrayList<Integer>();
        int[][] temp = new int[this.sizeField][this.sizeField];
        this.copyArray(temp, this.numberRooms, this.sizeField, this.sizeField);
        while ((this.currentAmountRoom < this.amountRoom) && (iterationAmount < (this.sizeField * this.sizeField * this.amountRoom)) && (this.amountRoom < (sizeField * sizeField))) {
            for (int i = 0; i < this.sizeField; i++) {
                for (int j = 0; j < this.sizeField; j++) {
                    if (this.numberRooms[i][j] == this.NORMAL_ROOM) {
                        listI.add(i);
                        listJ.add(j);
                    }
                }
            }
            num = gen.nextInt(listI.size());
            this.analizeSmallField(listI.get(num), listJ.get(num), temp);
            this.copyArray(this.numberRooms, temp, this.sizeField, this.sizeField);
            iterationAmount++;
        }
        this.copyArray(this.numberRoomsNoAmount, this.numberRooms, this.sizeField, this.sizeField);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="addRoomAmout - dodaje liczbe siąsiadujących pokoi">
    private void addRoomAmout() {
        int[][] temp = new int[this.sizeField][this.sizeField];
        this.copyArray(temp, this.numberRooms, this.sizeField, this.sizeField);
        for (int i = 0; i < this.sizeField; i++) {
            for (int j = 0; j < this.sizeField; j++) {
                if ((this.numberRooms[i][j] == this.NORMAL_ROOM) || (this.numberRooms[i][j] == this.BEGIN_ROOM)) {
                    this.checkNeighbor(temp, i, j);
                }
            }
        }
        this.copyArray(this.numberRooms, temp, this.sizeField, this.sizeField);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="checkNeighbor - sprawdza ile dany pokój ma przyległych do siebie pokoi">
    private void checkNeighbor(int[][] tempArray, int i, int j) {
        boolean east = false;
        boolean west = false;
        boolean north = false;
        boolean south = false;
        try {
            //góra
            if ((this.numberRooms[i - 1][j] == this.NORMAL_ROOM) || (this.numberRooms[i - 1][j] == this.BEGIN_ROOM)) {
                tempArray[i][j]++;
                north = true;
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            //Exception programing!
        }
        try {
            //dół
            if ((this.numberRooms[i + 1][j] == this.NORMAL_ROOM) || (this.numberRooms[i + 1][j] == this.BEGIN_ROOM)) {
                tempArray[i][j]++;
                south = true;
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            //Exception programing!
        }
        try {
            //lewo
            if ((this.numberRooms[i][j - 1] == this.NORMAL_ROOM) || (this.numberRooms[i][j - 1] == this.BEGIN_ROOM)) {
                tempArray[i][j]++;
                west = true;
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            //Exception programing!
        }
        try {
            //prawo
            if ((this.numberRooms[i][j + 1] == this.NORMAL_ROOM) || (this.numberRooms[i][j + 1] == this.BEGIN_ROOM)) {
                tempArray[i][j]++;
                east = true;
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            //Exception programing!
        }
        this.listDoors.add(new Doors(north, south, east, west));
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="addSpecialRoom - dodaje specjalnie pokoje">
    private void addSpecialRoom() {
        List<Integer> listI = new ArrayList<Integer>();
        List<Integer> listJ = new ArrayList<Integer>();
        for (int i = 0; i < this.sizeField; i++) {
            for (int j = 0; j < this.sizeField; j++) {
                if (this.numberRooms[i][j] == (this.NORMAL_ROOM + 1)) {
                    listI.add(i);
                    listJ.add(j);
                }
            }
        }
        Random gen = new Random();
        int num = gen.nextInt(listI.size());
        //Dodanie pokoju z bosem
        this.numberRoomsNoAmount[listI.get(num)][listJ.get(num)] = this.BOSS_ROOM;
        listI.remove(num);
        listJ.remove(num);
        num = gen.nextInt(listI.size());
        //Dodanie złotego pokoju
        this.numberRoomsNoAmount[listI.get(num)][listJ.get(num)] = this.TREASURE_ROOM;
    }
    //</editor-fold>
}
