package com.codecool.dungeoncrawl.logic.items.defence;

public enum ArmorType {
    SHIELD(5),
    HELMET(3);

    private final int protection;

    ArmorType(int protection) {
        this.protection = protection;
    }

    public int getProtection() {
        return protection;
    }
}
