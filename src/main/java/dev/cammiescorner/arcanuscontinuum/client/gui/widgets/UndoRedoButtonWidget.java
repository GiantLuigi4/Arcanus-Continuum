package dev.cammiescorner.arcanuscontinuum.client.gui.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.client.gui.screens.SpellcraftScreen;
import dev.cammiescorner.arcanuscontinuum.client.gui.util.UndoRedoStack;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.screen.narration.NarrationPart;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.util.function.Consumer;

public class UndoRedoButtonWidget extends PressableWidget {
	private final TooltipSupplier tooltipSupplier;
	private final PressAction onPress;
	private final MinecraftClient client = MinecraftClient.getInstance();
	private final UndoRedoStack undoRedoStack;
	private final boolean isUndo;

	public UndoRedoButtonWidget(int x, int y, boolean isUndo, UndoRedoStack stack, PressAction onPress) {
		super(x, y, 24, 16, Text.empty());
		this.isUndo = isUndo;
		this.onPress = onPress;
		this.undoRedoStack = stack;
		this.tooltipSupplier = new TooltipSupplier() {
			final String text = isUndo ? "undo" : "redo";

			@Override
			public void onTooltip(UndoRedoButtonWidget spellComponentWidget, MatrixStack matrices, int mouseX, int mouseY) {
				if(client.currentScreen != null)
					client.currentScreen.renderTooltip(matrices, Arcanus.translate("screen", "tooltip", text), mouseX, mouseY);
			}

			@Override
			public void supply(Consumer<Text> consumer) {
				consumer.accept(Arcanus.translate("screen", "tooltip", text));
			}
		};
	}

	@Override
	public void onPress() {
		onPress.onPress(this);
	}

	@Override
	public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
		RenderSystem.setShaderTexture(0, SpellcraftScreen.BOOK_TEXTURE);

		int v = isUndo ? 192 : 208;
		active = isUndo ? undoRedoStack.canUndo() : undoRedoStack.canRedo();

		if(!active)
			DrawableHelper.drawTexture(matrices, x, y, 48, v, width, height, 256, 256);
		else if(!isHoveredOrFocused())
			DrawableHelper.drawTexture(matrices, x, y, 0, v, width, height, 256, 256);
		else
			DrawableHelper.drawTexture(matrices, x, y, 24, v, width, height, 256, 256);

		if(isHoveredOrFocused()) {
			RenderSystem.disableDepthTest();
			renderTooltip(matrices, mouseX, mouseY);
			RenderSystem.enableDepthTest();
		}
	}

	public void renderTooltip(MatrixStack matrices, int mouseX, int mouseY) {
		tooltipSupplier.onTooltip(this, matrices, mouseX, mouseY);
	}

	@Override
	public void appendNarrations(NarrationMessageBuilder builder) {
		appendDefaultNarrations(builder);
		tooltipSupplier.supply(text -> builder.put(NarrationPart.HINT, text));
	}

	public interface TooltipSupplier {
		void onTooltip(UndoRedoButtonWidget spellComponentWidget, MatrixStack matrixStack, int i, int j);

		default void supply(Consumer<Text> consumer) {
		}
	}

	public interface PressAction {
		void onPress(UndoRedoButtonWidget buttonWidget);
	}
}
