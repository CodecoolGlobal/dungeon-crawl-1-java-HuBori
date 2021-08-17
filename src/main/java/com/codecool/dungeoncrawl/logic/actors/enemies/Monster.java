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

    @Override
    public void setHealth(int modify) {
        if (getHealth() + modify < 0) {
            getCell().setActor(null);
        } else {
            super.setHealth(modify);
        }
    }

    abstract public void monsterMove(GameMap map);
}
