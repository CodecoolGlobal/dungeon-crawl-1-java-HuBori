package com.codecool.dungeoncrawl.logic.actors.enemies;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.actors.enemies.Monster;

public class MuzzyMonster extends Monster {

    public MuzzyMonster(Cell cell) {
        super(cell);
        maxHealth = 30;
        defense = 10;
        attack = 1;
    }
}
