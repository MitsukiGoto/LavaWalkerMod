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

package com.github.mikn.lavawalker.asm.mixin;

import com.github.mikn.lavawalker.LavaWalker;
import com.github.mikn.lavawalker.init.EnchantmentInit;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.FrostWalkerEnchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FrostWalkerEnchantment.class)
public class FrostWalkerEnchantmentMixin extends Enchantment {

    public FrostWalkerEnchantmentMixin(EnchantmentDefinition definition) {
        super(definition);
    }

    @Inject(method = "checkCompatibility(Lnet/minecraft/world/item/enchantment/Enchantment;)Z", at = @At("RETURN"), cancellable = true)
    private void lava_walker$checkCompatibility(Enchantment enchantment, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(super.checkCompatibility(enchantment) && enchantment != Enchantments.DEPTH_STRIDER
                && LavaWalker.HOLDER.shouldExclusiveWithFrost ? enchantment != EnchantmentInit.LAVA_WALKER : true);
    }
}
