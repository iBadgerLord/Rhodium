package net.ibadgerlord.rhodium.items;

import net.ibadgerlord.rhodium.potion.GrandPotions;
import net.ibadgerlord.rhodium.util.init.RhodiumItemRegistry;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.potion.PotionUtil;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class GrandPotionItem extends Item {
    private static final int MAX_USE_TIME = 32;

    private final StatusEffect effectKey;
    private final int effectDuration;
    private final int effectAmplifier;
    private final int overlayColor;

    public GrandPotionItem(StatusEffect effectKey, int effectDuration, int effectAmplifier, int overlayColor, Settings settings) {
        super(settings);
        this.effectKey = effectKey;
        this.effectDuration = effectDuration;
        this.effectAmplifier = effectAmplifier;
        this.overlayColor = overlayColor;
    }

    public boolean hasGlint(ItemStack stack) {
        return true;
    }

    public int getMaxUseTime(ItemStack stack) {
        return 32;
    }

    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return ItemUsage.consumeHeldItem(world, user, hand);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (user instanceof ServerPlayerEntity) {
            ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)user;
            Criteria.CONSUME_ITEM.trigger(serverPlayerEntity, stack);
            serverPlayerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
        }

        if (user instanceof PlayerEntity && !((PlayerEntity)user).getAbilities().creativeMode) {
            stack.decrement(1);
        }

        if (!world.isClient) {

            List<StatusEffectInstance> effects = Arrays.asList(
                    new StatusEffectInstance(effectKey, effectDuration, effectAmplifier));
            for (StatusEffectInstance effect : effects) {
                user.addStatusEffect(effect);
            }

        }


        return stack.isEmpty() ? new ItemStack(RhodiumItemRegistry.GRAND_BOTTLE) : stack;
    }

}