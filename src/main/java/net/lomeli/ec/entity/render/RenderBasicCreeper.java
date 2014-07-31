package net.lomeli.ec.entity.render;

import net.minecraft.client.renderer.entity.RenderCreeper;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.util.ResourceLocation;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.lomeli.ec.lib.Strings;

@SideOnly(Side.CLIENT)
public class RenderBasicCreeper extends RenderCreeper {

    public ResourceLocation entityTexture;

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
    protected ResourceLocation getEntityTexture(EntityCreeper entity) {
        return entityTexture;
    }

}
