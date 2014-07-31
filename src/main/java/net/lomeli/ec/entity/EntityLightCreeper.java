package net.lomeli.ec.entity;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import net.lomeli.ec.lib.ECVars;

public class EntityLightCreeper extends EntityBaseCreeper {

    public EntityLightCreeper(World par1World) {
        super(par1World);
    }

    @Override
    public void explosion(int power, boolean flag) {
        int radius = getPowered() ? (int) (ECVars.lightCreeperRadius * power) : ECVars.lightCreeperRadius;
        for (int x = -radius; x <= radius; x++)
            for (int y = -radius; y <= radius; y++)
                for (int z = -radius; z <= radius; z++) {
                    if (Blocks.glowstone.canPlaceBlockAt(worldObj, (int) posX + x, (int) posY + y, (int) posZ + z)
                            && !Blocks.glowstone.canPlaceBlockAt(worldObj, (int) posX + x, (int) posY + y - 1, (int) posZ + z)) {
                        if (rand.nextBoolean())
                            worldObj.setBlock((int) posX + x, (int) posY + y, (int) posZ + z, Blocks.glowstone);
                    }
                }

    }
}
