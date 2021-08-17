package com.codecool.dungeoncrawl.logic.items.defence;

public class Armor {
    private ArmorType type;
    private String detail;

    public Armor(ArmorType type, String detail) {
        this.type = type;
        this.detail = detail;
    }

    public ArmorType getType() {
        return type;
    }
}
