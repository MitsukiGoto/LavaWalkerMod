/*
 Copyright (c) 2022 Mikndesu

 Permission is hereby granted, free of charge, to any person obtaining a copy of
 this software and associated documentation files (the "Software"), to deal in
 the Software without restriction, including without limitation the rights to
 use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 the Software, and to permit persons to whom the Software is furnished to do so,
 subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.mikn.lavawalker;

import com.github.mikn.lavawalker.config.LavaWalkerConfig;
import com.github.mikn.lavawalker.init.BlockInit;
import com.github.mikn.lavawalker.init.ItemInit;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LavaWalker implements ModInitializer {

    public static final String MODID = "lava_walker";
    public static final Logger LOGGER = LogManager.getLogger("LavaWalker/Main");
    public static final LavaWalkerConfig HOLDER;

    @Override
    public void onInitialize() {
        Registry.register(BuiltInRegistries.BLOCK,
                withLavaWalkerDefaultNameSpace("modded_obsidian"), BlockInit.MODDED_OBSIDIAN);
        Registry.register(BuiltInRegistries.ITEM,
                withLavaWalkerDefaultNameSpace("modded_obsidian"), ItemInit.MODDED_OBSIDIAN);
    }

    public static ResourceLocation withLavaWalkerDefaultNameSpace(String nameSpace) {
        return ResourceLocation.fromNamespaceAndPath(LavaWalker.MODID, nameSpace);
    }

    static {
        HOLDER = AutoConfig.register(LavaWalkerConfig.class, JanksonConfigSerializer::new).getConfig();
    }

}