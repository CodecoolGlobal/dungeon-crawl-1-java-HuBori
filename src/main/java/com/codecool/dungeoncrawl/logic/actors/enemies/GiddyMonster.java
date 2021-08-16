package com.codecool.dungeoncrawl.logic.actors.enemies;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.actors.enemies.Monster;

public class GiddyMonster extends Monster {
    private static final int maxHealth = 5;
    private static final int defense = 1;
    private static final int attack = 10;

    public GiddyMonster(Cell cell) {
        super(cell);
    }
}
