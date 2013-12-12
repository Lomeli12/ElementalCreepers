package net.lomeli.ec.core;

import net.lomeli.ec.entity.EntityElectricCreeper;
import net.lomeli.ec.entity.EntityFireCreeper;
import net.lomeli.ec.entity.EntityWaterCreeper;
import net.lomeli.ec.entity.render.RenderBasicCreeper;

import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy{
    @Override
    public void registerRenders(){
        super.registerRenders();
        RenderingRegistry.registerEntityRenderingHandler(EntityFireCreeper.class, new RenderBasicCreeper().setTexture("firecreeper"));
        RenderingRegistry.registerEntityRenderingHandler(EntityWaterCreeper.class, new RenderBasicCreeper().setTexture("watercreeper"));
        RenderingRegistry.registerEntityRenderingHandler(EntityElectricCreeper.class, new RenderBasicCreeper().setTexture("electriccreeper"));
    }
}
