package net.lomeli.ec.entity.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelCreeper;
import net.minecraft.client.renderer.entity.RenderCreeper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.util.ResourceLocation;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.lomeli.ec.lib.Strings;

@SideOnly(Side.CLIENT)
public class RenderBasicCreeper extends RenderCreeper {

    public static final ResourceLocation creeperTextures = new ResourceLocation("textures/entity/creeper/creeper");
    public ResourceLocation entityTexture;
    private ModelCreeper creeperModel = new ModelCreeper(2.0F);
    private boolean isTransparent;

    public RenderBasicCreeper() {
        super();
        isTransparent = false;
    }

    public RenderBasicCreeper setTransparent(boolean bool) {
        this.isTransparent = bool;
        return this;
    }

    public RenderCreeper setTexture(String entity) {
        entityTexture = new ResourceLocation(Strings.MOD_ID.toLowerCase() + ":textures/entities/" + entity + ".png");
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
                GL11.glPushMatrix();
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                GL11.glColor4f(1F, 1F, 1F, 0.3F);
            }
            super.doRender(par1EntityLiving, par2, par4, par6, par8, par9);
            if (this.isTransparent) {
                GL11.glColor4f(1F, 1F, 1F, 1F);
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glPopMatrix();
            }
        }
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityCreeper entity) {
        return entityTexture;
    }

    @Override
    protected void renderEquippedItems(EntityLivingBase entity, float rendertick) {
        super.renderEquippedItems(entity, rendertick);
        RenderHelper.specialRender(entity, creeperModel, this.renderManager);
    }
}
