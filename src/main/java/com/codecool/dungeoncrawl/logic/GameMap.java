package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.actors.*;
import com.codecool.dungeoncrawl.logic.items.Box;
import com.codecool.dungeoncrawl.logic.items.Key;
import com.codecool.dungeoncrawl.logic.items.Sword;

import java.util.*;

public class GameMap {
    Random random = new Random();
    private int width;
    private int height;
    private Cell[][] cells;
    private List<Skeleton> skeletons;
    private List<Bomber> bombers;
    private List<ThreeMusketeers> threeMusketeers;
    private Player player;
    private Key key;
    private Sword sword;

    private Box box;

    public GameMap(int width, int height, CellType defaultCellType) {
        this.width = width;
        this.height = height;
        this.threeMusketeers = new ArrayList<>();
        this.skeletons = new ArrayList<>();
        this.bombers = new ArrayList<>();
        cells = new Cell[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells[x][y] = new Cell(this, x, y, defaultCellType);
            }
        }
    }

    public Cell getCell(int x, int y) {
        return cells[x][y];
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void appendSkeleton(Skeleton skeleton) {skeletons.add(skeleton);}
    public void appendBomber(Bomber bomber) {bombers.add(bomber);}
    public void appendThreeMusketeers(ThreeMusketeers threeMusketeer) {threeMusketeers.add(threeMusketeer);}

    public Player getPlayer() {
        return player;
    }

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public Sword getSword() {
        return sword;
    }

    public void setSword(Sword sword) {
        this.sword = sword;
    }



    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void skeletonRound() {
        for (Skeleton skeleton: skeletons) {
            if (skeleton.getTileName().equals("skeleton0")) {
                skeleton.setTileName("skeleton2");
            } else if (skeleton.getTileName().equals("skeleton2")){
                skeleton.setTileName("skeleton1");
            } else {skeleton.setTileName("skeleton2");}
            switch (random.nextInt(4)) {
                case 0:
                    skeleton.move(0, -1);
                    break;
                case 1:
                    skeleton.move(0, 1);
                    break;
                case 2:
                    skeleton.move(-1, 0);
                    break;
                case 3:
                    skeleton.move(1, 0);
                    break;
            }
            if(isNextToIt(skeleton.getX(), skeleton.getY())) {
                dealDamage(skeleton.getAttackPower());
            }
        }

    }


    public void bomberRound() {
        for (Bomber bomber : bombers) {
            int x = bomber.getX();
            int y = bomber.getY();
            if (bomber.getTileName().equals("sleep")) {
                if (isNextToIt(x, y)) {
                    bomber.setTileName("ready");
                }
            } else if (bomber.getTileName().equals("ready")) {
                bomber.setTileName("explode");
                if (isNextToIt(x, y)) {
                    dealDamage(bomber.getAttackPower());
                }
            } else if (bomber.getTileName().equals("explode")){
                bomber.setTileName("floor");
                bomber.getCell().setType(CellType.FLOOR);
                bomber.getCell().setActor(null);
            }

        }
    }


    public void threeMusketeerRound() {
        for (ThreeMusketeers threeMusketeer:
             threeMusketeers) {
            boolean found = false;
            for (int i = 0; i < 15; i++) {
                for (int j = 0; j < 15; j++) {
                    if(isNextToIt(threeMusketeer.getX() + 7 - i, threeMusketeer.getY() + 7 - j)) {
                        int xDiff = player.getX() - threeMusketeer.getX();
                        int yDiff = player.getY() - threeMusketeer.getY();
                        threeMusketeer.move(Integer.signum(xDiff), 0);
                        threeMusketeer.move(0, Integer.signum(yDiff));
                        found = true;
                        break;
                    }
                }
                if (found) {break;}
            }
            if (isNextToIt(threeMusketeer.getX(), threeMusketeer.getY())) {
                dealDamage(threeMusketeer.getAttackPower());
            }
        }
    }


    private boolean isNextToIt(int x, int y) {
        Cell player = getPlayer().getCell();
        int px = player.getX();
        int py = player.getY();
        return px == x && py + 1 == y || px == x && py - 1 == y || px + 1 == x && py == y || px - 1 == x && py == y;
    }


    private void dealDamage (int damage) {
        player.setHealth(damage);
    }


    public void isAlive () {
        for (Skeleton skeleton :
                skeletons) {
            if (skeleton.getHealth() <= 0) {
                skeleton.setTileName("corpse");
                skeleton.getCell().setType(CellType.CORPSE);
                skeleton.getCell().setActor(null);
            }
        }
        for (Bomber bomber :
                bombers) {
            if (bomber.getHealth() <= 0) {
                bomber.setTileName("corpse");
                bomber.getCell().setType(CellType.CORPSE);
                bomber.getCell().setActor(null);
            }
        }
        for (ThreeMusketeers threeMusketeer:
             threeMusketeers) {
            if (threeMusketeer.getHealth() <= 0) {
                threeMusketeer.setTileName("corpse");
                threeMusketeer.getCell().setType(CellType.CORPSE);
                threeMusketeer.getCell().setActor(null);
            }
        }
        bombers.removeIf(bomber -> bomber.getHealth() <= 0);
        skeletons.removeIf(skeleton -> skeleton.getHealth() <= 0);
        threeMusketeers.removeIf(threeMusketeer -> threeMusketeer.getHealth() <= 0);
    }
}
