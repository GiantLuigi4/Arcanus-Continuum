package dev.cammiescorner.arcanuscontinuum.client.renderer.feature;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.client.ArcanusClient;
import dev.cammiescorner.arcanuscontinuum.client.models.feature.SpellPatternModel;
import dev.cammiescorner.arcanuscontinuum.common.items.StaffItem;
import dev.cammiescorner.arcanuscontinuum.common.registry.ArcanusComponents;
import dev.cammiescorner.arcanuscontinuum.common.util.StaffType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;

public class SpellPatternFeatureRenderer<T extends PlayerEntity, M extends EntityModel<T>> extends FeatureRenderer<T, M> {
	private static final Identifier TEXTURE = Arcanus.id("textures/entity/feature/magic_circles.png");
	private final MinecraftClient client = MinecraftClient.getInstance();
	private final SpellPatternModel<PlayerEntity> model;

	public SpellPatternFeatureRenderer(FeatureRendererContext<T, M> context) {
		super(context);
		model = new SpellPatternModel<>(client.getEntityModelLoader().getModelPart(SpellPatternModel.MODEL_LAYER));
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumerProvider vertices, int light, PlayerEntity player, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
		model.setAngles(player, limbAngle, limbDistance, animationProgress, headYaw, headPitch);

		ItemStack stack = player.getMainHandStack();
		int colour = Arcanus.getMagicColour(player.getGameProfile().getId());
		float r = (colour >> 16 & 255) / 255F;
		float g = (colour >> 8 & 255) / 255F;
		float b = (colour & 255) / 255F;

		model.showMagicCircles(ArcanusComponents.getPattern(player));
		model.first.roll += Math.toRadians(2);
		model.second.roll -= Math.toRadians(3);
		model.third.roll += Math.toRadians(4);

		model.first.pivotZ = -8 + MathHelper.sin((player.age + player.getId() + client.getTickDelta()) / (MathHelper.PI * 2)) * 0.5F;
		model.second.pivotZ = -11 + MathHelper.cos((player.age + player.getId() + client.getTickDelta()) / (MathHelper.PI * 2)) * 0.5F;
		model.third.pivotZ = -13 + MathHelper.sin((player.age + player.getId() + client.getTickDelta()) / (MathHelper.PI * 2)) * 0.5F;

		matrices.push();
		matrices.translate(0, 0.65, -0.35);

		if(ArcanusComponents.isCasting(player) && stack.getItem() instanceof StaffItem item) {
			if(item.staffType == StaffType.STAFF) {
				matrices.translate(player.getMainArm() == Arm.RIGHT ? -0.35 : 0.35, 0, 0.05);
				matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(player.getMainArm() == Arm.RIGHT ? 65 : -65));
			}
			else if(item.staffType == StaffType.WAND) {
				matrices.translate(player.getMainArm() == Arm.RIGHT ? -0.35 : 0.35, -0.05, -0.1);
			}
			else if(item.staffType == StaffType.GAUNTLET) {
				matrices.translate(player.getMainArm() == Arm.RIGHT ? -0.35 : 0.35, -0.05, 0.4);
			}
			else if(item.staffType == StaffType.BOOK) {
				matrices.translate(player.getMainArm() == Arm.RIGHT ? 0.35 : -0.35, -0.05, 0.4);
			}
			else if(item.staffType == StaffType.GUN) {
				matrices.translate(0, -0.15, 0.15);
			}
		}

		model.render(matrices, vertices.getBuffer(ArcanusClient.getMagicCircles(TEXTURE)), 15728850, OverlayTexture.DEFAULT_UV, r, g, b, 1F);
		matrices.pop();
	}
}
