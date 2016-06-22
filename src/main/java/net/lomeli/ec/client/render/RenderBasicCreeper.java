package net.lomeli.ec.client.render;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderCreeper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.lomeli.lomlib.util.ResourceUtil;

import net.lomeli.ec.client.render.layer.LayerSpecialEvent;
import net.lomeli.ec.lib.ModLib;

@SideOnly(Side.CLIENT)
public class RenderBasicCreeper extends RenderCreeper {

    public ResourceLocation entityTexture = new ResourceLocation("textures/entity/creeper/creeper.png");
    private boolean isTransparent;

    public RenderBasicCreeper(RenderManager renderManager) {
        super(renderManager);
        this.addLayer(new LayerSpecialEvent(this));
        isTransparent = false;
    }

    public RenderBasicCreeper(RenderManager manager, String texture) {
        this(manager);
        this.setTexture(texture);
    }

    public RenderBasicCreeper setTransparent(boolean bool) {
        this.isTransparent = bool;
        return this;
    }

    public RenderCreeper setTexture(String entity) {
        entityTexture = ResourceUtil.getEntityTexture(ModLib.MOD_ID.toLowerCase(), entity);
        return this;
    }

    public RenderCreeper setTexture(String entity, boolean bool) {
        if (bool)
            return setTexture(entity);
        if (!entity.endsWith(".png") || !entity.endsWith(".PNG"))
            entity += ".png";
        entityTexture = new ResourceLocation(entity);
        return this;
    }

    @Override
    public void doRender(EntityCreeper entity, double x, double y, double z, float entityYaw, float partialTicks) {
        if (entity != null) {
            if (this.isTransparent) {
                GlStateManager.pushMatrix();
                GlStateManager.enableBlend();
                GlStateManager.blendFunc(0x302, 0x303);
                GlStateManager.color(1f, 1f, 1f, 0.3f);
            }
            super.doRender(entity, x, y, z, entityYaw, partialTicks);
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
