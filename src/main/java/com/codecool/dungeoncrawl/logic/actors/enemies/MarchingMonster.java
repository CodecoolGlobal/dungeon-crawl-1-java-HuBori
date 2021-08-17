package com.codecool.dungeoncrawl.logic.actors.enemies;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;

import java.util.Random;

public class MarchingMonster extends Monster { // moves back and forth
    int[] direction = {0, 0};

    public MarchingMonster(Cell cell) {
        super(cell);
        maxHealth = 10;
        defense = 5;
        attack = 5;
        health = maxHealth;
        determineFacing();
    }

    private void determineFacing() {
        Random rand = new Random();
        if (rand.nextInt(2) == 1) {
            direction[0] = 0;
            direction[1] = 1;
        } else {
            direction[0] = 1;
            direction[1] = 0;
        }
    }

    @Override
    public String getTileName() {
        return "marching";
    }

    @Override
    public void monsterMove(GameMap map) {
        Cell cell = map.getCell(this.getX() + direction[0], this.getY() + direction[1]);
        if(cell.getType() == CellType.WALL){
            direction[0] *= -1;
            direction[1] *= -1;
        }
        this.tryMove(direction[0], direction[1]);
    }
}
