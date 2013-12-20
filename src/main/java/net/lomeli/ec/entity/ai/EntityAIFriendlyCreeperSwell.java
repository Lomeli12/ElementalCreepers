package net.lomeli.ec.entity.ai;

import net.lomeli.ec.entity.EntityFriendlyCreeper;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;

public class EntityAIFriendlyCreeperSwell extends EntityAIBase {

    private EntityFriendlyCreeper swellingCreeper;

    private EntityLiving creeperAttackTarget;

    public EntityAIFriendlyCreeperSwell(EntityFriendlyCreeper par1EntityCreeper) {
        swellingCreeper = par1EntityCreeper;
        setMutexBits(1);
    }

    @Override
    public boolean shouldExecute() {
        EntityLiving var1 = (EntityLiving) swellingCreeper.getAttackTarget();
        return swellingCreeper.getCreeperState() > 0 || var1 != null && swellingCreeper.getDistanceSqToEntity(var1) < 9.0D;
    }

    public void startExecuting() {
        creeperAttackTarget = (EntityLiving) swellingCreeper.getAttackTarget();
    }

    public void resetTask() {
        creeperAttackTarget = null;
    }

    public void updateTask() {
        creeperAttackTarget = (EntityLiving) swellingCreeper.getAttackTarget();
        if (creeperAttackTarget == null)
            swellingCreeper.setCreeperState(-1);
        else if (swellingCreeper.getDistanceSqToEntity(creeperAttackTarget) > 49.0D)
            swellingCreeper.setCreeperState(-1);
        else if (!swellingCreeper.getEntitySenses().canSee(creeperAttackTarget))
            swellingCreeper.setCreeperState(-1);
        else
            swellingCreeper.setCreeperState(1);
    }

}
