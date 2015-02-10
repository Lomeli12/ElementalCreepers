package net.lomeli.ec.entity.render;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import net.lomeli.ec.entity.EntityBaseCreeper;
import net.lomeli.ec.entity.model.ModelSpiderCreeper;

public class LayerSpiderCharge implements LayerRenderer {
    private static final ResourceLocation LIGHTNING_TEXTURE = new ResourceLocation("textures/entity/creeper/creeper_armor.png");
    private final RenderLiving creeperRenderer;
    private final ModelSpiderCreeper creeperModel = new ModelSpiderCreeper(2.0F);

    public LayerSpiderCharge(RenderLiving renderLiving) {
        this.creeperRenderer = renderLiving;
    }

    @Override
    public void doRenderLayer(EntityLivingBase p_177141_1_, float p_177141_2_, float p_177141_3_, float p_177141_4_, float p_177141_5_, float p_177141_6_, float p_177141_7_, float p_177141_8_) {
        if (p_177141_1_ != null && p_177141_1_ instanceof EntityBaseCreeper) {
            EntityBaseCreeper creep = (EntityBaseCreeper) p_177141_1_;
            if (creep.getPowered()) {
                GlStateManager.depthMask(!creep.isInvisible());
                this.creeperRenderer.bindTexture(LIGHTNING_TEXTURE);
                GlStateManager.matrixMode(5890);
                GlStateManager.loadIdentity();
                float f7 = (float) creep.ticksExisted + p_177141_4_;
                GlStateManager.translate(f7 * 0.01F, f7 * 0.01F, 0.0F);
                GlStateManager.matrixMode(5888);
                GlStateManager.enableBlend();
                float f8 = 0.5F;
                GlStateManager.color(f8, f8, f8, 1.0F);
                GlStateManager.disableLighting();
                GlStateManager.blendFunc(1, 1);
                this.creeperModel.setModelAttributes(this.creeperRenderer.getMainModel());
                this.creeperModel.render(creep, p_177141_2_, p_177141_3_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_);
                GlStateManager.matrixMode(5890);
                GlStateManager.loadIdentity();
                GlStateManager.matrixMode(5888);
                GlStateManager.enableLighting();
                GlStateManager.disableBlend();
            }
        }
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}
