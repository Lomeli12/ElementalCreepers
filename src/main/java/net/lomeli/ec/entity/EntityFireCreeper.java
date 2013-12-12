package net.lomeli.ec.entity;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class EntityFireCreeper extends EntityBaseCreeper {

    public EntityFireCreeper(World par1World) {
        super(par1World);
        this.isImmuneToFire = true;
    }

    @Override
    public void explosion(int power, boolean flag) {
        int radius = getPowered() ? (int) (this.explosionRadius * power) : this.explosionRadius;
        for (int x = -radius; x <= radius; x++)
            for (int y = -radius; y <= radius; y++)
                for (int z = -radius; z <= radius; z++) {
                    if (Block.dirt.canPlaceBlockAt(worldObj, (int) posX + x, (int) posY + y, (int) posZ + z)
                            && !Block.dirt.canPlaceBlockAt(worldObj, (int) posX + x, (int) posY + y - 1, (int) posZ + z)) {
                        if (rand.nextBoolean()) {
                            if (flag)
                                worldObj.setBlock((int) posX + x, (int) posY + y, (int) posZ + z, Block.fire.blockID);
                            else {
                                List<?> entityList = worldObj.getEntitiesWithinAABB(EntityLivingBase.class,
                                        AxisAlignedBB.getBoundingBox(posX, posY, posZ, posX + 1.0D, posY + 1.0D, posZ + 1.0D).expand(radius, radius, radius));
                                for (int i = 0; i < entityList.size(); i++) {
                                    EntityLivingBase entity = (EntityLivingBase) entityList.get(i);
                                    if (entity != null)
                                        entity.setFire(500);
                                }
                            }
                        }
                    }
                }
    }
}
