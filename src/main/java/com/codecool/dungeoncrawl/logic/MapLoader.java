package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.buildings.locks.Chest;
import com.codecool.dungeoncrawl.logic.buildings.locks.Door;
import com.codecool.dungeoncrawl.logic.buildings.locks.stairs.Stair;
import com.codecool.dungeoncrawl.logic.buildings.locks.stairs.StairDown;
import com.codecool.dungeoncrawl.logic.buildings.locks.stairs.StairUp;
import com.codecool.dungeoncrawl.logic.items.defence.Armor;
import com.codecool.dungeoncrawl.logic.items.defence.ArmorType;
import com.codecool.dungeoncrawl.logic.items.offence.Weapon;
import com.codecool.dungeoncrawl.logic.items.offence.WeaponType;
import com.codecool.dungeoncrawl.logic.items.key.Key;
import com.codecool.dungeoncrawl.logic.items.key.KeyType;
import com.codecool.dungeoncrawl.logic.actors.enemies.*;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MapLoader {

    public static GameMap loadMap(int level) {
        Map<Character, String> keyTypes = new HashMap<>(){{
            put('á', "storage");
            put('é', "dining room");
            put('í', "scriptory");
            put('ó', "bedroom");
            put('ö', "dressing room");
            put('ő', "toilet");
            put('ú', "living room");
            put('ü', "hall");
            put('ű', "kitchen");
        }};

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
                    if ("áéíóöőúüű".charAt(line.charAt(x)) != -1) {
                        cell.setType(CellType.FLOOR);
                        new Key(cell, KeyType.BRONSE, level, keyTypes.get(line.charAt(x)));
                    } else if ("ÁÉÍÓÖŐÚÜŰ".charAt(line.charAt(x)) != -1) {
                        cell.setType(CellType.DOOR); // represent by ÁÉÍÓÖŐÚÜŰ (key pairs: áéíóöőúüű) -> max 9 door / floor (or same type)
                        new Door(cell, level, keyTypes.get(line.charAt(x) - 'a' + 'A'));
                    } else {
                        switch (line.charAt(x)) {
                            case ' ':
                                cell.setType(CellType.EMPTY);
                                break;
                            case '#':
                                cell.setType(CellType.WALL);
                                break;
                            case '.':
                                cell.setType(CellType.FLOOR);
                                break;
                            case 'g': // giddy
                                cell.setType(CellType.FLOOR);
                                new GiddyMonster(cell);
                                break;
                            case 'm': // marching
                                cell.setType(CellType.FLOOR);
                                new MarchingMonster(cell);
                                break;
                            case 's': // stationary
                                cell.setType(CellType.FLOOR);
                                new MuzzyMonster(cell);
                                break;
                            case '@':
                                cell.setType(CellType.FLOOR);
                                map.setPlayer(new Player(cell));
                                break;
                            case 'k':
                                cell.setType(CellType.FLOOR);
                                new Key(cell, KeyType.GOLD, level, "key to next level");
                                break;
                            case 'c':
                                cell.setType(CellType.FLOOR);
                                new Key(cell, KeyType.SILVER, level, "key to treasure chest");
                                break;
                            case 'w':
                                cell.setType(CellType.FLOOR);
                                new Weapon(cell, WeaponType.LONGSWORD);
                                break;
                            case 'a':
                                cell.setType(CellType.FLOOR);
                                new Armor(cell, ArmorType.SHIELD);
                                break;
                            case 'd':
                                cell.setType(CellType.DOOR); // TODO: represent by ÁÉÍÓÖŐÚÜŰ (key pairs: áéíóöőúüű) -> max 9 door / floor (or same type)
                                new Door(cell, 1, "only"); // TODO: resolve magic number
                                break;
                            case 'A': // Use V and A for down and up (like arrows)
                                cell.setType(CellType.STAIR);
                                new StairUp(cell, level, String.valueOf(level + 1));
                                break;
                            case 'V':
                                cell.setType(CellType.STAIR);
                                new StairDown(cell, level, String.valueOf(level - 1));
                                break;
                            case 'C':
                                cell.setType(CellType.CHEST);
                                new Chest(cell, 1, "2");
                                break;
                            default:
                                throw new RuntimeException("Unrecognized character: '" + line.charAt(x) + "'");
                        }
                    }
                }
            }
        }
        return map;
    }

}
