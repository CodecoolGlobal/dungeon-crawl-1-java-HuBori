package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.GameMap;

import java.util.HashMap;

public class View {
    private final int width = 30;
    private final int height = 20;
    private HashMap<Integer, GameMap> dungeon;
    private int level = 1;
}
