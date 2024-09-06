package com.github.mikn.lavawalker.datagen;

import com.github.mikn.lavawalker.LavaWalker;
import net.minecraft.data.PackOutput;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class LavaWalkerItemModelProvider extends ItemModelProvider {

  public LavaWalkerItemModelProvider(PackOutput output,
      ExistingFileHelper exFileHelper) {
    super(output, LavaWalker.MODID, exFileHelper);
  }

  @Override
  protected void registerModels() {
    withExistingParent("modded_obsidian", ModelLocationUtils.getModelLocation(Blocks.OBSIDIAN));
  }
}
