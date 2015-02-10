package net.lomeli.ec.entity;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import net.lomeli.ec.lib.ECVars;

public class EntityFireCreeper extends EntityBaseCreeper {

    public EntityFireCreeper(World par1World) {
        super(par1World);
        this.isImmuneToFire = true;
    }

    @Override
    public void explosion(int power, boolean flag) {
        int radius = getPowered() ? (ECVars.fireCreeperRadius * power) : ECVars.fireCreeperRadius;
        for (int x = -radius; x <= radius; x++)
            for (int y = -radius; y <= radius; y++)
                for (int z = -radius; z <= radius; z++) {
                    BlockPos pos = new BlockPos((int) posX + x, (int) posY + y, (int) posZ + z);
                    if (Blocks.dirt.canPlaceBlockAt(worldObj, pos) && !Blocks.dirt.canPlaceBlockAt(worldObj, new BlockPos((int) posX + x, (int) posY + y - 1, (int) posZ + z))) {
                        if (rand.nextBoolean()) {
                            if (flag)
                                worldObj.setBlockState(pos, Blocks.fire.getDefaultState());
                            else {
                                List<?> entityList = worldObj.getEntitiesWithinAABB(EntityLivingBase.class,
                                        AxisAlignedBB.fromBounds(posX, posY, posZ, posX + 1.0D, posY + 1.0D, posZ + 1.0D).expand(radius, radius, radius));
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
