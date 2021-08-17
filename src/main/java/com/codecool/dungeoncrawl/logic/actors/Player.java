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

    public void setDefense(int modify) {
        defense = (defense + modify < 0) ? 0 : defense + modify;
    }

    public void setAttack(int modify) {
        attack = (attack + modify < 0) ? 0 : attack + modify;
    }

    @Override
    public void setHealth(int modify) {
        if (getHealth() + modify < 0) {
            System.out.println("Game Over"); // TODO: Implement it
        } else {
            super.setHealth(modify);
        }
    }

    public void monsterMove(GameMap map) {}
}
