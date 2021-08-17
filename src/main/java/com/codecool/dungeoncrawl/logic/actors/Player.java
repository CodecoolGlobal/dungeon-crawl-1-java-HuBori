package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;

public class Player extends Character {
    public Player(Cell cell) {
        super(cell);
        maxHealth = 10; // modifiable by potions (later feature)
        defense = 0; // modifiable by armors
        attack = 0; // modifiable by weapons
    }

    public String getTileName() {
        return "player";
    }

    public void monsterMove(GameMap map) {}
}
