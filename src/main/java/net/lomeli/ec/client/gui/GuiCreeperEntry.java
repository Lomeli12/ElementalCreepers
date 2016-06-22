package net.lomeli.ec.client.gui;

import java.io.IOException;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;

import net.lomeli.lomlib.util.LangUtil;

import net.lomeli.ec.client.CreeperEntry;
import net.lomeli.ec.client.gui.button.GuiPageButton;
import net.lomeli.ec.entity.EntityGhostCreeper;

public class GuiCreeperEntry extends GuiScreen {
    private int bookImageWidth = 192;
    private int bookImageHeight = 192;
    private int left, top;
    private final CreeperEntry entry;
    private GuiPageButton backButton;
    private final GuiItemList prevPage;

    public GuiCreeperEntry(GuiItemList page, CreeperEntry entry) {
        this.entry = entry;
        this.prevPage = page;
    }

    @Override
    public void initGui() {
        super.initGui();
        left = width / 2 - bookImageWidth / 2;
        top = height / 2 - bookImageHeight / 2;
        this.buttonList.add(this.backButton = new GuiPageButton(1, left + 38, top + 156, false));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        if (button == null) return;
        if (button.id == backButton.id) this.mc.displayGuiScreen(prevPage);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(GuiItemList.bookGuiTextures);

        this.drawTexturedModalRect(left, top, 0, 0, this.bookImageWidth, this.bookImageHeight);
        super.drawScreen(mouseX, mouseY, partialTicks);
        drawEntry();
    }

    private void drawEntry() {
        if (entry == null) return;
        Entity entity = entry.getEntity(mc.theWorld);
        FontRenderer fontrenderer = mc.fontRendererObj;
        fontrenderer.drawString(LangUtil.translate(entity.getName()), left + 60, top + 17, 894731);
        drawEntity(entity, left + 45, top + 30, 1f);
        boolean oldState = fontrenderer.getUnicodeFlag();
        fontrenderer.setUnicodeFlag(true);
        if (!prevPage.ghostClear && entity instanceof EntityGhostCreeper)
            fontrenderer.setUnicodeFlag(false);
        fontrenderer.drawSplitString(LangUtil.translate(entry.getUnlocalizedText()), left + 37, top + 35, bookImageWidth - 80, 0);
        fontrenderer.setUnicodeFlag(oldState);
    }

    private void drawEntity(Entity entity, int x, int y, float size) {
        if (entity == null) return;
        GlStateManager.pushMatrix();

        GlStateManager.color(1, 1, 1, 1);
        float max = Math.max(1, Math.max(entity.width, entity.height));
        int scale = (int) (40 * size / max);

        GlStateManager.pushMatrix();

        GlStateManager.translate(x, y, 50f);
        GlStateManager.scale(-scale, scale, scale);

        GlStateManager.rotate(180f, 0f, 0f, 1f);
        GlStateManager.rotate(30, 1, 0, 0);
        GlStateManager.rotate(135, 0, 1, 0);
        GlStateManager.rotate(-135, 0, 1, 0);

        GlStateManager.translate(0, entity.getYOffset(), 0);
        GlStateManager.rotate(45f, 0, 1, 0);
        mc.getRenderManager().playerViewY = 180f;
        mc.getRenderManager().renderEntityWithPosYaw(entity, 0d, 0d, 0d, 0f, 1f);

        GlStateManager.popMatrix();

        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) 240 / 1.0F, (float) 240 / 1.0F);

        GlStateManager.popMatrix();
    }
}
