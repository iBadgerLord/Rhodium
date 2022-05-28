package net.ibadgerlord.rhodium.world.feature;

import net.minecraft.block.Blocks;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.*;

import java.util.List;

public class RhodiumConfiguredFeatures {

    public static final List<OreFeatureConfig.Target> OVERWORLD_COBALT_ORE = List.of(
            OreFeatureConfig.createTarget(OreConfiguredFeatures.STONE_ORE_REPLACEABLES,
                    Blocks.STONE.getDefaultState()),
            OreFeatureConfig.createTarget(OreConfiguredFeatures.DEEPSLATE_ORE_REPLACEABLES,
                    Blocks.STONE.getDefaultState())
    );
    public static final List<OreFeatureConfig.Target> OVERWORLD_MYTHRIL_ORE = List.of(
            OreFeatureConfig.createTarget(OreConfiguredFeatures.STONE_ORE_REPLACEABLES,
                    Blocks.STONE.getDefaultState()),
            OreFeatureConfig.createTarget(OreConfiguredFeatures.DEEPSLATE_ORE_REPLACEABLES,
                    Blocks.STONE.getDefaultState())
    );
    public static final List<OreFeatureConfig.Target> OVERWORLD_PALLADIUM_ORE = List.of(
            OreFeatureConfig.createTarget(OreConfiguredFeatures.STONE_ORE_REPLACEABLES,
                    Blocks.STONE.getDefaultState()),
            OreFeatureConfig.createTarget(OreConfiguredFeatures.DEEPSLATE_ORE_REPLACEABLES,
                    Blocks.STONE.getDefaultState())
    );
    public static final List<OreFeatureConfig.Target> OVERWORLD_ORICHALCUM_ORE = List.of(
            OreFeatureConfig.createTarget(OreConfiguredFeatures.STONE_ORE_REPLACEABLES,
                    Blocks.STONE.getDefaultState()),
            OreFeatureConfig.createTarget(OreConfiguredFeatures.DEEPSLATE_ORE_REPLACEABLES,
                    Blocks.STONE.getDefaultState())
    );


    public static final RegistryEntry<ConfiguredFeature<OreFeatureConfig, ?>> COBALT_ORE =
            ConfiguredFeatures.register("cobalt_ore", Feature.ORE,
                    new OreFeatureConfig(OVERWORLD_COBALT_ORE, 8));
    public static final RegistryEntry<ConfiguredFeature<OreFeatureConfig, ?>> MYTHRIL_ORE =
            ConfiguredFeatures.register("mythril_ore", Feature.ORE,
                    new OreFeatureConfig(OVERWORLD_MYTHRIL_ORE, 6));
    public static final RegistryEntry<ConfiguredFeature<OreFeatureConfig, ?>> PALLADIUM_ORE =
            ConfiguredFeatures.register("palladium_ore", Feature.ORE,
                    new OreFeatureConfig(OVERWORLD_PALLADIUM_ORE, 4));
    public static final RegistryEntry<ConfiguredFeature<OreFeatureConfig, ?>> ORICHALCUM_ORE =
            ConfiguredFeatures.register("orichalcum_ore", Feature.ORE,
                    new OreFeatureConfig(OVERWORLD_ORICHALCUM_ORE, 2));

}
