package com.codecool.dungeoncrawl.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class StaticMob extends Actor {

    private String status = "sleep";

    public StaticMob(Cell cell, int health) {
        super(cell, health);
    }


    @Override
    public String getTileName() {
        return status;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
