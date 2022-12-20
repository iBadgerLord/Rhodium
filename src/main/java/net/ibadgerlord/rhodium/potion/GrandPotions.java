package net.ibadgerlord.rhodium.potion;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.potion.Potion;
import net.minecraft.util.registry.Registry;

public class GrandPotions {

    // Register Cursed Potions method
    private static Potion registerGrandPotion(String name, Potion potion) {
        return Registry.register(Registry.POTION, name, potion);
    }

    public static final Potion EMPTY = registerGrandPotion("empty",
            new Potion(new StatusEffectInstance[0]));
    public static final Potion WATER = registerGrandPotion("water",
            new Potion(new StatusEffectInstance[0]));
    public static final Potion HEALING = registerGrandPotion("healing",
            new Potion(new StatusEffectInstance[]{new StatusEffectInstance(StatusEffects.INSTANT_HEALTH, 1)}));

}
