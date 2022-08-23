package com.codecool.dungeoncrawl.actors;

import com.codecool.dungeoncrawl.logic.Cell;

import java.util.Random;

public class JumperGhost extends Mob {

    Random random = new Random();
    private int x, y;
    private int round = 1;
    private  boolean move;

    public JumperGhost(Cell cell, int health, int attackPower, int defensePower) {
        super(cell, health, attackPower, defensePower, "jumperGhost");
        this.x = getX();
        this.y = getY();

    }

    @Override
    public boolean move(int dx, int dy) {
        if (getX() + dx < 21 && getX() + dx >= 0 && getY() + dy < 21 && getY() + dy >= 0) {
            Cell nextCell = cell.getNeighbor(dx, dy);
            if (nextCell.getActor() == null) {
                cell.setActor(null);
                nextCell.setActor(this);
                cell = nextCell;
                return true;
            }
        }
        return false;
    }

    private int randomCoordinate() {
        return random.nextInt(3);
    }


    @Override
    public void mobRound() {
        switch (round) {
            case 1:
                x = getX();
                y = getY();
                move = false;
                int modifier = 0;
                while (!move) {
                    move = move(modifier - x, -y);
                    modifier++;
                }
                tileName = "orangeWall";
                round = 2;
                break;
            case 2:
                switch (random.nextInt(4)) {
                    case 0:
                        move = move(x + randomCoordinate(), y - randomCoordinate());
                        break;
                    case 1:
                        move = move(x + randomCoordinate(), y + randomCoordinate());
                        break;
                    case 2:
                        move = move(x - randomCoordinate(), y + randomCoordinate());
                        break;
                    case 3:
                        move = move(x - randomCoordinate(), y - randomCoordinate());
                        break;
                }
                if (move) {
                    tileName = "jumperGhost";
                    round = 3;
                }
                break;
            case 3:
                tileName = "jumperGhost1";
                Actor player = lookingForPlayer();
                if (player != null) {
                    if (player.getTileName().equals("player")) {
                        dealDamage(attackPower, player);
                    }
                }
                round = 4;
                break;
            case 4:
                tileName = "jumperGhost2";
                round = 1;
                break;
        }
    }
}
