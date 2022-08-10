package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Shield extends Item{
    private double specialBonus;

    public Shield(Cell cell, int bonusStat, double specialBonus) {
        super(cell, bonusStat);
        this.specialBonus = specialBonus;
    }

    @Override
    public String getTileName() {
        return "shield";
    }

    public double getSpecialBonus() {return specialBonus;}
}
