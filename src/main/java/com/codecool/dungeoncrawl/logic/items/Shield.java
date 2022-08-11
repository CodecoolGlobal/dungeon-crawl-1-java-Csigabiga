package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Shield extends Item{

    public Shield(Cell cell, String name, int bonusStat, double specialBonus) {
        super(cell, name, bonusStat, specialBonus);
    }

    @Override
    public String getTileName() {
        return "shield";
    }

}
