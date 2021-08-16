package com.codecool.dungeoncrawl.logic.actors.enemies;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.actors.enemies.Monster;

public class GiddyMonster extends Monster {
    private final int maxHealth = 5;
    private final int defense = 1;
    private final int attack = 10;

    public GiddyMonster(Cell cell) {
        super(cell);
    }
}
