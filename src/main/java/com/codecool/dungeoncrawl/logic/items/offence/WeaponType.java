package com.codecool.dungeoncrawl.logic.items.offence;

public enum WeaponType {
    LONGSWORD(5),
    MACE(10),
    DAGGER(1),
    SHORTSWORD(3),
    LANCE(8);

    private final int attack;

    WeaponType(int attack) {
        this.attack = attack;
    }

    public int getAttack() {
        return attack;
    }
}
