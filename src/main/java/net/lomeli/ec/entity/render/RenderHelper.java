package net.lomeli.ec.entity.render;

import org.lwjgl.opengl.GL11;

import java.util.Calendar;

import net.minecraft.block.Block;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelCreeper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;

import net.lomeli.ec.entity.model.ModelFriendlyCreeper;
import net.lomeli.ec.entity.model.ModelSpiderCreeper;
import net.lomeli.ec.lib.ECVars;

import static net.minecraftforge.client.IItemRenderer.ItemRenderType.EQUIPPED;
import static net.minecraftforge.client.IItemRenderer.ItemRendererHelper.BLOCK_3D;

public class RenderHelper {
    public static void specialRender(EntityLivingBase entity, ModelBase creeperModel, RenderManager renderManager) {
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        ItemStack itemstack = null;
        if (month == Calendar.OCTOBER && day == 31)
            itemstack = new ItemStack(Blocks.pumpkin, 1);

        if (month == Calendar.NOVEMBER && day == 12)
            itemstack = new ItemStack(Blocks.sponge, 1);

        if (month == Calendar.DECEMBER && (day >= 15 && day <= 31))
            itemstack = new ItemStack(Blocks.snow);

        if (itemstack != null && ECVars.special)
            renderItem(entity, itemstack, creeperModel, renderManager);
    }

    private static void renderItem(EntityLivingBase entity, ItemStack itemstack, ModelBase creeperModel, RenderManager renderManager) {
        if (itemstack != null && itemstack.getItem() != null) {
            GL11.glPushMatrix();

            if (creeperModel instanceof ModelCreeper)
                ((ModelCreeper) creeperModel).head.postRender(0.0625F);
            else if (creeperModel instanceof ModelSpiderCreeper)
                ((ModelSpiderCreeper) creeperModel).head.postRender(0.0625F);
            else if (creeperModel instanceof ModelFriendlyCreeper)
                ((ModelFriendlyCreeper) creeperModel).head.postRender(0.0625F);

            IItemRenderer customRenderer = MinecraftForgeClient.getItemRenderer(itemstack, EQUIPPED);
            boolean is3D = (customRenderer != null && customRenderer.shouldUseRenderHelper(EQUIPPED, itemstack, BLOCK_3D));
            if (itemstack.getItem() instanceof ItemBlock) {
                if (is3D || RenderBlocks.renderItemIn3d(Block.getBlockFromItem(itemstack.getItem()).getRenderType())) {
                    float f1 = 0.675F;
                    GL11.glTranslatef(0.0F, -0.34375F, 0.0F);
                    GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
                    GL11.glScalef(f1, -f1, f1);
                }
            }

            renderManager.itemRenderer.renderItem(entity, itemstack, 0);
            GL11.glPopMatrix();
        }
    }
}
