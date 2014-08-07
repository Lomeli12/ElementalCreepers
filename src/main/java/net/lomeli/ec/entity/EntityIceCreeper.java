package net.lomeli.ec.entity;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import net.lomeli.ec.lib.ECVars;

public class EntityIceCreeper extends EntityBaseCreeper {

    public EntityIceCreeper(World par1World) {
        super(par1World);
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (!worldObj.isRemote) {
            for (int i = 0; i < 4; i++) {
                if ((int) Math.round(posX + 0.5F) != (int) Math.round(prevPosX + 0.5F) || (int) Math.round(posY) != (int) Math.round(prevPosY)
                        || (int) Math.round(posZ + 0.5F) != (int) Math.round(prevPosZ + 0.5F)) {
                    if (worldObj.isAirBlock((int) Math.round(prevPosX), (int) Math.round(prevPosY), (int) Math.round(prevPosZ))
                            && Blocks.snow_layer.canPlaceBlockAt(worldObj, (int) Math.round(prevPosX), (int) Math.round(prevPosY), (int) Math.round(prevPosZ)))
                        worldObj.setBlock((int) (prevPosX), (int) (prevPosY + 0.5), (int) (prevPosZ), Blocks.snow_layer);
                }
            }
        }
    }

    @Override
    public void explosion(int power, boolean flag) {
        int radius = getPowered() ? (ECVars.iceCreeperRadius * power) : ECVars.iceCreeperRadius;
        for (int x = -radius; x <= radius; x++)
            for (int y = -radius; y <= radius; y++)
                for (int z = -radius; z <= radius; z++) {
                    Block block = worldObj.getBlock((int) posX + x, (int) posY + y, (int) posZ + z);
                    if (block == Blocks.water || block == Blocks.flowing_water)
                        worldObj.setBlock((int) posX + x, (int) posY + y, (int) posZ + z, Blocks.ice);
                    else if (block == Blocks.lava || block == Blocks.flowing_lava)
                        worldObj.setBlock((int) posX + x, (int) posY + y, (int) posZ + z, Blocks.obsidian);
                }
        if (ECVars.domeExplosion)
            this.domeExplosion(radius, Blocks.snow);
        else {
            for (int x = -radius; x <= radius; x++)
                for (int y = -radius; y <= radius; y++)
                    for (int z = -radius; z <= radius; z++) {
                        if (Blocks.dirt.canPlaceBlockAt(worldObj, (int) posX + x, (int) posY + y, (int) posZ + z) && !Blocks.dirt.canPlaceBlockAt(worldObj, (int) posX + x, (int) posY + y - 1, (int) posZ + z)) {
                            if (rand.nextBoolean())
                                worldObj.setBlock((int) posX + x, (int) posY + y, (int) posZ + z, Blocks.snow_layer);
                            else
                                worldObj.setBlock((int) posX + x, (int) posY + y, (int) posZ + z, Blocks.snow);
                        }
                    }
        }
    }

}
