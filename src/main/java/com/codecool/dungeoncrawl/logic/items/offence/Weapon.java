package com.codecool.dungeoncrawl.logic.items.offence;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.items.ItemType;

import java.util.List;

public class Weapon extends Item {
    private WeaponType subType;

    public Weapon(Cell cell, WeaponType type) {
        super(cell, ItemType.WEAPON, type.toString().toLowerCase());
        this.subType = type;
    }

    @Override
    public String getTileName() {
        return "weapon";
    }

    @Override
    public void pickUp(List<Item> inventory, GameMap map){
        inventory.add(this);
        map.getCell(cell.getX(), cell.getY()).setItem(null);
        map.getPlayer().setAttack(subType.getAttack());
    }

    public WeaponType getSubType() {
        return subType;
    }
}
