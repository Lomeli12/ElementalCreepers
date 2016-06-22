package net.lomeli.ec.client.render.factory;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;

import net.minecraftforge.fml.client.registry.IRenderFactory;

import net.lomeli.ec.client.render.RenderSilverCreeper;
import net.lomeli.ec.entity.EntitySilverCreeper;

public class SilverRenderFactory implements IRenderFactory<EntitySilverCreeper> {
    @Override
    public Render<? super EntitySilverCreeper> createRenderFor(RenderManager manager) {
        return new RenderSilverCreeper(manager);
    }
}
