package com.codecool.dungeoncrawl.logic.actors.enemies;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Character;

abstract public class Monster extends Character {

    public Monster(Cell cell) {
        super(cell);
        this.isPlayer = false;
        this.setHealth(maxHealth);
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

    protected void attackIfCan(Cell nextCell) {
        int[][] next = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        for (int i = 0; i < 4; i++) {
            Character nextActor = cell.getNeighbor(next[i][0], next[i][1]).getActor();
            if (nextActor != null) {
                if (!this.cell.getActor().isPlayer() && nextActor.isPlayer()) {
                    attack(nextActor);
                }
            }
        }
    }
}
