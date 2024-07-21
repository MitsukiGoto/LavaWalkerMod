package com.github.mikn.lavawalker;

import com.github.mikn.lavawalker.block.ModdedObsidian;
import com.github.mikn.lavawalker.init.BlockInit;
import com.github.mikn.lavawalker.init.ItemInit;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.blockstates.PropertyDispatch;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.data.models.model.DelegatedModel;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EnchantmentTags;
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

public class ModDataGenerator implements DataGeneratorEntrypoint {

  @Override
  public void onInitializeDataGenerator(FabricDataGenerator generator) {
    FabricDataGenerator.Pack pack = generator.createPack();
    pack.addProvider(BlockModelGenerator::new);
    pack.addProvider(EnchantmentGenerator::new);
  }

  private static class BlockModelGenerator extends FabricModelProvider {

    public BlockModelGenerator(FabricDataOutput output) {
      super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
      blockStateModelGenerator.blockStateOutput.accept(
          MultiVariantGenerator.multiVariant(BlockInit.MODDED_OBSIDIAN).with(
              PropertyDispatch.property(ModdedObsidian.AGE).select(0, Variant.variant()
                  .with(VariantProperties.MODEL,
                      ModelLocationUtils.getModelLocation(Blocks.OBSIDIAN))).select(1,
                  Variant.variant().with(VariantProperties.MODEL,
                      ModelLocationUtils.getModelLocation(Blocks.OBSIDIAN))).select(2,
                  Variant.variant().with(VariantProperties.MODEL,
                      ModelLocationUtils.getModelLocation(Blocks.CRYING_OBSIDIAN))).select(3,
                  Variant.variant().with(VariantProperties.MODEL,
                      ModelLocationUtils.getModelLocation(Blocks.CRYING_OBSIDIAN)))));
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
      itemModelGenerator.output.accept(
          ModelLocationUtils.getModelLocation(ItemInit.MODDED_OBSIDIAN),
          new DelegatedModel(ModelLocationUtils.getModelLocation(Blocks.OBSIDIAN)));
    }
  }

  private static class EnchantmentGenerator extends FabricDynamicRegistryProvider {

    public EnchantmentGenerator(FabricDataOutput output,
        CompletableFuture<HolderLookup.Provider> registriesFuture) {
      super(output, registriesFuture);
    }

    @Override
    protected void configure(HolderLookup.Provider registries, Entries entries) {
      HolderGetter<Enchantment> enchantmentHolder = registries.lookupOrThrow(
          Registries.ENCHANTMENT);
      HolderGetter<Item> itemHolder = registries.lookupOrThrow(Registries.ITEM);
      ResourceKey<Enchantment> LAVA_WALKER = ResourceKey.create(Registries.ENCHANTMENT,
          ResourceLocation.fromNamespaceAndPath(LavaWalker.MODID, "lava_walker"));
      entries.add(LAVA_WALKER, Enchantment.enchantment(
              Enchantment.definition(itemHolder.getOrThrow(ItemTags.FOOT_ARMOR_ENCHANTABLE), 2, 2,
                  Enchantment.dynamicCost(10, 10), Enchantment.dynamicCost(25, 10), 4,
                  EquipmentSlotGroup.FEET))
          .exclusiveWith(enchantmentHolder.getOrThrow(EnchantmentTags.BOOTS_EXCLUSIVE))
          .withEffect(EnchantmentEffectComponents.LOCATION_CHANGED, new ReplaceDisk(
                  new LevelBasedValue.Clamped(LevelBasedValue.perLevel(3.0F, 1.0F), 0.0F, 16.0F),
                  LevelBasedValue.constant(1.0F), new Vec3i(0, -1, 0), Optional.of(
                  BlockPredicate.allOf(BlockPredicate.matchesTag(new Vec3i(0, 1, 0), BlockTags.AIR),
                      BlockPredicate.matchesBlocks(Blocks.LAVA),
                      BlockPredicate.matchesFluids(Fluids.LAVA), BlockPredicate.unobstructed())),
                  BlockStateProvider.simple(BlockInit.MODDED_OBSIDIAN), Optional.of(GameEvent.BLOCK_PLACE)),
              LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS,
                  net.minecraft.advancements.critereon.EntityPredicate.Builder.entity().flags(
                      net.minecraft.advancements.critereon.EntityFlagsPredicate.Builder.flags()
                          .setOnGround(true)))).build(LAVA_WALKER.location()));
    }

    @Override
    public String getName() {
      return "dynamicData";
    }
  }
}
