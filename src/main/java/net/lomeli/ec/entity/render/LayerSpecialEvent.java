package net.lomeli.ec.entity.render;

import java.util.Calendar;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelCreeper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import net.lomeli.ec.entity.model.ModelFriendlyCreeper;
import net.lomeli.ec.entity.model.ModelSpiderCreeper;
import net.lomeli.ec.lib.ModVars;

public class LayerSpecialEvent implements LayerRenderer {
    private final RenderLiving renderer;

    public LayerSpecialEvent(RenderLiving renderLiving) {
        this.renderer = renderLiving;
    }

    @Override
    public void doRenderLayer(EntityLivingBase entity, float f, float f1, float renderTick, float f2, float f3, float f4, float f5) {
        if (entity != null && !entity.isInvisible()) {
            int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
            int month = Calendar.getInstance().get(Calendar.MONTH);
            ItemStack itemstack = null;
            if (month == Calendar.OCTOBER && day == 31)
                itemstack = new ItemStack(Blocks.pumpkin, 1);

            if (month == Calendar.NOVEMBER && day == 12)
                itemstack = new ItemStack(Blocks.sponge, 1);

            if (month == Calendar.DECEMBER && day == 25)
                itemstack = new ItemStack(Blocks.snow);

            if (itemstack != null && ModVars.special)
                renderItem(entity, itemstack, renderer.getMainModel());
        }
    }

    private void renderItem(EntityLivingBase entity, ItemStack itemstack, ModelBase creeperModel) {
        if (itemstack != null && itemstack.getItem() != null) {
            GlStateManager.pushMatrix();

            if (creeperModel instanceof ModelCreeper)
                ((ModelCreeper) creeperModel).head.postRender(0.0625F);
            else if (creeperModel instanceof ModelSpiderCreeper)
                ((ModelSpiderCreeper) creeperModel).head.postRender(0.0625F);
            else if (creeperModel instanceof ModelFriendlyCreeper)
                ((ModelFriendlyCreeper) creeperModel).head.postRender(0.0625F);

            float f1 = 0.675F;
            GlStateManager.translate(0.0F, -0.34375F, 0.0F);
            GlStateManager.scale(f1, -f1, f1);

            Minecraft.getMinecraft().getItemRenderer().renderItem(entity, itemstack, ItemCameraTransforms.TransformType.HEAD);

            GlStateManager.popMatrix();
        }
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}
