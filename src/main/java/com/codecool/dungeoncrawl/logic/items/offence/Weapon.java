package com.codecool.dungeoncrawl.logic.items.offence;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.items.ItemType;

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

    public WeaponType getSubType() {
        return subType;
    }
}
