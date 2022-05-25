package net.ibadgerlord.rhodium.util.init;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.ibadgerlord.rhodium.Rhodium;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class RhodiumParticleRegistry {

    public static final DefaultParticleType SMALL_SOUL_FIRE_FLAME = FabricParticleTypes.simple();

    public static final DefaultParticleType GREEN_FLAME = FabricParticleTypes.simple();
    public static final DefaultParticleType SMALL_GREEN_FLAME = FabricParticleTypes.simple();

    public static void registerRhodiumParticles() {
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(Rhodium.MOD_ID, "small_soul_fire_flame"), SMALL_SOUL_FIRE_FLAME);

        Registry.register(Registry.PARTICLE_TYPE, new Identifier(Rhodium.MOD_ID, "green_flame"), GREEN_FLAME);
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(Rhodium.MOD_ID, "small_green_flame"), SMALL_GREEN_FLAME);
    }


}
