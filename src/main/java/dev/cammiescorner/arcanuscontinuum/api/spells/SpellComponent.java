package dev.cammiescorner.arcanuscontinuum.api.spells;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

public class SpellComponent {
	private static final MutableText DISABLED_TRANSLATED_NAME = Arcanus.translate("spell_component", "disabled").formatted(Formatting.OBFUSCATED);
	private final boolean isEnabled;
	private final Weight weight;
	private final double manaCost;
	private final int coolDown;
	private final int minLevel;
	private String translationKey;
	private Identifier texture;

	public SpellComponent(boolean isEnabled, Weight weight, double manaCost, int coolDown, int minLevel) {
		this.isEnabled = isEnabled;
		this.weight = weight;
		this.manaCost = manaCost;
		this.coolDown = coolDown;
		this.minLevel = minLevel;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public Weight getWeight() {
		return weight;
	}

	public double getManaCost() {
		return manaCost;
	}

	public int getCoolDown() {
		return coolDown;
	}

	public int getMinLevel() {
		return minLevel;
	}

	public String getManaCostAsString() {
		return Arcanus.format(getManaCost());
	}

	public String getCoolDownAsString() {
		return Arcanus.format(getCoolDown() / 20D);
	}

	public Identifier getTexture() {
		if(texture == null) {
			Identifier id = Arcanus.SPELL_COMPONENTS.getId(this);
			String extra = "";

			if(this instanceof SpellShape)
				extra = "shapes/";
			if(this instanceof SpellEffect)
				extra = "effects/";

			texture = new Identifier(id.getNamespace(), "textures/spell_components/" + extra + id.getPath() + ".png");
		}

		return texture;
	}

	public String getTranslationKey() {
		if(translationKey == null)
			translationKey = Util.createTranslationKey("spell_component", Arcanus.SPELL_COMPONENTS.getId(this));

		return translationKey;
	}

	public MutableText getTranslatedName() {
		if(!isEnabled())
			return DISABLED_TRANSLATED_NAME;

		return Text.translatable(getTranslationKey());
	}
}
