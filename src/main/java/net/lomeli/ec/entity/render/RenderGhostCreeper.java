package net.lomeli.ec.entity.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.entity.EntityLiving;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderGhostCreeper extends RenderBasicCreeper {
    @Override
    public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9) {
        if (par1EntityLiving != null) {
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glColor4f(1F, 1F, 1F, 0.3F);
            super.doRenderLiving(par1EntityLiving, par2, par4, par6, par8, par9);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glColor4f(1F, 1F, 1F, 1F);
        }
    }
}
