package com.codecool.dungeoncrawl.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.Drawable;

import java.util.Objects;

public abstract class Actor implements Drawable {
    private Cell cell;
    private int health;
    private int defensePower;
    private int attackPower;

    public Actor(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
    }

    public void move(int dx, int dy) {
        Cell nextCell = cell.getNeighbor(dx, dy);
        if (nextCell.getType().getTileName().matches("floor|floor1|floor2|corpse") &&
             nextCell.getActor() == null) {
            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
        }

    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {this.health = health;}

    public Cell getCell() {
        return cell;
    }

    public int getX() {
        return cell.getX();
    }

    public int getY() {
        return cell.getY();
    }

    public void setDefensePower(int defensePower) {this.defensePower = defensePower;}

    public int getDefensePower() {return defensePower;}

    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    public int getAttackPower() {
        return attackPower;
    }
}
