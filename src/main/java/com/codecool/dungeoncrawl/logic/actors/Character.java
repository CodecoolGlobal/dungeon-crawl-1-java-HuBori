package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.Main;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Drawable;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.buildings.lock.Lock;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.items.ItemType;
import com.codecool.dungeoncrawl.logic.items.key.Key;

import java.util.ArrayList;
import java.util.List;

public abstract class Character implements Drawable {
    protected Cell cell;
    protected boolean isPlayer;
    private boolean hasMoved = false;
    // stats
    protected static int maxHealth;
    protected static int defense;
    protected static int attack;
    protected int health = 0;

    public Character(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
        this.health = maxHealth;
    }

    public void tryMove(int dx, int dy, ArrayList<Item> keys) {
        Cell nextCell = this.cell.getNeighbor(dx, dy);
        attackIfCan(nextCell);
        if (nextCell.getActor() == null) {
            if (canGoThrough(cell, keys)) {
                move(nextCell);
            }
        }
    }

    public void move(Cell nextCell) {
        cell.setActor(null);
        nextCell.setActor(this);
        cell = nextCell;
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

    public static int getMaxHealth() {
        return maxHealth;
    }

    public static int getDefense() {
        return defense;
    }

    public static int getAttack() {
        return attack;
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

    public boolean isPlayer() {
        return isPlayer;
    }

    protected void attackIfCan(Cell nextCell) {
    }

/*    protected void attackIfCan(Cell nextCell) {
        int[][] next = new int[][] {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        for (int i = 0; i < 4; i++) {
            Character nextActor = cell.getNeighbor(next[i][0], next[i][1]).getActor();
            if (nextActor != null) {
                if(cell.getActor().isPlayer) {
                    attack(nextActor);
                    System.out.println("Attack the enemy!");
                    System.out.println("\tit's health: " + nextActor.health);
                }
                if(cell.getActor().isPlayer && !nextActor.isPlayer) {
                    nextActor.attack(this);
                    System.out.println("The enemy attacked me!");
                    System.out.println("\tmy health: " + health);
                }
            }
        }
    }*/

    public void attack(Character victim) {
        if (this.attack >= victim.defense) {
            victim.health -= this.attack;
            this.health -= victim.attack;
            if (this.isPlayer && this.health <= 0 || victim.isPlayer && victim.health <= 0) {
                // dies if HP 0 (should change to < 0 later)
                // TODO: Game Over!
                Main.logging.add("Our hero has perished and their corpse will now be desiccated and mutilated and eaten."); // add rainbow emoji
                System.out.println("You ded lol");
                //System.exit(0);
            }
            if(!victim.isPlayer && victim.health <= 0){
                victim.cell.setActor(null);
                Main.logging.add("You have slayed your foe!");
            }
        }
        if (this.isPlayer) { // TODO display in window
            Main.logging.add("Your valiant onslaught has brought you to " + this.health + "HP.  " +
                    "Whilst you have brought your enemy to " + victim.health + "HP.");
        } else {
            Main.logging.add("Their fiendish attack has brought you to " + victim.health + "HP.  " +
                    "But it has cost them dearly, they are only at " + this.health + "HP now.");
        }
    }

    private boolean canGoThrough(Cell cell, ArrayList<Item> keys){
        if (cell.isLock()) {
            if (!isPlayer) {
                System.out.println("Monsters can't go through doors");
                return false;
            } else {
                Lock lock = cell.getLock();
                for (int i = 0; i < keys.size(); i++) {
                    if (keys.get(i).getType() == ItemType.KEY) {
                        lock.attemptOpen((Key) keys.get(i));
                        System.out.println("Try key: " + keys.get(i));
                        if (lock.isOpen()) {
                            System.out.println("Sesame unfold!");
                            return true;
                        }
                    }
                }
                System.out.println("Get a key first! :P"); // This happens, player gets stuck
                return false;
            }
        } else {
            return true;
        }
    }
}
