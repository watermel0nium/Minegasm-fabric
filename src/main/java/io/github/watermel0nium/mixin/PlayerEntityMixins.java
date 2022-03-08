package io.github.watermel0nium.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import io.github.watermel0nium.ClientEventHandler;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixins {
	@Inject(at = @At("TAIL"), method = "tick()V")
	private void playerTick(CallbackInfo ci) {
		ClientEventHandler.onPlayerTick((PlayerEntity) (Object) this);
	}

	@Inject(at = @At("RETURN"), method = "canHarvest(Lnet/minecraft/block/BlockState;)Z")
	private void onHarvestCheck(BlockState state, CallbackInfoReturnable<Boolean> ci) {
		ClientEventHandler.onHarvest((PlayerEntity) (Object) this, state, ci.getReturnValueZ());
	}
}
