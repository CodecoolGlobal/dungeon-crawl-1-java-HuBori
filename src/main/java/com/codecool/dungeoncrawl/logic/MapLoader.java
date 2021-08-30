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

import static java.lang.Integer.parseInt;

public class MapLoader {

    private final Map<Character, String> doorTypes;

    private Cell cell;
    private int level;
    private char ch;

    public static GameMap loadMap(String filename) {
        doorTypes = setDoorTypes();

        InputStream is = MapLoader.class.getResourceAsStream("/maps/" + filename);
        Scanner scanner = new Scanner(is);
        int width = scanner.nextInt();
        int height = scanner.nextInt();
        System.out.println("width: " + width + " height: "+ height);

        scanner.nextLine(); // empty line

        level = parseInt(filename.split("level-")[1].split(".txt")[0]);
        GameMap map = new GameMap(width, height, CellType.EMPTY);
        for (int y = 0; y < height; y++) {
            String line = scanner.nextLine();
            for (int x = 0; x < width; x++) {
                if (x < line.length()) {
                    cell = map.getCell(x, y);
                    ch = line.charAt(x);
                    if (!checkForDoorsAndKeys()) {
                        setCellByMapField();
                    }
                }
            }
        }
        return map;
    }

    private static Map<Character, String> setDoorTypes(){
        Map<Character, String> doorTypes = new HashMap<>(){{
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
        return doorTypes;
    }

    private static boolean fieldIsFloor(){
        if (ch = '.' || ch = 'm' || ch = 'g' || ch = 's' || ch = '@' || ch = 'k' || ch = 'c' || ch = 'w' || ch = 'a') {
            cell.setType(CellType.FLOOR);
            switch (ch) {
                case 'g': new GiddyMonster(cell); break; // giddy
                case 'm': new MarchingMonster(cell); break; // marching
                case 's': new MuzzyMonster(cell); break; // stationary
                case '@': map.setPlayer(new Player(cell)); break;
                case 'k': new Key(cell, KeyType.GOLD, level, "key to next level");break;
                case 'c': new Key(cell, KeyType.SILVER, level, "key to treasure chest");break;
                case 'w': new Weapon(cell, WeaponType.LONGSWORD); break;
                case 'a': new Armor(cell, ArmorType.SHIELD); break;
            }
        }
    }

    private static void setCellByMapField() {
        if (!fieldIsFloor()) {
            switch (ch) {
                case '#': cell.setType(CellType.WALL); break;
                case 'A': // Use V and A for down and up (like arrows)
                    cell.setType(CellType.STAIRUP);
                    new StairUp(cell, level, String.valueOf(level + 1));
                    break;
                case 'V':
                    cell.setType(CellType.STAIRDOWN);
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

    private static boolean checkForDoorsAndKeys() {
        if ("áéíóöőúüű".indexOf(ch) != -1) {
            cell.setType(CellType.FLOOR);
            new Key(cell, KeyType.BRONSE, level, doorTypes.get(ch));
            return true;
        } else if ("ÁÉÍÓÖŐÚÜŰ".indexOf(ch) != -1) {
            cell.setType(CellType.DOOR); // represent by ÁÉÍÓÖŐÚÜŰ (key pairs: áéíóöőúüű) -> max 9 door / floor (or same type)
            new Door(cell, level, doorTypes.get(Character.toLowerCase(ch)));
            return true;
        }
        return false;
    }
}
