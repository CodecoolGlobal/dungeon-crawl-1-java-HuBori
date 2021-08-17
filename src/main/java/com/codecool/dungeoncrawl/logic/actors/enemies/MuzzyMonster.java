package com.codecool.dungeoncrawl.logic.actors.enemies;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Character;
import com.codecool.dungeoncrawl.logic.actors.enemies.Monster;

public class MuzzyMonster extends Monster { // immobile
    public MuzzyMonster(Cell cell) {
        super(cell);
        maxHealth = 30;
        defense = 10;
        attack = 10;
        health = maxHealth;
    }

    @Override
    public String getTileName() {
        return "muzzy";
    }

    @Override
    public void monsterMove(GameMap map) {
        int[][] directions = {{1,1},{-1,-1},{0,1},{1,0},{0,-1},{-1,0},{-1,1},{1,-1}};
        for (int i = 0; i < directions.length; i++) {
            Character neighbour = this.cell.getNeighbor(directions[i][0], directions[i][1]).getActor();
            if(neighbour != null && neighbour.isPlayer()){
                attack(this.cell.getNeighbor(directions[i][0], directions[i][1]).getActor());
            }
        }
    }
}
