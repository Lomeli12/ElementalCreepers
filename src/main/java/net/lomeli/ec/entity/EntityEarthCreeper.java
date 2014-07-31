package net.lomeli.ec.entity;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import net.lomeli.ec.lib.ECVars;

public class EntityEarthCreeper extends EntityBaseCreeper {

    public EntityEarthCreeper(World par1World) {
        super(par1World);
    }

    @Override
    public void explosion(int power, boolean flag) {
        int radius = getPowered() ? (int) (ECVars.earthCreeperRadius * power) : ECVars.earthCreeperRadius;
        for (int x = -radius; x <= radius; x++)
            for (int y = -radius; y <= radius; y++)
                for (int z = -radius; z <= radius; z++) {
                    if (Blocks.dirt.canPlaceBlockAt(worldObj, (int) posX + x, (int) posY + y, (int) posZ + z) && !Blocks.dirt.canPlaceBlockAt(worldObj, (int) posX + x, (int) posY + y - 1, (int) posZ + z)) {
                        if (rand.nextBoolean())
                            worldObj.setBlock((int) posX + x, (int) posY + y, (int) posZ + z, Blocks.dirt);
                    }
                }

    }
}
