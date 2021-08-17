package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Drawable;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.enemies.Monster;

public abstract class Character implements Drawable {
    private boolean hasMoved = false;
    private Cell cell;
    protected static int maxHealth;
    protected static int defense;
    protected static int attack;
    private int health;

    public Character(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
        this.health = maxHealth;
    }

    public void move(int dx, int dy) {
        Cell nextCell = cell.getNeighbor(dx, dy);
        cell.setActor(null);
        nextCell.setActor(this);
        cell = nextCell;
        attackIfCan();
    }

    abstract public void monsterMove(GameMap map);

    public boolean getHasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int modify) {
        health = (defense + modify < 0) ? 0 : defense + modify;
    }

    public Cell getCell() {
        return cell;
    }

    public int getX() {
        return cell.getX();
    }

    public int getY() {
        return cell.getY();
    }

    private void attackIfCan() {
        int[][] next = new int[][] {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        for (int i = 0; i < 4; i++) {
            if (cell.getNeighbor(next[i][0], next[i][1]).getActor() != null) {
                attack(cell.getNeighbor(next[i][0], next[i][1]).getActor());
                System.out.println("Attack the enemy!");
                System.out.println("\tit's health: " + cell.getNeighbor(next[i][0], next[i][1]).getActor().health);
                if (cell.getNeighbor(next[i][0], next[i][1]).getActor() != null) {
                    cell.getNeighbor(next[i][0], next[i][1]).getActor().attack(this);
                    System.out.println("The enemy attacked me!");
                    System.out.println("\tmy health: " + health);
                }
            }
        }
    }

    public void attack(Character victim) {
        if (attack >= victim.defense) {
            victim.health -= attack;
            if (victim.health <= 0) { // dies if HP 0 (should change to < 0 later)
                if (victim instanceof Player) {
                    // TODO: Game Over!
                    System.out.println("Game Over!");
                }
                cell.setActor(null);
            }
        }
    }
}
