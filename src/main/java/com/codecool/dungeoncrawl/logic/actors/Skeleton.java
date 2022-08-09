package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Skeleton extends Actor {
    public Skeleton(Cell cell) {
        super(cell);
        this.setHealth(5);
        setAttackPower(3);
        setDefensePower(0);
    }

    @Override
    public String getTileName() {
        return "skeleton";
    }
}
