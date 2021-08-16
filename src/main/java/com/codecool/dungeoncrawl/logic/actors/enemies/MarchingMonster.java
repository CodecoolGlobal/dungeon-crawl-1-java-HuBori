package com.codecool.dungeoncrawl.logic.actors.enemies;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.enemies.Monster;

public class MarchingMonster extends Monster { // moves back and forth
    public MarchingMonster(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "marching";
    }

    public void monsterMove(GameMap map) {}
}
