package com.github.mikn.lavawalker.enchantment;

import com.github.mikn.lavawalker.LavaWalker;
import com.github.mikn.lavawalker.init.BlockInit;
import java.util.Optional;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.ReplaceDisk;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;

public class LavaWalkerEnchantment {

  public static final ResourceKey<Enchantment> LAVA_WALKER = registerKey("lava_walker");

  public static void bootstrap(BootstrapContext<Enchantment> context) {
    HolderGetter<Enchantment> enchantmentHolder = context.lookup(
        Registries.ENCHANTMENT);
    HolderGetter<Item> itemHolder = context.lookup(Registries.ITEM);
    register(context, LAVA_WALKER, Enchantment.enchantment(
            Enchantment.definition(itemHolder.getOrThrow(ItemTags.FOOT_ARMOR_ENCHANTABLE), 2, 2,
                Enchantment.dynamicCost(10, 10), Enchantment.dynamicCost(25, 10), 4,
                EquipmentSlotGroup.FEET))
        .withEffect(EnchantmentEffectComponents.LOCATION_CHANGED, new ReplaceDisk(
                new LevelBasedValue.Clamped(LevelBasedValue.perLevel(3.0F, 1.0F), 0.0F, 16.0F),
                LevelBasedValue.constant(1.0F), new Vec3i(0, -1, 0), Optional.of(
                BlockPredicate.allOf(BlockPredicate.matchesTag(new Vec3i(0, 1, 0), BlockTags.AIR),
                    BlockPredicate.matchesBlocks(Blocks.LAVA),
                    BlockPredicate.matchesFluids(Fluids.LAVA), BlockPredicate.unobstructed())),
                BlockStateProvider.simple(BlockInit.MODDED_OBSIDIAN.get()),
                Optional.of(GameEvent.BLOCK_PLACE)),
            LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS,
                net.minecraft.advancements.critereon.EntityPredicate.Builder.entity().flags(
                    net.minecraft.advancements.critereon.EntityFlagsPredicate.Builder.flags()
                        .setOnGround(true)))));
  }

  private static void register(BootstrapContext<Enchantment> pContext, ResourceKey<Enchantment> key,
      Enchantment.Builder builder) {
    pContext.register(key, builder.build(key.location()));
  }

  public static ResourceKey<Enchantment> registerKey(String name) {
    return ResourceKey.create(
        Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(LavaWalker.MODID, name));
  }

}
