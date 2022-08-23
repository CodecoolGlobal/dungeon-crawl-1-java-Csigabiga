package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Drawable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class Tiles {
    public static int TILE_WIDTH = 32;

    private static Image tileset = new Image("/tiles.png", 543 * 2, 543 * 2, true, false);
    public static Map<String, Tile> tileMap = new HashMap<>();
    public static class Tile {
        public final int x, y, w, h;
        Tile(int i, int j) {
            x = i * (TILE_WIDTH + 2);
            y = j * (TILE_WIDTH + 2);
            w = TILE_WIDTH;
            h = TILE_WIDTH;
        }
    }

    static {
        tileMap.put("empty", new Tile(0, 0));
        tileMap.put("wall", new Tile(10, 17));
        tileMap.put("orangeWall", new Tile(6, 13));
        tileMap.put("orangeWall2", new Tile(7, 15));
        tileMap.put("orangeWallBroken", new Tile(6, 15));
        tileMap.put("blueSwitchLeft", new Tile(3, 10));
        tileMap.put("blueSwitchRight", new Tile(4, 10));
        tileMap.put("blueSwitchLock", new Tile(0, 11));
        tileMap.put("trapRouteTile", new Tile(17, 0));
        tileMap.put("floor", new Tile(2, 0));
        tileMap.put("floor1", new Tile(4, 0));
        tileMap.put("floor2", new Tile(3, 0));
        tileMap.put("corpse", new Tile(0, 15));
        tileMap.put("player", new Tile(27, 0));
        tileMap.put("skeleton0", new Tile(23, 8));
        tileMap.put("skeleton1", new Tile(21, 8));
        tileMap.put("skeleton2", new Tile(19, 8));
        tileMap.put("sword", new Tile(0, 28));
        tileMap.put("key", new Tile(17, 23));
        tileMap.put("sleep", new Tile(23, 7));
        tileMap.put("ready", new Tile(20, 7));
        tileMap.put("explode", new Tile(27, 11));
        tileMap.put("musketeer", new Tile(30, 6));
        tileMap.put("jumperGhost", new Tile(23, 9));
        tileMap.put("jumperGhost1", new Tile(21, 9));
        tileMap.put("jumperGhost2", new Tile(27, 11));
        tileMap.put("stairUp", new Tile(2, 6));
        tileMap.put("stairDown", new Tile(3, 6));
        tileMap.put("shield", new Tile(2,23));
        tileMap.put("infoBarMiddle", new Tile(20,15));
        tileMap.put("heart", new Tile(26,22));
        tileMap.put("numberZero", new Tile(19,29));
        tileMap.put("numberOne", new Tile(20,29));
        tileMap.put("numberTwo", new Tile(21,29));
        tileMap.put("numberThree", new Tile(22,29));
        tileMap.put("numberFour", new Tile(23,29));
        tileMap.put("numberFive", new Tile(24,29));
        tileMap.put("numberSix", new Tile(25,29));
        tileMap.put("numberSeven", new Tile(26,29));
        tileMap.put("numberEight", new Tile(27,29));
        tileMap.put("numberNine", new Tile(28,29));
        tileMap.put("infoBarShield", new Tile(7,26));
        tileMap.put("infoBarSword", new Tile(2,29));
        tileMap.put("infoBarCoins", new Tile(9,26));
        tileMap.put("infoBarBag", new Tile(15,26));
        tileMap.put("doubleDot", new Tile(29,29));
        tileMap.put("closedBlueDoor", new Tile(1, 9));
        tileMap.put("openedBlueDoor", new Tile(2, 9));
        tileMap.put("trigger", new Tile(7, 13));
        tileMap.put("box", new Tile(12, 18));
        tileMap.put("net", new Tile(2, 15));
        tileMap.put("finish", new Tile(17, 8));
        tileMap.put("W", new Tile(28, 31));
        tileMap.put("E", new Tile(23, 30));
        tileMap.put("L", new Tile(30, 30));
        tileMap.put("D", new Tile(22, 30));
        tileMap.put("O", new Tile(20, 31));
        tileMap.put("N", new Tile(19, 31));
        tileMap.put("diamond", new Tile(23, 4));
    }

    public static void drawTile(GraphicsContext context, Drawable d, int x, int y) {
        Tile tile = tileMap.get(d.getTileName());
        context.drawImage(tileset, tile.x, tile.y, tile.w, tile.h,
                x * TILE_WIDTH, y * TILE_WIDTH, TILE_WIDTH, TILE_WIDTH);
    }
}
