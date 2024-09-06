package com.github.mikn.lavawalker.datagen;

import static com.github.mikn.lavawalker.enchantment.LavaWalkerEnchantment.LAVA_WALKER;

import com.github.mikn.lavawalker.LavaWalker;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EnchantmentTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class LavaWalkerTagProvider extends EnchantmentTagsProvider {

  public static final TagKey<Enchantment> LAVA_WALKER_EXCLUSIVE = TagKey.create(
      Registries.ENCHANTMENT,
      ResourceLocation.fromNamespaceAndPath(LavaWalker.MODID, "exclusive_set/lava_walker"));

  public LavaWalkerTagProvider(PackOutput output,
      CompletableFuture<Provider> lookupProvider,
      @Nullable ExistingFileHelper existingFileHelper) {
    super(output, lookupProvider, LavaWalker.MODID, existingFileHelper);
  }

  @Override
  protected void addTags(Provider provider) {
    tag(EnchantmentTags.TREASURE).addOptional(LAVA_WALKER.location()).replace(false);
    tag(EnchantmentTags.TRADEABLE).addOptional(LAVA_WALKER.location()).replace(false);
    tag(EnchantmentTags.TRADES_DESERT_COMMON).addOptional(LAVA_WALKER.location()).replace(false);
    tag(EnchantmentTags.TRADES_TAIGA_COMMON).addOptional(LAVA_WALKER.location()).replace(false);
    tag(LAVA_WALKER_EXCLUSIVE).add(Enchantments.FROST_WALKER).addOptional(LAVA_WALKER.location());
  }
}
