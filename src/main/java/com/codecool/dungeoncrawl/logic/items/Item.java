package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Drawable;

import java.io.Serializable;

public abstract class Item implements Drawable, Serializable {
    private final Cell cell;
    protected String name;
    private int bonusStat;

    private double specialBonus;
    private boolean toBeDeleted = false;

    public Item(Cell cell, String name, int bonusStat, double specialBonus) {
        this.cell = cell;
        this.name = name;
        this.bonusStat = bonusStat;
        this.specialBonus = specialBonus;
        this.cell.setItem(this);
    }

    public Cell getCell() {
        return cell;
    }
    public int getBonus() {return bonusStat;}

    public double getSpecialBonus() {return specialBonus;}
    public boolean isToBeDeleted() {
        return toBeDeleted;
    }
    public void setToBeDeleted(boolean toBeDeleted) {
        this.toBeDeleted = toBeDeleted;
    }
}
