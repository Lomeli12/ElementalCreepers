package net.lomeli.ec.entity;

import net.minecraft.block.Block;
import net.minecraft.world.World;

public class EntityMagmaCreeper extends EntityBaseCreeper {

    public EntityMagmaCreeper(World par1World) {
        super(par1World);
        this.isImmuneToFire = true;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if ((int) Math.round(posX + 0.5F) != (int) Math.round(prevPosX + 0.5F) || (int) Math.round(posY) != (int) Math.round(prevPosY)
                || (int) Math.round(posZ + 0.5F) != (int) Math.round(prevPosZ + 0.5F))
            worldObj.setBlock((int) Math.round(prevPosX), (int) Math.round(prevPosY), (int) Math.round(prevPosZ), Block.fire.blockID);
    }

    @Override
    public void explosion(int power, boolean flag) {
        int radius = getPowered() ? (int) (this.explosionRadius * power) : this.explosionRadius;
        for (int x = -radius; x <= radius; x++)
            for (int y = -radius; y <= radius; y++)
                for (int z = -radius; z <= radius; z++) {
                    if (Block.lavaStill.canPlaceBlockAt(worldObj, (int) posX + x, (int) posY + y, (int) posZ + z)
                            && !Block.lavaStill.canPlaceBlockAt(worldObj, (int) posX + x, (int) posY + y - 1, (int) posZ + z)) {
                        if (rand.nextBoolean())
                            worldObj.setBlock((int) posX + x, (int) posY + y, (int) posZ + z, Block.lavaMoving.blockID);
                    }
                }
    }

}
