package net.lomeli.ec.entity.render;

import net.lomeli.ec.lib.Strings;

import net.lomeli.lomlib.client.ResourceUtil;

import net.minecraft.client.renderer.entity.RenderCreeper;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderBasicCreeper extends RenderCreeper {

    public ResourceLocation entityTexture;

    public RenderCreeper setTexture(String entity) {
        entityTexture = ResourceUtil.getEntityTexture(Strings.MOD_ID.toLowerCase(), entity + ".png");
        return this;
    }

    public RenderCreeper setTexture(String entity, boolean bool) {
        if (bool)
            return setTexture(entity);
        entityTexture = new ResourceLocation(entity + ".png");
        return this;
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return entityTexture;
    }

}
