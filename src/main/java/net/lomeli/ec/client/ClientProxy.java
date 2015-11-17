package net.lomeli.ec.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.item.Item;
import net.minecraft.world.World;

import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;

import net.lomeli.lomlib.client.BasicItemMesh;
import net.lomeli.lomlib.util.RenderUtils;

import net.lomeli.ec.ElementalCreepers;
import net.lomeli.ec.core.CommonProxy;
import net.lomeli.ec.entity.*;
import net.lomeli.ec.entity.render.*;
import net.lomeli.ec.lib.ModLib;

public class ClientProxy extends CommonProxy {
    @Override
    public void registerEvents() {
        super.registerEvents();
        FMLCommonHandler.instance().bus().register(ElementalCreepers.updater);
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
        RenderingRegistry.registerEntityRenderingHandler(EntitySilverCreeper.class, new RenderSilverCreeper());
        registerEntityRendering(EntityFurnaceCreeper.class, "furnacecreeper");

        RenderLiving renderLiving = (RenderLiving) RenderUtils.getEntityRenderer(EntityCreeper.class);
        if (renderLiving != null)
            RenderUtils.addLayerToRenderer(renderLiving, new LayerSpecialEvent(renderLiving));

        registerModel(Item.getItemFromBlock(ElementalCreepers.silverCreepBlock), 0, "stone");
        registerModel(Item.getItemFromBlock(ElementalCreepers.silverCreepBlock), 1, "cobblestone");
        registerModel(Item.getItemFromBlock(ElementalCreepers.silverCreepBlock), 2, "stonebrick");
        registerModel(Item.getItemFromBlock(ElementalCreepers.silverCreepBlock), 3, "mossy_stonebrick");
        registerModel(Item.getItemFromBlock(ElementalCreepers.silverCreepBlock), 4, "cracked_stonebrick");
        registerModel(Item.getItemFromBlock(ElementalCreepers.silverCreepBlock), 5, "chiseled_stonebrick");
        ModelBakery.addVariantName(Item.getItemFromBlock(ElementalCreepers.silverCreepBlock), "stone", "cobblestone", "stonebrick", "mossy_stonebrick", "cracked_stonebrick", "chiseled_stonebrick");
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(ElementalCreepers.creepapedia, new BasicItemMesh(ModLib.MOD_ID.toLowerCase() + ":creepapedia"));
    }

    private void registerModel(Item item, int metaData, String name) {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, metaData, new ModelResourceLocation(name, "inventory"));
    }

    private void registerEntityRendering(Class<? extends EntityBaseCreeper> clazz, String texture) {
        RenderingRegistry.registerEntityRenderingHandler(clazz, new RenderBasicCreeper().setTexture(texture));
    }

    @Override
    public void spawnIllusionCreepers(World worldObj, double posX, double posY, double posZ) {
        super.spawnIllusionCreepers(worldObj, posX, posY, posZ);
    }
}
