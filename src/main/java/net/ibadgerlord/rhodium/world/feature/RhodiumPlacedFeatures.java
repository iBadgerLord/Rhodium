package net.ibadgerlord.rhodium.world.feature;

import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.placementmodifier.HeightRangePlacementModifier;

public class RhodiumPlacedFeatures {

    public static final RegistryEntry<PlacedFeature> COBALT_ORE_PLACED = PlacedFeatures.register("cobalt_ore_placed",
            RhodiumConfiguredFeatures.COBALT_ORE, RhodiumOreFeatures.modifiersWithCount(7,
                    HeightRangePlacementModifier.trapezoid(YOffset.aboveBottom(-80), YOffset.aboveBottom(80))));
    public static final RegistryEntry<PlacedFeature> MYTHRIL_ORE_PLACED = PlacedFeatures.register("mythril_ore_placed",
            RhodiumConfiguredFeatures.MYTHRIL_ORE, RhodiumOreFeatures.modifiersWithCount(7,
                    HeightRangePlacementModifier.trapezoid(YOffset.aboveBottom(-90), YOffset.aboveBottom(70))));
    public static final RegistryEntry<PlacedFeature> PALLADIUM_ORE_PLACED = PlacedFeatures.register("palladium_ore_placed",
            RhodiumConfiguredFeatures.PALLADIUM_ORE, RhodiumOreFeatures.modifiersWithCount(7,
                    HeightRangePlacementModifier.trapezoid(YOffset.aboveBottom(-100), YOffset.aboveBottom(60))));
    public static final RegistryEntry<PlacedFeature> ORICHALCUM_ORE_PLACED = PlacedFeatures.register("orichalcum_ore_placed",
            RhodiumConfiguredFeatures.ORICHALCUM_ORE, RhodiumOreFeatures.modifiersWithCount(7,
                    HeightRangePlacementModifier.trapezoid(YOffset.aboveBottom(-110), YOffset.aboveBottom(50))));

}