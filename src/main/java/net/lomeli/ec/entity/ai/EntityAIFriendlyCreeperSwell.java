package net.lomeli.ec.entity.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIBase;

import net.lomeli.ec.entity.EntityFriendlyCreeper;

public class EntityAIFriendlyCreeperSwell extends EntityAIBase {
    EntityFriendlyCreeper swellingCreeper;

    Entity creeperAttackTarget;

    public EntityAIFriendlyCreeperSwell(EntityFriendlyCreeper par1EntityCreeper) {
        swellingCreeper = par1EntityCreeper;
        setMutexBits(1);
    }

    @Override
    public boolean shouldExecute() {
        Entity var1 = swellingCreeper.getAttackTarget();
        return swellingCreeper.getCreeperState() > 0 || var1 != null && swellingCreeper.getDistanceSqToEntity(var1) < 9.0D;
    }

    @Override
    public void startExecuting() {
        // swellingCreeper.getNavigator().clearPathEntity();
        creeperAttackTarget = swellingCreeper.getAttackTarget();
    }

    @Override
    public void resetTask() {
        creeperAttackTarget = null;
    }

    @Override
    public void updateTask() {
        creeperAttackTarget = swellingCreeper.getAttackTarget();
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
