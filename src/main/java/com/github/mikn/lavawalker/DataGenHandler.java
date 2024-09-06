package com.github.mikn.lavawalker;

import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = LavaWalker.MODID)
public class DataGenHandler {

  @SubscribeEvent
  public static void gatherData(GatherDataEvent event) {
    DataGenerator generator = event.getGenerator();
    PackOutput output = generator.getPackOutput();
    ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
    CompletableFuture<Provider> lookUpProvider = event.getLookupProvider();
    generator.addProvider(event.includeClient(),
        new LavaWalkerBlockStateProvider(output, existingFileHelper));
    generator.addProvider(event.includeClient(),
        new LavaWalkerItemModelProvider(output, existingFileHelper));
    generator.addProvider(event.includeServer(),
        new LavaWalkerEnchantmentProvider(output, lookUpProvider));
  }
}
