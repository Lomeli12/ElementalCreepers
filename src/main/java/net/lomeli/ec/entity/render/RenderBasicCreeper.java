package net.lomeli.ec.entity.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderCreeper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.lomeli.lomlib.util.ResourceUtil;

import net.lomeli.ec.lib.ModLib;

@SideOnly(Side.CLIENT)
public class RenderBasicCreeper extends RenderCreeper {

    public ResourceLocation entityTexture;
    private boolean isTransparent;

    public RenderBasicCreeper() {
        super(Minecraft.getMinecraft().getRenderManager());
        this.addLayer(new LayerSpecialEvent(this));
        isTransparent = false;
    }

    public RenderBasicCreeper setTransparent(boolean bool) {
        this.isTransparent = bool;
        return this;
    }

    public RenderCreeper setTexture(String entity) {
        entityTexture = ResourceUtil.getEntityTexture(ModLib.MOD_ID.toLowerCase(), entity + ".png");
        return this;
    }

    public RenderCreeper setTexture(String entity, boolean bool) {
        if (bool)
            return setTexture(entity);
        entityTexture = new ResourceLocation(entity + ".png");
        return this;
    }

    @Override
    public void doRender(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9) {
        if (par1EntityLiving != null) {
            if (this.isTransparent) {
                GlStateManager.pushMatrix();
                GlStateManager.enableBlend();
                GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                GlStateManager.color(1f, 1f, 1f, 0.3f);
            }
            super.doRender(par1EntityLiving, par2, par4, par6, par8, par9);
            if (this.isTransparent) {
                GlStateManager.color(1f, 1f, 1f, 1f);
                GlStateManager.disableBlend();
                GlStateManager.popMatrix();
            }
        }
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityCreeper entity) {
        return entityTexture;
    }
}
