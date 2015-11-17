package net.lomeli.ec.core;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.registry.GameRegistry;

import net.lomeli.lomlib.core.recipes.ShapedFluidRecipe;

import net.lomeli.ec.ElementalCreepers;
import net.lomeli.ec.core.handler.EventHandler;
import net.lomeli.ec.core.world.SilverBlockGenerator;
import net.lomeli.ec.entity.EntityIllusionCreeper;

public class CommonProxy {
    public EventHandler handler;

    public void registerEvents() {
        handler = new EventHandler();
        MinecraftForge.EVENT_BUS.register(handler);
        GameRegistry.registerWorldGenerator(new SilverBlockGenerator(), 5);
        GameRegistry.addRecipe(new ShapedFluidRecipe(new ItemStack(ElementalCreepers.creepapedia), "GGG", "GBG", "GGG", 'G', Items.gunpowder, 'B', Items.book));
    }

    public void registerRenders() {
    }

    public void spawnIllusionCreepers(World worldObj, double posX, double posY, double posZ) {
        for (int i = 0; i < 4; i++) {
            EntityIllusionCreeper entity = new EntityIllusionCreeper(worldObj);
            entity.split = true;
            entity.illusion = true;
            entity.setPositionAndUpdate(posX, posY, posZ);
            entity.motionY = 0.5F;
            worldObj.spawnEntityInWorld(entity);
        }
    }
}
