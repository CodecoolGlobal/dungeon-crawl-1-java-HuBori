package com.codecool.dungeoncrawl.logic.items.defence;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.items.ItemType;

import java.util.List;

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

    @Override
    public void pickUp(List<Item> inventory, GameMap map){
        inventory.add(this);
        map.getCell(cell.getX(), cell.getY()).setItem(null);
        map.getPlayer().setDefense(subType.getProtection());
    }

    public ArmorType getSubType() {
        return subType;
    }
}
