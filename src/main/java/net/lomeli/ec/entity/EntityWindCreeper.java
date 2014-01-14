package net.lomeli.ec.entity;

import net.lomeli.ec.lib.ECVars;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.world.World;

public class EntityWindCreeper extends EntityBaseCreeper {

    public EntityWindCreeper(World par1World) {
        super(par1World);
        this.explosionRadius = ECVars.windCreeperRadius;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(0.5D);
    }

    @Override
    public void explosion(int power, boolean flag) {
        int exPower = this.explosionRadius * power;
        this.worldObj.createExplosion(this, posX, posY, posZ, (float) exPower, flag);
    }

}
