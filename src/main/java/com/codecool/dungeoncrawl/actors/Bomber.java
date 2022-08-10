package com.codecool.dungeoncrawl.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Bomber extends Actor {

    private String tileName = "sleep";

    public Bomber(Cell cell, int health, int attackPower, int defensePower) {
        super(cell, health, attackPower, defensePower);
    }


    @Override
    public String getTileName() {
        return tileName;
    }



    public void setTileName(String tileName) {
        this.tileName = tileName;
    }
}
