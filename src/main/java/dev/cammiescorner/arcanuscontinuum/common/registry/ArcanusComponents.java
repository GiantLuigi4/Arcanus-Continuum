package dev.cammiescorner.arcanuscontinuum.common.registry;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.api.spells.Pattern;
import dev.cammiescorner.arcanuscontinuum.api.spells.SpellShape;
import dev.cammiescorner.arcanuscontinuum.common.compat.ArcanusCompat;
import dev.cammiescorner.arcanuscontinuum.common.compat.PehkuiCompat;
import dev.cammiescorner.arcanuscontinuum.common.components.*;
import dev.cammiescorner.arcanuscontinuum.common.entities.magic.*;
import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class ArcanusComponents implements EntityComponentInitializer {
	public static final ComponentKey<WizardLevelComponent> WIZARD_LEVEL_COMPONENT = createComponent("wizard_level", WizardLevelComponent.class);
	public static final ComponentKey<ManaComponent> MANA_COMPONENT = createComponent("mana", ManaComponent.class);
	public static final ComponentKey<BurnoutComponent> BURNOUT_COMPONENT = createComponent("burnout", BurnoutComponent.class);
	public static final ComponentKey<CastingComponent> CASTING_COMPONENT = createComponent("casting", CastingComponent.class);
	public static final ComponentKey<PatternComponent> PATTERN_COMPONENT = createComponent("casting_pattern", PatternComponent.class);
	public static final ComponentKey<LastCastTimeComponent> LAST_CAST_TIME_COMPONENT = createComponent("last_cast_time", LastCastTimeComponent.class);
	public static final ComponentKey<StunComponent> STUN_COMPONENT = createComponent("stun", StunComponent.class);
	public static final ComponentKey<QuestComponent> QUEST_COMPONENT = createComponent("quests", QuestComponent.class);
	public static final ComponentKey<MagicColourComponent> MAGIC_COLOUR = createComponent("magic_colour", MagicColourComponent.class);
	public static final ComponentKey<BoltTargetComponent> BOLT_TARGET = createComponent("bolt_target", BoltTargetComponent.class);
	public static final ComponentKey<SpellShapeComponent> SPELL_SHAPE = createComponent("spell_shape", SpellShapeComponent.class);
	public static final ComponentKey<SizeComponent> SIZE = createComponent("size", SizeComponent.class);

	@Override
	public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
		registry.beginRegistration(PlayerEntity.class, WIZARD_LEVEL_COMPONENT).respawnStrategy(RespawnCopyStrategy.ALWAYS_COPY).end(WizardLevelComponent::new);
		registry.beginRegistration(LivingEntity.class, MANA_COMPONENT).respawnStrategy(RespawnCopyStrategy.ALWAYS_COPY).end(ManaComponent::new);
		registry.beginRegistration(LivingEntity.class, BURNOUT_COMPONENT).respawnStrategy(RespawnCopyStrategy.ALWAYS_COPY).end(BurnoutComponent::new);
		registry.beginRegistration(PlayerEntity.class, CASTING_COMPONENT).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(CastingComponent::new);
		registry.beginRegistration(PlayerEntity.class, PATTERN_COMPONENT).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(PatternComponent::new);
		registry.beginRegistration(LivingEntity.class, LAST_CAST_TIME_COMPONENT).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(LastCastTimeComponent::new);
		registry.beginRegistration(LivingEntity.class, STUN_COMPONENT).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(StunComponent::new);
		registry.beginRegistration(PlayerEntity.class, QUEST_COMPONENT).respawnStrategy(RespawnCopyStrategy.ALWAYS_COPY).end(QuestComponent::new);
		registry.beginRegistration(ManaShieldEntity.class, MAGIC_COLOUR).respawnStrategy(RespawnCopyStrategy.ALWAYS_COPY).end(MagicColourComponent::new);
		registry.beginRegistration(SmiteEntity.class, MAGIC_COLOUR).respawnStrategy(RespawnCopyStrategy.ALWAYS_COPY).end(MagicColourComponent::new);
		registry.beginRegistration(MagicRuneEntity.class, MAGIC_COLOUR).respawnStrategy(RespawnCopyStrategy.ALWAYS_COPY).end(MagicColourComponent::new);
		registry.beginRegistration(MagicProjectileEntity.class, MAGIC_COLOUR).respawnStrategy(RespawnCopyStrategy.ALWAYS_COPY).end(MagicColourComponent::new);
		registry.beginRegistration(AreaOfEffectEntity.class, MAGIC_COLOUR).respawnStrategy(RespawnCopyStrategy.ALWAYS_COPY).end(MagicColourComponent::new);
		registry.beginRegistration(BeamEntity.class, MAGIC_COLOUR).respawnStrategy(RespawnCopyStrategy.ALWAYS_COPY).end(MagicColourComponent::new);
		registry.beginRegistration(LivingEntity.class, BOLT_TARGET).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(BoltTargetComponent::new);
		registry.beginRegistration(MagicProjectileEntity.class, SPELL_SHAPE).respawnStrategy(RespawnCopyStrategy.ALWAYS_COPY).end(SpellShapeComponent::new);

		ArcanusCompat.PEHKUI.ifEnabled(() -> () -> PehkuiCompat.registerEntityComponents(registry));
	}

	private static <T extends Component> ComponentKey<T> createComponent(String name, Class<T> component) {
		return ComponentRegistry.getOrCreate(Arcanus.id(name), component);
	}

	// ----- Helper Methods ----- //
	public static double getMaxMana(LivingEntity entity) {
		return MANA_COMPONENT.get(entity).getMaxMana();
	}

	public static double getManaLock(LivingEntity entity) {
		return MANA_COMPONENT.get(entity).getManaLock();
	}

	public static double getTrueMaxMana(LivingEntity entity) {
		return MANA_COMPONENT.get(entity).getTrueMaxMana();
	}

	public static double getMana(LivingEntity entity) {
		return MANA_COMPONENT.get(entity).getMana();
	}

	public static void setMana(LivingEntity entity, double amount) {
		MANA_COMPONENT.get(entity).setMana(amount);
	}

	public static boolean addMana(LivingEntity entity, double amount, boolean simulate) {
		return MANA_COMPONENT.get(entity).addMana(amount, simulate);
	}

	public static boolean drainMana(LivingEntity entity, double amount, boolean simulate) {
		return MANA_COMPONENT.get(entity).drainMana(amount, simulate);
	}

	public static double getBurnout(LivingEntity entity) {
		return BURNOUT_COMPONENT.get(entity).getBurnout();
	}

	public static void setBurnout(LivingEntity entity, double amount) {
		BURNOUT_COMPONENT.get(entity).setBurnout(amount);
	}

	public static boolean addBurnout(LivingEntity entity, double amount, boolean simulate) {
		return BURNOUT_COMPONENT.get(entity).addBurnout(amount, simulate);
	}

	public static boolean drainBurnout(LivingEntity entity, double amount, boolean simulate) {
		return BURNOUT_COMPONENT.get(entity).drainBurnout(amount, simulate);
	}

	public static int getWizardLevel(LivingEntity entity) {
		return WIZARD_LEVEL_COMPONENT.get(entity).getLevel();
	}

	public static void setWizardLevel(LivingEntity entity, int level) {
		WIZARD_LEVEL_COMPONENT.get(entity).setLevel(level);
	}

	public static int maxSpellSize(LivingEntity entity) {
		int level = getWizardLevel(entity);
		return level > 0 ? level == 1 ? 2 : 2 + level : 0;
	}

	public static void increaseWizardLevel(LivingEntity entity, int amount) {
		setWizardLevel(entity, getWizardLevel(entity) + amount);
	}

	public static boolean isCasting(LivingEntity entity) {
		return CASTING_COMPONENT.get(entity).isCasting();
	}

	public static void setCasting(LivingEntity entity, boolean casting) {
		CASTING_COMPONENT.get(entity).setCasting(casting);
	}

	public static List<Pattern> getPattern(LivingEntity entity) {
		return PATTERN_COMPONENT.get(entity).getPattern();
	}

	public static void setPattern(LivingEntity entity, List<Pattern> pattern) {
		PATTERN_COMPONENT.get(entity).setPattern(pattern);
	}

	public static void clearPattern(LivingEntity entity) {
		PATTERN_COMPONENT.get(entity).clearPattern();
	}

	public static long getLastCastTime(LivingEntity entity) {
		return LAST_CAST_TIME_COMPONENT.get(entity).getLastCastTime();
	}

	public static void setLastCastTime(LivingEntity entity, long time) {
		LAST_CAST_TIME_COMPONENT.get(entity).setLastCastTime(time);
	}

	public static int getStunTimer(LivingEntity entity) {
		return STUN_COMPONENT.get(entity).getStunTimer();
	}

	public static void setStunTimer(LivingEntity entity, int timer) {
		STUN_COMPONENT.get(entity).setStunTimer(timer);
	}

	public static List<Identifier> getQuestIds(PlayerEntity player) {
		return QUEST_COMPONENT.get(player).getQuestIds();
	}

	public static long getLastCompletedQuestTime(PlayerEntity player) {
		return QUEST_COMPONENT.get(player).getLastCompletedQuestTime();
	}

	public static void setLastCompletedQuestTime(PlayerEntity player, long time) {
		QUEST_COMPONENT.get(player).setLastCompletedQuestTime(time);
	}

	public static int getColour(Entity entity) {
		return MAGIC_COLOUR.get(entity).getColour();
	}

	public static void setColour(Entity entity, int colour) {
		MAGIC_COLOUR.get(entity).setColour(colour);
	}

	public static Vec3d getBoltPos(LivingEntity entity) {
		return BOLT_TARGET.get(entity).getPos();
	}

	public static void setBoltPos(LivingEntity entity, Vec3d pos) {
		BOLT_TARGET.get(entity).setPos(pos);
	}

	public static boolean shouldRenderBolt(LivingEntity entity) {
		return BOLT_TARGET.get(entity).shouldRender();
	}

	public static void setShouldRenderBolt(LivingEntity entity, boolean shouldRender) {
		BOLT_TARGET.get(entity).setShouldRender(shouldRender);
	}

	public static void setBoltAge(LivingEntity entity, int timer) {
		BOLT_TARGET.get(entity).setAge(timer);
	}

	public static SpellShape getSpellShape(Entity entity) {
		return SPELL_SHAPE.get(entity).getSpellShape();
	}

	public static void setSpellShape(Entity entity, SpellShape shape) {
		SPELL_SHAPE.get(entity).setSpellShape(shape);
	}

	public static void setScale(Entity entity, float scale, double strength) {
		SIZE.get(entity).setScale(scale, strength);
	}
}
