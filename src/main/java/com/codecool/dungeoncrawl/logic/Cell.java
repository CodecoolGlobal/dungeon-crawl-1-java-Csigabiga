package com.codecool.dungeoncrawl.logic;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.actors.Actor;

import java.io.Serializable;

public class Cell implements Drawable, Serializable {
    private CellType type;
    private Actor actor;
    private final GameMap gameMap;

    private Item item;
    private final int x, y;

    Cell(GameMap gameMap, int x, int y, CellType type) {
        this.gameMap = gameMap;
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public CellType getType() {
        return type;
    }

    public void setType(CellType type) {
        this.type = type;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public Actor getActor() {
        return actor;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Cell getNeighbor(int dx, int dy) {
        try {
            return gameMap.getCell(x + dx, y + dy);
        } catch (Exception exception) { return  null;}
    }

    @Override
    public String getTileName() {
        return type.getTileName();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
