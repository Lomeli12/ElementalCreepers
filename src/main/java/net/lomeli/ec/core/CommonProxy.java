package net.lomeli.ec.core;

import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;

import net.lomeli.ec.entity.EntityIllusionCreeper;

public class CommonProxy {
    public static FakePlayer ecPlayer;

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
}
