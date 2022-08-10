package com.codecool.dungeoncrawl.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Skeleton extends Actor {
    private String name = "skeleton0";
    public Skeleton(Cell cell, int health) {
        super(cell, health);
        setAttackPower(2);
        setDefensePower(0);
    }

    @Override
    public String getTileName() {
        return name;
    }
    public void setTileName(String name) {this.name=name;}
}
