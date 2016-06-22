package net.lomeli.ec.client.render.factory;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;

import net.minecraftforge.fml.client.registry.IRenderFactory;

import net.lomeli.ec.client.render.RenderSpiderCreeper;
import net.lomeli.ec.entity.EntitySpiderCreeper;

public class SpiderRenderFactory implements IRenderFactory<EntitySpiderCreeper>{
    @Override
    public Render<? super EntitySpiderCreeper> createRenderFor(RenderManager manager) {
        return new RenderSpiderCreeper(manager);
    }
}
