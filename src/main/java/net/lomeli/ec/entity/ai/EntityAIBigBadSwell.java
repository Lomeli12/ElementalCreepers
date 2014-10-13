package net.lomeli.ec.entity.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;

import net.lomeli.ec.entity.EntityBigBadCreep;

public class EntityAIBigBadSwell extends EntityAIBase {

    protected EntityBigBadCreep swellingCreeper;

    protected EntityLivingBase creeperAttackTarget;

    public EntityAIBigBadSwell(EntityBigBadCreep creeper) {
        this.swellingCreeper = creeper;
        this.setMutexBits(1);
    }

    @Override
    public boolean shouldExecute() {
        EntityLivingBase entitylivingbase = this.swellingCreeper.getAttackTarget();
        return this.swellingCreeper.getCreeperState() > 0 || entitylivingbase != null && this.swellingCreeper.getDistanceSqToEntity(entitylivingbase) < 54.0D;
    }

    @Override
    public void startExecuting() {
        this.swellingCreeper.getNavigator().clearPathEntity();
        this.creeperAttackTarget = this.swellingCreeper.getAttackTarget();
    }

    @Override
    public void resetTask() {
        this.creeperAttackTarget = null;
    }

    @Override
    public void updateTask() {
        if (this.creeperAttackTarget == null)
            this.swellingCreeper.setCreeperState(-1);
        else if (this.swellingCreeper.getDistanceSqToEntity(this.creeperAttackTarget) > 300D)
            this.swellingCreeper.setCreeperState(-1);
        else if (!this.swellingCreeper.getEntitySenses().canSee(this.creeperAttackTarget))
            this.swellingCreeper.setCreeperState(-1);
        else
            this.swellingCreeper.setCreeperState(1);
    }
}
