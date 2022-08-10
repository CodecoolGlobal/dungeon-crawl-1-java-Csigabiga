package com.codecool.dungeoncrawl.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class ThreeMusketeers extends Actor{
    private String tileName = "musketeer";
    public ThreeMusketeers(Cell cell, int health, int attackPower, int defensePower) {
        super(cell, health, attackPower, defensePower);
    }

    @Override
    public String getTileName() {
        return tileName;
    }

    public void setTileName(String tileName) {this.tileName = tileName;}
}
