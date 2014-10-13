package net.lomeli.ec.core;

import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;

import cpw.mods.fml.common.FMLCommonHandler;

import net.lomeli.ec.ElementalCreepers;
import net.lomeli.ec.entity.EntityIllusionCreeper;

public class CommonProxy {
    public static FakePlayer ecPlayer;
    public EventHandler handler;

    public void registerEvents() {
        handler = new EventHandler();
        MinecraftForge.EVENT_BUS.register(handler);
        FMLCommonHandler.instance().bus().register(ElementalCreepers.config);
    }

    public void registerRenders() {
    }

    public FakePlayer getEcPlayer(WorldServer world) {
        if (ecPlayer == null)
            ecPlayer = FakePlayerFactory.getMinecraft(world);
        return ecPlayer;
    }

    public void spawnIllusionCreepers(World worldObj, double posX, double posY, double posZ) {
        for (int i = 0; i < 4; i++) {
            EntityIllusionCreeper entity = new EntityIllusionCreeper(worldObj);
            entity.split = true;
            entity.illusion = true;
            entity.setPositionAndUpdate(posX, posY, posZ);
            entity.motionY = 0.5F;
            worldObj.spawnEntityInWorld(entity);
            entity.onSpawnWithEgg(null);
        }
    }

    public void spawnPortalParticle(World world, double posX, double posY, double posZ, float r, float g, float b) {
    }
}
