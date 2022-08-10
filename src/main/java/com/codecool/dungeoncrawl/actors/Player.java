package com.codecool.dungeoncrawl.actors;

import com.codecool.dungeoncrawl.Main;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.items.Item;

import javax.swing.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;
import java.util.StringJoiner;

public class Player extends Actor {

    private final ArrayList<Item> items = new ArrayList<>();
    private int bonusAttack = 0;
    private int bonusShield = 0;
    public Player(Cell cell, int health, int attackPower, int defensePower) {
        super(cell, health, attackPower, defensePower);
    }

    public void attack(int dx, int dy){
        Cell attackCell = getCell().getNeighbor(dx, dy);
        if(attackCell.getActor() != null){
            Actor enemy = attackCell.getActor();
            calculateAttack(enemy);

        }
    }

    @Override
    public void setHealth(int damage) {
        super.setHealth(damage-bonusShield);
    }

    public void checkBonuses() {
        for (Item item:
             items) {
            if (item.getTileName().equals("sword")) {
                this.bonusAttack = item.getBonus();
            } else if (item.getTileName().equals("shield")) {
                this.bonusShield = item.getBonus();
            }
        }
    }


    public ArrayList<Item> inventory () {return items;}

    public void addToInventory() {
        items.add(getCell().getItem());
        getCell().setItem(null);
    }

    public String getTileName() {
        return "player";
    }

    private void pickUpItems() {
        if (getCell().getItem().getClass().getSimpleName().equals("Sword") || getCell().getItem().getClass().getSimpleName().equals("Key")) {
            Main.setButtonDisabledStatus(false);
        }
    }

    private void openDoor(int dx, int dy) {
        for (Item item: items) {
            if (item.getClass().getSimpleName().equals("Key")) {
                getCell().getNeighbor(dx, dy).setType(CellType.OPENBLUEDOOR);
                items.remove(item);
            }
        }
    }

    @Override
    public void move(int dx, int dy) {
        super.move(dx, dy);
        if (getCell().getItem() != null) {
            if (getCell().getItem().getClass().getSimpleName().equals("Sword") || getCell().getItem().getClass().getSimpleName().equals("Key") || getCell().getItem().getTileName().equals("shield")) {
                Main.setButtonDisabledStatus(false);
                pickUpItems();
            } else if (Objects.equals(getCell().getNeighbor(dx, dy).getTileName(), "closedBlueDoor")) {
                openDoor(dx, dy);
            }
        }
    }

    public void moveBox(int dx, int dy, char key) {
        if (getCell().getNeighbor(dx, dy).getTileName().equals("box")) {
            int boxPositionX = getX();
            int boxPositionY = getY();
            int playerPositionX = getCell().getX();
            int playerPositionY = getCell().getY();

            if (key == 'S') {
                boxPositionY = getY() - 1;
            }
        }
    }

    public void calculateAttack(Actor enemy){
        enemy.setHealth(getAttackPower()+bonusAttack);
    }

    public String display() {
        StringJoiner stringJoiner = new StringJoiner("\n");
        for (Item item: items) {
            stringJoiner.add(item.getClass().getSimpleName());
        }
        return stringJoiner.toString();
    }
}
