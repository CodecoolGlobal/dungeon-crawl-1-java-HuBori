package com.codecool.dungeoncrawl.logic.items.defence;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.items.ItemType;

public class Armor extends Item {
    private ArmorType subType;

    public Armor(Cell cell, ArmorType type) {
        super(cell, ItemType.ARMOR, type.toString().toLowerCase());
        this.subType = type;
    }

    @Override
    public String getTileName() {
        return "armor";
    }

    public ArmorType getSubType() {
        return subType;
    }
}
