package net.lomeli.ec.client.gui;

import com.google.common.collect.Lists;

import java.io.IOException;
import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import net.lomeli.ec.client.CreeperEntry;
import net.lomeli.ec.client.gui.button.GuiItemButton;
import net.lomeli.ec.client.gui.button.GuiPageButton;
import net.lomeli.ec.lib.ModLib;

public class GuiItemList extends GuiScreen {
    public static final ResourceLocation bookGuiTextures = new ResourceLocation(ModLib.MOD_ID + ":textures/gui/book.png");
    private final List<CreeperEntry> entries;
    private List<GuiCreeperEntry> pages;
    private int listSize = 12;
    private int selected, left, top;
    private int bookImageWidth = 192;
    private int bookImageHeight = 192;
    public GuiPageButton buttonNextPage;
    public GuiPageButton buttonPreviousPage;
    public boolean ghostClear;

    public GuiItemList(List<CreeperEntry> list, boolean ghostClear) {
        this.entries = list;
        this.pages = Lists.newArrayList();
        this.selected = 0;
        this.ghostClear = ghostClear;
    }

    @Override
    public void initGui() {
        super.initGui();
        left = width / 2 - bookImageWidth / 2;
        top = height / 2 - bookImageHeight / 2;
        this.buttonList.add(this.buttonNextPage = new GuiPageButton(0, left + 120, top + 156, true));
        this.buttonList.add(this.buttonPreviousPage = new GuiPageButton(1, left + 38, top + 156, false));
        this.buttonPreviousPage.visible = false;
        this.buttonNextPage.visible = entries.size() > listSize;
        for (int i = 0; i < entries.size(); i++) {
            CreeperEntry entry = entries.get(i);
            if (entry != null) {
                pages.add(new GuiCreeperEntry(this, entry));
                Entity entity = entry.getEntity(mc.theWorld);
                String name = entry.getEntityClass().getCanonicalName();
                if (entity != null)
                    name = entity.getName();
                GuiItemButton itemButton = new GuiItemButton(2 + i, left + 40, top + 15 + (11 * (i % listSize)), bookImageWidth / 2, name);
                if (i >= listSize) {
                    itemButton.visible = false;
                    itemButton.enabled = false;
                }
                if (entity == null)
                    itemButton.enabled = false;
                this.buttonList.add(itemButton);
            }
        }
        resetButtons();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(GuiItemList.bookGuiTextures);
        this.drawTexturedModalRect(left, top, 0, 0, this.bookImageWidth, this.bookImageHeight);

        for (int i = 0; i < this.buttonList.size(); ++i) {
            GuiButton button = (GuiButton) this.buttonList.get(i);
            int id = i - 2;
            if (button instanceof GuiItemButton)
                ((GuiItemButton) button).draw(this.mc, mouseX, mouseY, id >= selected * listSize && id < listSize + (selected * listSize));
            else
                button.drawButton(this.mc, mouseX, mouseY);
        }

        for (int i = 0; i < this.labelList.size(); ++i) {
            ((GuiLabel) this.labelList.get(i)).drawLabel(this.mc, mouseX, mouseY);
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        if (button == null) return;
        switch (button.id) {
            case 0:
                if (selected < (entries.size() / listSize))
                    selected++;
                if (!buttonPreviousPage.visible)
                    buttonPreviousPage.visible = true;
                if (selected == (entries.size() / listSize))
                    button.visible = false;
                break;
            case 1:
                if (selected > -1)
                    selected--;
                if (!buttonNextPage.visible)
                    buttonNextPage.visible = true;
                if (selected == 0)
                    button.visible = false;
                break;
            default:
                if (button.id - 2 < pages.size())
                    mc.displayGuiScreen(pages.get(button.id - 2));
                return;
        }
    }

    public void resetButtons() {
        if (entries.size() > listSize) {
            buttonNextPage.visible = true;
            buttonPreviousPage.visible = true;
            if (selected >= (entries.size() / listSize))
                buttonNextPage.visible = false;
            if (selected == 0)
                buttonPreviousPage.visible = false;
        }
    }
}
