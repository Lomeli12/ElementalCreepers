package net.lomeli.ec.entity;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import net.lomeli.ec.lib.ECVars;

import net.lomeli.lomlib.entity.EntityUtil;

public class EntityElectricCreeper extends EntityBaseCreeper {

    public EntityElectricCreeper(World par1World) {
        super(par1World);
        this.explosionRadius = ECVars.electricCreeperRadius;
    }

    @Override
    public void explosion(int power, boolean flag) {
        int radius = getPowered() ? (int) (this.explosionRadius * 1.5F) : this.explosionRadius;
        List<?> entityList = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(posX, posY, posZ, posX + 1.0D, posY + 1.0D, posZ + 1.0D).expand(radius, radius, radius));
        for (int i = 0; i < entityList.size(); i++) {
            EntityLivingBase entity = (EntityLivingBase) entityList.get(i);
            if (entity != null && !EntityUtil.isHostileEntity(entity)) {
                EntityLightningBolt bolt = new EntityLightningBolt(worldObj, entity.posX, entity.posY, entity.posZ);
                worldObj.spawnEntityInWorld(bolt);
            }
        }
    }
}
