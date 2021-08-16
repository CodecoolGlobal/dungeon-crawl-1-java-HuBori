package com.codecool.dungeoncrawl.logic.actors.enemies;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.actors.enemies.Monster;

public class MuzzyMonster extends Monster {
    private final int maxHealth = 30;
    private final int defense = 10;
    private final int attack = 1;

    public MuzzyMonster(Cell cell) {
        super(cell);
    }
}
