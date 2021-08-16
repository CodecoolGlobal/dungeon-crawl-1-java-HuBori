package com.codecool.dungeoncrawl.logic.actors.enemies;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.actors.enemies.Monster;

public class GiddyMonster extends Monster {

    public GiddyMonster(Cell cell) {
        super(cell);
        maxHealth = 5;
        defense = 1;
        attack = 10;
    }
}
