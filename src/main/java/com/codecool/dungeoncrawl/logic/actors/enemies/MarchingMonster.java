package com.codecool.dungeoncrawl.logic.actors.enemies;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.actors.enemies.Monster;

public class MarchingMonster extends Monster {
    private static final int maxHealth = 10;
    private static final int defense = 5;
    private static final int attack = 2;

    public MarchingMonster(Cell cell) {
        super(cell);
    }
}
