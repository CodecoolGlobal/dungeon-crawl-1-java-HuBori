package com.codecool.dungeoncrawl.logic.actors.enemies;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.enemies.Monster;

public class MuzzyMonster extends Monster { // immobile
    public MuzzyMonster(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "muzzy";
    }

    public void monsterMove(GameMap map) {}
}
