package net.lomeli.ec.core;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityPortalFX;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.world.World;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;

import net.lomeli.ec.entity.*;
import net.lomeli.ec.entity.addon.AddonEntities;
import net.lomeli.ec.entity.addon.EntityEUCreeper;
import net.lomeli.ec.entity.addon.EntityMJCreeper;
import net.lomeli.ec.entity.addon.EntityRFCreeper;
import net.lomeli.ec.entity.render.*;

public class ClientProxy extends CommonProxy {
    @Override
    public void registerEvents() {
        super.registerEvents();
        FMLCommonHandler.instance().bus().register(new VersionChecker());
    }

    @Override
    public void registerRenders() {
        super.registerRenders();
        RenderingRegistry.registerEntityRenderingHandler(EntityCreeper.class, new RenderBasicCreeper().setTexture("textures/entity/creeper/creeper", false));
        registerEntityRendering(EntityFireCreeper.class, "firecreeper");
        registerEntityRendering(EntityWaterCreeper.class, "watercreeper");
        registerEntityRendering(EntityElectricCreeper.class, "electriccreeper");
        registerEntityRendering(EntityCookieCreeper.class, "cookiecreeper");
        registerEntityRendering(EntityDarkCreeper.class, "darkcreeper");
        registerEntityRendering(EntityLightCreeper.class, "lightcreeper");
        registerEntityRendering(EntityEarthCreeper.class, "earthcreeper");
        registerEntityRendering(EntityMagmaCreeper.class, "magmacreeper");
        registerEntityRendering(EntityReverseCreeper.class, "reversecreeper");
        registerEntityRendering(EntityIceCreeper.class, "icecreeper");
        RenderingRegistry.registerEntityRenderingHandler(EntityFriendlyCreeper.class, new RenderFriendlyCreeper());
        RenderingRegistry.registerEntityRenderingHandler(EntityGhostCreeper.class, new RenderBasicCreeper().setTransparent(true).setTexture("textures/entity/creeper/creeper", false));
        registerEntityRendering(EntityIllusionCreeper.class, "illusioncreeper");
        registerEntityRendering(EntityPsyhicCreeper.class, "psychiccreeper");
        RenderingRegistry.registerEntityRenderingHandler(EntitySpiderCreeper.class, new RenderSpiderCreeper());

        registerEntityRendering(EntityWindCreeper.class, "windcreeper");
        registerEntityRendering(EntityHydrogenCreeper.class, "hydrogencreeper");
        registerEntityRendering(EntityEnderCreeper.class, "endercreeper");
        registerEntityRendering(EntityStoneCreeper.class, "stonecreeper");
        registerEntityRendering(EntitySolarCreeper.class, "solarcreeper");
        registerEntityRendering(EntityBirthdayCreeper.class, "cakecreeper");
        registerEntityRendering(EntityFireworkCreeper.class, "fireworkcreeper");
        RenderingRegistry.registerEntityRenderingHandler(EntityBigBadCreep.class, new RenderBigBadCreep());
        registerEntityRendering(EntitySpringCreeper.class, "springcreeper");

        if (Loader.isModLoaded("IC2"))
            registerEntityRendering(EntityEUCreeper.class, "eucreeper");

        if (AddonEntities.doesRFExist())
            registerEntityRendering(EntityRFCreeper.class, "rfcreeper");

        if (Loader.isModLoaded("BuildCraft|Core"))
            registerEntityRendering(EntityMJCreeper.class, "mjcreeper");
    }

    private void registerEntityRendering(Class<? extends EntityBaseCreeper> clazz, String texture) {
        RenderingRegistry.registerEntityRenderingHandler(clazz, new RenderBasicCreeper().setTexture(texture));
    }

    @Override
    public void spawnIllusionCreepers(World worldObj, double posX, double posY, double posZ) {
        super.spawnIllusionCreepers(worldObj, posX, posY, posZ);
    }

    @Override
    public void spawnPortalParticle(World world, double posX, double posY, double posZ, float r, float g, float b) {
        EntityPortalFX effect = new EntityPortalFX(world, posX + 0.5, posY + 0.1 + world.rand.nextDouble() * 2.0D, posZ + 0.5, world.rand.nextGaussian(), 0.0D, world.rand.nextGaussian());
        effect.setRBGColorF(r, g, b);
        Minecraft.getMinecraft().effectRenderer.addEffect(effect);
    }
}
