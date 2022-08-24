package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.actors.*;
import com.codecool.dungeoncrawl.logic.items.*;

import java.util.*;

public class GameMap {
    private final int width;
    private final int height;
    private Cell[][] cells;
    private final List<Mob> mobs;
    private final List<Item> items;
    private Player player;

    public GameMap(int width, int height, CellType defaultCellType) {
        this.width = width;
        this.height = height;
        this.mobs = new ArrayList<>();
        this.items = new ArrayList<>();
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


    public void appendItems(Item item) {items.add(item);}
    public void removeItem(Item item) {items.remove(item);}


    public Player getPlayer() {
        return player;
    }


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
