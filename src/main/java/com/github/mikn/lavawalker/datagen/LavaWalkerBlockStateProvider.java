package com.github.mikn.lavawalker.datagen;

import com.github.mikn.lavawalker.LavaWalker;
import com.github.mikn.lavawalker.block.ModdedObsidian;
import com.github.mikn.lavawalker.init.BlockInit;
import net.minecraft.data.PackOutput;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile.UncheckedModelFile;
import net.neoforged.neoforge.client.model.generators.VariantBlockStateBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class LavaWalkerBlockStateProvider extends BlockStateProvider {

  public LavaWalkerBlockStateProvider(PackOutput output,
      ExistingFileHelper exFileHelper) {
    super(output, LavaWalker.MODID, exFileHelper);
  }

  @Override
  protected void registerStatesAndModels() {
    VariantBlockStateBuilder.PartialBlockstate builder = getVariantBuilder(
        BlockInit.MODDED_OBSIDIAN.get()).partialState();
    final ConfiguredModel OBSIDIAN = ConfiguredModel.builder()
        .modelFile(new UncheckedModelFile(ModelLocationUtils.getModelLocation(Blocks.OBSIDIAN)))
        .buildLast();
    final ConfiguredModel CRYING_OBSIDIAN = ConfiguredModel.builder().modelFile(
            new UncheckedModelFile(ModelLocationUtils.getModelLocation(Blocks.CRYING_OBSIDIAN)))
        .buildLast();
    builder.with(ModdedObsidian.AGE, 0).setModels(OBSIDIAN);
    builder.with(ModdedObsidian.AGE, 1).setModels(OBSIDIAN);
    builder.with(ModdedObsidian.AGE, 2).setModels(CRYING_OBSIDIAN);
    builder.with(ModdedObsidian.AGE, 3).setModels(CRYING_OBSIDIAN);
  }
}
