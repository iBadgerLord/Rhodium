package net.ibadgerlord.rhodium.items.potions;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class GrandPotionItem extends Item {

    public GrandPotionItem(Settings settings) {
        super(settings);
    }

    public ItemStack getDefaultStack() {
        return PotionUtil.setPotion(super.getDefaultStack(), Potions.WATER);
    }

    public int getMaxUseTime(ItemStack stack) {
        return 32;
    }

    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    public boolean hasGlint(ItemStack stack) {
        return true;
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return ItemUsage.consumeHeldItem(world, user, hand);
    }


}
