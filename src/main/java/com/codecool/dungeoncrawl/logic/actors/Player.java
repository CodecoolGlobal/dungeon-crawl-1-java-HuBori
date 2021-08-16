package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Player extends Character {
    private static int maxHealth = 10; // modifiable by potions (later feature)
    private static int defense = 0; // modifiable by armors
    private static int attack = 0; // modifiable by weapons
    public Player(Cell cell) {
        super(cell);
    }

    public String getTileName() {
        return "player";
    }
}
