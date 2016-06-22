package net.lomeli.ec.client.render.factory;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;

import net.minecraftforge.fml.client.registry.IRenderFactory;

import net.lomeli.ec.client.render.RenderFriendlyCreeper;
import net.lomeli.ec.entity.EntityFriendlyCreeper;

public class FriendlyRenderFactory implements IRenderFactory<EntityFriendlyCreeper> {
    @Override
    public Render<? super EntityFriendlyCreeper> createRenderFor(RenderManager manager) {
        return new RenderFriendlyCreeper(manager);
    }
}
