package me.vinceh121.minegasm.mixin;

import java.util.function.Supplier;

import net.minecraft.util.registry.RegistryEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import com.therainbowville.minegasm.client.ClientEventHandler;

import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

@Mixin(ClientWorld.class)
public class ClientWorldMixins {
	@Inject(at = @At("TAIL"), method = "<init>")
	public void onWorldLoad(ClientPlayNetworkHandler netHandler, ClientWorld.Properties properties, RegistryKey registryRef, RegistryEntry registryEntry, int loadDistance, int simulationDistance, Supplier profiler, WorldRenderer worldRenderer, boolean debugWorld, long seed, CallbackInfo ci) {
		ClientEventHandler.onWorldLoaded((World) (Object) this);
	}

	@Inject(at = @At("TAIL"), method = "removeEntity", locals = LocalCapture.CAPTURE_FAILEXCEPTION)
	public void onRemoveEntity(int entityId, Entity.RemovalReason removalReason, CallbackInfo ci, Entity entity) {
		ClientEventHandler.onWorldExit(entity);
	}

	@Inject(at = @At("HEAD"), method = "addEntityPrivate")
	public void onEntityAdded(int id, Entity entity, CallbackInfo ci) {
		ClientEventHandler.onWorldEntry(entity);
	}
}
