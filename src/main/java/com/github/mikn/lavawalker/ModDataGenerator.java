package com.github.mikn.lavawalker;

import com.github.mikn.lavawalker.block.ModdedObsidian;
import com.github.mikn.lavawalker.init.BlockInit;
import com.github.mikn.lavawalker.init.ItemInit;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.blockstates.PropertyDispatch;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.data.models.model.DelegatedModel;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class ModDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();
        pack.addProvider(TagGenerator::new);
    }

    private static class TagGenerator extends FabricModelProvider {

        public TagGenerator(FabricDataOutput output) {
            super(output);
        }

        @Override
        public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
            blockStateModelGenerator.blockStateOutput
                    .accept(MultiVariantGenerator.multiVariant(BlockInit.MODDED_OBSIDIAN)
                            .with(PropertyDispatch.property(ModdedObsidian.AGE)
                                    .select(0,
                                            Variant.variant().with(VariantProperties.MODEL,
                                                    ModelLocationUtils.getModelLocation(Blocks.OBSIDIAN)))
                                    .select(1,
                                            Variant.variant().with(VariantProperties.MODEL,
                                                    ModelLocationUtils.getModelLocation(Blocks.OBSIDIAN)))
                                    .select(2,
                                            Variant.variant().with(VariantProperties.MODEL,
                                                    ModelLocationUtils.getModelLocation(Blocks.CRYING_OBSIDIAN)))
                                    .select(3,
                                            Variant.variant().with(VariantProperties.MODEL,
                                                    ModelLocationUtils.getModelLocation(Blocks.CRYING_OBSIDIAN)))));
        }

        @Override
        public void generateItemModels(ItemModelGenerators itemModelGenerator) {
            itemModelGenerator.output.accept(ModelLocationUtils.getModelLocation(ItemInit.MODDED_OBSIDIAN),
                    new DelegatedModel(ModelLocationUtils.getModelLocation(Blocks.OBSIDIAN)));
        }
    }
}
