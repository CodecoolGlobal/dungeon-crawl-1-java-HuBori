package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;

public class Player extends Character {
    public Player(Cell cell) {
        super(cell);
        this.isPlayer = true;
        maxHealth = 10; // modifiable by potions (later feature)
        defense = 0; // modifiable by armors
        attack = 1; // modifiable by weapons; base attack 1 cause fists
        health = this.maxHealth;
    }

    public String getTileName() {
        return "player";
    }

    public void monsterMove(GameMap map) {
    }

    @Override
    protected void attackIfCan(Cell nextCell) { // TODO if the player and an enemy would step on each other
        if (nextCell.getActor() != null) {
            attack(nextCell.getActor());
        }
    }
}
