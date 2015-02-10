package net.lomeli.ec.entity;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.IMob;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import net.lomeli.ec.lib.ECVars;

public class EntityElectricCreeper extends EntityBaseCreeper {

    public EntityElectricCreeper(World par1World) {
        super(par1World);
    }

    @Override
    public void explosion(int power, boolean flag) {
        int radius = getPowered() ? (int) (ECVars.electricCreeperRadius * 1.5F) : ECVars.electricCreeperRadius;
        List<?> entityList = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.fromBounds(posX, posY, posZ, posX + 1.0D, posY + 1.0D, posZ + 1.0D).expand(radius, radius, radius));
        for (int i = 0; i < entityList.size(); i++) {
            EntityLivingBase entity = (EntityLivingBase) entityList.get(i);
            if (entity != null && !(entity instanceof IMob)) {
                EntityLightningBolt bolt = new EntityLightningBolt(worldObj, entity.posX, entity.posY, entity.posZ);
                worldObj.spawnEntityInWorld(bolt);
            }
        }
    }
}
