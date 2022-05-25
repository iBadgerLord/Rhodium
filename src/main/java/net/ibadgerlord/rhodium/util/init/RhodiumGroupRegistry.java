package net.ibadgerlord.rhodium.util.init;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.ibadgerlord.rhodium.Rhodium;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class RhodiumGroupRegistry {

    public static final ItemGroup RHODIUM_GROUP = FabricItemGroupBuilder.build(new Identifier(Rhodium.MOD_ID, "rhodium"),
            () -> new ItemStack(RhodiumItemRegistry.ACORN));

}
