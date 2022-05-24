package com.github.mikn.lavawalker.asm.mixin;

import com.github.mikn.lavawalker.event.OnChangedBlockEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(method = "onChangedBlock(Lnet/minecraft/core/BlockPos;)V", at = @At("HEAD"), cancellable = true)
    private void inject(BlockPos blockPos, CallbackInfo ci) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        if (MinecraftForge.EVENT_BUS.post(new OnChangedBlockEvent(blockPos, livingEntity))) {
            ci.cancel();
        }
    }
}
