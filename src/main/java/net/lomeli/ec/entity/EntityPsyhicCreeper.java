package net.lomeli.ec.entity;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import net.lomeli.ec.entity.explosion.ExplosionPsychic;
import net.lomeli.ec.lib.ECVars;

public class EntityPsyhicCreeper extends EntityBaseCreeper {

    public EntityPsyhicCreeper(World par1World) {
        super(par1World, false);
    }

    @Override
    public void explosion(int power, boolean flag) {
        int exPower = ECVars.psychicCreeperRadius * power;
        if (!worldObj.isRemote)
            createPsyhicBurst(this, posX, posY, posZ, exPower, true);
    }

    private ExplosionPsychic createPsyhicBurst(Entity entity, double x, double y, double z, float strength, boolean flag) {
        ExplosionPsychic explosion = new ExplosionPsychic(worldObj, entity, x, y, z, strength, ECVars.psychicCreeperPower);
        explosion.isFlaming = false;
        explosion.isSmoking = flag;
        explosion.doExplosionA();
        explosion.doExplosionB(true);
        return explosion;
    }
}
