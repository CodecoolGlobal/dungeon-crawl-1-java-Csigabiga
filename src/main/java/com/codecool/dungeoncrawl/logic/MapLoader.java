package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.actors.ThreeMusketeers;
import com.codecool.dungeoncrawl.logic.items.Key;
import com.codecool.dungeoncrawl.logic.items.Shield;
import com.codecool.dungeoncrawl.logic.items.Sword;
import com.codecool.dungeoncrawl.actors.Player;
import com.codecool.dungeoncrawl.actors.Skeleton;
import com.codecool.dungeoncrawl.actors.Bomber;

import java.io.InputStream;
import java.util.Random;
import java.util.Scanner;

public class MapLoader  {

    private static void randomTile(Cell cell, CellType ... cellTypes){
        Random random = new Random();
        cell.setType(cellTypes[random.nextInt(cellTypes.length)]);
    }

    public static GameMap loadMap(String mapPath) {
        InputStream is = MapLoader.class.getResourceAsStream(mapPath);
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
                            randomTile(cell, CellType.FLOOR, CellType.FLOOR1, CellType.FLOOR2);
                            break;
                        case 's':
                            randomTile(cell, CellType.FLOOR, CellType.FLOOR1, CellType.FLOOR2);
                            map.appendSkeleton(new Skeleton(cell, 6, 3, 2));
                            break;
                        case '@':
                            randomTile(cell, CellType.FLOOR, CellType.FLOOR1, CellType.FLOOR2);
                            map.setPlayer(new Player(cell, 10, 4 , 2));
                            break;
                        case 'k':
                            randomTile(cell, CellType.FLOOR, CellType.FLOOR1, CellType.FLOOR2);
                            map.setKey(new Key(cell, 0));
                            break;
                        case 'b':
                            randomTile(cell, CellType.FLOOR, CellType.FLOOR1, CellType.FLOOR2);
                            map.appendBomber(new Bomber(cell, 4, 6, 1));
                            break;
                        case 'x':
                            randomTile(cell, CellType.FLOOR, CellType.FLOOR1, CellType.FLOOR2);
                            map.setSword(new Sword(cell, 3));
                            break;
                        case 'l':
                            cell.setType(CellType.STAIRDOWN);
                            break;
                        case 'L':
                            cell.setType(CellType.STAIRUP);
                            break;
                        case '!':
                            cell.setType(CellType.HEART);
                            break;
                        case '=':
                            cell.setType(CellType.INFOBARMIDDLE);
                            break;
                        case '>':
                            cell.setType(CellType.ORANGEWALL);
                            break;
                        case '?':
                            cell.setType(CellType.INFOBARSHIELD);
                            break;
                        case '+':
                            cell.setType(CellType.INFOBARSWORD);
                            break;
                        case '<':
                            randomTile(cell, CellType.ORANGEWALL2, CellType.ORANGEWALLBROKEN, CellType.ORANGEWALL);
                            break;
                        case 'M':
                            randomTile(cell, CellType.FLOOR, CellType.FLOOR1, CellType.FLOOR2);
                            map.appendThreeMusketeers(new ThreeMusketeers(cell, 10, 4, 3));
                            break;
                        case 'p':
                            randomTile(cell, CellType.FLOOR, CellType.FLOOR1, CellType.FLOOR2);
                            map.setShield(new Shield(cell, 1, 0.5));
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
