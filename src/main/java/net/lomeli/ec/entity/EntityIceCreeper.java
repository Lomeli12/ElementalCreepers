package net.lomeli.ec.entity;

import net.lomeli.ec.lib.ECVars;

import net.minecraft.block.Block;
import net.minecraft.world.World;

public class EntityIceCreeper extends EntityBaseCreeper {

    public EntityIceCreeper(World par1World) {
        super(par1World);
        this.explosionRadius = ECVars.iceCreeperRadius;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (!worldObj.isRemote) {
            for (int i = 0; i < 4; i++) {
                if ((int) Math.round(posX + 0.5F) != (int) Math.round(prevPosX + 0.5F) || (int) Math.round(posY) != (int) Math.round(prevPosY)
                        || (int) Math.round(posZ + 0.5F) != (int) Math.round(prevPosZ + 0.5F)) {
                    if (Block.snow.canPlaceBlockAt(worldObj, (int) Math.round(prevPosX), (int) Math.round(prevPosY), (int) Math.round(prevPosZ)))
                        worldObj.setBlock((int) Math.round(prevPosX), (int) Math.round(prevPosY), (int) Math.round(prevPosZ), Block.snow.blockID);
                }
            }
        }
    }

    @Override
    public void explosion(int power, boolean flag) {
        int radius = getPowered() ? (int) (this.explosionRadius * power) : this.explosionRadius;
        for (int x = -radius; x <= radius; x++)
            for (int y = -radius; y <= radius; y++)
                for (int z = -radius; z <= radius; z++) {
                    int id = worldObj.getBlockId((int) posX + x, (int) posY + y, (int) posZ + z);
                    if (id == Block.waterStill.blockID || id == Block.waterMoving.blockID)
                        worldObj.setBlock((int) posX + x, (int) posY + y, (int) posZ + z, Block.ice.blockID);
                    else if (id == Block.lavaStill.blockID || id == Block.lavaMoving.blockID)
                        worldObj.setBlock((int) posX + x, (int) posY + y, (int) posZ + z, Block.obsidian.blockID);

                    if (Block.dirt.canPlaceBlockAt(worldObj, (int) posX + x, (int) posY + y, (int) posZ + z)
                            && !Block.dirt.canPlaceBlockAt(worldObj, (int) posX + x, (int) posY + y - 1, (int) posZ + z)) {
                        if (rand.nextBoolean())
                            worldObj.setBlock((int) posX + x, (int) posY + y, (int) posZ + z, Block.snow.blockID);
                    }
                }
    }

}
