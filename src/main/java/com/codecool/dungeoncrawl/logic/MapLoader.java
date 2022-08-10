package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.items.Key;
import com.codecool.dungeoncrawl.logic.items.Sword;
import com.codecool.dungeoncrawl.actors.Player;
import com.codecool.dungeoncrawl.actors.Skeleton;
import com.codecool.dungeoncrawl.actors.Bomber;

import java.io.InputStream;
import java.util.Random;
import java.util.Scanner;

public class MapLoader  {

    private static void randomFloorTile(Cell cell){
        Random random = new Random();
        int randomFloor = random.nextInt(3);
        switch (randomFloor){
            case 0:
                cell.setType(CellType.FLOOR);
                break;
            case 1:
                cell.setType(CellType.FLOOR1);
                break;
            case 2:
                cell.setType(CellType.FLOOR2);
                break;
        }
    }

    public static GameMap loadMap() {
        InputStream is = MapLoader.class.getResourceAsStream("/map.txt");
        Scanner scanner = new Scanner(is);
        int width = scanner.nextInt();
        int height = scanner.nextInt();

        scanner.nextLine(); // empty line

        GameMap map = new GameMap(width, height, CellType.EMPTY);
        for (int y = 0; y < height; y++) {
            String line = scanner.nextLine();
            for (int x = 0; x < width; x++) {
                if (x < line.length()) {
                    Cell cell = map.getCell(x, y);
                    switch (line.charAt(x)) {
                        case ' ':
                            cell.setType(CellType.EMPTY);
                            break;
                        case '#':
                            cell.setType(CellType.WALL);
                            break;
                        case '.':
                            randomFloorTile(cell);
                            break;
                        case 's':
                            cell.setType(CellType.FLOOR);
                            map.appendSkeleton(new Skeleton(cell, 6));
                            break;
                        case 'k':
                            cell.setType(CellType.FLOOR);
                            map.appendBomber(new Bomber(cell, 3));
                            break;
                        case '@':
                            cell.setType(CellType.FLOOR);
                            map.setPlayer(new Player(cell, 10));
                        case 'x':
                            cell.setType(CellType.FLOOR);
                            map.setSword(new Sword(cell));
                            break;
                            break;
                        default:
                            throw new RuntimeException("Unrecognized character: '" + line.charAt(x) + "'");
                    }
                }
            }
        }
        return map;
    }
}
