package dev.cammiescorner.arcanuscontinuum.common.spell_components.effects;

import dev.cammiescorner.arcanuscontinuum.api.spells.SpellEffect;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellType;
import dev.cammiescorner.arcanuscontinuum.api.spells.Weight;
import dev.cammiescorner.arcanuscontinuum.common.items.StaffItem;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusSpellComponents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ExchangeSpellEffect extends SpellEffect {
	public ExchangeSpellEffect(SpellType type, ParticleEffect particle, Weight weight, double manaCost, int coolDown, int minLevel) {
		super(type, particle, weight, manaCost, coolDown, minLevel);
	}

	@Override
	public void effect(@Nullable LivingEntity caster, World world, HitResult target, List<SpellEffect> effects, StaffItem staffItem, ItemStack stack) {
		if(target.getType() == HitResult.Type.ENTITY) {
			EntityHitResult entityHit = (EntityHitResult) target;

			System.out.println();
			if(entityHit.getEntity() instanceof LivingEntity livingEntity && caster != null && caster.distanceTo(livingEntity) <= 5 * effects.stream().filter(effect -> effect == ArcanusSpellComponents.EXCHANGE).count()) {
				Vec3d casterPos = caster.getPos();
				Vec3d targetPos = livingEntity.getPos();

				caster.teleport(targetPos.getX(), targetPos.getY(), targetPos.getZ(), true);
				livingEntity.teleport(casterPos.getX(), casterPos.getY(), casterPos.getZ(), true);
			}
		}
	}
}