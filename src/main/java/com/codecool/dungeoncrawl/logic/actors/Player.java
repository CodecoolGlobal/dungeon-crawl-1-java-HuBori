package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;

import java.util.Objects;

public class Player extends Character {
    private String name = "you didn't care enough to name your character, eh? are you a dev or something?";
    private boolean cheatmode = false;

    public Player(Cell cell) {
        super(cell);
        this.isPlayer = true;
        maxHealth = 10; // modifiable by potions (later feature)
        defense = 0; // modifiable by armors
        attack = 1; // modifiable by weapons; base attack 1 cause fists
        this.setHealth(this.maxHealth);
        //checkCheat(name);
    }

    public void checkCheat(String name){
        System.out.println(name);
        if(name.equals("Bori") || name.equals("Áron") || name.equals("Orodben")){
            cheatmode = true;
        }
    }

    public boolean isCheatmode() {
        return cheatmode;
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
        this.health = modify;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void monsterMove(GameMap map) {}

    @Override
    protected void attackIfCan(Cell nextCell) {
        if (nextCell.getActor() != null) {
            attack(nextCell.getActor());
        }
    }
}
