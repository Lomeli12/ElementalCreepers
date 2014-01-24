package net.lomeli.ec.entity;

import net.lomeli.ec.entity.explosion.ExplosionPsychic;
import net.lomeli.ec.lib.ECVars;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class EntityPsyhicCreeper extends EntityBaseCreeper{
    
    public EntityPsyhicCreeper(World par1World) {
        super(par1World, false);
        this.explosionRadius = ECVars.psychicCreeperRadius;
    }
    
    @Override
    public void explosion(int power, boolean flag) {
        int exPower = this.explosionRadius * power;
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
