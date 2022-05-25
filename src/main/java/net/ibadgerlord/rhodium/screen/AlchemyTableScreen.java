package net.ibadgerlord.rhodium.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.ibadgerlord.rhodium.Rhodium;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.BrewingStandScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class AlchemyTableScreen extends HandledScreen<AlchemyTableScreenHandler> {

    private static final Identifier TEXTURE =
            new Identifier(Rhodium.MOD_ID, "textures/gui/container/alchemy_table.png");

    public AlchemyTableScreen(AlchemyTableScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight);

        int p = handler.getProgress();
        int pp = MathHelper.clamp(((256 - p) * 32) / 256, 0, 256);
        if (handler.isCrafting()) {
            drawTexture(matrices, x + 72, y + 27, 176, 0, pp, 32);
        }

        int f = handler.getFuel();
        int fp = MathHelper.clamp((18 * f + 20 - 1) / 20, 0, 18);
        if (fp > 0) {
            this.drawTexture(matrices, x + 21, y + 51, 176, 32, fp, 4);
        }

    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

}
