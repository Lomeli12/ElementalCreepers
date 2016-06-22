package net.lomeli.ec.client.render.factory;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;

import net.minecraftforge.fml.client.registry.IRenderFactory;

import net.lomeli.ec.client.render.RenderBasicCreeper;
import net.lomeli.ec.entity.EntityBaseCreeper;

public class CreeperRenderFactory implements IRenderFactory<EntityBaseCreeper> {
    private String texture;

    public CreeperRenderFactory(String texture) {
        this.texture = texture;
    }

    public String getTexture() {
        return texture;
    }

    @Override
    public Render<? super EntityBaseCreeper> createRenderFor(RenderManager manager) {
        return new RenderBasicCreeper(manager, texture);
    }

    public static class GhostCreeperFactory extends CreeperRenderFactory {
        public GhostCreeperFactory() {
            super("textures/entity/creeper/creeper");
        }

        @Override
        public Render<? super EntityBaseCreeper> createRenderFor(RenderManager manager) {
            return new RenderBasicCreeper(manager).setTransparent(true).setTexture(getTexture(), false);
        }
    }
}
