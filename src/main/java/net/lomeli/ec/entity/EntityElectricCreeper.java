package net.lomeli.ec.entity;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.IMob;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import net.lomeli.lomlib.util.ObfUtil;

import net.lomeli.ec.lib.ModVars;

public class EntityElectricCreeper extends EntityBaseCreeper {

    public EntityElectricCreeper(World par1World) {
        super(par1World);
    }

    @Override
    public void explosion(int power, boolean flag) {
        int radius = getPowered() ? (int) (ModVars.electricCreeperRadius * 1.5F) : ModVars.electricCreeperRadius;
        List<?> entityList = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.fromBounds(posX, posY, posZ, posX + 1.0D, posY + 1.0D, posZ + 1.0D).expand(radius, radius, radius));
        for (int i = 0; i < entityList.size(); i++) {
            EntityLivingBase entity = (EntityLivingBase) entityList.get(i);
            if (entity != null && !(entity instanceof IMob)) {
                EntityLightningBolt bolt = new EntityLightningBolt(worldObj, entity.posX, entity.posY, entity.posZ);
                worldObj.spawnEntityInWorld(bolt);
            }
        }

        entityList = worldObj.getEntitiesWithinAABB(EntityPigZombie.class, AxisAlignedBB.fromBounds(posX, posY, posZ, posX + 1.0D, posY + 1.0D, posZ + 1.0D).expand(radius, radius, radius));
        for (int i = 0; i < entityList.size(); i++) {
            EntityPigZombie zombie = (EntityPigZombie) entityList.get(i);
            if (zombie != null) {
                ObfUtil.setFieldValue(EntityPigZombie.class, zombie, 400 + rand.nextInt(400), "angerLevel", "field_70837_d");
                zombie.setAttackTarget(this.getAttackTarget());
            }
        }
    }
}
