package net.lomeli.ec.entity.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import net.lomeli.lomlib.util.ResourceUtil;

import net.lomeli.ec.entity.EntityBaseCreeper;
import net.lomeli.ec.entity.model.ModelSpiderCreeper;
import net.lomeli.ec.lib.ModLib;

public class RenderSpiderCreeper extends RenderLiving {
    public RenderSpiderCreeper() {
        super(Minecraft.getMinecraft().getRenderManager(), new ModelSpiderCreeper(), 0.5f);
        this.addLayer(new LayerSpecialEvent(this));
        this.addLayer(new LayerSpiderCharge(this));
    }

    @Override
    protected void preRenderCallback(EntityLivingBase p_77041_1_, float p_77041_2_) {
        if (p_77041_1_ instanceof EntityBaseCreeper) {
            EntityBaseCreeper creeper = (EntityBaseCreeper) p_77041_1_;
            float f1 = creeper.getCreeperFlashIntensity(p_77041_2_);
            float f2 = 1.0F + MathHelper.sin(f1 * 100.0F) * f1 * 0.01F;
            f1 = MathHelper.clamp_float(f1, 0.0F, 1.0F);
            f1 *= f1;
            f1 *= f1;
            float f3 = (1.0F + f1 * 0.4F) * f2;
            float f4 = (1.0F + f1 * 0.1F) / f2;
            GlStateManager.scale(f3, f4, f3);
        }
        super.preRenderCallback(p_77041_1_, p_77041_2_);
    }

    @Override
    protected int getColorMultiplier(EntityLivingBase p_77030_1_, float p_77030_2_, float p_77030_3_) {
        if (p_77030_1_ instanceof EntityBaseCreeper) {
            EntityBaseCreeper creeper = (EntityBaseCreeper) p_77030_1_;
            float f2 = creeper.getCreeperFlashIntensity(p_77030_3_);

            if ((int) (f2 * 10.0F) % 2 == 0) {
                return 0;
            } else {
                int i = (int) (f2 * 0.2F * 255.0F);
                i = MathHelper.clamp_int(i, 0, 255);
                return i << 24 | 16777215;
            }
        }
        return super.getColorMultiplier(p_77030_1_, p_77030_2_, p_77030_3_);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity var1) {
        return ResourceUtil.getEntityTexture(ModLib.MOD_ID.toLowerCase(), "spidercreeper.png");
    }
}