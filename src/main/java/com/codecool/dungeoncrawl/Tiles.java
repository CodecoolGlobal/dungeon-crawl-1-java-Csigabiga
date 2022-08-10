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
        tileMap.put("closedBlueDoor", new Tile(1, 9));
        tileMap.put("openedBlueDoor", new Tile(2, 9));
        tileMap.put("sleep", new Tile(23, 7));
        tileMap.put("ready", new Tile(20, 7));
        tileMap.put("explode", new Tile(27, 11));
        tileMap.put("musketeer", new Tile(30, 6));
        tileMap.put("stairUp", new Tile(2, 6));
        tileMap.put("stairDown", new Tile(3, 6));
    }

    public static void drawTile(GraphicsContext context, Drawable d, int x, int y) {
        Tile tile = tileMap.get(d.getTileName());
        context.drawImage(tileset, tile.x, tile.y, tile.w, tile.h,
                x * TILE_WIDTH, y * TILE_WIDTH, TILE_WIDTH, TILE_WIDTH);
    }
}
