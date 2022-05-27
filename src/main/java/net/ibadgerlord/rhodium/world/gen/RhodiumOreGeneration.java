package net.ibadgerlord.rhodium.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.ibadgerlord.rhodium.world.feature.RhodiumPlacedFeatures;
import net.minecraft.world.gen.GenerationStep;

public class RhodiumOreGeneration {

    public static void generateOres() {
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.UNDERGROUND_ORES, RhodiumPlacedFeatures.COBALT_ORE_PLACED.getKey().get());
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.UNDERGROUND_ORES, RhodiumPlacedFeatures.MYTHRIL_ORE_PLACED.getKey().get());
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.UNDERGROUND_ORES, RhodiumPlacedFeatures.PALLADIUM_ORE_PLACED.getKey().get());
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.UNDERGROUND_ORES, RhodiumPlacedFeatures.ORICHALCUM_ORE_PLACED.getKey().get());

    }

}
