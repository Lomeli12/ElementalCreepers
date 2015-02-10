package net.lomeli.ec.entity.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelCreeper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import net.lomeli.ec.entity.EntityBigBadCreep;

public class RenderBigBadCreep extends RenderLiving {
    private static final ResourceLocation creeperTextures = new ResourceLocation("textures/entity/creeper/creeper.png");

    public RenderBigBadCreep() {
        super(Minecraft.getMinecraft().getRenderManager(), new ModelCreeper(), 0.5F);
        this.addLayer(new LayerCharge(this));
        this.addLayer(new LayerSpecialEvent(this));
    }

    protected void func_180570_a(EntityBigBadCreep p_180570_1_, float p_180570_2_) {
        float f1 = p_180570_1_.getCreeperFlashIntensity(p_180570_2_);
        float f2 = 1.0F + MathHelper.sin(f1 * 100.0F) * f1 * 0.01F;
        f1 = MathHelper.clamp_float(f1, 0.0F, 1.0F);
        f1 *= f1;
        f1 *= f1;
        float f3 = (1.0F + f1 * 0.4F) * f2;
        float f4 = (1.0F + f1 * 0.1F) / f2;
        GlStateManager.scale(f3, f4, f3);
    }

    protected int func_180571_a(EntityBigBadCreep p_180571_1_, float p_180571_2_, float p_180571_3_) {
        float f2 = p_180571_1_.getCreeperFlashIntensity(p_180571_3_);

        if ((int) (f2 * 10.0F) % 2 == 0) {
            return 0;
        } else {
            int i = (int) (f2 * 0.2F * 255.0F);
            i = MathHelper.clamp_int(i, 0, 255);
            return i << 24 | 16777215;
        }
    }

    @Override
    protected void preRenderCallback(EntityLivingBase p_77041_1_, float p_77041_2_) {
        this.func_180570_a((EntityBigBadCreep) p_77041_1_, p_77041_2_);
        GlStateManager.scale(6, 6, 6);
    }

    @Override
    protected int getColorMultiplier(EntityLivingBase p_77030_1_, float p_77030_2_, float p_77030_3_) {
        return this.func_180571_a((EntityBigBadCreep) p_77030_1_, p_77030_2_, p_77030_3_);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return creeperTextures;
    }
}
