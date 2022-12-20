package net.ibadgerlord.rhodium.potion;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.potion.Potion;
import net.minecraft.util.registry.Registry;

public class CursedPotions {

    // Register Grand Potions method
    private static Potion registerCursedPotion(String name, Potion potion) {
        return Registry.register(Registry.POTION, name, potion);
    }

    public static final Potion EMPTY = registerCursedPotion("empty",
            new Potion(new StatusEffectInstance[0]));
    public static final Potion POISON = registerCursedPotion("poison",
            new Potion(new StatusEffectInstance[]{new StatusEffectInstance(StatusEffects.POISON, 900)}));

}
