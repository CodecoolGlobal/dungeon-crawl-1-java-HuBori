package com.codecool.dungeoncrawl.logic.actors.enemies;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.enemies.Monster;

import java.util.Random;

public class MarchingMonster extends Monster { // moves back and forth
    int[] direction = {0, 0};

    public MarchingMonster(Cell cell) {
        super(cell);
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
        if(map.getCell(this.getX() + direction[0], this.getY() + direction[1]).getType() == CellType.WALL){
            direction[0] *= -1;
            direction[1] *= -1;
        }
        this.move(direction[0], direction[1]);
    }
}
