package com.codecool.dungeoncrawl.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;

public class Bomber extends Mob{


    public Bomber(Cell cell, int health, int attackPower, int defensePower) {
        super(cell, health, attackPower, defensePower, "sleep");
    }


    @Override
    public void mobRound() {
        Actor player = lookingForPlayer();
        if (player != null) {
            switch (tileName) {
                case "sleep":
                    if (player.getTileName().equals("player")) {
                        tileName = "ready";
                    }
                    break;
                case "ready":
                    tileName = "explode";
                    if (player.getTileName().equals("player")) {
                        dealDamage(attackPower, player);
                    }
                    break;
                case "explode":
                    health = 0;
                    tileName = "floor";
                    cell.setType(CellType.FLOOR);
                    cell.setActor(null);
                    break;
            }
        } else {
            if (tileName.equals("ready")) {
                tileName = "explode";
            } else if (tileName.equals("explode")) {
                health = 0;
                tileName = "floor";
                cell.setType(CellType.FLOOR);
                cell.setActor(null);
            }
        }
    }
}

