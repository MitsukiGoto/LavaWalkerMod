package com.github.mikn.lavawalker;

import com.github.mikn.lavawalker.enchantment.LavaWalkerEnchantment;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

public class LavaWalkerEnchantmentProvider extends DatapackBuiltinEntriesProvider {

  private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder().add(
      Registries.ENCHANTMENT, LavaWalkerEnchantment::bootstrap);

  public LavaWalkerEnchantmentProvider(PackOutput output, CompletableFuture<Provider> registries) {
    super(output, registries, BUILDER, Set.of(LavaWalker.MODID));
  }
}
