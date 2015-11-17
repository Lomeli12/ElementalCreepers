package net.lomeli.ec.client.gui.button;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;

import net.minecraftforge.fml.client.FMLClientHandler;

import net.lomeli.lomlib.util.LangUtil;

public class GuiItemButton extends GuiButton {

    public GuiItemButton(int id, int x, int y, int width, String text) {
        super(id, x, y, width, FMLClientHandler.instance().getClient().fontRendererObj.FONT_HEIGHT + 2, text);
    }

    public void draw(Minecraft mc, int x, int y, boolean show) {
        enabled = show;
        visible = show;
        drawButton(mc, x, y);
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (!visible) return;
        FontRenderer fontrenderer = mc.fontRendererObj;
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.blendFunc(770, 771);
        fontrenderer.setUnicodeFlag(true);
        fontrenderer.drawString(LangUtil.translate(displayString), xPosition, yPosition, hovered ? 0x00F200 : 0x009E00);
        fontrenderer.setUnicodeFlag(false);
    }
}
