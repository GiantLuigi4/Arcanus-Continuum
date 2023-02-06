package dev.cammiescorner.arcanuscontinuum.common.spell_components.shapes;

import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellEffect;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellGroup;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellShape;
import dev.cammiescorner.arcanuscontinuum.api.spells.Weight;
import dev.cammiescorner.arcanuscontinuum.common.items.StaffItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.List;
import java.util.function.Predicate;

public class HitscanSpellShape extends SpellShape {
	private static final double RANGE_MODIFIER = 2.5D;
	private static final double MAX_ANGLE_DIFF = Math.toRadians(5);

	public HitscanSpellShape(Weight weight, double manaCost, int coolDown, int minLevel) {
		super(weight, manaCost, coolDown, minLevel);
	}

	@Override
	public void cast(LivingEntity caster, Vec3d castFrom, @Nullable Entity castSource, World world, StaffItem staffItem, ItemStack stack, List<SpellEffect> effects, List<SpellGroup> spellGroups, int groupIndex) {
		double range = ReachEntityAttributes.getAttackRange(caster, caster instanceof PlayerEntity player && player.isCreative() ? 5 : 4.5) * RANGE_MODIFIER;
		range *= range;
		Box box = new Box(castFrom.add(-range, -range, -range), castFrom.add(range, range, range));
		List<Entity> affectedEntities = world.getOtherEntities(castSource, box);

		Predicate<Entity> predicate = entity -> {
			Vec3d look = caster.getRotationVector();
			Vec3d direction = entity.getPos().subtract(castFrom);
			double angle = Math.acos(look.dotProduct(direction) / (look.length() * direction.length()));
			return angle > MAX_ANGLE_DIFF;
		};

		Entity hit = getClosestEntity(affectedEntities, range, castFrom, castSource == caster ? predicate : entity -> true);

		if(hit != null)
			for(SpellEffect effect : new HashSet<>(effects))
				effect.effect(caster, world, new EntityHitResult(hit), effects, staffItem, stack);

		castNext(caster, hit != null ? hit.getPos() : castFrom, castSource, world, staffItem, stack, effects, spellGroups, groupIndex);
	}

	@Nullable
	private static Entity getClosestEntity(List<Entity> entityList, double range, Vec3d pos, Predicate<Entity> predicate) {
		double d = -1.0;
		Entity value = null;

		for (Entity entity : entityList) {
			if (predicate.test(entity)) {
				double e = entity.squaredDistanceTo(pos);
				if (e <= range && (d == -1.0 || e < d)) {
					d = e;
					value = entity;
				}
			}
		}

		return value;
	}
}
