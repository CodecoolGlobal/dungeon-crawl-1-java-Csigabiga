package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Drawable;

import java.util.ArrayList;

public abstract class Item implements Drawable {
    private Cell cell;
    private ArrayList<Item> items = new ArrayList<>();

    public Item(Cell cell) {
        this.cell = cell;
        this.cell.setItem(this);
    }

    public Cell getCell() {
        return cell;
    }

    public void addToInventory(Item item) {
        items.add(item);
    }
}
