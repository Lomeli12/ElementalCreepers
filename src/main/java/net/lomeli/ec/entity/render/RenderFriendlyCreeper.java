package net.lomeli.ec.entity.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.lomeli.ec.entity.EntityFriendlyCreeper;
import net.lomeli.ec.entity.model.ModelFriendlyCreeper;
import net.lomeli.ec.lib.Strings;

@SideOnly(Side.CLIENT)
public class RenderFriendlyCreeper extends RenderLiving {
    private static final ResourceLocation armoredCreeperTextures = new ResourceLocation("textures/entity/creeper/creeper_armor.png");

    public RenderFriendlyCreeper() {
        super(new ModelFriendlyCreeper(), 0.5F);
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
            GL11.glScalef(var6, var7, var6);
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
    protected int inheritRenderPass(EntityLivingBase p_77035_1_, int p_77035_2_, float p_77035_3_) {
        return -1;
    }

    @Override
    protected int shouldRenderPass(EntityLivingBase p_77032_1_, int p_77032_2_, float p_77032_3_) {
        if (((EntityFriendlyCreeper) p_77032_1_).getPowered()) {
            if (p_77032_1_.isInvisible())
                GL11.glDepthMask(false);
            else
                GL11.glDepthMask(true);

            if (p_77032_2_ == 1) {
                float f1 = (float) p_77032_1_.ticksExisted + p_77032_3_;
                this.bindTexture(armoredCreeperTextures);
                GL11.glMatrixMode(GL11.GL_TEXTURE);
                GL11.glLoadIdentity();
                float f2 = f1 * 0.01F;
                float f3 = f1 * 0.01F;
                GL11.glTranslatef(f2, f3, 0.0F);
                this.setRenderPassModel(this.mainModel);
                GL11.glMatrixMode(GL11.GL_MODELVIEW);
                GL11.glEnable(GL11.GL_BLEND);
                float f4 = 0.5F;
                GL11.glColor4f(f4, f4, f4, 1.0F);
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
                return 1;
            }

            if (p_77032_2_ == 2) {
                GL11.glMatrixMode(GL11.GL_TEXTURE);
                GL11.glLoadIdentity();
                GL11.glMatrixMode(GL11.GL_MODELVIEW);
                GL11.glEnable(GL11.GL_LIGHTING);
                GL11.glDisable(GL11.GL_BLEND);
            }
        }

        return -1;
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return new ResourceLocation(Strings.MOD_ID.toLowerCase(), ((EntityFriendlyCreeper) entity).tamedTexture());
    }

}
