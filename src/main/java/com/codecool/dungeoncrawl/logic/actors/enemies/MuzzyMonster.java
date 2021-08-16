package com.codecool.dungeoncrawl.logic.actors.enemies;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.actors.enemies.Monster;

public class MuzzyMonster extends Monster {
    private static final int maxHealth = 30;
    private static final int defense = 10;
    private static final int attack = 1;

    public MuzzyMonster(Cell cell) {
        super(cell);
    }
}
