package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.actors.*;
import com.codecool.dungeoncrawl.logic.items.Box;
import com.codecool.dungeoncrawl.logic.items.Key;
import com.codecool.dungeoncrawl.logic.items.Shield;
import com.codecool.dungeoncrawl.logic.items.Sword;

import java.util.*;

public class GameMap {
    private final int width;
    private final int height;
    private final Cell[][] cells;
    private final List<Mob> mobs;
    private Player player;
    private Key blueKey;
    private Sword basicSword;
    private Shield shield;

    private Box box;

    private  int mapNumber = 0;

    public int getMapNumber() {
        return mapNumber;
    }

    public GameMap(int width, int height, CellType defaultCellType) {
        this.mapNumber++;
        this.width = width;
        this.height = height;
        this.mobs = new ArrayList<>();
        cells = new Cell[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells[x][y] = new Cell(this, x, y, defaultCellType);
            }
        }
    }

    public Cell getCell(int x, int y) {
        return cells[x][y];
    }

    public void setPlayer(Player player) {
        this.player = player;
    }


    public void appendMobs(Mob mob) {mobs.add(mob);}
    public List<Mob> getMobs() {return mobs;}


    public Player getPlayer() {
        return player;
    }

    public Key getKey() {
        return blueKey;
    }

    public void setKey(Key key) {
        this.blueKey = key;
    }

    public Sword getSword() {
        return basicSword;
    }

    public void setSword(Sword sword) {
        this.basicSword = sword;
    }
    public void setShield(Shield shield) {this.shield = shield;}
    public Shield getShield() { return shield;}



    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
