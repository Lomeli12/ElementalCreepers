package net.lomeli.ec.entity.render;

import net.lomeli.ec.lib.Strings;

import net.lomeli.lomlib.util.ResourceUtil;

import net.minecraft.client.renderer.entity.RenderCreeper;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderBasicCreeper extends RenderCreeper {

    public ResourceLocation entityTexture;

    public RenderCreeper setTexture(String entity) {
        entityTexture = ResourceUtil.getEntityTexture(Strings.MOD_ID.toLowerCase(), entity + ".png");
        return this;
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return entityTexture;
    }

}
