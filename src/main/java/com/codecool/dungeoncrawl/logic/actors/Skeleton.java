package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Skeleton extends Actor {
    public Skeleton(Cell cell) {
        super(cell);
        this.setHealth(10);
        setAttackPower(2);
        setDefensePower(0);
    }

    @Override
    public String getTileName() {
        return "skeleton";
    }
}
