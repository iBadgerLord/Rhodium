package net.ibadgerlord.rhodium.util.other;

import net.minecraft.util.StringIdentifiable;

public enum FlameType implements StringIdentifiable {

    NONE("none"),
    REGULAR_FLAME("regular"),
    SOUL_FLAME("soul"),
    COPPER_FLAME("copper");

    private final String name;

    private FlameType(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    @Override
    public String asString() {
        return this.name;
    }

}
