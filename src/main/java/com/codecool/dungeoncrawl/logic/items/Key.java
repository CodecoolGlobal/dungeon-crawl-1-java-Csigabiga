package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Key extends Item {
    public Key(Cell cell, String name, int bonusStat, double specialBonus) {
        super(cell, name, bonusStat, specialBonus);
    }

    @Override
    public String getTileName() {
        return "key";
    }
}
