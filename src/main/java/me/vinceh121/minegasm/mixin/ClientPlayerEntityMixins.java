package me.vinceh121.minegasm.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.therainbowville.minegasm.client.ClientEventHandler;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixins {

	@Shadow
	public boolean healthInitialized;

	@Inject(at = @At("HEAD"), method = "setExperience(FII)V")
	private void onXpAdded(float progress, int total, int level, CallbackInfo ci) {
		ClientPlayerEntity thos = (ClientPlayerEntity) (Object) this;
		ClientEventHandler.onXpChange(thos, level - thos.experienceLevel);
	}

	@Inject(at = @At("HEAD"), method = "damage(Lnet/minecraft/entity/damage/DamageSource;F)Z")
	private void playerReceivedDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> ci) {
		ClientEventHandler.onHurt(((ClientPlayerEntity) (Object) this).getGameProfile(), source, amount);
	}

	@Inject(at = @At("HEAD"), method = "updateHealth(F)V")
	private void onGoCommitDie(float health, CallbackInfo ci) {
		if (this.healthInitialized && health == 0) {
			ClientEventHandler.onDeath((PlayerEntity) (Object) this);
		}
	}

	@Inject(at = @At("HEAD"), method = "requestRespawn()V")
	private void onRespawn(CallbackInfo ci) {
		System.out.println("respawn owo");
		ClientEventHandler.onRespawn();
	}
}
