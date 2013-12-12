package net.lomeli.ec.entity;

import net.lomeli.ec.lib.ECVars;

import net.minecraft.block.Block;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class EntityDarkCreeper extends EntityBaseCreeper {

    public EntityDarkCreeper(World par1World) {
        super(par1World);
        this.explosionRadius = ECVars.darkCreeperRadius;
    }

    @Override
    public void explosion(int power, boolean flag) {
        int radius = getPowered() ? (int) (this.explosionRadius * power) : this.explosionRadius;
        for (int x = -radius; x <= radius; x++)
            for (int y = -radius; y <= radius; y++)
                for (int z = -radius; z <= radius; z++) {
                    int id = worldObj.getBlockId((int) posX + x, (int) posY + y, (int) posZ + z);
                    if (Block.lightValue[id] > 0.5F) {
                        Block.blocksList[id].dropBlockAsItem(worldObj, (int) posX + x, (int) posY + y, (int) posZ + z,
                                worldObj.getBlockMetadata((int) posX + x, (int) posY + y, (int) posZ + z), 0);
                        worldObj.setBlock((int) posX + x, (int) posY + y, (int) posZ + z, 0);
                        Block.blocksList[id].onBlockDestroyedByExplosion(worldObj, (int) posX + x, (int) posY + y, (int) posZ + z, new Explosion(worldObj, this, 0.0D, 0.0D, 0.0D,
                                0.0F));
                    }
                }
    }

}
