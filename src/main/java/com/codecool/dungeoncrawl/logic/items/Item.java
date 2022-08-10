package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Drawable;

public abstract class Item implements Drawable {
    private Cell cell;
    private int bonusStat;

    public Item(Cell cell, int bonusStat) {
        this.cell = cell;
        this.bonusStat = bonusStat;
        this.cell.setItem(this);
    }

    public Cell getCell() {
        return cell;
    }
    public int getBonus() {return bonusStat;}
}
