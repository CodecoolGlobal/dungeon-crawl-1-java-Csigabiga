package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Key extends Item {
    public Key(Cell cell, int bonusStat) {
        super(cell, bonusStat);
    }

    @Override
    public String getTileName() {
        return "key";
    }
}
