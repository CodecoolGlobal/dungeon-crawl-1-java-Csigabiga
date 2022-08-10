package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.actors.Actor;
import com.codecool.dungeoncrawl.logic.items.Key;
import com.codecool.dungeoncrawl.logic.items.Sword;
import com.codecool.dungeoncrawl.actors.Player;
import com.codecool.dungeoncrawl.actors.Skeleton;
import com.codecool.dungeoncrawl.actors.Bomber;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameMap {
    Random random = new Random();
    private int width;
    private int height;
    private Cell[][] cells;
    private List<Skeleton> skeletons;
    private List<Bomber> bombers;
    private Player player;
    private Key key;
    private Sword sword;

    public GameMap(int width, int height, CellType defaultCellType) {
        this.width = width;
        this.height = height;
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
    public void appendBomber(Bomber bomber) {
        bombers.add(bomber);}

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
        }

    }


    public void bomberRound() {
        for (Bomber bomber : bombers) {
            int x = bomber.getX();
            int y = bomber.getY();
            Cell player = getPlayer().getCell();
            int px = player.getX();
            int py = player.getY();
            if (bomber.getTileName().equals("sleep")) {
                if (px == x && py + 1 == y || px == x && py - 1 == y || px + 1 == x && py == y || px - 1 == x && py == y) {
                    bomber.setTileName("ready");
                }
            } else if (bomber.getTileName().equals("ready")) {
                bomber.setTileName("explode");
                if (px == x && py + 1 == y || px == x && py - 1 == y || px + 1 == x && py == y || px - 1 == x && py == y) {
                    getPlayer().setHealth(6);
                }
            } else if (bomber.getTileName().equals("explode")){
                bomber.setTileName("floor");
                bomber.getCell().setType(CellType.FLOOR);
                bomber.getCell().setActor(null);
            }

        }
    }
}
