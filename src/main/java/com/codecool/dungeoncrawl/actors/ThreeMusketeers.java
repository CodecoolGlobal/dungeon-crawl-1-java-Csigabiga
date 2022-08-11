package com.codecool.dungeoncrawl.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class ThreeMusketeers extends Mob{


    public ThreeMusketeers(Cell cell, int health, int attackPower, int defensePower) {
        super(cell, health, attackPower, defensePower, "musketeer");
    }


    @Override
    public void mobRound() {
        Actor player = lookingForPlayer();
        boolean found = false;
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                Actor playerNear = isPlayerNear(7 - i, 7 - j);
                if (playerNear != null) {
                    if (playerNear.getTileName().equals("player")) {
                        int xDiff = playerNear.getX() - getX();
                        int yDiff = playerNear.getY() - getY();
                        move(Integer.signum(xDiff), 0);
                        move(0, Integer.signum(yDiff));
                        found = true;
                        break;
                    }
                }
            }
            if (found) {
                break;
            }
        }
        if (player != null) {
            dealDamage(attackPower, player);
        }
    }


    private Actor isPlayerNear(int x, int y) {
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (getCell().getX() + x + i >= 0 && getCell().getX() + x + i <= 24 && getCell().getY() + y + j >= 0 && getCell().getY() + y + j <= 21) {
                    if (getCell().getNeighbor(x + i, y + j).getActor() != null) {
                        if (getCell().getNeighbor(x + i, y + j).getActor().getTileName().equals("player")) {
                            return getCell().getNeighbor(x + i, y + j).getActor();
                        }
                    }
                }
            }
        }
        return null;
    }
}
