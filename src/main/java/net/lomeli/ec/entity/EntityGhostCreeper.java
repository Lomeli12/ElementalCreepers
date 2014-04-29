package net.lomeli.ec.entity;

import net.minecraft.world.World;

import net.lomeli.ec.lib.ECVars;

public class EntityGhostCreeper extends EntityBaseCreeper {

    public EntityGhostCreeper(World par1World) {
        super(par1World);
        this.explosionRadius = ECVars.ghostCreeperRadius;
    }

    @Override
    public void explosion(int power, boolean flag) {
        int exPower = this.explosionRadius * power;
        this.worldObj.createExplosion(this, posX, posY, posZ, exPower, false);
    }

}
