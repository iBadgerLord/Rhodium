package net.ibadgerlord.rhodium.items.potions;

import net.ibadgerlord.rhodium.util.init.RhodiumItemRegistry;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.List;

public class GrandLeaping extends GrandPotionItem {

    public GrandLeaping(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        PlayerEntity playerEntity;
        PlayerEntity playerEntity2 = playerEntity = user instanceof PlayerEntity ? (PlayerEntity)user : null;

        if (!world.isClient) {
            Criteria.CONSUME_ITEM.trigger((ServerPlayerEntity)playerEntity, stack);
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 3600, 2));
            if (!playerEntity.getAbilities().creativeMode) {
                stack.decrement(1);
            }

            if (!playerEntity.getAbilities().creativeMode) {
                if (stack.isEmpty()) {
                    return new ItemStack(RhodiumItemRegistry.GRAND_BOTTLE);
                }
                if (playerEntity != null) {
                    playerEntity.getInventory().insertStack(new ItemStack(RhodiumItemRegistry.GRAND_BOTTLE));
                }
            }

        }

        world.emitGameEvent((Entity)user, GameEvent.DRINKING_FINISH, user.getCameraBlockPos());
        return stack;
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add( new TranslatableText("item.rhodium.grand_leaping_potion.tooltip1").formatted(Formatting.BLUE) );
    }

}
