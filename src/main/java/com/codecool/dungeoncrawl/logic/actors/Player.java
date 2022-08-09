package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Player extends Actor {
    public Player(Cell cell) {
        super(cell);
        this.setHealth(10);
        this.setAttackPower(3);
        this.setDefensePower(1);
    }

    public String getTileName() {
        return "player";
    }
}
