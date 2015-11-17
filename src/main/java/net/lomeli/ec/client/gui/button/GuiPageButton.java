package net.lomeli.ec.client.gui.button;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;

import net.lomeli.ec.client.gui.GuiItemList;

public class GuiPageButton extends GuiButton {
    private final boolean altTexture;

    public GuiPageButton(int id, int x, int y, boolean useAltTexture) {
        super(id, x, y, 23, 13, "");
        this.altTexture = useAltTexture;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (this.visible) {
            boolean flag = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            mc.getTextureManager().bindTexture(GuiItemList.bookGuiTextures);
            int k = 0;
            int l = 192;

            if (flag)
                k += 23;

            if (!this.altTexture)
                l += 13;

            this.drawTexturedModalRect(this.xPosition, this.yPosition, k, l, 23, 13);
        }
    }
}
