package net.lomeli.ec.core;

import net.lomeli.ec.entity.*;
import net.lomeli.ec.entity.addon.*;
import net.lomeli.ec.entity.render.*;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.Loader;

public class ClientProxy extends CommonProxy {
    @Override
    public void registerRenders() {
        super.registerRenders();
        RenderingRegistry.registerEntityRenderingHandler(EntityFireCreeper.class, new RenderBasicCreeper().setTexture("firecreeper"));
        RenderingRegistry.registerEntityRenderingHandler(EntityWaterCreeper.class, new RenderBasicCreeper().setTexture("watercreeper"));
        RenderingRegistry.registerEntityRenderingHandler(EntityElectricCreeper.class, new RenderBasicCreeper().setTexture("electriccreeper"));
        RenderingRegistry.registerEntityRenderingHandler(EntityCookieCreeper.class, new RenderBasicCreeper().setTexture("cookiecreeper"));
        RenderingRegistry.registerEntityRenderingHandler(EntityDarkCreeper.class, new RenderBasicCreeper().setTexture("darkcreeper"));
        RenderingRegistry.registerEntityRenderingHandler(EntityLightCreeper.class, new RenderBasicCreeper().setTexture("lightcreeper"));
        RenderingRegistry.registerEntityRenderingHandler(EntityEarthCreeper.class, new RenderBasicCreeper().setTexture("earthcreeper"));
        RenderingRegistry.registerEntityRenderingHandler(EntityMagmaCreeper.class, new RenderBasicCreeper().setTexture("magmacreeper"));
        RenderingRegistry.registerEntityRenderingHandler(EntityReverseCreeper.class, new RenderBasicCreeper().setTexture("reversecreeper"));
        RenderingRegistry.registerEntityRenderingHandler(EntityIceCreeper.class, new RenderBasicCreeper().setTexture("icecreeper"));
        RenderingRegistry.registerEntityRenderingHandler(EntityFriendlyCreeper.class, new RenderFriendlyCreeper());
        RenderingRegistry.registerEntityRenderingHandler(EntityGhostCreeper.class, new RenderGhostCreeper().setTexture("textures/entity/creeper/creeper", false));
        RenderingRegistry.registerEntityRenderingHandler(EntityIllusionCreeper.class, new RenderBasicCreeper().setTexture("illusioncreeper"));
        RenderingRegistry.registerEntityRenderingHandler(EntityPsyhicCreeper.class, new RenderBasicCreeper().setTexture("psychiccreeper"));
        RenderingRegistry.registerEntityRenderingHandler(EntitySpiderCreeper.class, new RenderSpiderCreeper());

        RenderingRegistry.registerEntityRenderingHandler(EntityWindCreeper.class, new RenderBasicCreeper().setTexture("windcreeper"));
        RenderingRegistry.registerEntityRenderingHandler(EntityHydrogenCreeper.class, new RenderBasicCreeper().setTexture("hydrogencreeper"));
        RenderingRegistry.registerEntityRenderingHandler(EntityEnderCreeper.class, new RenderBasicCreeper().setTexture("endercreeper"));
        RenderingRegistry.registerEntityRenderingHandler(EntityStoneCreeper.class, new RenderBasicCreeper().setTexture("stonecreeper"));
        RenderingRegistry.registerEntityRenderingHandler(EntitySolarCreeper.class, new RenderBasicCreeper().setTexture("solarcreeper"));
        
        if (Loader.isModLoaded("IC2"))
            RenderingRegistry.registerEntityRenderingHandler(EntityEUCreeper.class, new RenderBasicCreeper().setTexture("eucreeper"));

        if (AddonEntities.doesRFExist())
            RenderingRegistry.registerEntityRenderingHandler(EntityRFCreeper.class, new RenderBasicCreeper().setTexture("rfcreeper"));

        if (Loader.isModLoaded("BuildCraft|Core"))
            RenderingRegistry.registerEntityRenderingHandler(EntityMJCreeper.class, new RenderBasicCreeper().setTexture("mjcreeper"));
    }
}
