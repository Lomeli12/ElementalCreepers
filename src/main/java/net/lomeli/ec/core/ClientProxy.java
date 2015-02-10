package net.lomeli.ec.core;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.world.World;

import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;

import net.lomeli.ec.ElementalCreepers;
import net.lomeli.ec.entity.*;
import net.lomeli.ec.entity.render.*;

public class ClientProxy extends CommonProxy {
    @Override
    public void registerEvents() {
        super.registerEvents();
        FMLCommonHandler.instance().bus().register(ElementalCreepers.checker);
        FMLCommonHandler.instance().bus().register(ElementalCreepers.config);
    }

    @Override
    public void registerRenders() {
        super.registerRenders();
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

        RenderLiving renderLiving = (RenderLiving) Minecraft.getMinecraft().getRenderManager().entityRenderMap.get(EntityCreeper.class);
        if (renderLiving != null)
            renderLiving.addLayer(new LayerSpecialEvent(renderLiving));
    }

    private void registerEntityRendering(Class<? extends EntityBaseCreeper> clazz, String texture) {
        RenderingRegistry.registerEntityRenderingHandler(clazz, new RenderBasicCreeper().setTexture(texture));
    }

    @Override
    public void spawnIllusionCreepers(World worldObj, double posX, double posY, double posZ) {
        super.spawnIllusionCreepers(worldObj, posX, posY, posZ);
    }
}
