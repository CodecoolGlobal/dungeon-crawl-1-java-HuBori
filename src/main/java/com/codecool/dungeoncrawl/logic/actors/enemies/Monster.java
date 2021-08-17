package com.codecool.dungeoncrawl.logic.actors.enemies;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Character;

abstract public class Monster extends Character {

    public Monster(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "default";
    }

    abstract public void monsterMove(GameMap map);
}
