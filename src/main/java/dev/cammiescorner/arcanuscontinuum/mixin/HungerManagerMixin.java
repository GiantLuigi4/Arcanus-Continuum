package dev.cammiescorner.arcanuscontinuum.mixin;

import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusComponents;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(HungerManager.class)
public class HungerManagerMixin {
	@ModifyVariable(method = "update", at = @At(value = "INVOKE_ASSIGN",
			target = "Lnet/minecraft/world/GameRules;getBoolean(Lnet/minecraft/world/GameRules$Key;)Z",
			ordinal = 0
	))
	public boolean arcanuscontinuum$hasBurnout(boolean bl, PlayerEntity player) {
		return ArcanusComponents.getBurnout(player) <= 0 && player.world.getGameRules().getBoolean(GameRules.NATURAL_REGENERATION);
	}
}
