package net.lomeli.ec.core;

import net.minecraft.world.World;

import net.minecraftforge.common.MinecraftForge;

import net.lomeli.ec.entity.EntityIllusionCreeper;

public class CommonProxy {
    public EventHandler handler;

    public void registerEvents() {
        handler = new EventHandler();
        MinecraftForge.EVENT_BUS.register(handler);
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
