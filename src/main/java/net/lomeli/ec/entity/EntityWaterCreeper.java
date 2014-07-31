package net.lomeli.ec.entity;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import net.lomeli.ec.lib.ECVars;

public class EntityWaterCreeper extends EntityBaseCreeper {

    public EntityWaterCreeper(World par1World) {
        super(par1World);
    }

    @Override
    public void explosion(int power, boolean flag) {
        int radius = getPowered() ? (int) (ECVars.waterCreeperRadius * power) : ECVars.waterCreeperRadius;
        for (int x = -radius; x <= radius; x++)
            for (int y = -radius; y <= radius; y++)
                for (int z = -radius; z <= radius; z++) {
                    if (Blocks.water.canPlaceBlockAt(worldObj, (int) posX + x, (int) posY + y, (int) posZ + z) && !Blocks.water.canPlaceBlockAt(worldObj, (int) posX + x, (int) posY + y - 1, (int) posZ + z)) {
                        if (rand.nextBoolean())
                            worldObj.setBlock((int) posX + x, (int) posY + y, (int) posZ + z, Blocks.flowing_water);
                    }
                }
    }
}
