package net.kav.kav_better_death.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class MixinPlayerEntity {

    @Inject(method = "shouldAlwaysDropXp", at = @At("HEAD"), cancellable = true)
    private void modifyShouldAlwaysDropXp(CallbackInfoReturnable<Boolean> cir) {
        // Prevent the player from dropping experience points
        cir.setReturnValue(false);
    }

    @Inject(method = "getXpToDrop", at = @At("HEAD"), cancellable = true)
    private void modifyGetXpToDrop(CallbackInfoReturnable<Integer> cir) {
        // Check if the player should keep their experience points
        cir.setReturnValue(0);
        System.out.println("here");
    }

    // You can add other Mixin methods here if needed.
}