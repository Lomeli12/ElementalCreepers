package net.lomeli.ec.client.render.factory;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;

import net.minecraftforge.fml.client.registry.IRenderFactory;

import net.lomeli.ec.client.render.RenderBigBadCreep;
import net.lomeli.ec.entity.EntityBigBadCreep;

public class BigBadRenderFactory implements IRenderFactory<EntityBigBadCreep> {
    @Override
    public Render<? super EntityBigBadCreep> createRenderFor(RenderManager manager) {
        return new RenderBigBadCreep(manager);
    }
}
