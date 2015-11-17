package net.lomeli.ec.entity.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.lomeli.ec.entity.EntityFriendlyCreeper;
import net.lomeli.ec.entity.model.ModelFriendlyCreeper;
import net.lomeli.ec.lib.ModLib;

@SideOnly(Side.CLIENT)
public class RenderFriendlyCreeper extends RenderLiving {

    public RenderFriendlyCreeper() {
        super(Minecraft.getMinecraft().getRenderManager(), new ModelFriendlyCreeper(), 0.5F);
        this.addLayer(new LayerSpecialEvent(this));
        this.addLayer(new LayerCharge(this));
    }

    @Override
    protected void preRenderCallback(EntityLivingBase par1EntityCreeper, float par2) {
        if (par1EntityCreeper instanceof EntityFriendlyCreeper) {
            float var4 = ((EntityFriendlyCreeper) par1EntityCreeper).getCreeperFlashIntensity(par2);
            float var5 = 1.0F + MathHelper.sin(var4 * 100.0F) * var4 * 0.01F;

            if (var4 < 0.0F)
                var4 = 0.0F;

            if (var4 > 1.0F)
                var4 = 1.0F;

            var4 *= var4;
            var4 *= var4;
            float var6 = (1.0F + var4 * 0.4F) * var5;
            float var7 = (1.0F + var4 * 0.1F) / var5;
            GlStateManager.scale(var6, var7, var6);
        }
    }

    @Override
    protected int getColorMultiplier(EntityLivingBase p_77030_1_, float p_77030_2_, float p_77030_3_) {
        float var5 = ((EntityFriendlyCreeper) p_77030_1_).getCreeperFlashIntensity(p_77030_2_);

        if ((int) (var5 * 10.0F) % 2 == 0)
            return 0;
        else {
            int var6 = (int) (var5 * 0.2F * 255.0F);

            if (var6 < 0)
                var6 = 0;

            if (var6 > 255)
                var6 = 255;

            short var7 = 255;
            short var8 = 255;
            short var9 = 255;
            return var6 << 24 | var7 << 16 | var8 << 8 | var9;
        }
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return new ResourceLocation(ModLib.MOD_ID.toLowerCase(), ((EntityFriendlyCreeper) entity).tamedTexture());
    }
}
